package com.empresa.ejemplos.ejemplo5;

import java.lang.ScopedValue;

public class NotificationService {
    // ScopedValue permite almacenar un valor específico para cada hilo.
    // Aquí, se usa para almacenar el mensaje personalizado para cada notificación.
    static final ScopedValue<String> notificationScoped = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        // Simular el envío de notificaciones a 1000 usuarios concurrentemente.
        for (int i = 0; i < 1000; i++) {
            final int userId = i;  // Capturar el ID del usuario actual

            // Crear un hilo virtual para enviar la notificación a cada usuario.
            Thread thread = Thread.startVirtualThread(() -> {
                // ScopedValue.where permite asociar un valor a este hilo específico.
                // En este caso, asignamos el mensaje de notificación personalizado.
                ScopedValue.where(notificationScoped, "Hola Usuario #" + userId).run(() -> {
                    enviarNotificacion();  // Ejecutar el envío de la notificación
                });
            });

            // Esperar a que el hilo termine antes de procesar el siguiente usuario.
            thread.join();
        }
    }

    // Método que simula el envío de la notificación, obteniendo el valor desde ScopedValue.
    public static void enviarNotificacion() {
        // notificationScoped.get() devuelve el valor asociado al hilo actual (el mensaje personalizado).
        System.out.println("Enviando notificación: " + notificationScoped.get());
    }
}
