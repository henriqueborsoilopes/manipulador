package br.unipar.manipulador.modelo.util;

import java.io.File;
import java.io.IOException;

public class ArquivoUtil {
    
    public static File gerarPasta() {
        File pasta = new File("C:\\Projetos\\manipulador");
        if (!pasta.exists() && !pasta.isDirectory()) {
            pasta.mkdir();
        }
        return pasta;
    }
    
    public static File gerarArquivoCSV(File pasta) {
        File arquivo = new File(pasta.getPath(), "dados.csv");
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }
        return arquivo;
    }
}
