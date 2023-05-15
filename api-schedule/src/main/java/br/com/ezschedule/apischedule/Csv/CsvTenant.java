package br.com.ezschedule.apischedule.Csv;

import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.FormatterClosedException;

public class CsvTenant {
    public static void gravaArquivoCsvTenant(ListaObj<Tenant> lista, String nomeArquivo)
    {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;
        Path diretorioBase;

        nomeArquivo += ".csv";

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos");
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos");
        }

        if(!diretorioBase.toFile().exists()){
            diretorioBase.toFile().mkdir();
        }

        try {
            arq = new FileWriter(diretorioBase + "/" + nomeArquivo);
            saida = new Formatter(arq);

        }catch (IOException e){
            System.out.println("Erro ao abrir o arquivo!");
            System.exit(1);
        }

        try {

            for (int i = 0; i < lista.getTamanho(); i++)
            {
                Tenant tenant = lista.getElemento(i);
                saida.format("%d;%s;%s;%d;%s\n", tenant.getIdUser(), tenant.getName(), tenant.getEmail(), tenant.getApartmentNumber(), tenant.getResidentsBlock());
            }

        }catch (FormatterClosedException e){
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        }
        finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException  e)
            {
                System.out.println("Erro ao fechar o arquivo!");
                deuRuim = true;
            }

            if(deuRuim)
            {
                System.exit(1);
            }
        }
    }

    public static ResponseEntity<byte[]> buscarArquivo(String nomeArquivo){

        nomeArquivo +=".csv";

        File arquivoBuscado = Path.of(System.getProperty("user.dir") + "/arquivos/" + nomeArquivo).toFile();

        if(System.getProperty("os.name").contains("Windows")){
            arquivoBuscado = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos/" + nomeArquivo).toFile();
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
