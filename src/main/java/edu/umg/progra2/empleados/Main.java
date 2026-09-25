package edu.umg.progra2.empleados;

import edu.umg.progra2.empleados.dao.dao;
import edu.umg.progra2.empleados.modelo.modelo;
import edu.umg.progra2.empleados.servicio.servicio;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    
    private static final String[] TIPOS_CONTRATO = {
            "Temporal",
            "Permanente",
            "Por hora"
    };

    private static final servicio service =
            new servicio(new dao());

    public static void main(String[] args) {

        int opcion;

        do {

            mostrarMenu();

            opcion = leerEntero("Elige una opción: ");

            try {

                switch (opcion) {

                    case 1:
                        listarEmpleados();
                        break;

                    case 2:
                        registrarEmpleado();
                        break;

                    case 3:
                        editarEmpleado();
                        break;

                    case 4:
                        eliminarEmpleado();
                        break;

                    case 5:
                        verTotales();
                        break; 
                    
                    case 6:
                        System.out.println(
                                "Programa finalizado."
                        );
                        break;

                    default:
                        System.out.println(
                                "Opción no válida."
                        );
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error de base de datos: "
                                + e.getMessage()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );
            }

        } while (opcion != 6);
    }

    private static void mostrarMenu() {

        System.out.println();
        System.out.println("==============================");
        System.out.println("     SISTEMA DE EMPLEADOS");
        System.out.println("==============================");
        System.out.println("1. Listar empleados");
        System.out.println("2. Registrar empleado");
        System.out.println("3. Editar empleado");
        System.out.println("4. Eliminar empleado");
        System.out.println("5. Ver totales");
        System.out.println("6. Salir");
        System.out.println("==============================");
    }

    private static void listarEmpleados()
            throws SQLException {

        List<modelo> empleados =
                service.listarTodos();

        System.out.println();
        System.out.println("========== EMPLEADOS ==========");

        if (empleados.isEmpty()) {

            System.out.println(
                    "No hay empleados registrados."
            );

            return;
        }

        for (modelo empleado : empleados) {

            String estado =
                    empleado.isActivo()
                            ? "Activo"
                            : "Inactivo";

            System.out.printf(
                    "[%d] %-25s | %-15s | Q%s | %s%n",
                    empleado.getId(),
                    empleado.getNombreCompleto(),
                    empleado.getDepartamento(),
                    empleado.getSalario().toPlainString(),
                    empleado.getTipoContrato(),
                    estado
            );
        }
    }

    private static void registrarEmpleado()
            throws SQLException {

        System.out.println();
        System.out.println("===== REGISTRAR EMPLEADO =====");

        String nombre =
                leerTexto("Nombre completo: ");

        String departamento =
                leerTexto("Departamento: ");

        BigDecimal salario =
                leerDecimal("Salario mensual: ");

        LocalDate fecha =
                leerFecha("Fecha de contratación (YYYY-MM-DD): ");

        boolean activo =
                leerBooleano("¿Está activo? (s/n): ");
        
        String tipoContrato = 
        		seleccionarTipoContrato();

        modelo empleado = new modelo(
        		
                nombre,
                departamento,
                salario,
                fecha,
                activo,
                tipoContrato
        );

        service.registrar(empleado);

        System.out.println(
                "Empleado registrado correctamente."
        );

        System.out.println(
                "ID asignado: " + empleado.getId()
        );
    }

    private static void editarEmpleado()
            throws SQLException {

        System.out.println();
        System.out.println("======= EDITAR EMPLEADO =======");

        int id =
                leerEntero("ID del empleado: ");

        modelo empleado =
                service.buscarPorId(id);

        if (empleado == null) {

            System.out.println(
                    "No existe un empleado con ese ID."
            );

            return;
        }

        System.out.println(
                "Empleado encontrado: "
                        + empleado.getNombreCompleto()
        );

        String nombre =
                leerTexto("Nuevo nombre completo: ");

        String departamento =
                leerTexto("Nuevo departamento: ");

        BigDecimal salario =
                leerDecimal("Nuevo salario: ");

        LocalDate fecha =
                leerFecha(
                        "Nueva fecha de contratación (YYYY-MM-DD): "
                );

        boolean activo =
                leerBooleano("¿Está activo? (s/n): ");
        
        String tipoContrato =
                seleccionarTipoContrato();

        empleado.setNombreCompleto(nombre);
        empleado.setDepartamento(departamento);
        empleado.setSalario(salario);
        empleado.setFechaContratacion(fecha);
        empleado.setActivo(activo);
        empleado.setTipoContrato(tipoContrato);
        
        service.actualizar(empleado);

        System.out.println(
                "Empleado actualizado correctamente."
        );
    }

    private static void eliminarEmpleado()
            throws SQLException {

        System.out.println();
        System.out.println("====== ELIMINAR EMPLEADO ======");

        int id =
                leerEntero("ID del empleado: ");

        modelo empleado =
                service.buscarPorId(id);

        if (empleado == null) {

            System.out.println(
                    "No existe un empleado con ese ID."
            );

            return;
        }

        System.out.println(
                "Empleado: "
                        + empleado.getNombreCompleto()
        );

        boolean confirmar =
                leerBooleano(
                        "¿Confirmas la eliminación? (s/n): "
                );

        if (!confirmar) {

            System.out.println(
                    "Eliminación cancelada."
            );

            return;
        }

        service.eliminar(id);

        System.out.println(
                "Empleado eliminado del sistema."
        );
    }

    private static String leerTexto(String mensaje) {

        System.out.print(mensaje);

        return scanner.nextLine();
    }

    private static int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingresa un número entero válido."
                );
            }
        }
    }

    private static BigDecimal leerDecimal(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return new BigDecimal(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingresa un salario válido."
                );
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return LocalDate.parse(
                        scanner.nextLine()
                );

            } catch (Exception e) {

                System.out.println(
                        "Formato inválido. Usa YYYY-MM-DD."
                );
            }
        }
    }

    private static boolean leerBooleano(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String respuesta =
                    scanner.nextLine()
                            .trim()
                            .toLowerCase();

            if (respuesta.equals("s")) {
                return true;
            }

            if (respuesta.equals("n")) {
                return false;
            }

            System.out.println(
                    "Responde solamente s o n."
            );
        }
    }
    
    private static String seleccionarTipoContrato() {

        JComboBox<String> comboBox =
                new JComboBox<>(TIPOS_CONTRATO);

        int resultado = JOptionPane.showConfirmDialog(
                null,
                comboBox,
                "Seleccionar tipo de contrato",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) {

            throw new IllegalArgumentException(
                    "Debes seleccionar un tipo de contrato."
            );
        }

        String tipoContrato =
                (String) comboBox.getSelectedItem();

        validarTipoContrato(tipoContrato);

        return tipoContrato;
    }
    
    private static void validarTipoContrato(String tipoContrato) {

        if (tipoContrato == null) {

            throw new IllegalArgumentException(
                    "El tipo de contrato es obligatorio."
            );
        }

        for (String tipo : TIPOS_CONTRATO) {

            if (tipo.equals(tipoContrato)) {
                return;
            }
        }

        throw new IllegalArgumentException(
                "Tipo de contrato no válido."
        );
    }
    
    private static void verTotales()
            throws SQLException {

        List<modelo> empleados =
                service.listarTodos();

        System.out.println();
        System.out.println("========== TOTALES ==========");

        if (empleados.isEmpty()) {

            System.out.println(
                    "No hay empleados registrados."
            );

            System.out.println(
                    "Suma de salarios: Q0.00"
            );

            System.out.println(
                    "Promedio de salarios: Q0.00"
            );

            return;
        }

        BigDecimal suma = BigDecimal.ZERO;

        for (modelo empleado : empleados) {

            suma = suma.add(
                    empleado.getSalario()
            );
        }

        BigDecimal promedio =
                suma.divide(
                        BigDecimal.valueOf(empleados.size()),
                        2,
                        java.math.RoundingMode.HALF_UP
                );

        System.out.println(
                "Cantidad de empleados: "
                        + empleados.size()
        );

        System.out.println(
                "Suma de salarios: Q"
                        + suma.toPlainString()
        );

        System.out.println(
                "Promedio de salarios: Q"
                        + promedio.toPlainString()
        );
    }
    
}