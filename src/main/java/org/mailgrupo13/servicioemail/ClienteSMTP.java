package org.mailgrupo13.servicioemail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteSMTP {

    private static final String SERVIDOR = "mail.tecnoweb.org.bo";
    private static final int PUERTO = 25;
    private static final String EMISOR = "grupo13sc@tecnoweb.org.bo";

    private static void enviarComando(DataOutputStream salida, BufferedReader entrada, String comando) throws IOException {
        salida.writeBytes(comando);
        String respuesta = leerRespuesta(entrada);

        int codigoRespuesta = Integer.parseInt(respuesta.substring(0, 3));
        if (codigoRespuesta >= 400) {
            throw new IOException("No se pudo enviar el correo, error durante el comando: " + comando + ".\nError" + respuesta);
        }
    }

    static protected String leerRespuesta(BufferedReader in) throws IOException {
        StringBuilder lines = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            lines.append(line).append("\n");
            if (line.length() > 3 && line.charAt(3) == ' ') break;
        }
        if (line == null) {
            throw new IOException("S : Server unawares closed the connection.");
        }
        return lines.toString();
    }

    public void enviarCorreo(String usuarioReceptor, String subject, String mensaje) {
        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("S : " + entrada.readLine());

            enviarComando(salida, entrada, "HELO " + SERVIDOR + "\r\n");
            enviarComando(salida, entrada, "MAIL FROM: " + EMISOR + " \r\n");
            enviarComando(salida, entrada, "RCPT TO: " + usuarioReceptor + " \r\n");
            enviarComando(salida, entrada, "DATA\r\n");
            enviarComando(salida, entrada, "Subject: " + subject + "\r\n" + mensaje + "\r\n.\r\n");
            enviarComando(salida, entrada, "QUIT\r\n");

        } catch (Exception e) {
            System.out.println("S : No se pudo conectar con el servidor, error: " + e.getMessage());
        }
    }
}
