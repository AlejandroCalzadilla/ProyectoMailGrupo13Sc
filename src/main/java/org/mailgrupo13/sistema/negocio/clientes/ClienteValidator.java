package org.mailgrupo13.sistema.negocio.clientes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ClienteValidator {

    public static  void validarCampos(String nombre, String apellido, String telefono, String genero, String fechanaciemiento, int idUsuario) {
        if (nombre == null || nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        if (apellido == null || apellido.isEmpty())
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (genero == null || genero.isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío");
        }
        if (!esFechaValida(fechanaciemiento)) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida");
        }
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("El ID de usuario debe ser mayor que 0");
        }
        if(nombre.length()<3 )
            throw new IllegalArgumentException("El nombre es muy corto");

        if(apellido.length()<3)
            throw new IllegalArgumentException("El apellido es muy corto");


    }

    public static boolean esFechaValida(String fechaStr) {
        try {
            // Intentamos parsear la fecha con el formato esperado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(fechaStr, formatter);
            return true;  // Si no lanza excepción, la fecha es válida
        } catch (DateTimeParseException e) {
            return false;  // Si lanza excepción, el formato no es válido
        }
    }
}
