package br.unipar.manipulador.modelo.servico;

import br.unipar.manipulador.modelo.repositorio.PessoaRepositorio;
import br.unipar.manipulador.modelo.dto.PaginaDTO;
import br.unipar.manipulador.modelo.entidade.Caracteristica;
import br.unipar.manipulador.modelo.entidade.Endereco;
import br.unipar.manipulador.modelo.entidade.Pessoa;
import br.unipar.manipulador.modelo.entidade.Telefone;
import br.unipar.manipulador.modelo.entidade.enumerado.TipoSanguinio;
import br.unipar.manipulador.modelo.entidade.enumerado.TipoTelefone;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PessoaServico {
    
    private final PessoaRepositorio repository;
    
    public PessoaServico(PessoaRepositorio repository) {
        this.repository = repository;
    }
    
    public Pessoa inserir(Pessoa pessoa) {
        return repository.inserir(pessoa);
    }
    
    public PaginaDTO<Pessoa> acharTodosPaginado(String nome, int numPagina, int tamPagina) {
        return repository.acharTodosPaginado(nome, numPagina, tamPagina);
    }
    
    public List<Pessoa> csvParaEntidade(File file) {
        List<Pessoa> pessoas = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
            PessoaServico pessoaServico = new PessoaServico(new PessoaRepositorio());
            String[] colunas;
            String linha;
            buffer.readLine();
            while ((linha = buffer.readLine())!= null) {
                colunas = linha.split(",");
                Pessoa pessoa = new Pessoa();
                List<Telefone> telefones = new ArrayList<>();
                Endereco endereco = new Endereco();
                Caracteristica caracteristica = new Caracteristica();
                
                pessoa.setId(null);
                pessoa.setNome(colunas[0]);
                pessoa.setIdade(Integer.parseInt(colunas[1]));
                pessoa.setCpf(colunas[2]);
                pessoa.setRg(colunas[3]);
                pessoa.setDataNasc(LocalDate.parse(colunas[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                
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
                endereco.setPessoa(pessoa);
                
                pessoa.setEndereco(endereco);
                
                telefones.add(new Telefone(null, TipoTelefone.TELEFONE_FIXO, colunas[17], pessoa));
                telefones.add(new Telefone(null, TipoTelefone.CELULAR, colunas[18], pessoa));
                
                pessoa.getTelefones().addAll(telefones);
                
                caracteristica.setAltura(Double.parseDouble(colunas[19]));
                caracteristica.setPeso(Double.parseDouble(colunas[20]));
                caracteristica.setTipo_sanguineo(TipoSanguinio.paraEnum(colunas[21]));
                caracteristica.setCor(colunas[22]);
                
                caracteristica.setPessoa(pessoa);
                
                pessoa.setCaracteristica(caracteristica);
                
                pessoaServico.inserir(pessoa);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        
        return pessoas;
    }
    
    public void gerarArquivoCSV(List<Pessoa> pessoas, File arquivo) {
        try (FileWriter fw = new FileWriter(arquivo);
             BufferedWriter buffer = new BufferedWriter(fw)) {
            for (Pessoa pessoa : pessoas) {   
                buffer.write(pessoa.getId().toString() + ";");
                buffer.write(pessoa.getNome() + ";");
                buffer.write(pessoa.getIdade() + ";");
                buffer.write(pessoa.getCpf() + ";");
                buffer.write(pessoa.getRg() + ";");
                buffer.write(pessoa.getDataNasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ";");
                buffer.write(pessoa.getCaracteristica().getSexo() + ";");
                buffer.write(pessoa.getCaracteristica().getSigno() + ";");
                buffer.write(pessoa.getMae() + ";");
                buffer.write(pessoa.getPai() + ";");
                buffer.write(pessoa.getEmail() + ";");
                buffer.write(pessoa.getSenha() + ";");
                buffer.write(pessoa.getEndereco().getCep() + ";");
                buffer.write(pessoa.getEndereco().getRua() + ";");
                buffer.write(pessoa.getEndereco().getNumero() + ";");
                buffer.write(pessoa.getEndereco().getBairro() + ";");
                buffer.write(pessoa.getEndereco().getCidade() + ";");
                buffer.write(pessoa.getEndereco().getEstado() + ";");
                buffer.write(pessoa.getTelefones().get(0).getCelular() + ";");
                buffer.write(pessoa.getTelefones().get(1).getCelular() + ";");
                buffer.write(String.valueOf(pessoa.getCaracteristica().getAltura()) + ";");
                buffer.write(String.valueOf(pessoa.getCaracteristica().getPeso()) + ";");
                buffer.write(pessoa.getCaracteristica().getTipo_sanguineo().getDescricao() + ";");
                buffer.write(pessoa.getCaracteristica().getCor() + ";");
                buffer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}
