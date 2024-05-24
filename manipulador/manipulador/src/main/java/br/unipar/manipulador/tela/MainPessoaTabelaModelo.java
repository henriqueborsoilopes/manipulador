package br.unipar.manipulador.tela;

import br.unipar.manipulador.modelo.entidade.Pessoa;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

public class MainPessoaTabelaModelo extends AbstractTableModel {

    private final List<Pessoa> pessoas;
    private final String[] colunas = {"ID", "Nome", "Idade", "CPF", "RG", "Nascimento", "Sexo", "Signo", "Mãe", "Pai", "Email", "Senha", "CEP", "Endereço", "Número", "Bairro", "Cidade", "Estado", "Telefone", "Celular", "Altura", "Peso", "Tipo Sanguíneo", "Cor"};
    private final Class<?>[] types = {Integer.class, String.class, Integer.class, String.class, String.class, Object.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Double.class, Double.class, String.class, String.class};
    private final boolean[] canEdit = {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};

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
    
    public int getColumnAlignment(int columnIndex) {
        
        return switch (columnIndex) {
            case 0 -> SwingConstants.CENTER;
            case 1 -> SwingConstants.CENTER;
            case 2 -> SwingConstants.CENTER;
            case 3 -> SwingConstants.CENTER;
            case 4 -> SwingConstants.CENTER;
            case 5 -> SwingConstants.CENTER;
            case 6 -> SwingConstants.CENTER;
            case 7 -> SwingConstants.CENTER;
            case 8 -> SwingConstants.CENTER;
            case 9 -> SwingConstants.CENTER;
            case 10 -> SwingConstants.CENTER;
            case 11 -> SwingConstants.CENTER;
            case 12 -> SwingConstants.CENTER;
            case 13 -> SwingConstants.CENTER;
            case 14 -> SwingConstants.CENTER;
            case 15 -> SwingConstants.CENTER;
            case 16 -> SwingConstants.CENTER;
            case 17 -> SwingConstants.CENTER;
            case 18 -> SwingConstants.CENTER;
            case 19 -> SwingConstants.CENTER;
            case 20 -> SwingConstants.CENTER;
            case 21 -> SwingConstants.CENTER;
            case 22 -> SwingConstants.CENTER;
            case 23 -> SwingConstants.CENTER;
            default -> SwingConstants.CENTER;
        };
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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }
}

