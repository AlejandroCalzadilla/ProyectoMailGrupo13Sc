package org.mailgrupo13.sistema.negocio.proveedores;

import org.mailgrupo13.sistema.modelo.ProveedoresM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresService {
  private final ProveedoresM proveedoresM;


     ProveedoresService(ProveedoresM proveedoresM) {
         this.proveedoresM = proveedoresM;
     }



    public List<ProveedoresN> obtenerProveedores() throws SQLException {
        return mapear(proveedoresM.obtenerProveedores());
    }


    public String agregarProveedor(String nombre, String pais, String telefono, String email, String direccion) throws SQLException {

            ProveedorValidator.validarCampos(nombre, pais, telefono, email, direccion);
            ProveedoresM proveedoresMObj = cargar(0, nombre, pais, telefono, email, direccion);
            return  proveedoresMObj.crearProveedor();

    }

    public String actualizarProveedor(int id, String nombre, String pais, String telefono, String email, String direccion) throws SQLException {
            ProveedorValidator.validarCampos(nombre, pais, telefono, email, direccion);
            ProveedoresM proveedoresMObj = cargar(id, nombre, pais, telefono, email, direccion);
            return proveedoresMObj.actualizarProveedor();

    }

    public String eliminarProveedor(int id) throws SQLException {
        return proveedoresM.eliminarProveedor(id);
    }










    private List<ProveedoresN> mapear(List<ProveedoresM> proveedoresMList) throws SQLException {
        List<ProveedoresN> proveedoresNList = new ArrayList<>();
        for (ProveedoresM proveedoresM : proveedoresMList) {
            ProveedoresN proveedoresN = new ProveedoresN();
            proveedoresN.setId(proveedoresM.getId());
            proveedoresN.setNombre(proveedoresM.getNombre());
            proveedoresN.setPais(proveedoresM.getPais());
            proveedoresN.setTelefono(proveedoresM.getTelefono());
            proveedoresN.setEmail(proveedoresM.getEmail());
            proveedoresN.setDireccion(proveedoresM.getDireccion());
            proveedoresN.setCreadoEn(proveedoresM.getCreadoEn());
            proveedoresN.setActualizadoEn(proveedoresM.getActualizadoEn());
            proveedoresNList.add(proveedoresN);
        }
        return proveedoresNList;
    }

    private ProveedoresM cargar(int id, String nombre, String pais, String telefono, String email, String direccion) throws SQLException {
        ProveedoresM proveedoresMObj = new ProveedoresM();
        proveedoresMObj.setId(id);
        proveedoresMObj.setNombre(nombre);
        proveedoresMObj.setPais(pais);
        proveedoresMObj.setTelefono(telefono);
        proveedoresMObj.setEmail(email);
        proveedoresMObj.setDireccion(direccion);
        proveedoresMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        proveedoresMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return proveedoresMObj;
    }


}
