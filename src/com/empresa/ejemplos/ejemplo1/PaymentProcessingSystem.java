package com.empresa.ejemplos.ejemplo1;

import java.lang.ScopedValue;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class PaymentProcessingSystem {
    // ScopedValue se utiliza para almacenar un valor específico para cada hilo.
    // Aquí, se utiliza para almacenar el ID de la solicitud de pago.
    static final ScopedValue<String> requestScoped = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        // Creamos un ExecutorService basado en hilos virtuales para ejecutar las tareas concurrentemente.
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        // Simular el procesamiento de 1000 solicitudes de pago concurrentes.
        for (int i = 0; i < 1000; i++) {
            final int requestId = i; // Guardar el ID de la solicitud de pago

            // Crear un hilo virtual para procesar cada solicitud de pago.
            Thread thread = Thread.startVirtualThread(() -> {
                // ScopedValue.where asegura que el valor (el ID de la solicitud) esté disponible para el hilo.
                ScopedValue.where(requestScoped, "Request-" + requestId).run(() -> {
                    try {
                        procesarSolicitud(); // Procesar la solicitud de pago
                    } catch (InterruptedException e) {
                        // Manejo de excepciones en caso de que la solicitud sea interrumpida
                        System.err.println("Error en la solicitud " + requestId);
                        e.printStackTrace();
                    }
                });
            });

            // Asegurarnos de que cada hilo termine antes de continuar al siguiente.
            thread.join();
        }

        // Apagamos el ExecutorService para asegurarnos de que no acepte nuevas tareas.
        executor.shutdown();
        // Esperar hasta que todas las tareas hayan terminado o un timeout de 1 minuto.
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            System.err.println("El procesamiento no ha terminado en el tiempo esperado.");
        } else {
            System.out.println("Todas las solicitudes de pago han sido procesadas correctamente.");
        }
    }

    // Método que simula el procesamiento de una solicitud de pago.
    public static void procesarSolicitud() throws InterruptedException {
        // Simular tareas concurrentes relacionadas con la solicitud usando un StructuredTaskScope.
        try (var scope = new StructuredTaskScope<Void>()) {
            // Tarea 1: Validar el pago
            scope.fork(() -> {
                validarPago();
                return null;
            });

            // Tarea 2: Calcular los impuestos relacionados con el pago
            scope.fork(() -> {
                calcularImpuestos();
                return null;
            });

            // Unir las tareas, es decir, esperar a que ambas terminen antes de continuar.
            scope.join();
            
            // Una vez completadas las tareas, mostramos un mensaje confirmando el procesamiento de la solicitud.
            System.out.println("Procesamiento completado para: " + requestScoped.get());
        }
    }

    // Método que simula la validación del pago.
    public static void validarPago() {
        // Obtener el valor del ScopedValue, que contiene el ID de la solicitud actual.
        System.out.println("Validando pago para: " + requestScoped.get());
    }

    // Método que simula el cálculo de impuestos basado en el pago.
    public static void calcularImpuestos() {
        // Obtener el valor del ScopedValue (ID de la solicitud) y calcular los impuestos.
        System.out.println("Calculando impuestos para: " + requestScoped.get());
    }
}
