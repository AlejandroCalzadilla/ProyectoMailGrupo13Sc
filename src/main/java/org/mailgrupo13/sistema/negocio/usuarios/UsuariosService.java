package org.mailgrupo13.sistema.negocio.usuarios;

import org.mailgrupo13.sistema.modelo.UsuariosM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UsuariosService {
    private UsuariosM usuariosM;

    public UsuariosService(UsuariosM usuariosM) {
        this.usuariosM = usuariosM;
    }


    public String obtenerUsuarios() throws SQLException {
        return mapear(usuariosM.obtenerUsuarios());
    }

    public UsuariosN leerUsuario(int id) throws SQLException {
        return  mapearbyid(usuariosM.leerUsuario(id));
    }


    public String agregarUsuario(String email, String password, String nombre) throws SQLException {
            UsuariosValidator.validarCampos(email, password, nombre);
            UsuariosM usuariosMObj = cargar(0, email, password, nombre);
            return  usuariosMObj.crearUsuario();
    }

    public String actualizarUsuario(int id, String email, String password, String nombre) throws SQLException {
            UsuariosValidator.validarCampos(email, password, nombre);
            UsuariosM usuariosMObj = cargar(id, email, password, nombre);
            return usuariosMObj.actualizarUsuario();

    }

    public String eliminarUsuario(int id) throws SQLException {
        return usuariosM.eliminarUsuario(id);
    }





    private String mapear(List<UsuariosM> usuariosMList) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-20s %-20s  %-30s%n";
        sb.append(String.format(format, "ID", "Email", "Password", "Nombre"));
        sb.append("------------------------------------------------------------------------------------------------------------\n");
        for (UsuariosM usuariosM : usuariosMList) {
            sb.append(String.format(format,
                    usuariosM.getId(),
                    usuariosM.getEmail(),
                    usuariosM.getPassword(),
                    usuariosM.getNombre()
            ));
        }
        return sb.toString();
    }

    private UsuariosN mapearbyid(UsuariosM usuarioM) throws SQLException {
        UsuariosN usuariosN = new UsuariosN();
           usuariosN.setId(usuarioM.getId());
           usuariosN.setEmail(usuarioM.getEmail());
           usuariosN.setPassword(usuarioM.getPassword());
           usuariosN.setNombre(usuarioM.getNombre());

           return usuariosN;
    }


    private UsuariosM cargar(int id, String email, String password, String nombre) throws SQLException {
        UsuariosM usuariosMObj = new UsuariosM();
        usuariosMObj.setId(id);
        usuariosMObj.setEmail(email);
        usuariosMObj.setPassword(password);
        usuariosMObj.setNombre(nombre);
       // usuariosMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
       // usuariosMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return usuariosMObj;
    }






}
