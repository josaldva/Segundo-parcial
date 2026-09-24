package edu.umg.progra2.empleados.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.umg.progra2.empleados.modelo.modelo;
import edu.umg.progra2.empleados.util.util;

public class dao {

    public void insertar(modelo empleado) throws SQLException {

        String sql = """
                INSERT INTO empleados
                (nombre_completo, departamento, salario,
                 fecha_contratacion, activo)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conexion = util.obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setString(1, empleado.getNombreCompleto());
            ps.setString(2, empleado.getDepartamento());
            ps.setBigDecimal(3, empleado.getSalario());
            ps.setDate(
                    4,
                    Date.valueOf(empleado.getFechaContratacion())
            );
            ps.setBoolean(5, empleado.isActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    empleado.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<modelo> listarTodos() throws SQLException {

        List<modelo> empleados = new ArrayList<>();

        String sql = """
                SELECT id,
                       nombre_completo,
                       departamento,
                       salario,
                       fecha_contratacion,
                       activo
                FROM empleados
                ORDER BY id
                """;

        try (
                Connection conexion = util.obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                modelo empleado = new modelo(
                        rs.getInt("id"),
                        rs.getString("nombre_completo"),
                        rs.getString("departamento"),
                        rs.getBigDecimal("salario"),
                        rs.getDate("fecha_contratacion").toLocalDate(),
                        rs.getBoolean("activo")
                );

                empleados.add(empleado);
            }
        }

        return empleados;
    }

    public modelo buscarPorId(int id) throws SQLException {

        String sql = """
                SELECT id,
                       nombre_completo,
                       departamento,
                       salario,
                       fecha_contratacion,
                       activo
                FROM empleados
                WHERE id = ?
                """;

        try (
                Connection conexion = util.obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new modelo(
                            rs.getInt("id"),
                            rs.getString("nombre_completo"),
                            rs.getString("departamento"),
                            rs.getBigDecimal("salario"),
                            rs.getDate("fecha_contratacion").toLocalDate(),
                            rs.getBoolean("activo")
                    );
                }
            }
        }

        return null;
    }

    public void actualizar(modelo empleado) throws SQLException {

        String sql = """
                UPDATE empleados
                SET nombre_completo = ?,
                    departamento = ?,
                    salario = ?,
                    fecha_contratacion = ?,
                    activo = ?
                WHERE id = ?
                """;

        try (
                Connection conexion = util.obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, empleado.getNombreCompleto());
            ps.setString(2, empleado.getDepartamento());
            ps.setBigDecimal(3, empleado.getSalario());
            ps.setDate(
                    4,
                    Date.valueOf(empleado.getFechaContratacion())
            );
            ps.setBoolean(5, empleado.isActivo());
            ps.setInt(6, empleado.getId());

            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {

        String sql = """
                DELETE FROM empleados
                WHERE id = ?
                """;

        try (
                Connection conexion = util.obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }
}