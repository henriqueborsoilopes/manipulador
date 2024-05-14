package br.unipar.manipulador;

import br.unipar.manipulador.entidade.Caracteristica;
import br.unipar.manipulador.entidade.Endereco;
import br.unipar.manipulador.entidade.Pessoa;
import br.unipar.manipulador.entidade.Telefone;
import br.unipar.manipulador.entidade.enumedo.TipoSanguinio;
import br.unipar.manipulador.entidade.enumedo.TipoTelefone;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Manipulador {

    public static void main(String[] args) {
//        /*Cria arquivo*/
//        File pasta = new File("C:\\Users\\00240508\\manipulador");
//        if (pasta.isDirectory()) {
//            System.out.println("O pasta existe!");
//        } else {
//            System.out.println("O pasta não existe.");
//            pasta.mkdir();
//            System.out.println("Pasta criado com sucesso!");
//        }
//        /*Cria arquivo*/
//        File arquivo = new File(pasta, "meu_arquivo.txt");
//        if (arquivo.exists()) {
//            System.out.println("O arquivo existe!");
//        } else {
//            System.out.println("O arquivo não existe.");
//            try {
//                arquivo.createNewFile();
//                System.out.println("Arquivo criado com sucesso!");
//            } catch (IOException e) {
//                System.out.println("Erro ao criar o arquivo: " + e.getMessage());
//            }
//        }
//        /*Escreve arquivo*/
//        try (FileWriter escritor = new FileWriter("C:\\Users\\00240508\\manipulador\\meu_arquivo.txt");
//             BufferedWriter buffer = new BufferedWriter(escritor)) {
//            for (int i = 0; i <= 10; i++) {
//                buffer.write(i + " x 9 = " + (i * 9));
//                buffer.newLine();
//            }
//        } catch (IOException e) {
//            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
//        }
//        /*Cria arquivo*/
//        File arquivo2 = new File("C:\\Users\\00240508\\manipulador\\meu_arquivo.txt");
//        if (arquivo2.exists()) {
//            arquivo2.delete();
//            System.out.println("Arquivo deletado com sucesso!");
//        }
        /*Ler arquivo*/
        try (BufferedReader buffer = new BufferedReader(new FileReader("C:\\Users\\00240508\\Projetos\\pessoa.csv"))) {
            String[] colunas;
            String linha;
            List<Pessoa> pessoas = new ArrayList<>();
            buffer.readLine();
            while ((linha = buffer.readLine())!= null) {
                colunas = linha.split(",");
                Pessoa pessoa = new Pessoa();
                List<Telefone> telefones = new ArrayList<>();
                Endereco endereco = new Endereco();
                Caracteristica caracteristica = new Caracteristica();

                pessoa.setNome(colunas[0]);
                pessoa.setIdade(Integer.parseInt(colunas[1]));
                pessoa.setCpf(colunas[2]);
                pessoa.setRg(colunas[3]);
                pessoa.setData_nasc(LocalDate.parse(colunas[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                
                caracteristica.setSexo(colunas[5]);
                caracteristica.setSigno(colunas[6]);
                
                pessoa.setMae(colunas[7]);
                pessoa.setPai(colunas[8]);
                pessoa.setEmail(colunas[9]);
                pessoa.setSenha(colunas[10]);
                
                endereco.setCep(colunas[11]);
                endereco.setRua(colunas[12]);
                endereco.setNumero(colunas[13]);
                endereco.setBairro(colunas[14]);
                endereco.setCidade(colunas[15]);
                endereco.setEstado(colunas[16]);
                
                telefones.add(new Telefone(1L, TipoTelefone.TELEFONE_FIXO, colunas[17]));
                telefones.add(new Telefone(2L, TipoTelefone.CELULAR, colunas[18]));
                
                pessoa.getTelefones().addAll(telefones);
                
                caracteristica.setAltura(Double.parseDouble(colunas[19]));
                caracteristica.setPeso(Double.parseDouble(colunas[20]));
                caracteristica.setTipo_sanguineo(TipoSanguinio.paraEnum(colunas[21]));
                caracteristica.setCor(colunas[22]);
                
                pessoas.add(pessoa);
            }
            
            System.out.println(pessoas.get(0).getNome());
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }
}
