class Aluno extends Pessoa {
    private double notaProva1;
    private double notaProva2;

    public Aluno(String nome, String cpf) {
        super(nome, cpf);
    }

    public double getNotaProva1() {
        return notaProva1;
    }

    public void setNotaProva1(double notaProva1) {
        this.notaProva1 = notaProva1;
    }

    public double getNotaProva2() {
        return notaProva2;
    }

    public void setNotaProva2(double notaProva2) {
        this.notaProva2 = notaProva2;
    }

    public boolean passouDeAno() {
        return (notaProva1 + notaProva2) / 2 >= 6;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Aluno";
    }
}