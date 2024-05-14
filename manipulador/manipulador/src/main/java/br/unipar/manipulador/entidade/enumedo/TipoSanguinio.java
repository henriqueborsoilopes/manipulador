package br.unipar.manipulador.entidade.enumedo;

public enum TipoSanguinio {
    
    A_POSITIVO(1, "A+"),
    A_NEGATIVO(2, "A-"),
    B_POSITIVO(1, "B+"),
    B_NEGATIVO(2, "B-"),
    AB_POSITIVO(1, "AB+"),
    AB_NEGATIVO(2, "AB-"),
    O_POSITIVO(1, "O+"),
    O_NEGATIVO(2, "O-");
    
    private int codigo;
    private String descricao;
    
    public static TipoSanguinio paraEnum(String descricao) {
        if (descricao == null) {
            return null;
        }
        
        for (TipoSanguinio tipo : TipoSanguinio.values()) {
            if (tipo.descricao.equals(descricao)) {
                return tipo;
            }
        }
        
        throw new IllegalArgumentException("Descrição inválido. Descrição: " + descricao);
    }
    
        public static TipoSanguinio paraEnum(int codigo) {
        if (codigo == 0) {
            return null;
        }
        
        for (TipoSanguinio tipo : TipoSanguinio.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        
        throw new IllegalArgumentException("Código inválido. Código: " + codigo);
    }
    
    private TipoSanguinio(int codigo, String descricao) {
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
