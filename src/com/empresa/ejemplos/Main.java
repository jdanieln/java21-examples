package com.empresa.ejemplos;

import com.empresa.ejemplos.ejemplo1.PaymentProcessingSystem;
import com.empresa.ejemplos.ejemplo2.PayrollSystem;
import com.empresa.ejemplos.ejemplo3.BankTransactionSystem;
import com.empresa.ejemplos.ejemplo4.OrderProcessingSystem;
import com.empresa.ejemplos.ejemplo5.NotificationService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selecciona el ejemplo que deseas ejecutar:");
        System.out.println("1. Ejemplo 1: Procesamiento Concurrente de Solicitudes de Pago");
        System.out.println("2. Ejemplo 2: Procesamiento de Nómina");
        System.out.println("3. Ejemplo 3: Procesamiento de Transacciones Bancarias");
        System.out.println("4. Ejemplo 4: Procesamiento de Pedidos");
        System.out.println("5. Ejemplo 5: Servicio de Notificaciones");

        int seleccion = scanner.nextInt();

        switch (seleccion) {
            case 1:
                System.out.println("Ejecutando Ejemplo 1...");
                PaymentProcessingSystem.main(args);
                break;
            case 2:
                System.out.println("Ejecutando Ejemplo 2...");
                PayrollSystem.main(args);
                break;
            case 3:
                System.out.println("Ejecutando Ejemplo 3...");
                BankTransactionSystem.main(args);
                break;
            case 4:
                System.out.println("Ejecutando Ejemplo 4...");
                OrderProcessingSystem.main(args);
                break;
            case 5:
                System.out.println("Ejecutando Ejemplo 5...");
                NotificationService.main(args);
                break;
            default:
                System.out.println("Selección no válida. Intenta nuevamente.");
        }

        scanner.close();
    }
}
