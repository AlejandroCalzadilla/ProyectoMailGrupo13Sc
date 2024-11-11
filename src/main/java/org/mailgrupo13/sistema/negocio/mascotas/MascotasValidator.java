package org.mailgrupo13.sistema.negocio.mascotas;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MascotasValidator {

    public static void validarCampos(String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) {
        if (nombre == null || nombre.isEmpty() || nombre.length()<3 || nombre.length()>50) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 50 caracteres");
        }
        if (peso == null || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que 0");
        }
        if (color == null || color.isEmpty() || !color.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("El color solo puede contener letras");
        }
        if (!esFechaValida(fechaNacimiento)) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida");
        }
        if (urlFoto == null || urlFoto.isEmpty() || !esUrlValida(urlFoto)) {
            throw new IllegalArgumentException("La URL de la foto no es válida");
        }
        if (idCliente <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser mayor que 0");
        }
        if (idRaza <= 0) {
            throw new IllegalArgumentException("El ID de la raza debe ser mayor que 0");
        }
    }

    // Método para validar la fecha en formato "yyyy-MM-dd"
    private static boolean esFechaValida(String fechaStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(fechaStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean esUrlValida(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }



}