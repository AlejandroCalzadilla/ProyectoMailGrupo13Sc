package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriasM {

    private int id;
    private String nombre;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private Conexion conexion;
    private Connection conn;

    // Constructor
    public CategoriasM() throws SQLException {
        conexion = Conexion.getInstancia();
        conn = conexion.getConnection();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Timestamp getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(Timestamp actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    // Métodos CRUD

    // Crear una categoría
    public boolean crearCategoria() {
        if (existeCategoria(nombre)) {
            throw new IllegalArgumentException("Ya existe una categoría con el mismo nombre: " + nombre);
        }

        String sql = "INSERT INTO category (name, created_at, updated_at) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setTimestamp(2, creadoEn);
            stmt.setTimestamp(3, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al crear la categoría: " + e.getMessage(), e);
        }
    }

    // Leer una categoría por ID
    public CategoriasM leerCategoria(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CategoriasM categoriasM =new CategoriasM();
                categoriasM.id = rs.getInt("id");
                categoriasM.nombre = rs.getString("name");
                categoriasM.creadoEn = rs.getTimestamp("created_at");
                categoriasM.actualizadoEn = rs.getTimestamp("updated_at");
                return  categoriasM;
            } else {
                throw new IllegalArgumentException("No existe una categoría con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer la categoría: " + e.getMessage(), e);
        }
    }

    // Actualizar una categoría
    public String actualizarCategoria() {
        if (existeCategoria(nombre)) {
            throw new IllegalArgumentException("Ya existe una categoría con el mismo nombre: " + nombre);
        }

        String checkSql = "SELECT COUNT(*) FROM category WHERE id = ?";
        String sql = "UPDATE category SET name = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe una categoría con el ID proporcionado: " + id);
            }

            stmt.setString(1, nombre);
            stmt.setTimestamp(2, actualizadoEn);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            return "Categoría actualizada";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al actualizar la categoría: " + e.getMessage(), e);
        }
    }

    // Eliminar una categoría
    public String eliminarCategoria(int id) {
        String checkSql = "SELECT COUNT(*) FROM category WHERE id = ?";
        String deleteSql = "DELETE FROM category WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe una categoría con el ID proporcionado: " + id);
            }

            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
            return "Categoría eliminada";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar la categoría: " + e.getMessage(), e);
        }
    }

    // Obtener todas las categorías
    public List<CategoriasM> obtenerCategorias() {
        List<CategoriasM> categorias = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CategoriasM categoria = new CategoriasM();
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("name"));
                categoria.setCreadoEn(rs.getTimestamp("created_at"));
                categoria.setActualizadoEn(rs.getTimestamp("updated_at"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    // Verificar si una categoría existe por nombre
    private boolean existeCategoria(String nombre) {
        String sql = "SELECT COUNT(*) FROM category WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}