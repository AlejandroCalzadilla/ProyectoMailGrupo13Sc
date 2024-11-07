package org.mailgrupo13.servicioemail;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServicioEmail {
    private boolean conectado;
    private ClientePOP clientePOP;
    private ClienteSMTP clienteSMTP;

    public ServicioEmail() {
        this.conectado = true;
        this.clientePOP = new ClientePOP();
        this.clienteSMTP = new ClienteSMTP();
    }

    public void iniciarClientePOP() throws IOException {
        clientePOP.conectar();
        int totalCorreos = clientePOP.obtenerTotalDeCorreos();
        for (int i = 10; i <= 12; i++) {
            //String correo = clientePOP.obtenerCorreo(i);
            String correo = clientePOP.obtenerCorreoYEliminar(i);
            // acá guardamos en la base de datos el correo
            guardarCorreo(correo);
            // Luego de guardar el correo, evaluamos y respondemos
            evaluarYResponderCorreo(correo);
        }
        clientePOP.desconectar();
    }

    private void guardarCorreo(String correo) {
        String messageId = extraerMessageId(correo);
        // TODO: Guardar el correo en la base de datos
    }

    public void detener() {
        conectado = false;
        System.out.println("S : El cliente POP se ha detenido automáticamente.");
    }

    public static String extraerMessageId(String correo) {
        for (String line : correo.split("\n")) {
            if (line.startsWith("Message-ID:")) {
                return line.substring(11).trim();
            }
        }
        return null;
    }

    private String extraerRemitente(String correo) {
        for (String line : correo.split("\n")) {
            if (line.startsWith("From:")) {
                return line.substring(6).trim();
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        ServicioEmail servicioEmail = new ServicioEmail();
        servicioEmail.iniciarClientePOP();

        // Programar el apagado automático después de 15 segundos
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(servicioEmail::detener, 25, TimeUnit.SECONDS);

        while (servicioEmail.conectado) {
            servicioEmail.clientePOP.revisarCorreos();

            try {
                Thread.sleep(10000); // Esperar 10 segundos entre revisiones
            } catch (InterruptedException e) {
                System.out.println("Interrupción en el ciclo de revisión: " + e.getMessage());
            }
        }

        // Desconectar antes de salir
        scheduler.shutdown();
    }

    private void evaluarYResponderCorreo(String correo) {
        String subject = extraerSubject(correo);
        String remitente = extraerRemitente(correo);
        if (subject != null) {
            String respuesta = ejecutarConsulta(subject);
            clienteSMTP.enviarCorreo(remitente, "Resultado de la Consulta", respuesta);
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

    private static String ejecutarConsulta(String subject) {
        String query = parsearQuery(subject);
        String respuestaConsulta = "";
        if (query != null && subject.contains("PATTERN")) {
            respuestaConsulta = "Entro a ejecutar consulta";
            /*try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
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
                respuestaConsulta = queryResult.toString();
            } catch (Exception e) {
                System.out.println("Error ejecutando la consulta: " + e.getMessage());
            }*/
        } else {
            respuestaConsulta = "No se pudo ejecutar la consulta";
        }
        return respuestaConsulta;
    }

    private static String parsearQuery(String subject) {
        // Implementa la lógica para parsear el subject y generar la consulta SQL
        // Por ejemplo, si el subject es "PATTERN: SELECT * FROM users"
        if (subject.startsWith("PATTERN:")) {
            return subject.substring(8).trim();
        }
        return null;
    }
}
