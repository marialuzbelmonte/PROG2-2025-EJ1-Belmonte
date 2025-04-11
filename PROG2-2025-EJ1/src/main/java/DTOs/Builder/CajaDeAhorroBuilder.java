package DTOs.Builder;
import DTOs.CajaDeAhorro;

public class CajaDeAhorroBuilder implements ICuentaBuilder {
    private double saldo;
    private int operaciones;

    @Override
    public CajaDeAhorroBuilder withSaldo(double saldo) {
        this.saldo = saldo;
        return this;
    }

    @Override
    public CajaDeAhorroBuilder withOperaciones(int operaciones) {
        this.operaciones = operaciones;
        return this;
    }

    @Override
    public CajaDeAhorro build() {
        return new CajaDeAhorro(saldo, operaciones);
    }

}
