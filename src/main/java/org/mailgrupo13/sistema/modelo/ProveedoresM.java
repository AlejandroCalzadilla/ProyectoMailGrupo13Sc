package org.mailgrupo13.sistema.modelo;
import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresM {

    private int id;
    private String nombre;
    private String pais;
    private String telefono;
    private String email;
    private String direccion;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public ProveedoresM() throws SQLException {
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    // Crear un proveedor
    public String crearProveedor() {
        if (existeProveedor(nombre)) {
            throw new IllegalArgumentException("Ya existe un proveedor con el mismo nombre: " + nombre);
        }
        String sql = "INSERT INTO suppliers (name, country, phone_number, email, address, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, pais);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, direccion);
            stmt.setTimestamp(6, creadoEn);
            stmt.setTimestamp(7, actualizadoEn);
            stmt.executeUpdate();
            return "Proveedor creado";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al crear el proveedor: " + e.getMessage(), e);
        }
    }

    // Leer un proveedor por ID
    public ProveedoresM leerProveedor(int id) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ProveedoresM proveedor = new ProveedoresM();
                proveedor.setId(rs.getInt("id"));
                proveedor.setNombre(rs.getString("name"));
                proveedor.setPais(rs.getString("country"));
                proveedor.setTelefono(rs.getString("phone_number"));
                proveedor.setEmail(rs.getString("email"));
                proveedor.setDireccion(rs.getString("address"));
                proveedor.setCreadoEn(rs.getTimestamp("created_at"));
                proveedor.setActualizadoEn(rs.getTimestamp("updated_at"));
                return proveedor;
            } else {
                throw new IllegalArgumentException("No existe un proveedor con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer el proveedor: " + e.getMessage(), e);
        }
    }

    // Actualizar un proveedor
    public String actualizarProveedor() {
        if (existeProveedor(nombre)) {
            throw new IllegalArgumentException("Ya existe un proveedor con el mismo nombre: " + nombre);
        }

        String checkSql = "SELECT COUNT(*) FROM suppliers WHERE id = ?";
        String sql = "UPDATE suppliers SET name = ?, country = ?, phone_number = ?, email = ?, address = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe un proveedor con el ID proporcionado: " + id);
            }

            stmt.setString(1, nombre);
            stmt.setString(2, pais);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, direccion);
            stmt.setTimestamp(6, actualizadoEn);
            stmt.setInt(7, id);
            stmt.executeUpdate();
            return "Proveedor actualizado";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al actualizar el proveedor: " + e.getMessage(), e);
        }
    }

    // Eliminar un proveedor
    public String eliminarProveedor(int id) {
        String checkSql = "SELECT COUNT(*) FROM suppliers WHERE id = ?";
        String deleteSql = "DELETE FROM suppliers WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe un proveedor con el ID proporcionado: " + id);
            }

            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
            return "Proveedor eliminado";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar el proveedor: " + e.getMessage(), e);
        }
    }

    // Obtener todos los proveedores
    public List<ProveedoresM> obtenerProveedores() {
        List<ProveedoresM> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProveedoresM proveedor = new ProveedoresM();
                proveedor.setId(rs.getInt("id"));
                proveedor.setNombre(rs.getString("name"));
                proveedor.setPais(rs.getString("country"));
                proveedor.setTelefono(rs.getString("phone_number"));
                proveedor.setEmail(rs.getString("email"));
                proveedor.setDireccion(rs.getString("address"));
                proveedor.setCreadoEn(rs.getTimestamp("created_at"));
                proveedor.setActualizadoEn(rs.getTimestamp("updated_at"));
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    // Verificar si un proveedor existe por nombre
    private boolean existeProveedor(String nombre) {
        String sql = "SELECT COUNT(*) FROM suppliers WHERE name = ?";
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