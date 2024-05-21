package br.unipar.manipulador.tela;

import br.unipar.manipulador.modelo.entidade.Pessoa;
import br.unipar.manipulador.modelo.repositorio.PessoaRepositorio;
import br.unipar.manipulador.modelo.servico.PessoaServico;
import br.unipar.manipulador.modelo.dto.PaginaDTO;
import br.unipar.manipulador.modelo.infra.ConexaoBD;
import br.unipar.manipulador.modelo.servico.excecao.ArquivoExcecao;
import br.unipar.manipulador.modelo.util.ArquivoUtil;
import br.unipar.manipulador.modelo.util.BarraProgresso;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

public class MainControlador extends javax.swing.JFrame {

    private String pesquisarNome = "";

    private PaginaDTO<Pessoa> pagina = new PaginaDTO<>(0, 22, 0);

    public MainControlador() {
        ConexaoBD.getEntityManagerFactory();
        initComponents();
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tabelaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        carregarTabela();
                
        JMenuBar menuBar = new JMenuBar();

        JMenu menuPessoa = new JMenu("Pessoa");

        JMenuItem itemImporta = new JMenuItem("Importar CSV");
        JMenuItem itemExporta = new JMenuItem("Exportar CSV");

        menuPessoa.add(itemImporta);
        menuPessoa.add(itemExporta);

        menuBar.add(menuPessoa);

        setJMenuBar(menuBar);

        itemImporta.addActionListener((ActionEvent e) -> {
            abrirArquivo();
        });

        itemExporta.addActionListener((ActionEvent e) -> {
            escolherDiretorio();
        });

        tabelaClientes.setRowHeight(29);
        txtNumPagina.setText(String.valueOf(pagina.getNumPagina() + 1));
        btAnterior.setText("← " + String.valueOf(pagina.getNumPagina() + 1));
        btAnterior.setEnabled(false);
        btProximo.setText(pagina.getPaginaAtual() + " →");

        txtPesquisar.requestFocus();
        txtPesquisar.addKeyListener(keyPressed());

        btAnterior.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaAnterior();
            }
        });

        btProximo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                proximaPagina();
            }
        });

        btPesquisar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pesquisar();
            }
        });
    }

    private KeyAdapter keyPressed() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER ->
                        pesquisar();
                    case KeyEvent.VK_LEFT ->
                        paginaAnterior();
                    case KeyEvent.VK_RIGHT ->
                        proximaPagina();
                }
            }
        };
    }

    public void paginaAnterior() {
        if (btAnterior.isEnabled()) {
            int numPg = pagina.getNumPagina();
            pagina.setNumPagina(numPg -= 1);
            txtNumPagina.setText(String.valueOf(pagina.getNumPagina() + 1));
            if (pagina.getNumPagina() >= 1) {
                btAnterior.setText("← " + String.valueOf(pagina.getNumPagina()));
            }
            btProximo.setEnabled(true);
            carregarTabela();
            if (pagina.getNumPagina() < 1) {
                btAnterior.setEnabled(false);
            }
        }
    }

    public void proximaPagina() {
        if (btProximo.isEnabled()) {
            btAnterior.setEnabled(true);
            int numPg = pagina.getNumPagina();
            pagina.setNumPagina(numPg += 1);
            txtNumPagina.setText(String.valueOf(pagina.getNumPagina() + 1));
            if (pagina.getNumPagina() >= 2) {
                btAnterior.setText("← " + String.valueOf(pagina.getNumPagina()));
            }
            carregarTabela();
            if (pagina.isUltimaPagina()) {
                btProximo.setEnabled(false);
            }
        }
    }

    public void pesquisar() {
        pesquisarNome = txtPesquisar.getText();
        pagina.setNumPagina(0);
        carregarTabela();
        txtNumPagina.setText(String.valueOf(pagina.getNumPagina() + 1));
        btAnterior.setText("← " + String.valueOf(pagina.getNumPagina() + 1));
        btAnterior.setEnabled(false);
        if (pagina.isUltimaPagina()) {
            btProximo.setEnabled(false);
            btProximo.setText("1 →");
        } else {
            btProximo.setEnabled(true);
            btProximo.setText(String.valueOf(pagina.getPaginaAtual()) + " →");
        }
        txtPesquisar.setText("");
    }

    private void carregarTabela() {
        pagina = new PessoaServico(new PessoaRepositorio()).acharTodosPaginado(pesquisarNome, pagina.getNumPagina(), pagina.getTamPagina());
        MainPessoaTabelaModelo modelo = new MainPessoaTabelaModelo(pagina.getConteudo());
        tabelaClientes.setModel(modelo);
        for (int columnIndex = 0; columnIndex < tabelaClientes.getColumnCount(); columnIndex++) {
            tabelaClientes.getColumnModel().getColumn(columnIndex).setPreferredWidth(150);
            tabelaClientes.getColumnModel().getColumn(columnIndex).setCellRenderer(new DefaultTableCellRenderer());
            tabelaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tabelaClientes.doLayout();
            int width = (int) tabelaClientes.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(tabelaClientes, tabelaClientes.getColumnModel().getColumn(columnIndex).getHeaderValue(), false, false, -1, columnIndex)
                    .getPreferredSize().getWidth();
            for (int row = 0; row < tabelaClientes.getRowCount(); row++) {
                int preferedWidth = (int) tabelaClientes.getCellRenderer(row, columnIndex)
                        .getTableCellRendererComponent(tabelaClientes, tabelaClientes.getValueAt(row, columnIndex), false, false, row, columnIndex)
                        .getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            tabelaClientes.getColumnModel().getColumn(columnIndex).setPreferredWidth(width + 20);
        }
    }

    public void abrirArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Arquivos", "csv"));

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            BarraProgresso barra = new BarraProgresso(this);
            
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        PessoaServico servico = new PessoaServico(new PessoaRepositorio());
                        for (Pessoa pessoa : servico.csvParaEntidade(arquivo)) {
                            servico.inserir(pessoa);
                        }
                        JOptionPane.showConfirmDialog(MainControlador.this,"Arquivo importado com sucesso!", "Confimação", JOptionPane.OK_OPTION);
                    } catch (HeadlessException e) {
                        JOptionPane.showMessageDialog(MainControlador.this,
                                "Erro ao processar o arquivo: " + e.getMessage(),
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    barra.dispose();
                }
            };
            
            worker.execute();
            barra.setVisible(true);
            carregarTabela();
        }
    }

    public void escolherDiretorio() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedDir = fileChooser.getSelectedFile();
            BarraProgresso barra = new BarraProgresso(this);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        File pasta = ArquivoUtil.gerarArquivoCSV(selectedDir, false);
                        new PessoaServico(new PessoaRepositorio()).gerarArquivoCSV(pagina.getConteudo(), pasta);
                        JOptionPane.showConfirmDialog(MainControlador.this, "Arquivo exportado com sucesso!", "Confimação", JOptionPane.OK_OPTION);
                    } catch (ArquivoExcecao e) {
                        if (e.isArquivoExiste()) {
                            int resposta = JOptionPane.showConfirmDialog(MainControlador.this, e.getMessage(), "Aviso", JOptionPane.OK_CANCEL_OPTION);
                            if (resposta == JOptionPane.OK_OPTION) {
                                File pasta = ArquivoUtil.gerarArquivoCSV(selectedDir, true);
                                try {
                                    new PessoaServico(new PessoaRepositorio()).gerarArquivoCSV(pagina.getConteudo(), pasta);
                                    JOptionPane.showConfirmDialog(MainControlador.this, e.getMessage(), "Arquivo exportado com sucesso!", JOptionPane.OK_OPTION);
                                } catch (HeadlessException ex) {
                                    JOptionPane.showMessageDialog(MainControlador.this,"Erro ao processar o arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showConfirmDialog(MainControlador.this, e.getMessage(), "Erro", JOptionPane.OK_OPTION);
                        }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    barra.dispose();
                }
            };

            worker.execute();
            barra.setVisible(true);
        }
    }

    @Override
    public void dispose() {
        int resposta = JOptionPane.showConfirmDialog(MainControlador.this, "Deseja realmente fechar a janela?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            ConexaoBD.closeEntityManagerFactory();
            super.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtPesquisar = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaClientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btAnterior = new javax.swing.JButton();
        txtNumPagina = new javax.swing.JTextField();
        btProximo = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(900, 500));
        setResizable(false);

        background.setBackground(new java.awt.Color(51, 51, 51));
        background.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtPesquisar.setToolTipText("");
        txtPesquisar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btPesquisar.setBackground(new java.awt.Color(0, 0, 102));
        btPesquisar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        btPesquisar.setText("Pesquisar (Enter)");
        btPesquisar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(txtPesquisar))
                .addContainerGap())
        );

        tabelaClientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabelaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelaClientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelaClientes.setRowHeight(40);
        tabelaClientes.setShowGrid(true);
        tabelaClientes.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(tabelaClientes);
        tabelaClientes.getAccessibleContext().setAccessibleDescription("");

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        btAnterior.setBackground(new java.awt.Color(0, 0, 102));
        btAnterior.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAnterior.setForeground(new java.awt.Color(255, 255, 255));
        btAnterior.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btAnterior.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtNumPagina.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNumPagina.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumPagina.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNumPagina.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNumPagina.setEnabled(false);

        btProximo.setBackground(new java.awt.Color(0, 0, 102));
        btProximo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btProximo.setForeground(new java.awt.Color(255, 255, 255));
        btProximo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btProximo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btProximo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNumPagina, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btProximo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btAnterior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton btAnterior;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JToggleButton btProximo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaClientes;
    private javax.swing.JTextField txtNumPagina;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
