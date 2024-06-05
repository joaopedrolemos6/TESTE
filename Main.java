import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Main {
    private static PessoaDAO pessoaDAO = new PessoaDAO();
    private static LivroDAO livroDAO = new LivroDAO();
    private static EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Cadastrar Funcionario");
            System.out.println("3. Adicionar Livro");
            System.out.println("4. Fazer Empréstimo");
            System.out.println("5. Escolher Aulas de Esporte");
            System.out.println("6. Calcular Passagem de Ano");
            System.out.println("7. Listar Pessoas");
            System.out.println("8. Listar Livros");
            System.out.println("9. Listar Empréstimos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarAluno(scanner);
                    break;
                case 2:
                    cadastrarFuncionario(scanner);
                    break;
                case 3:
                    adicionarLivro(scanner);
                    break;
                case 4:
                    fazerEmprestimo(scanner);
                    break;
                case 5:
                    escolherAulasEsporte(scanner);
                    break;
                case 6:
                    calcularPassagemDeAno(scanner);
                    break;
                case 7:
                    listarPessoas();
                    break;
                case 8:
                    listarLivros();
                    break;
                case 9:
                    listarEmprestimos();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void cadastrarAluno(Scanner scanner) {
        System.out.print("Nome do aluno: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do aluno: ");
        String cpf = scanner.nextLine();
        Aluno aluno = new Aluno(nome, cpf);
        pessoaDAO.cadastrarPessoa(aluno);
        System.out.println("Aluno cadastrado com sucesso.");
    }

    private static void cadastrarFuncionario(Scanner scanner) {
        System.out.print("Nome do funcionario: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do funcionario: ");
        String cpf = scanner.nextLine();
        System.out.print("Cargo do funcionario: ");
        String cargo = scanner.nextLine();
        Funcionario funcionario = new Funcionario(nome, cpf, cargo);
        pessoaDAO.cadastrarPessoa(funcionario);
        System.out.println("Funcionario cadastrado com sucesso.");
    }

    private static void adicionarLivro(Scanner scanner) {
        System.out.print("Título do livro: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor do livro: ");
        String autor = scanner.nextLine();
        Livro livro = new Livro(titulo, autor);
        livroDAO.adicionarLivro(livro);
        System.out.println("Livro adicionado com sucesso.");
    }

    private static void fazerEmprestimo(Scanner scanner) {
        System.out.print("CPF da pessoa: ");
        String cpf = scanner.nextLine();
        Pessoa pessoa = pessoaDAO.encontrarPessoaPorCpf(cpf);

        if (pessoa == null) {
            System.out.println("Pessoa não encontrada.");
            return;
        }

        System.out.print("Título do livro: ");
        String titulo = scanner.nextLine();
        Livro livro = livroDAO.encontrarLivroPorTitulo(titulo);

        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        Emprestimo emprestimo = new Emprestimo(pessoa, livro);
        emprestimoDAO.fazerEmprestimo(emprestimo);
        System.out.println("Empréstimo realizado com sucesso.");
    }

    private static void escolherAulasEsporte(Scanner scanner) {
        System.out.println("Opção de escolher aulas de esporte ainda não implementada.");
    }

    private static void calcularPassagemDeAno(Scanner scanner) {
        System.out.print("CPF do aluno: ");
        String cpf = scanner.nextLine();
        Aluno aluno = (Aluno) pessoaDAO.encontrarPessoaPorCpf(cpf);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.print("Nota da Prova 1: ");
        double notaProva1 = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Nota da Prova 2: ");
        double notaProva2 = scanner.nextDouble();
        scanner.nextLine();

        aluno.setNotaProva1(notaProva1);
        aluno.setNotaProva2(notaProva2);

        if (aluno.passouDeAno()) {
            System.out.println("O aluno passou de ano!");
        } else {
            System.out.println("O aluno não passou de ano.");
        }
    }

    private static void listarPessoas() {
        List<Pessoa> pessoas = pessoaDAO.listarPessoas();
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
        } else {
            for (Pessoa pessoa : pessoas) {
                System.out.println(pessoa);
            }
        }
    }

    private static void listarLivros() {
        List<Livro> livros = livroDAO.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            for (Livro livro : livros) {
                System.out.println(livro);
            }
        }
    }

    private static void listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoDAO.listarEmprestimos();
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo realizado.");
        } else {
            for (Emprestimo emprestimo : emprestimos) {
                System.out.println(emprestimo);
            }
        }
    }
}