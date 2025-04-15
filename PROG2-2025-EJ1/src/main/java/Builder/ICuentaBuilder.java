package Builder;

public interface ICuentaBuilder<L>
{
    ICuentaBuilder withSaldo(double saldo);
    ICuentaBuilder withOperaciones (int operaciones);
}
