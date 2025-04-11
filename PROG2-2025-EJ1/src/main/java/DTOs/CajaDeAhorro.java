package DTOs;

public class CajaDeAhorro extends Cuenta implements IGestionSaldo {

    public CajaDeAhorro(double saldo, int operaciones) {
        super(saldo, operaciones);
    }

    @Override
    public synchronized boolean agregarSaldo(double monto) {
        if (monto <= 0){
            return false;
        }
        else{
            saldo += monto;
            operaciones++;
            return true;
        }
    }

    @Override
    public synchronized boolean quitarSaldo(double monto) {
        if(saldo < monto || monto <= 0){
            return false;
        } else{
            saldo += monto;
            operaciones++;
            return true;
        }
    }

    @Override
    public synchronized double getSaldo() {
        return saldo;
    }

    @Override
    public int getOperaciones() {
        return operaciones;
    }
}
