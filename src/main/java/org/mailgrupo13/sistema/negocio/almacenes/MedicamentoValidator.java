package org.mailgrupo13.sistema.negocio.almacenes;

import java.sql.Date;

public class MedicamentoValidator {

    public static void validarCampos(String nombre, String dosis, String fabricante, Date fechaCaducidad, boolean sustanciaControlada, int categoriaId) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (dosis == null || dosis.trim().isEmpty()) {
            throw new IllegalArgumentException("La dosis no puede estar vacía");
        }
        if (fabricante == null || fabricante.trim().isEmpty()) {
            throw new IllegalArgumentException("El fabricante no puede estar vacío");
        }
        if (fechaCaducidad == null) {
            throw new IllegalArgumentException("La fecha de caducidad no puede estar vacía");
        }
        if (categoriaId <= 0) {
            throw new IllegalArgumentException("El ID de la categoría debe ser mayor que cero");
        }

        if (nombre.length() < 3 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 50 caracteres");
        }
        if (dosis.length() < 1 || dosis.length() > 20) {
            throw new IllegalArgumentException("La dosis debe tener entre 1 y 20 caracteres");
        }
        if (fabricante.length() < 3 || fabricante.length() > 50) {
            throw new IllegalArgumentException("El fabricante debe tener entre 3 y 50 caracteres");
        }

        if (!nombre.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras, números y espacios");
        }
        if (!dosis.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("La dosis solo debe contener letras, números y espacios");
        }
        if (!fabricante.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("El fabricante solo debe contener letras, números y espacios");
        }
    }
}