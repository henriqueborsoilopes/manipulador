package br.unipar.manipulador.tela;

import br.unipar.manipulador.modelo.entidade.Pessoa;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MainPessoaTabelaModelo extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    private final List<Pessoa> pessoas;
    private final String[] colunas = {"id", "nome", "idade", "cpf", "rg", "data_nasc", "sexo", "signo", "mae", "pai",	"email", "senha", "cep", "endereco", "numero",	"bairro", "cidade", "estado", "telefone_fixo",	"celular", "altura", "peso", "tipo_sanguineo", "cor"};

    public MainPessoaTabelaModelo(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    @Override
    public int getRowCount() {
        return pessoas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pessoa pessoa = pessoas.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> pessoa.getId();
            case 1 -> pessoa.getNome();
            case 2 -> pessoa.getIdade();
            case 3 -> pessoa.getCpf();
            case 4 -> pessoa.getRg();
            case 5 -> pessoa.getDataNasc();
            case 6 -> pessoa.getCaracteristica().getSexo();
            case 7 -> pessoa.getCaracteristica().getSigno();
            case 8 -> pessoa.getMae();
            case 9 -> pessoa.getPai();
            case 10 -> pessoa.getEmail();
            case 11 -> pessoa.getSenha();
            case 12 -> pessoa.getEndereco().getCep();
            case 13 -> pessoa.getEndereco().getRua();
            case 14 -> pessoa.getEndereco().getNumero();
            case 15 -> pessoa.getEndereco().getBairro();
            case 16 -> pessoa.getEndereco().getCidade();
            case 17 -> pessoa.getEndereco().getEstado();
            case 18 -> pessoa.getTelefones().get(0).getCelular();
            case 19 -> pessoa.getTelefones().get(1).getCelular();
            case 20 -> pessoa.getCaracteristica().getAltura();
            case 21 -> pessoa.getCaracteristica().getPeso();
            case 22 -> pessoa.getCaracteristica().getTipo_sanguineo().getDescricao();
            case 23 -> pessoa.getCaracteristica().getCor();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}
