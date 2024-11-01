package classes;

import java.util.List;

public class Veiculo {

    private String placa;
    private String modelo;
    private Tamanho tamanho;

    public Veiculo(String placa, String modelo, Tamanho tamanho) {
        this.placa = placa;
        this.modelo = modelo;
        this.tamanho = tamanho;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {


        return "Veiculo{" +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tamanho=" + tamanho +
                '}';
    }
}
