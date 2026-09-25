package edu.umg.progra2.empleados.servicio;

import edu.umg.progra2.empleados.dao.dao;
import edu.umg.progra2.empleados.modelo.modelo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class servicio {

    private final dao empleadoDAO;

    public servicio(dao empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    public void registrar(modelo empleado) throws SQLException {
        validarEmpleado(empleado);
        empleadoDAO.insertar(empleado);
    }

    public List<modelo> listarTodos() throws SQLException {
        return empleadoDAO.listarTodos();
    }

    public modelo buscarPorId(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }
        return empleadoDAO.buscarPorId(id);
    }

    public void actualizar(modelo empleado) throws SQLException {
        if (empleado.getId() <= 0) {
            throw new IllegalArgumentException(
                    "El empleado debe tener un ID válido."
            );
        }
        validarEmpleado(empleado);
        empleadoDAO.actualizar(empleado);
    }

    public void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }
        empleadoDAO.eliminar(id);
    }

    private void validarEmpleado(modelo empleado) {
        if (empleado.getNombreCompleto() == null ||
                empleado.getNombreCompleto().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede quedar vacío."
            );
        }

        if (empleado.getDepartamento() == null ||
                empleado.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El departamento no puede quedar vacío."
            );
        }

        if (empleado.getSalario() == null ||
                empleado.getSalario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El salario debe ser mayor que cero."
            );
        }

        if (empleado.getFechaContratacion() == null) {
            throw new IllegalArgumentException(
                    "La fecha de contratación es obligatoria."
            );
        }

        if (empleado.getFechaContratacion().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de contratación no puede ser futura."
            );
        }
        
        if (empleado.getTipoContrato() == null ||
                empleado.getTipoContrato().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El tipo de contrato es obligatorio."
            );
        }

        if (!empleado.getTipoContrato().equals("Temporal") &&
                !empleado.getTipoContrato().equals("Permanente") &&
                !empleado.getTipoContrato().equals("Por hora")) {

            throw new IllegalArgumentException(
                    "El tipo de contrato no es válido."
            );
        }
    }
    
}