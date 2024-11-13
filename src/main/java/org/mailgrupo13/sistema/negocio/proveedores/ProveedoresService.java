package org.mailgrupo13.sistema.negocio.proveedores;

import org.mailgrupo13.sistema.modelo.ProveedoresM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresService {
  private ProveedoresM proveedoresM;


     ProveedoresService(ProveedoresM proveedoresM) {
         this.proveedoresM = proveedoresM;
     }



    public ProveedoresN leerProveedor(int id ) throws SQLException {

        ProveedoresN proveedor = new ProveedoresN();
        this.proveedoresM=this.proveedoresM.leerProveedor(id);
        proveedor.setNombre(this.proveedoresM.getNombre());
        proveedor.setPais(this.proveedoresM.getPais());
        proveedor.setTelefono(this.proveedoresM.getTelefono());
        proveedor.setEmail(this.proveedoresM.getEmail());
        proveedor.setDireccion(this.proveedoresM.getDireccion());
        proveedor.setCreadoEn(Timestamp.valueOf(this.proveedoresM.getCreadoEn().toString()));
        proveedor.setActualizadoEn(Timestamp.valueOf(this.proveedoresM.getActualizadoEn().toString()));
        return proveedor;
    }


    public String obtenerProveedores() throws SQLException {
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










    private String mapear(List<ProveedoresM> proveedoresMList) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-20s %-15s %-15s %-30s %-30s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "Pais", "Telefono", "Email", "Direccion", "Creado En", "Actualizado En"));
        sb.append("------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (ProveedoresM proveedoresM : proveedoresMList) {
            sb.append(String.format(format,
                    proveedoresM.getId(),
                    proveedoresM.getNombre(),
                    proveedoresM.getPais(),
                    proveedoresM.getTelefono(),
                    proveedoresM.getEmail(),
                    proveedoresM.getDireccion(),
                    proveedoresM.getCreadoEn(),
                    proveedoresM.getActualizadoEn()));
        }
        return sb.toString();
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
