package br.com.ezschedule.apischedule.csv;

import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.FormatterClosedException;

public class CsvForTenant {
    public static void saveArchiveCsv(ListObject<Tenant> lista, String fileName)
    {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;
        Path diretorioBase;

        fileName += ".csv";

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/files");
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/files");
        }

        if(!diretorioBase.toFile().exists()){
            diretorioBase.toFile().mkdir();
        }

        try {
            arq = new FileWriter(diretorioBase + "/" + fileName);
            saida = new Formatter(arq);

        }catch (IOException e){
            System.out.println("Error to open archive!");
            System.exit(1);
        }

        try {

            saida.format("%S;%S;%S;%S;%S;%S;%B\n", "ID", "NAME", "EMAIL", "N°-APTO", "BLOCO", "N°-PHONE", "IS ADMIN?");

            for (int i = 0; i < lista.getLength(); i++)
            {
                Tenant tenant = lista.getElement(i);
                saida.format("%d;%s;%s;%d;%s;%s;%b\n",
                                                 tenant.getId(),
                                                 tenant.getName(),
                                                 tenant.getEmail(),
                                                 tenant.getApartmentNumber(),
                                                 tenant.getResidentsBlock(),
                                                 tenant.getPhoneNumber(),
                                                 tenant.getIsAdmin());
            }

        }catch (FormatterClosedException e){
            System.out.println("Error writing file");
            deuRuim = true;
        }
        finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException  e)
            {
                System.out.println("Error closing file");
                deuRuim = true;
            }

            if(deuRuim)
            {
                System.exit(1);
            }
        }
    }

    public static ResponseEntity<byte[]> searchArchive(String fileName){

        fileName +=".csv";

        File arquivoBuscado = Path.of(System.getProperty("user.dir") + "/files/" + fileName).toFile();

        if(System.getProperty("os.name").contains("Windows")){
            arquivoBuscado = Path.of(System.getProperty("java.io.tmpdir") + "/files/" + fileName).toFile();
        }

        if(!arquivoBuscado.exists()){
            return ResponseEntity.status(404).build();
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
