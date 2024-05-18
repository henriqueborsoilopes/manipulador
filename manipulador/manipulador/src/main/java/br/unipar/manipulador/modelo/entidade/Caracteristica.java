package br.unipar.manipulador.modelo.entidade;

import br.unipar.manipulador.modelo.entidade.enumerado.TipoSanguinio;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tb_caracteristica")
public class Caracteristica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sexo;	
    private String signo;
    private double altura;	
    private double peso;	
    private int tipo_sanguineo;	
    private String cor;
    
    @OneToOne
    private Pessoa pessoa;
    
    public Caracteristica() { }

    public Caracteristica(Long id, String sexo, String signo, double altura, double peso, TipoSanguinio tipo_sanguineo, String cor, Pessoa pessoa) {
        this.id = id;
        this.sexo = sexo;
        this.signo = signo;
        this.altura = altura;
        this.peso = peso;
        this.tipo_sanguineo = tipo_sanguineo.getCodigo();
        this.cor = cor;
        this.pessoa = pessoa;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Caracteristica other = (Caracteristica) obj;
        return Objects.equals(this.id, other.id);
    }
}
