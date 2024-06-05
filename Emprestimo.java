import java.util.List;
public class Emprestimo {
    private Pessoa pessoa;
    private Livro livro;

    public Emprestimo(Pessoa pessoa, Livro livro) {
        this.pessoa = pessoa;
        this.livro = livro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Livro getLivro() {
        return livro;
    }

    @Override
    public String toString() {
        return "Pessoa: " + pessoa.getNome() + ", Livro: " + livro.getTitulo();
    }
}