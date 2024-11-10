package org.mailgrupo13.sistema.negocio.categorias;

import org.mailgrupo13.sistema.modelo.CategoriasM;
import org.mailgrupo13.sistema.modelo.EspeciesM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    private  CategoriasM categoriasM;
      public CategoriaService(  CategoriasM categoriasM) {
            this.categoriasM = categoriasM;

      }



    public String obtenerCategorias() throws SQLException {
        return mapeard(categoriasM.obtenerCategorias());
    }

    public String agregarCategoria(String nombre) throws SQLException {
        validarCampos(nombre);
        CategoriasM categoriasMObj = cargar(0, nombre);
        categoriasMObj.crearCategoria();
        return "Categoría agregada con éxito";
    }

    public String actualizarCategoria(int id, String nombre) throws SQLException {
        validarCampos(nombre);
        CategoriasM categoriasMObj = cargar(id, nombre);
        return categoriasMObj.actualizarCategoria();

    }

    public String eliminarCategoria(int id) throws SQLException {
        return categoriasM.eliminarCategoria(id);
    }



    public String mapeard(List<CategoriasM> especiesM) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "Creado En", "Actualizado En"));
        sb.append("---------------------------------------------------------------------------\n");
        for (CategoriasM especieM : especiesM) {
            sb.append(String.format(format,
                    especieM.getId(),
                    especieM.getNombre(),
                    especieM.getCreadoEn(),
                    especieM.getActualizadoEn()));
        }
        return sb.toString();
    }









    private CategoriasM cargar(int id, String nombre) throws SQLException {
        CategoriasM categoriasMObj = new CategoriasM();
        categoriasMObj.setId(id);
        categoriasMObj.setNombre(nombre);
        categoriasMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        categoriasMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return categoriasMObj;
    }

    private void validarCampos(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (nombre.length() < 5 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 5 y 50 caracteres");
        }

        if (!nombre.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras, números y espacios");
        }

    }

}
