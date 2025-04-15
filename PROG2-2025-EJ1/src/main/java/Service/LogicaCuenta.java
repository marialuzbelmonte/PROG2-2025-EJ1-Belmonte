package Service;

import Model.IGestionSaldo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LogicaCuenta {
    private static LogicaCuenta instancia;
    private final List<IGestionSaldo> cuentas;

    private LogicaCuenta() {
        cuentas = new ArrayList<>();
    }

    public static synchronized LogicaCuenta getInstancia() {
        if (instancia == null) {
            instancia = new LogicaCuenta();
        }
        return instancia;
    }

    public synchronized void agregarCuenta(IGestionSaldo cuenta) {
        cuentas.add(cuenta);
    }

    // Método para agregat saldo con CompletableFuture
    public CompletableFuture<Boolean> agregarSaldo(int nroCuenta, double monto) {
        return CompletableFuture.supplyAsync(() -> {
            if (validar(nroCuenta)) {
                return cuentas.get(nroCuenta).agregarSaldo(monto);
            }
            return false;
        });
    }

    // Método para quitar saldo con CompletableFuture
    public CompletableFuture<Boolean> quitarSaldo(int nroCuenta, double monto) {
        return CompletableFuture.supplyAsync(() -> {
            if (validar(nroCuenta)) {
                return cuentas.get(nroCuenta).quitarSaldo(monto);
            }
            return false;
        });
    }

    // Método para consultar saldo de forma asíncrona
    public CompletableFuture<Double> consultarSaldo(int nroCuenta) {
        return CompletableFuture.supplyAsync(() -> {
            if (validar(nroCuenta)) {
                return cuentas.get(nroCuenta).getSaldo();
            }
            return -1.0;
        });
    }

    // Método para obtener cantidad de operaciones de manera asíncrona
    public CompletableFuture<Integer> getOperaciones(int nroCuenta) {
        return CompletableFuture.supplyAsync(() -> {
            if (validar(nroCuenta)) {
                return cuentas.get(nroCuenta).getOperaciones();
            }
            return -1;
        });
    }

    public List<IGestionSaldo> getCuentas() {
        return cuentas;
    }

    // Validación de si el índice de cuenta es válido
    private boolean validar(int i) {
        return i >= 0 && i < cuentas.size();
    }

    // Método para ejecutar múltiples operaciones de manera concurrente
    public void ejecutarOperacionesConcurrentes(int cantidadOperaciones) throws InterruptedException, ExecutionException {
        List<CompletableFuture<Void>> futureList = new ArrayList<>();

        for (int i = 0; i < cantidadOperaciones; i++) {
            final int cuentaIndex = (int) (Math.random() * cuentas.size());
            final double monto = 10 + Math.random() * 90;  // Monto aleatorio entre 10 y 100
            boolean esDeposito = Math.random() < 0.5; // Decide aleatoriamente si es un depósito o extracción

            CompletableFuture<Void> futureOp;
            if (esDeposito) {
                futureOp = agregarSaldo(cuentaIndex, monto).thenAccept(result -> {
                    if (result) {
                        System.out.println("Se agregó $" + monto + " a la cuenta #" + cuentaIndex);
                    }
                });
            } else {
                futureOp = quitarSaldo(cuentaIndex, monto).thenAccept(result -> {
                    if (result) {
                        System.out.println("Se quitó $" + monto + " de la cuenta #" + cuentaIndex);
                    }
                });
            }

            futureList.add(futureOp);
        }

        // Esperar a que todas las operaciones se completen
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        allOf.join();  // Espera a que todas las operaciones hayan terminado
    }
}
