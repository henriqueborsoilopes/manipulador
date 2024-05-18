package br.unipar.manipulador.modelo.entidade.enumerado;

public enum TipoTelefone {
    
    TELEFONE_FIXO(1, "Telefone fixo"),
    CELULAR(2, "Celular");
    
    private int codigo;
    private String descricao;
    
    public static TipoTelefone paraEnum(int codigo) {
        if (codigo == 0) {
            return null;
        }
        
        for (TipoTelefone tipo : TipoTelefone.values()) {
            if (tipo.codigo == codigo) {
                return tipo;
            }
        }
        
        throw new IllegalArgumentException("Código inválido. Código: " + codigo);
    }
    
    private TipoTelefone(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
