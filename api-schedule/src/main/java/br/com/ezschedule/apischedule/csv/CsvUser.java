package br.com.ezschedule.apischedule.csv;

import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CsvUser {
    public static void writeUserFile(ListaObj<Tenant> lista, String fileName)
    {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;
        Path diretorioBase;

        fileName += ".csv";

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos");
        } else {
            diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos");
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

            saida.format("%-10S;%-50S;%-50S;%-50S;%-50S;%-50S;%-20S;\n", "Id", "Nome", "Email", "Cpf", "Numero do Apto", "Bloco", "É sindico?");

            for (int i = 0; i < lista.getTamanho(); i++)
            {
                Tenant tenant = lista.getElemento(i);
                saida.format("%-10d;%-50s;%-50s;%-50s;%-50d;%-50s;%-20b\n",
                        tenant.getIdUser(),
                        tenant.getName(),
                        tenant.getEmail(),
                        tenant.getCpf(),
                        tenant.getApartmentNumber(),
                        tenant.getResidentsBlock(),
                        tenant.isAdmin());
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

    public static ResponseEntity<byte[]> fetchFile(String fileName){

        fileName +=".csv";

        File arquivoBuscado = Path.of(System.getProperty("user.dir") + "/arquivos/" + fileName).toFile();

        if(System.getProperty("os.name").contains("Windows")){
            arquivoBuscado = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos/" + fileName).toFile();
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


    public static void readCsvFile(String nomeArquivo){

        FileReader arq = null;
        Scanner Entrada = null;
        Boolean deuRuim = false;

        nomeArquivo += ".csv";

        //Bloco Try-Catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArquivo);
            Entrada = new Scanner(arq).useDelimiter(";|\\n");
        } catch (FileNotFoundException e){
            System.out.println(e);
            System.exit(1);
        }

        // Bloco Try-Catch para ler o arquivo
        try {
            System.out.printf("%4S %-15S %-9S %4S %4S\n","id","nome","email","numero-apt", "bloco");
            while (Entrada.hasNext()){
                int id = Entrada.nextInt();
                String nome = Entrada.next();
                String email = Entrada.next();
                Integer numeroApt = Entrada.nextInt();
                String bloco = Entrada.next();

                System.out.printf("%4d %-15s %-9s %4d %4s\n", id,nome,email,numeroApt,bloco);
            }
        } catch (NoSuchElementException e){

            System.out.println("Arquivo com problemas");
            deuRuim = true;
            System.exit(1);

        } catch (IllegalStateException e){

            System.out.println("Erro na leitura do arquivo");
            deuRuim = true;

        }
        finally {
            Entrada.close();
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
}
