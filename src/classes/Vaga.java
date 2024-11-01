package classes;

public class Vaga {

    private Integer numero;
    private Tamanho tamanho;
    private Boolean disponibilidade;

    public Vaga(Integer numero, Tamanho tamanho) {
        this.numero = numero;
        this.tamanho = tamanho;
        this.disponibilidade = true;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    @Override
    public String toString() {
        return "Vaga{" +
                "numero=" + numero +
                ", tamanho=" + tamanho +
                ", disponibilidade=" + disponibilidade +
                '}';
    }
}
