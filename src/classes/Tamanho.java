package classes;

public enum Tamanho {

    PEQUENO(1),
    MEDIO(2),
    GRANDE(3);

    private Integer id;

    Tamanho(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
