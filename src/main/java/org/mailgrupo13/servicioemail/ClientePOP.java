package org.mailgrupo13.servicioemail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientePOP {
    static final String HOST = "mail.tecnoweb.org.bo";
    static final int PORT = 110;
    static final String USER = "grupo13sc";
    static final String PASS = "grup013grup013*";
    static final String DB_URL = "jdbc:postgresql://mail.tecnoweb.org.bo/db_agenda";
    static final String DB_USER = "agenda";
    static final String DB_PASS = "agendaagenda";

    private Socket socket;
    private BufferedReader entrada;
    private DataOutputStream salida;
    private boolean conectado = true;

    public void conectar() throws IOException {
        socket = new Socket(HOST, PORT);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new DataOutputStream(socket.getOutputStream());
        System.out.println("S : " + entrada.readLine());

        enviarComando(salida, entrada, "USER " + USER + "\r\n");
        enviarComando(salida, entrada, "PASS " + PASS + "\r\n");
    }

    private static String enviarComando(DataOutputStream salida, BufferedReader entrada, String comando) throws IOException {
        System.out.print("C : " + comando);
        salida.writeBytes(comando);
        String response;
        if (comando.startsWith("RETR") || comando.startsWith("LIST")) {
            response = leerRespuestaMultilinea(entrada);
            System.out.println("S : " + response);
            if (comando.startsWith("RETR")) {
                evaluarCorreo(response);
            }
            return response;
        }
        return entrada.readLine();
    }

    static protected String leerRespuestaMultilinea(BufferedReader in) throws IOException {
        StringBuilder lines = new StringBuilder();
        while (true) {
            String line = in.readLine();
            if (line == null) throw new IOException("S : Server unawares closed the connection.");
            if (line.equals(".")) break;
            if (line.startsWith(".")) line = line.substring(1);
            lines.append("\n").append(line);
        }
        return lines.toString();
    }

    private static void evaluarCorreo(String correo) {
        String subject = extraerSubject(correo);
        if (subject != null) {
            ejecutarConsulta(subject);
        }
    }

    private static String extraerSubject(String correo) {
        for (String line : correo.split("\n")) {
            if (line.startsWith("Subject:")) {
                return line.substring(8).trim();
            }
        }
        return null;
    }

    private static void ejecutarConsulta(String subject) {
        String query = parsearQuery(subject);
        if (query != null && subject.contains("PATTERN")) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                StringBuilder queryResult = new StringBuilder();
                int rowCount = 1;

                while (rs.next()) {
                    queryResult.append("Row ").append(rowCount).append(": ");
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        queryResult.append(rs.getString(i).trim()).append(" ");
                    }
                    queryResult.append("\r\n");
                    rowCount++;
                }
                //ClienteSMTP.enviarCorreo(SMTP_SERVER, SMTP_PORT, SMTP_USER_EMISOR, SMTP_USER_RECEPTOR, "Resultado de la Consulta", queryResult.toString());
                System.out.print(queryResult.toString());
            } catch (Exception e) {
                System.out.println("Error ejecutando la consulta: " + e.getMessage());
            }
        }
    }

    private static String parsearQuery(String subject) {
        // Implementa la lógica para parsear el subject y generar la consulta SQL
        // Por ejemplo, si el subject es "PATTERN: SELECT * FROM users"
        if (subject.startsWith("PATTERN:")) {
            return subject.substring(8).trim();
        }
        return null;
    }

    public int obtenerTotalDeCorreos() throws IOException {
        String response = enviarComando(salida, entrada, "STAT\r\n");
        response = response.substring(4, response.length() - 4);
        int i = 1;
        while (response.charAt(i) != ' ') {
            i++;
        }
        response = response.substring(0, i);
        return Integer.parseInt(response);
    }

    public void desconectar() {
        try {
            enviarComando(salida, entrada, "QUIT\r\n");
            entrada.close();
            salida.close();
            socket.close();
            System.out.println("S : Conexión finalizada.");
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public void revisarCorreos() {
        try {
            conectar();
            String x;
            x = enviarComando(salida, entrada, "LIST\r\n");
            System.out.println(x);
        } catch (Exception e) {
            System.out.println("Error al revisar correos: " + e.getMessage());
        }
    }
}