package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClienteM {

  private int id;
  private String nombre;
  private String apellido;
  private String telefono;
  private String genero;
  private Date fechaNacimiento;
  private int idUsuario;
  private Timestamp creadoEn;
  private Timestamp actualizadoEn;

  private Conexion conexion;
  private Connection conn;

  public ClienteM() throws SQLException {

    conexion= conexion.getInstancia();
    conn=conexion.getConnection();
  }

  // Getters y Setters

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }

  public String getApellido() { return apellido; }
  public void setApellido(String apellido) { this.apellido = apellido; }

  public String getTelefono() { return telefono; }
  public void setTelefono(String telefono) { this.telefono = telefono; }

  public String getGenero() { return genero; }
  public void setGenero(String genero) { this.genero = genero; }

  public Date getFechaNacimiento() { return fechaNacimiento; }
  public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

  public int getIdUsuario() { return idUsuario; }
  public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

  public Timestamp getCreadoEn() { return creadoEn; }
  public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

  public Timestamp getActualizadoEn() { return actualizadoEn; }
  public void setActualizadoEn(Timestamp actualizadoEn) { this.actualizadoEn = actualizadoEn; }

  // Consultas CRUD

  // Crear un cliente
  public boolean crearCliente() {
    String sql = "INSERT INTO customers (first_name, last_name, phone_number, gender, birthdate, id_user, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, nombre);
      stmt.setString(2, apellido);
      stmt.setString(3, telefono);
      stmt.setString(4, genero);
      stmt.setDate(5, fechaNacimiento);
      stmt.setInt(6, idUsuario);
      stmt.setTimestamp(7, creadoEn);
      stmt.setTimestamp(8, actualizadoEn);
      stmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public List<ClienteM> obtenerClientes() {
    List<ClienteM> clientes = new ArrayList<>();
    String sql = "SELECT * FROM customers";
    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        ClienteM cliente = new ClienteM();
        cliente.setId(rs.getInt("id"));
        cliente.setNombre(rs.getString("first_name"));
        cliente.setApellido(rs.getString("last_name"));
        cliente.setTelefono(rs.getString("phone_number"));
        cliente.setGenero(rs.getString("gender"));
        cliente.setFechaNacimiento(rs.getDate("birthdate"));
        cliente.setIdUsuario(rs.getInt("user_id"));
        cliente.setCreadoEn(rs.getTimestamp("created_at"));
        cliente.setActualizadoEn(rs.getTimestamp("updated_at"));
        clientes.add(cliente);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return clientes;
  }


  // Leer un cliente por ID
  public boolean leerCliente(int id) {
    String sql = "SELECT * FROM customers WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        this.id = rs.getInt("id");
        nombre = rs.getString("first_name");
        apellido = rs.getString("last_name");
        telefono = rs.getString("phone_number");
        genero = rs.getString("gender");
        fechaNacimiento = rs.getDate("birthdate");
        idUsuario = rs.getInt("user_id");
        creadoEn = rs.getTimestamp("created_at");
        actualizadoEn = rs.getTimestamp("updated_at");
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  // Actualizar un cliente
  public boolean actualizarCliente() {
    String sql = "UPDATE customers SET first_name = ?, last_name = ?, phone_number = ?, gender = ?, birthdate = ?, id_user = ?, updated_at = ? WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, nombre);
      stmt.setString(2, apellido);
      stmt.setString(3, telefono);
      stmt.setString(4, genero);
      stmt.setDate(5, fechaNacimiento);
      stmt.setInt(6, idUsuario);
      stmt.setTimestamp(7, actualizadoEn);
      stmt.setInt(8, id);
      stmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Eliminar un cliente
  public boolean eliminarCliente(int id) {
    String sql = "DELETE FROM customers WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
