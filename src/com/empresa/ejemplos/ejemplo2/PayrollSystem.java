package com.empresa.ejemplos.ejemplo2;

public class PayrollSystem {
    public static void main(String[] args) throws InterruptedException {
        // Definir un arreglo de empleados de diferentes tipos: permanentes, consultores y freelancers.
        Employee[] employees = {
                new PermanentEmployee("Daniel", 3000),  // Empleado permanente con salario base
                new Consultant("Anna", 500, 20),        // Consultor pagado por horas
                new Freelancer("John", 400, 15)         // Freelancer que paga comisión
        };

        // Procesar el salario de cada empleado en paralelo utilizando hilos virtuales.
        for (Employee emp : employees) {
            // Crear un hilo virtual para procesar el salario de cada empleado
            Thread thread = Thread.startVirtualThread(() -> {
                System.out.println("Procesando salario para: " + emp.nombre());
                
                // Calcular el salario dependiendo del tipo de empleado
                double salario = calcularSalario(emp);
                
                // Imprimir el salario calculado
                System.out.println("Salario calculado para " + emp.nombre() + ": " + salario);
            });

            // Esperar a que el hilo que procesa el salario del empleado termine antes de seguir al siguiente.
            thread.join();
        }
    }

    // Método que calcula el salario del empleado dependiendo de su tipo.
    public static double calcularSalario(Employee employee) {
        // Expresión switch mejorada que utiliza Pattern Matching para manejar diferentes tipos de empleados.
        return switch (employee) {
            // Para empleados permanentes, simplemente se devuelve el salario base.
            case PermanentEmployee p -> p.salarioBase();
            
            // Para consultores, el salario se calcula multiplicando la tarifa por hora por las horas trabajadas.
            case Consultant c -> c.tarifaHora() * c.horasTrabajadas();
            
            // Para freelancers, se aplica una comisión del 15%, por lo que se reduce el salario en ese porcentaje.
            case Freelancer f -> f.tarifaHora() * f.horasTrabajadas() * 0.85;
            
            // Si se encuentra un tipo de empleado no soportado, se lanza una excepción.
            default -> throw new IllegalArgumentException("Tipo de empleado no soportado");
        };
    }
}

// Sealed interface que define los métodos comunes a todos los empleados.
// 'sealed' asegura que solo las clases permitidas puedan implementar esta interfaz.
sealed interface Employee permits PermanentEmployee, Consultant, Freelancer {
    String nombre();  // Método común para obtener el nombre del empleado
}

// Record que representa a un empleado permanente con un salario base fijo.
record PermanentEmployee(String nombre, double salarioBase) implements Employee {}

// Record que representa a un consultor, pagado por horas trabajadas.
record Consultant(String nombre, double tarifaHora, int horasTrabajadas) implements Employee {}

// Record que representa a un freelancer, pagado por horas trabajadas pero con una comisión del 15%.
record Freelancer(String nombre, double tarifaHora, int horasTrabajadas) implements Employee {}
