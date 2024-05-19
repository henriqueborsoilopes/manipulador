package br.unipar.manipulador.modelo.util;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class BarraProgresso extends JDialog {
    private final JProgressBar progressBar;

    public BarraProgresso(JFrame parent) {
        super(parent, "Processando...", true);
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        add(progressBar);
        setSize(300, 75);
        setLocationRelativeTo(parent);
    }
}
