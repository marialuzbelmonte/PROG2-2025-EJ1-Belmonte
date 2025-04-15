import Service.LogicaCuenta;
import Model.CajaDeAhorro;
import Model.CuentaCorriente;
import Model.IGestionSaldo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        LogicaCuenta logica = LogicaCuenta.getInstancia();
        Random random = new Random();

        // Crear 10 cuentas aleatorias
        for (int i = 0; i < 10; i++) {
            if (random.nextBoolean()) {
                logica.agregarCuenta(new CajaDeAhorro(random.nextDouble(1000), 0));
            } else {
                double saldo = random.nextDouble(1000);
                double descubierto = 200.0;
                logica.agregarCuenta(new CuentaCorriente(saldo, 0, descubierto));
            }
        }

        // Crear un pool de threads
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Ejecutar 10.000 operaciones concurrentes
        for (int i = 0; i < 10_000; i++) {
            executor.execute(() -> {
                int cuenta = random.nextInt(10);
                double monto = 10 + random.nextDouble(90); // entre 10 y 100
                boolean esDeposito = random.nextBoolean();

                if (esDeposito) {
                    logica.agregarSaldo(cuenta, monto);
                } else {
                    logica.quitarSaldo(cuenta, monto);
                }
            });
        }

        // Cerrar el executor (espera que terminen los threads)
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Espera activa
        }

        // Mostrar saldos finales
        System.out.println("Resultados finales:");
        int i = 0;
        for (IGestionSaldo cuenta : logica.getCuentas()) {
            System.out.println("Cuenta #" + i++);
            System.out.println("Saldo: $" + cuenta.getSaldo());
            System.out.println("Operaciones: " + cuenta.getOperaciones());
            System.out.println("--------------------");
        }
    }
}
