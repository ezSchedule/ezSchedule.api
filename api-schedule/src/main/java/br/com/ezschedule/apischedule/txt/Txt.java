package br.com.ezschedule.apischedule.txt;

import br.com.ezschedule.apischedule.csv.ListaObj;
import br.com.ezschedule.apischedule.model.DtoClasses.ServiceImportDTO.ServiceImportDTO;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.ServiceRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class Txt {
    @Autowired
    private ServiceRepository repository;
    @Autowired
    private TenantRepository tenantRepository;

    public static String save(MultipartFile file){

        Path diretorioBase;

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos/" + file.getOriginalFilename());
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos/" + file.getOriginalFilename());
        }

        try{
            Files.createDirectories(diretorioBase);
            file.transferTo(diretorioBase.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file.getOriginalFilename();

    }

    public boolean readTxt(String nameArchive){

        BufferedReader entrada = null;

        List<ServiceImportDTO> listService = new ArrayList<>();

        String registro, tipoRegistro, nameService, emailUser;

        int qtdRegDadoGravado;
        int contaRegDadoLido = 0;

        Path diretorioBase;

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos/" + nameArchive);
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos/" + nameArchive);
        }

        try {
            entrada = new BufferedReader(new FileReader(diretorioBase.toString()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {

            registro = entrada.readLine(); // le o primeiro registro do arquivo

            while (registro != null) {
                tipoRegistro = registro.substring(0,2);// obtem os 2 primeiros caracteres do registro
                // substring - primeiro argumento é onde começa a substring dentro da string
                // e o segundo argumento é onde termina a substring + 1
                // Verifica se o tipoRegistro é um header, ou um trailer, ou um registro de dados
                if (tipoRegistro.equals("00")) {
                    System.out.println("é um registro de header");
                    System.out.println("Tipo de arquivo: " + registro.substring(2,15));
                    System.out.println("Data/Hora: " + registro.substring(15,34));
                    System.out.println("Versão do arquivo: " + registro.substring(34,36));
                }
                else if (tipoRegistro.equals("01")) {
                    System.out.println("é um registro de trailer");
                    qtdRegDadoGravado = Integer.parseInt(registro.substring(2,6).trim());
                    if (contaRegDadoLido == qtdRegDadoGravado) {
                        System.out.println("Quantidade de registros lidos compatível com " +
                                "quantidade de registros gravados");
                    }
                    else {
                        System.out.println("Quantidade de registros lidos incompatível com " +
                                "quantidade de registros gravados");
                    }
                }
                else if (tipoRegistro.equals("02")) {
                    System.out.println("é um registro de dados");
                    nameService = registro.substring(2,22).trim();
                    System.out.println(nameService);
                    emailUser = registro.substring(47,87).trim();
                    System.out.println(emailUser);

                    Optional<Tenant> tenant = tenantRepository.findByEmail(emailUser);

                    if(tenant.isPresent()){
                        System.out.println("Prestador de serviço existente...");
                        br.com.ezschedule.apischedule.model.Service service = new br.com.ezschedule.apischedule.model.Service();
                        service.setTenant(tenant.get());
                        service.setServiceName(nameService);

                        System.out.println(service.getServiceName() + service.getId() + service.getTenant().getEmail());

                        repository.save(service);

                    }

                    // contabiliza que leu mais um registro de dados
                    contaRegDadoLido++;
                }
                else {
                    System.out.println("tipo de registro inválido");
                }

                // le o proximo registro do arquivo
                registro = entrada.readLine();
            }
            entrada.close();

            File file = new File(diretorioBase.toString());

            file.delete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public static void saveForExport(String registro, String nomeArq){
        // Grava o registro de header
        BufferedWriter saida = null;

        Path diretorioBase;

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos/");
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos/");
        }

        if(!diretorioBase.toFile().exists()){
            diretorioBase.toFile().mkdir();
        }

        diretorioBase = Path.of(diretorioBase + "/" + nomeArq);

        // try-catch para abrir o arquivo
        try {
            saida = new BufferedWriter(new FileWriter(diretorioBase.toString(), true));
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // try-catch para gravar o registro e finalizar
        try {
            saida.append(registro + "\n");
            saida.close();
        }
        catch (IOException erro) {
            System.out.println("Erro ao gravar no arquivo");
        }
    }

    public String writeTxt(int idCondominium) {

        String nomeArq = "services.txt" + LocalDateTime.now();
        int contaRegistroDado = 0;

        // Monta o registro de header
        String header = "00LISTA DE SERV";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";

        // Grava o registro de header
        saveForExport(header, nomeArq);

        Optional<List<br.com.ezschedule.apischedule.model.Service>> serviceList = Optional.of(repository.listServiceByCondominium(idCondominium));

        if(!serviceList.isPresent()){
            return "not value in list services";
        }

        // Monta e grava os registros de dados ou registros de corpo
        String corpo;

        for (br.com.ezschedule.apischedule.model.Service service : serviceList.get()) {

            corpo = "02";
            corpo += String.format("%-20.20s",service.getServiceName());
            corpo += String.format("%-5.5s",service.getTenant().getId());
            corpo += String.format("%-20.20s",service.getTenant().getName());
            corpo += String.format("%-40.40s",service.getTenant().getEmail());

            saveForExport(corpo, nomeArq);
            contaRegistroDado++;
        }

        // Monta e grava o registro de trailer
        String trailer = "01";
        trailer += String.format("%-5.5s",contaRegistroDado);
        saveForExport(trailer, nomeArq);

        return nomeArq;
    }

    public ResponseEntity<byte[]> searchArchiveTxt(String nomeArquivo){

        File arquivoBuscado;

        if(System.getProperty("os.name").contains("Windows")){
            arquivoBuscado = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos/" + nomeArquivo).toFile();
        } else {
            arquivoBuscado = Path.of(System.getProperty("user.dir") + "/arquivos/" + nomeArquivo).toFile();
        }

        if(!arquivoBuscado.exists()){
            System.out.println("Erro no endereço");
            System.out.println(arquivoBuscado);
        }

        try {
            InputStream fileInputStream = new FileInputStream(arquivoBuscado);
            return ResponseEntity.status(200)
                    .header("Content-Disposition",
                            "attachment; filename=" + arquivoBuscado.getName())
                    .body(fileInputStream.readAllBytes());
        }catch (Exception e){
            return ResponseEntity.status(404).build();
        }
    }

}
