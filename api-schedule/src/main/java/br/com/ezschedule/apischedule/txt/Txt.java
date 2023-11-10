package br.com.ezschedule.apischedule.txt;

import br.com.ezschedule.apischedule.model.DtoClasses.ServiceImportDTO.ServiceImportDTO;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.observer.PileObject;
import br.com.ezschedule.apischedule.repository.ServiceRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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

    private PileObject<File> pilhaOfPath = new PileObject<>(100);

    public static String save(MultipartFile file){

        Path diretorioBase;

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/files/" + file.getOriginalFilename());
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/files/" + file.getOriginalFilename());
        }

        try{
            Files.createDirectories(diretorioBase);
            file.transferTo(diretorioBase.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file.getOriginalFilename();

    }

    public boolean readTxt(String fileName){

        BufferedReader entrada = null;

        List<ServiceImportDTO> listService = new ArrayList<>();

        String registro, tipoRegistro, nameService, emailUser;

        int qtdRegDadoGravado;
        int contaRegDadoLido = 0;

        Path diretorioBase;

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/files/" + fileName);
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/files/" + fileName);
        }

        try {
            entrada = new BufferedReader(new FileReader(diretorioBase.toString()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {

            registro = entrada.readLine(); // le o primeiro registro do arquivo

            while (registro != null) {
                tipoRegistro = registro.substring(0,2);
                if (tipoRegistro.equals("00")) {
                    System.out.println("write header");
                }
                else if (tipoRegistro.equals("01")) {
                    System.out.println("This is trailer");
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
                    System.out.println("This is data");
                    nameService = registro.substring(2,22).trim();
                    System.out.println(nameService);
                    emailUser = registro.substring(47,87).trim();
                    System.out.println(emailUser);

                    Optional<Tenant> tenant = tenantRepository.findByEmail(emailUser);

                    if(tenant.isPresent()){
                        System.out.println("Service not found...");
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
                    System.out.println("file type invalid");
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
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/files/");
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/files/");
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
            System.out.println("Error to open file");
            System.exit(1);
        }

        // try-catch para gravar o registro e finalizar
        try {
            saida.append(registro + "\n");
            saida.close();
        }
        catch (IOException erro) {
            System.out.println("Error to save file");
        }
    }

    public String writeTxt(int idCondominium) {

        String nomeArq = "services" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm-ss")) + ".txt";
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

    public ResponseEntity<byte[]> searchArchiveTxt(String fileName){

        File pathBase;

        if(System.getProperty("os.name").contains("Windows")){
            pathBase = Path.of(System.getProperty("java.io.tmpdir") + "/files/" + fileName).toFile();
        } else {
            pathBase = Path.of(System.getProperty("user.dir") + "/files/" + fileName).toFile();
        }

        if(!pathBase.exists()){
            System.out.println("Address error");
            System.out.println(pathBase);
        }

        pilhaOfPath.push(pathBase);

        try {
            InputStream fileInputStream = new FileInputStream(pathBase);
            return ResponseEntity.status(200)
                    .header("Content-Disposition",
                            "attachment; filename=" + pathBase.getName())
                    .body(fileInputStream.readAllBytes());
        }catch (Exception e){
            return ResponseEntity.status(404).build();
        }
    }

    @Scheduled(fixedDelay = 300000)
    public void excludeFiles(){
        if(!pilhaOfPath.isEmpty()){
            while (!pilhaOfPath.isEmpty()){
                System.out.println("drop file");
                File file = pilhaOfPath.pop();
                file.delete();
            }
        }
        System.out.println("Pile is empty");
        System.out.println("Finish schedule");
    }
}
