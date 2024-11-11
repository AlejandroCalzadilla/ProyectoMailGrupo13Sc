package org.mailgrupo13.sistema.negocio.mascotas;


import org.mailgrupo13.sistema.modelo.MascotasM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MascotasService {

    private MascotasM mascotasM;

    public MascotasService(MascotasM mascotasM) {
        this.mascotasM = mascotasM;
    }




    public MascotasN leerMascota(int id) throws SQLException {
          MascotasN mascota = new MascotasN();
          this.mascotasM=this.mascotasM.leerMascota(id);
          mascota.setNombre(this.mascotasM.getNombre());
            mascota.setPeso(this.mascotasM.getPeso());
            mascota.setColor(this.mascotasM.getColor());
            mascota.setFechaNacimiento(Date.valueOf(this.mascotasM.getFechaNacimiento().toString()));
            mascota.setUrlFoto(this.mascotasM.getUrlFoto());
            mascota.setIdCliente(this.mascotasM.getIdCliente());
            mascota.setIdRaza(this.mascotasM.getIdRaza());
            mascota.setCreadoEn(Timestamp.valueOf(this.mascotasM.getCreadoEn().toString()));
            mascota.setActualizadoEn(Timestamp.valueOf(this.mascotasM.getActualizadoEn().toString()));

          return mascota;

    }


    public String obtenerMascotas() throws SQLException {
        return mapeard(mascotasM.obtenerMascotas());
    }

    public String agregarMascota(String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) throws SQLException {
        MascotasValidator.validarCampos(nombre, peso, color, fechaNacimiento, urlFoto, idCliente, idRaza);
        MascotasM mascotasMObj = cargar(0, nombre, peso, color, fechaNacimiento, urlFoto, idCliente, idRaza);
        mascotasMObj.crearMascota();
        return "Mascota agregada con éxito";
    }

    public String actualizarMascota(int id, String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) throws SQLException {
        MascotasValidator.validarCampos(nombre, peso, color, fechaNacimiento, urlFoto, idCliente, idRaza);
        MascotasM mascotasMObj = cargar(id, nombre, peso, color, fechaNacimiento, urlFoto, idCliente, idRaza);
        return mascotasMObj.actualizarMascota();
    }



    public String eliminarMascota(int id) throws SQLException {
        return mascotasM.eliminarMascota(id);
    }

    private MascotasM cargar(int id, String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) throws SQLException {
        MascotasM mascotasMObj = new MascotasM();
        mascotasMObj.setId(id);
        mascotasMObj.setNombre(nombre);
        mascotasMObj.setPeso(peso);
        mascotasMObj.setColor(color);
        mascotasMObj.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimiento));
        mascotasMObj.setUrlFoto(urlFoto);
        mascotasMObj.setIdCliente(idCliente);
        mascotasMObj.setIdRaza(idRaza);
        mascotasMObj.setCreadoEn(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        mascotasMObj.setActualizadoEn(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        return mascotasMObj;
    }



    public String mapeard(List<MascotasM> mascotasMList) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-10s %-10s %-15s %-30s %-10s %-10s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "Peso", "Color", "Fecha Nac.", "URL Foto", "ID Cliente", "ID Raza", "Creado En", "Actualizado En"));
        sb.append("------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (MascotasM mascotaM : mascotasMList) {
            sb.append(String.format(format,
                    mascotaM.getId(),
                    mascotaM.getNombre(),
                    mascotaM.getPeso(),
                    mascotaM.getColor(),
                    mascotaM.getFechaNacimiento(),
                    mascotaM.getUrlFoto(),
                    mascotaM.getIdCliente(),
                    mascotaM.getIdRaza(),
                    mascotaM.getCreadoEn(),
                    mascotaM.getActualizadoEn()));
        }
        return sb.toString();
    }





}
