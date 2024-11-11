package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspeciesM {

    private int id;
    private String nombre;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private Conexion conexion;
    private Connection conn;



    public EspeciesM() throws SQLException {

        conexion= conexion.getInstancia();
        conn=conexion.getConnection();
    }

    public String crearEspecie() {
        if (existeEspecie(nombre)) {
            throw new IllegalArgumentException("Ya existe una especie con el mismo nombre: " + nombre);
        }

        String sql = "INSERT INTO species (name, created_at, updated_at) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setTimestamp(2, creadoEn);
            stmt.setTimestamp(3, actualizadoEn);
            stmt.executeUpdate();
           return "Especie creada";
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error al crear la especie: " + e.getMessage(), e);
        }
    }

    private boolean existeEspecie(String nombre) {
        String sql = "SELECT COUNT(*) FROM species WHERE name = ?";
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


    public List<EspeciesM> obtenerEspecies() {
        List<EspeciesM> especies = new ArrayList<>();
        String sql = "SELECT * FROM species";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EspeciesM especie = new EspeciesM();
                especie.setId(rs.getInt("id"));
                especie.setNombre(rs.getString("name"));
                especie.setCreadoEn(rs.getTimestamp("created_at"));
                especie.setActualizadoEn(rs.getTimestamp("updated_at"));
                especies.add(especie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return especies;
    }


    public EspeciesM leerEspecie(int id) {
        String sql = "SELECT * FROM species WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                EspeciesM especie = new EspeciesM();
                especie.setId(rs.getInt("id"));
                especie.setNombre(rs.getString("name"));
                especie.setCreadoEn(rs.getTimestamp("created_at"));
                especie.setActualizadoEn(rs.getTimestamp("updated_at"));
                return especie;
            } else {
                throw new IllegalArgumentException("No existe una especie con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer la especie: " + e.getMessage(), e);


        }
    }


    public String actualizarEspecie() {
        if (existeEspecie(nombre)) {
            throw new IllegalArgumentException("Ya existe una especie con el mismo nombre: " + nombre);
        }
        String checkSql = "SELECT COUNT(*) FROM species WHERE id = ?";
        String sql = "UPDATE species SET name = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql) ;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe una especie con el ID proporcionado: " + id);
            }

            stmt.setString(1, nombre);
            stmt.setTimestamp(2, actualizadoEn);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            return "especie actualizada";
        }  catch (SQLException e) {
            throw new IllegalArgumentException("Error al crear la especie: " + e.getMessage(), e);
        }
    }


    public String eliminarEspecie(int id) {
        String checkSql = "SELECT COUNT(*) FROM species WHERE id = ?";
        String deleteSql = "DELETE FROM species WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql) ;
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql))

        {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe una especie con el ID proporcionado: " + id);
            }
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
            return "especie eliminada";
        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new IllegalArgumentException("No se puede eliminar la especie porque está siendo referenciada por otros registros.");
            } else {
                throw new IllegalArgumentException("Error al eliminar la especie: " + e.getMessage());
            }
        }
    }








    @Override
    public String toString() {
        return "EspeciesM{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }

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

}
