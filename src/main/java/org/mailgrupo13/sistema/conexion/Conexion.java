package org.mailgrupo13.sistema.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Conexion instancia;
    private Connection connection;
    private String url = "jdbc:postgresql://www.tecnoweb.org.bo:5432/db_grupo13sc";
    private String usuario = "grupo13sc";
    private String contraseña = "grup013grup013*";

    private Conexion() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, usuario, contraseña);
        } catch (ClassNotFoundException ex) {
            throw new SQLException(ex);
        }
    }

    public static Conexion getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new Conexion();
        } else if (instancia.getConnection().isClosed()) {
            instancia = new Conexion();
        }

        return instancia;
    }

    public Connection getConnection() {
        return connection;
    }
}