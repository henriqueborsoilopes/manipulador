package br.unipar.manipulador.modelo.servico.excecao;

public class ArquivoExcecao extends RuntimeException {
    
    private boolean arquivoExiste;
    
    public ArquivoExcecao(String mensagem, boolean arquivoExiste) {
        super(mensagem);
        this.arquivoExiste = arquivoExiste;
    }

    public boolean isArquivoExiste() {
        return arquivoExiste;
    }

    public void setArquivoExiste(boolean arquivoExiste) {
        this.arquivoExiste = arquivoExiste;
    }
}
