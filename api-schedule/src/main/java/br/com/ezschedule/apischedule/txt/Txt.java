package br.com.ezschedule.apischedule.txt;

import br.com.ezschedule.apischedule.model.DtoClasses.ServiceImportDTO.ServiceImportDTO;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.ServiceRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public boolean writeTxt(String nameArchive){

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
                    nameService = registro.substring(7,27).trim();
                    System.out.println(nameService);
                    emailUser = registro.substring(72,112).trim();
                    System.out.println(emailUser);

                    Optional<Tenant> tenant = tenantRepository.findByEmail(emailUser);

                    if(tenant.isPresent()){
                        System.out.println("Prestador de serviço existente...");
                        br.com.ezschedule.apischedule.model.Service service = new br.com.ezschedule.apischedule.model.Service();
                        service.setTenant(tenant.get());
                        service.setServiceName(nameService);

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

}
