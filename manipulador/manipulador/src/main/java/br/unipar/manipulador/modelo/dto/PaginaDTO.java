package br.unipar.manipulador.modelo.dto;

import br.unipar.manipulador.modelo.util.FormatarUtil;
import java.util.ArrayList;
import java.util.List;

public class PaginaDTO<T> {
    
    private List<T> conteudo = new ArrayList<>();
    private int numPagina;
    private int tamPagina;
    private long totalElementos;
    
    public PaginaDTO(){ }

    public PaginaDTO(int numPagina, int tamPagina, long totalElementos) {
        this.numPagina = numPagina;
        this.tamPagina = tamPagina;
        this.totalElementos = totalElementos;
    }
    
    public boolean isUltimaPagina() {
        return ((numPagina + 1 ) * tamPagina) >= totalElementos;
    }
    
    public int getPaginaAtual() {
        double pagina = (double) totalElementos / tamPagina;
        return FormatarUtil.arredondaParaCima(pagina);
    }

    public List<T> getConteudo() {
        return conteudo;
    }

    public int getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(int numPagina) {
        this.numPagina = numPagina;
    }

    public int getTamPagina() {
        return tamPagina;
    }

    public void setTamPagina(int tamPagina) {
        this.tamPagina = tamPagina;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }
}
