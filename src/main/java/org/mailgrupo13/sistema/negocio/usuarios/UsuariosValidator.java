package org.mailgrupo13.sistema.negocio.usuarios;

import java.util.regex.Pattern;

public class UsuariosValidator {

    public static void validarCampos(String email,String password, String nombre) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (!esEmailValido(email)) {
            throw new IllegalArgumentException("El email no es válido");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (email.length() < 5) {
            throw new IllegalArgumentException("El email es muy corto");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("La contraseña es muy corta");
        }
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre es muy corto");
        }
    }

    private static boolean esEmailValido(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

}
