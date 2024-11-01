package classes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Estacionamento {

    private List<Vaga> vagas;
    private List<Veiculo> veiculos;
    private List<Historico> historicos;
    private Scanner scanner = new Scanner(System.in);

    public Estacionamento() {
        this.historicos = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.vagas = new ArrayList<>();
    }

    public void iniciarSistema() {
        System.out.println("Iniciando Sistema: Gerenciamento de Estacionamento");
        menu();
    }

    private void menu() {
        while (true) {
            System.out.println("Escolha uma das opções abaixo:\n" +
                    "1 - Cadastrar Vaga\n" +
                    "2 - Cadastrar Veículo\n" +
                    "3 - Registrar Entrada em Vaga\n" + // fazer verificação de carro ja ta em vaga
                    "4 - Registrar Saida de Vaga\n" + // fazer verificação de carro nao esta em vaga
                    "5 - Relatório de vagas ocupadas\n" +
                    "6 - Ver histórico de ocupação de um carro\n" +
                    "Digite o número da opção escolhida: ");
            int opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    cadastrarVaga();
                    break;
                case 2:
                    cadastrarVeiculo();
                    break;
                case 3:
                    registrarEntrada();
                    break;
                case 4:
                    registrarSaida();
                    break;
                case 5:
                    relatorioVagasOculpadas();
                    break;
                case 6:
                    historicoOcupacao();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    continue;
            }
        }
    }

    private Boolean veiculoEstaEmVaga(String placa) {
        for (Historico historico : historicos) {
            if (Objects.equals(historico.getIdVeiculo(), placa)) {
                if (historico.getEntrada() != null && historico.getSaida() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean vagaEstaDisponivel(Integer numero) {
        for (Vaga vaga : vagas) {
            if (Objects.equals(vaga.getNumero(), numero)) {
                if (vaga.getDisponibilidade()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void historicoOcupacao() {
        System.out.println("Digite a placa do veículo: ");
        String placaVeiculo = scanner.next();
        if (!veiculoExiste(placaVeiculo)) {
            System.out.println("Veículo não existe, registro cancelado!");
            return;
        }
        for (Historico historico : historicos) {
            Long duracaoMinutos = null;
            if (historico.getSaida() != null) {
                Duration duracao = Duration.between(historico.getEntrada(), historico.getSaida());
                duracaoMinutos = duracao.toMinutes();
            }
            System.out.println("Historico{" +
                    "placa=" + historico.getIdVeiculo() +
                    ", tempoPermanencia=" + ((duracaoMinutos == null) ? "Ainda na vaga" : duracaoMinutos + " minutos") +
                    ", valoPago=" + historico.getValorPago() +
                    '}');
        }

    }

    private void relatorioVagasOculpadas() {
        for (Vaga vaga : vagas) {
            if (!vaga.getDisponibilidade()) {
                System.out.println("Vaga{" +
                        "número=" + vaga.getNumero() +
                        ", tamanho=" + vaga.getTamanho() +
                        ", placaCarro=" + pegarPlacaVeiculoPorVaga(vaga.getNumero()) +
                        '}');
            }
        }
    }

    private String pegarPlacaVeiculoPorVaga(Integer idVaga) {
        for (Historico historico : historicos) {
            if (Objects.equals(historico.getIdVaga(), idVaga)) {
                return historico.getIdVeiculo();
            }
        }
        return null;
    }

    private void cadastrarVaga() {
        System.out.println("Digite o número da vaga: ");
        int numeroVaga = scanner.nextInt();
        if (vagaExiste(numeroVaga)) {
            System.out.println("Vaga com esse número já existe, cadastro cancelado!");
            return;
        }
        Tamanho tamanhoVaga = selecionarTamanho();
        Vaga vaga = new Vaga(numeroVaga, tamanhoVaga);
        vagas.add(vaga);
        System.out.println("Vaga cadastrada com sucesso: " + vaga);
    }

    private void cadastrarVeiculo() {
        System.out.println("Digite a placa do veículo: ");
        String placaVeiculo = scanner.next();
        if (veiculoExiste(placaVeiculo)) {
            System.out.println("Veículo com essa placa já existe, cadastro cancelado!");
            return;
        }
        System.out.println("Digite o modelo do veículo: ");
        String modeloVeiculo = scanner.next();
        Tamanho tamanhoVeiculo = selecionarTamanho();
        Veiculo veiculo = new Veiculo(placaVeiculo, modeloVeiculo, tamanhoVeiculo);
        veiculos.add(veiculo);
        System.out.println("Veículo cadastrado com sucesso: " + veiculo);
    }

    private void registrarEntrada() {
        System.out.println("Digite o número da vaga: ");
        int numeroVaga = scanner.nextInt();
        if (!vagaExiste(numeroVaga)) {
            System.out.println("Vaga não existe, registro cancelado!");
            return;
        }
        if (!vagaEstaDisponivel(numeroVaga)) {
            System.out.println("Vaga já está ocupada!");
            return;
        }
        System.out.println("Digite a placa do veículo: ");
        String placaVeiculo = scanner.next();
        if (!veiculoExiste(placaVeiculo)) {
            System.out.println("Veículo não existe, registro cancelado!");
            return;
        }
        if (!tamanhoCompativel(numeroVaga, placaVeiculo)) {
            System.out.println("Tamanho da vaga náo é compatível com o tamanho do veículo, registro cancelado!");
            return;
        }
        if (veiculoEstaEmVaga(placaVeiculo)) {
            System.out.println("Veículo já está em uma vaga!");
            return;
        }
        LocalDateTime entradaHistorico = LocalDateTime.now();
        Historico historico = new Historico(numeroVaga, placaVeiculo, entradaHistorico);
        historicos.add(historico);
        mudarDisponibilidadeVaga(numeroVaga);
        System.out.println("Entra registrada com sucesso: " + historico);
    }

    private void registrarSaida() {
        System.out.println("Digite a placa do veículo: ");
        String placaVeiculo = scanner.next();
        if (!veiculoExiste(placaVeiculo)) {
            System.out.println("Veículo não existe, registro cancelado!");
            return;
        }
        if (!veiculoEstaEmVaga(placaVeiculo)) {
            System.out.println("Veículo não está em uma vaga!");
            return;
        }
        LocalDateTime saidaHistorico = LocalDateTime.now();
        for (Historico historico : historicos) {
            if (Objects.equals(historico.getIdVeiculo(), placaVeiculo)) {
                historico.setSaida(saidaHistorico);
                Duration duracao = Duration.between(historico.getEntrada(), saidaHistorico);
                long duracaoMinutos = duracao.toMinutes();
                if (duracaoMinutos <= 60) {
                    historico.setValorPago(5.00);
                    break;
                } else if (duracaoMinutos <= 180) {
                    historico.setValorPago(10.00);
                } else {
                    historico.setValorPago(15.00);
                }
                mudarDisponibilidadeVaga(historico.getIdVaga());
                System.out.println("Sainda registrada com sucesso: " + historico);
            }
        }
    }

    private void mudarDisponibilidadeVaga(Integer numero) {
        for (Vaga vaga : vagas) {
            if (Objects.equals(vaga.getNumero(), numero)) {
                vaga.setDisponibilidade(!vaga.getDisponibilidade());
            }
        }
    }

    private Boolean tamanhoCompativel(Integer idVaga, String idVeiculo) {
        for (Vaga vaga : vagas) {
            if (Objects.equals(vaga.getNumero(), idVaga)) {
                for (Veiculo veiculo : veiculos) {
                    if (Objects.equals(veiculo.getPlaca(), idVeiculo)) {
                        if (vaga.getTamanho().getId() >= veiculo.getTamanho().getId()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Boolean vagaExiste(Integer numero) {
        for (Vaga vaga : vagas) {
            if (Objects.equals(vaga.getNumero(), numero)) {
                return true;
            }
        }
        return false;
    }

    private Boolean veiculoExiste(String placa) {
        for (Veiculo veiculo : veiculos) {
            if (Objects.equals(veiculo.getPlaca(), placa)) {
                return true;
            }
        }
        return false;
    }

    private Tamanho selecionarTamanho() {
        while (true) {
            System.out.println("Escolha uma das opções abaixo:\n" +
                    "1 - Pequeno\n" +
                    "2 - Médio\n" +
                    "3 - Grande\n" +
                    "Digite o número da opção escolhida: ");
            int opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    return Tamanho.PEQUENO;
                case 2:
                    return Tamanho.MEDIO;
                case 3:
                    return Tamanho.GRANDE;
                default:
                    System.out.println("Opção inválida!");
                    continue;
            }
        }

    }

}
