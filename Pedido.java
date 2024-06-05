public class Pedido {
    private Pessoa pessoa;
    private double valor;

    public Pedido(Pessoa pessoa, double valor) {
        this.pessoa = pessoa;
        this.valor = valor;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public double getValor() {
        return valor;
    }
}