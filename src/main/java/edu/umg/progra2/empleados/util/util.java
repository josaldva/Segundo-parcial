package edu.umg.progra2.empleados.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class util {

    private static final String URL =
            "jdbc:mysql://localhost:3306/empresa_db";

    private static final String USUARIO = "root";

    private static final String PASSWORD = "123456789";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}