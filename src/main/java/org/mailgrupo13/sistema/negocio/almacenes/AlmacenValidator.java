package org.mailgrupo13.sistema.negocio.almacenes;

public class AlmacenValidator {

    public static void validarCampos(String nombre, String ubicacion, String descripcion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación no puede estar vacía");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }

        if (nombre.length() < 7 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 7 y 50 caracteres");
        }
        if (ubicacion.length() < 7 || ubicacion.length() > 50) {
            throw new IllegalArgumentException("La ubicación debe tener entre 7 y 50 caracteres");
        }
        if (descripcion.length() < 10 ||  descripcion.length() > 255) {
            throw new IllegalArgumentException("La descripción debe tener entre 10 y  255 caracteres");
        }

        if (!nombre.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras, números y espacios");
        }
        if (!ubicacion.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("La ubicación solo debe contener letras, números y espacios");
        }
    }

}
