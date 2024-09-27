package com.empresa.ejemplos.ejemplo4;

import java.util.concurrent.StructuredTaskScope;

public class OrderProcessingSystem {
    public static void main(String[] args) throws InterruptedException {
        // Crear un nuevo pedido con los detalles del mismo.
        Order order = new Order("Pedido123", "Laptop", 1500.0, 2.0);

        // Usamos un StructuredTaskScope para manejar varias tareas concurrentes relacionadas con el pedido.
        try (var scope = new StructuredTaskScope<Void>()) {
            // Tarea 1: Validar el pedido de manera concurrente.
            scope.fork(() -> {
                validarPedido(order);
                return null;
            });

            // Tarea 2: Calcular el costo de envío de manera concurrente.
            scope.fork(() -> {
                calcularEnvio(order);
                return null;
            });

            // Unir las tareas, es decir, esperar a que ambas terminen antes de continuar.
            scope.join();

            // Imprimir un mensaje indicando que el pedido ha sido procesado.
            System.out.println("Pedido procesado: " + order.idPedido());
        } catch (Exception e) {
            // Manejar cualquier excepción que ocurra durante el procesamiento del pedido.
            System.err.println("Error durante el procesamiento del pedido: " + e.getMessage());
        }
    }

    // Método para validar el pedido, simulando la validación de los detalles del pedido.
    public static void validarPedido(Order order) {
        System.out.println("Validando pedido: " + order.idPedido() + " - " + order.producto());
    }

    // Método para calcular el costo del envío, basado en el peso del producto.
    public static void calcularEnvio(Order order) {
        double costoEnvio = order.peso() * 5; // Costo de envío calculado a $5 por kg de peso.
        System.out.println("Costo de envío para " + order.producto() + ": $" + costoEnvio);
    }
}

// Usamos un record para representar un pedido. Un record genera automáticamente
// los métodos getter, toString, equals, y hashCode.
record Order(String idPedido, String producto, double precio, double peso) {}
