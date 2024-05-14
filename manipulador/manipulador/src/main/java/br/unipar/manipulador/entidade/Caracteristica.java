package br.unipar.manipulador.entidade;

import br.unipar.manipulador.entidade.enumedo.TipoSanguinio;

public class Caracteristica {
    
    private Long id;
    private String sexo;	
    private String signo;
    private double altura;	
    private double peso;	
    private int tipo_sanguineo;	
    private String cor;
    
    public Caracteristica() { }

    public Caracteristica(Long id, String sexo, String signo, double altura, double peso, TipoSanguinio tipo_sanguineo, String cor) {
        this.id = id;
        this.sexo = sexo;
        this.signo = signo;
        this.altura = altura;
        this.peso = peso;
        this.tipo_sanguineo = tipo_sanguineo.getCodigo();
        this.cor = cor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public TipoSanguinio getTipo_sanguineo() {
        return TipoSanguinio.paraEnum(tipo_sanguineo);
    }

    public void setTipo_sanguineo(TipoSanguinio tipo_sanguineo) {
        this.tipo_sanguineo = tipo_sanguineo.getCodigo();
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
