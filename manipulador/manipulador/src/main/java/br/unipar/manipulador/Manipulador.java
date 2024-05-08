package br.unipar.manipulador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Manipulador {

    public static void main(String[] args) {
        /*Cria arquivo*/
        File pasta = new File("C:\\Users\\00240508\\manipulador");
        if (pasta.isDirectory()) {
            System.out.println("O pasta existe!");
        } else {
            System.out.println("O pasta não existe.");
            pasta.mkdir();
            System.out.println("Pasta criado com sucesso!");
        }
        /*Cria arquivo*/
        File arquivo = new File(pasta, "meu_arquivo.txt");
        if (arquivo.exists()) {
            System.out.println("O arquivo existe!");
        } else {
            System.out.println("O arquivo não existe.");
            try {
                arquivo.createNewFile();
                System.out.println("Arquivo criado com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }
        /*Escreve arquivo*/
        try (FileWriter escritor = new FileWriter("C:\\Users\\00240508\\manipulador\\meu_arquivo.txt");
             BufferedWriter buffer = new BufferedWriter(escritor)) {
            for (int i = 0; i <= 10; i++) {
                buffer.write(i + " x 9 = " + (i * 9));
                buffer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
        /*Ler arquivo*/
        try (FileReader leitor = new FileReader("C:\\Users\\00240508\\manipulador\\meu_arquivo.txt");
             BufferedReader buffer = new BufferedReader(leitor)) {
            String linha;
            while ((linha = buffer.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        /*Cria arquivo*/
        File arquivo2 = new File("C:\\Users\\00240508\\manipulador\\meu_arquivo.txt");
        if (arquivo2.exists()) {
            arquivo2.delete();
            System.out.println("Arquivo deletado com sucesso!");
        }
    }
}
