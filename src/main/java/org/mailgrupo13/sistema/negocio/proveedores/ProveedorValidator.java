package org.mailgrupo13.sistema.negocio.proveedores;

public class ProveedorValidator {

    public static void validarCampos(String nombre, String pais, String telefono, String email, String direccion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (pais == null || pais.trim().isEmpty()) {
            throw new IllegalArgumentException("El país no puede estar vacío");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }

        if (nombre.length() < 3 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 50 caracteres");
        }
        if (pais.length() < 3 || pais.length() > 50) {
            throw new IllegalArgumentException("El país debe tener entre 3 y 50 caracteres");
        }
        if (telefono.length() < 7 || telefono.length() > 15) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 15 caracteres");
        }
        if (direccion.length() < 10 || direccion.length() > 255) {
            throw new IllegalArgumentException("La dirección debe tener entre 10 y 255 caracteres");
        }

        if (!nombre.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras, números y espacios");
        }
        if (!pais.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("El país solo debe contener letras, números y espacios");
        }
        if (!telefono.matches("\\+?[0-9]+")) {
            throw new IllegalArgumentException("El teléfono solo debe contener números y opcionalmente un signo más al inicio");
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
    }
}
