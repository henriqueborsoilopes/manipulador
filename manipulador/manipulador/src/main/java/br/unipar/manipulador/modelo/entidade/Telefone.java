package br.unipar.manipulador.modelo.entidade;

import br.unipar.manipulador.modelo.entidade.enumerado.TipoTelefone;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tb_telefone")
public class Telefone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int tipoTelefone;	
    private String celular;
    
    @ManyToOne
    private Pessoa pessoa;
    
    public Telefone() { }    

    public Telefone(Long id, TipoTelefone tipoTelefone, String celular, Pessoa pessoa) {
        this.id = id;
        this.tipoTelefone = tipoTelefone.getCodigo();
        this.celular = celular;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTelefone getTipoTelefone() {
        return TipoTelefone.paraEnum(tipoTelefone);
    }

    public void setTipoTelefone(TipoTelefone tipoTelefone) {
        this.tipoTelefone = tipoTelefone.getCodigo();
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Telefone other = (Telefone) obj;
        return Objects.equals(this.id, other.id);
    }
}
