package br.unipar.manipulador.entidade;

import br.unipar.manipulador.entidade.enumedo.TipoTelefone;
import java.util.Objects;

public class Telefone {
    
    private Long id;
    private int tipoTelefone;	
    private String celular;
    
    public Telefone() { }    

    public Telefone(Long id, TipoTelefone tipoTelefone, String celular) {
        this.id = id;
        this.tipoTelefone = tipoTelefone.getCodigo();
        this.celular = celular;
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
