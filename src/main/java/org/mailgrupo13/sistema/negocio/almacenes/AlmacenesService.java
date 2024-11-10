package org.mailgrupo13.sistema.negocio.almacenes;

import org.mailgrupo13.sistema.modelo.AlmacenesM;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AlmacenesService {

  public  final AlmacenesM almacenesM;


    public AlmacenesService(AlmacenesM almacenesM) {
       this.almacenesM=almacenesM;

    }
    public String obtenerAlmacenes() throws SQLException {
        return mapear2(almacenesM.obtenerAlmacenes());
    }

    public String agregarAlmacen(String nombre, String ubicacion, String descripcion) throws SQLException {
            AlmacenValidator.validarCampos(nombre, ubicacion, descripcion);
            AlmacenesM almacenesMObj = cargar(0, nombre, ubicacion, descripcion);
            return almacenesMObj.crearAlmacen();

    }

    public String actualizarAlmacen(int id, String nombre, String ubicacion, String descripcion) throws SQLException {
            AlmacenValidator.validarCampos(nombre, ubicacion, descripcion);
            AlmacenesM almacenesMObj = cargar(id, nombre, ubicacion, descripcion);
            return  almacenesMObj.actualizarAlmacen();

    }

    public String eliminarAlmacen(int id) throws SQLException {
         return  almacenesM.eliminarAlmacen(id);
    }


    public String mapear2(List<AlmacenesM> almacenesM) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-20s %-20s  %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "Ubicacion", "Descpripcion"));
        sb.append("---------------------------------------------------------------------------\n");
        for (AlmacenesM almacenM : almacenesM) {
            sb.append(String.format(format,
                    almacenM.getId(),
                    almacenM.getNombre(),
                    almacenM.getUbicacion(),
                    almacenM.getDescripcion()
            ));
        }
        return sb.toString();
    }



    private AlmacenesM cargar(int id, String nombre, String ubicacion, String descripcion) throws SQLException {
        AlmacenesM almacenesMObj = new AlmacenesM();
        almacenesMObj.setId(id);
        almacenesMObj.setNombre(nombre);
        almacenesMObj.setUbicacion(ubicacion);
        almacenesMObj.setDescripcion(descripcion);
        almacenesMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        almacenesMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return almacenesMObj;
    }
}
