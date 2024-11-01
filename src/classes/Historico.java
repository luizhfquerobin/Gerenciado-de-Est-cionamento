package classes;

import java.time.LocalDateTime;

public class Historico {
    private Integer idVaga;
    private String idVeiculo;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private Double valorPago;

    public Historico(Integer idVaga, String idVeiculo, LocalDateTime entrada) {
        this.idVaga = idVaga;
        this.idVeiculo = idVeiculo;
        this.entrada = entrada;
    }

    public Integer getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(Integer idVaga) {
        this.idVaga = idVaga;
    }

    public String getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(String idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    @Override
    public String toString() {
        return "Historico{" +
                "idVaga=" + idVaga +
                ", idVeiculo='" + idVeiculo + '\'' +
                ", entrada=" + entrada +
                ", saida=" + saida +
                ", valorPago=" + valorPago +
                '}';
    }
}
