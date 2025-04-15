package Model;

public abstract class Cuenta {
    protected double saldo;
    protected int operaciones;

    public Cuenta(double saldo, int operaciones) {
        this.saldo = saldo;
        this.operaciones = operaciones;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
