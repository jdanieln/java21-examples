package com.empresa.ejemplos.ejemplo3;

public class BankTransactionSystem {
    public static void main(String[] args) throws InterruptedException {
        // Crear un array de transacciones bancarias de diferentes tipos: depósito, retiro y pago de préstamo.
        Transaction[] transactions = {
                new DepositTransaction("Depósito", 1000),  // Transacción de depósito
                new WithdrawalTransaction("Retiro", 500), // Transacción de retiro
                new LoanPaymentTransaction("Pago de Préstamo", 300)  // Transacción de pago de préstamo
        };

        // Procesar cada transacción concurrentemente usando hilos virtuales
        for (Transaction transaction : transactions) {
            // Crear un hilo virtual para procesar cada transacción bancaria
            Thread thread = Thread.startVirtualThread(() -> {
                procesarTransaccion(transaction);  // Ejecutar el procesamiento de la transacción
            });

            // Esperar a que el hilo termine antes de continuar con la siguiente transacción
            thread.join();
        }
    }

    // Método que procesa la transacción, mostrando un mensaje dependiendo del tipo de transacción.
    public static void procesarTransaccion(Transaction transaction) {
        // Usar una expresión switch mejorada para manejar los diferentes tipos de transacciones.
        String resultado = switch (transaction) {
            case DepositTransaction dt -> "Procesando " + dt.tipo() + " por " + dt.monto() + " dólares.";
            case WithdrawalTransaction wt -> "Procesando " + wt.tipo() + " de " + wt.monto() + " dólares.";
            case LoanPaymentTransaction lpt -> "Procesando " + lpt.tipo() + " de " + lpt.monto() + " dólares.";
            // Si se encuentra una transacción desconocida, lanzar una excepción.
            default -> throw new IllegalArgumentException("Transacción desconocida");
        };

        // Imprimir el resultado del procesamiento de la transacción
        System.out.println(resultado);
    }
}

// Definir una interfaz sellada (sealed interface) para transacciones bancarias
sealed interface Transaction permits DepositTransaction, WithdrawalTransaction, LoanPaymentTransaction {
    // Métodos comunes para obtener el tipo y el monto de la transacción.
    String tipo();
    double monto();
}

// Record que representa una transacción de depósito
record DepositTransaction(String tipo, double monto) implements Transaction {}

// Record que representa una transacción de retiro
record WithdrawalTransaction(String tipo, double monto) implements Transaction {}

// Record que representa una transacción de pago de préstamo
record LoanPaymentTransaction(String tipo, double monto) implements Transaction {}
