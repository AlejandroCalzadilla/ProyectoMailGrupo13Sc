package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
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
    conexion = Conexion.getInstancia();
    conn = conexion.getConnection();
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
  public String crearCliente() {
    if (!existeUsuario(idUsuario)) {
      throw new IllegalArgumentException("No existe un usuario con el ID proporcionado: " + idUsuario);
    }

    String sql = "INSERT INTO customers (first_name, last_name, phone_number, gender, birthdate, user_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
      return "Cliente creado";
    } catch (SQLException e) {
      throw new IllegalArgumentException("Error al crear el cliente: " + e.getMessage(), e);
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
  public ClienteM leerCliente(int id) {
    String sql = "SELECT * FROM customers WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
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
        return cliente;
      } else {
        throw new IllegalArgumentException("No existe un cliente con el ID proporcionado: " + id);
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException("Error al leer el cliente: " + e.getMessage(), e);
    }
  }

  // Actualizar un cliente
  public String actualizarCliente() {
    if (!existeUsuario(idUsuario)) {
      throw new IllegalArgumentException("No existe un usuario con el ID proporcionado: " + idUsuario);
    }

    String checkSql = "SELECT COUNT(*) FROM customers WHERE id = ?";
    String sql = "UPDATE customers SET first_name = ?, last_name = ?, phone_number = ?, gender = ?, birthdate = ?, updated_at = ? WHERE id = ?";
    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      checkStmt.setInt(1, id);
      ResultSet rs = checkStmt.executeQuery();
      if (rs.next() && rs.getInt(1) == 0) {
        throw new IllegalArgumentException("No existe un cliente con el ID proporcionado: " + id);
      }

      stmt.setString(1, nombre);
      stmt.setString(2, apellido);
      stmt.setString(3, telefono);
      stmt.setString(4, genero);
      stmt.setDate(5, fechaNacimiento);
      stmt.setTimestamp(6, actualizadoEn);
      stmt.setInt(7, id);
      stmt.executeUpdate();
      return "Cliente actualizado";
    } catch (SQLException e) {
      throw new IllegalArgumentException("Error al actualizar el cliente: " + e.getMessage(), e);
    }
  }

  // Eliminar un cliente
  public boolean eliminarCliente(int id) {
    String checkSql = "SELECT COUNT(*) FROM customers WHERE id = ?";
    String deleteSql = "DELETE FROM customers WHERE id = ?";
    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
         PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
      checkStmt.setInt(1, id);
      ResultSet rs = checkStmt.executeQuery();
      if (rs.next() && rs.getInt(1) == 0) {
        throw new IllegalArgumentException("No existe un cliente con el ID proporcionado: " + id);
      }

      deleteStmt.setInt(1, id);
      deleteStmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new IllegalArgumentException("Error al eliminar el cliente: " + e.getMessage(), e);
    }
  }

  // Verificar si un usuario existe por ID
  private boolean existeUsuario(int idUsuario) {
    String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, idUsuario);
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