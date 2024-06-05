import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaLanchonete {
    private static List<Pessoa> pessoas = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();

    private static void fazerPedido(Scanner scanner) {
        System.out.print("CPF da pessoa: ");
        String cpf = scanner.nextLine();
        Pessoa pessoa = encontrarPessoaPorCpf(cpf);

        if (pessoa == null) {
            System.out.println("Pessoa não encontrada.");
            return;
        }

        System.out.print("Valor do pedido: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        Pedido pedido = new Pedido(pessoa, valor);
        pedidos.add(pedido);
        System.out.println("Pedido realizado com sucesso!");
    }

    private static Pessoa encontrarPessoaPorCpf(String cpf) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getCpf().equals(cpf)) {
                return pessoa;
            }
        }
        return null;
    }

    private static void listarPessoas() {
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
        } else {
            for (Pessoa pessoa : pessoas) {
                System.out.println(pessoa);
            }
        }
    }

    private static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido realizado.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }
}
