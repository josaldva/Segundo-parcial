package edu.umg.progra2.empleados.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class modelo {

    private int id;
    private String nombreCompleto;
    private String departamento;
    private BigDecimal salario;
    private LocalDate fechaContratacion;
    private boolean activo;

    public modelo(
            int id,
            String nombreCompleto,
            String departamento,
            BigDecimal salario,
            LocalDate fechaContratacion,
            boolean activo) {

        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.departamento = departamento;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }

    public modelo(
            String nombreCompleto,
            String departamento,
            BigDecimal salario,
            LocalDate fechaContratacion,
            boolean activo) {

        this(
            0,
            nombreCompleto,
            departamento,
            salario,
            fechaContratacion,
            activo
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}