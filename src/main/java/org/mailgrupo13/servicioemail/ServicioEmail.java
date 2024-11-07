package org.mailgrupo13.servicioemail;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServicioEmail {
    private boolean conectado;
    private ClientePOP clientePOP;
    private ClienteSMTP clienteSMTP;
    private ComandoEmail comandoEmail;

    public ServicioEmail() {
        this.conectado = true;
        this.clientePOP = new ClientePOP();
        this.clienteSMTP = new ClienteSMTP();
        this.comandoEmail = new ComandoEmail();
    }

    public void iniciarClientePOP() throws IOException {
        clientePOP.conectar();
        int totalCorreos = clientePOP.obtenerTotalDeCorreos();
        for (int i = 1; i <= totalCorreos; i++) {
            String correo = clientePOP.obtenerCorreo(i);
            //String correo = clientePOP.obtenerCorreoYEliminar(i);
            // acá guardamos en la base de datos el correo
            guardarCorreo(correo);
            // After saving the email, we evaluate and respond if necessary
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
        /*ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(servicioEmail::detener, 25, TimeUnit.SECONDS);
        while (servicioEmail.conectado) {
            servicioEmail.clientePOP.revisarCorreos();

            try {
                Thread.sleep(10000); // Esperar 10 segundos entre revisiones
            } catch (InterruptedException e) {
                System.out.println("Interrupción en el ciclo de revisión: " + e.getMessage());
            }
        }
        scheduler.shutdown();*/
    }

    private void evaluarYResponderCorreo(String correo) {
        String subject = extraerSubject(correo);
        String remitente = extraerRemitente(correo);
        if (subject != null) {
            String respuesta = procesarCorreo(subject);
            if (!respuesta.isEmpty()) {
                System.out.println(respuesta);
                //clienteSMTP.enviarCorreo(remitente, "Resultado de la Consulta", respuesta);
            }
        }
    }

    private static String extraerSubject(String correo) {
        for (String line : correo.split("\n")) {
            if (line.startsWith("Subject:")) {
                return line.substring(9).trim();
            }
        }
        return null;
    }

    public String procesarCorreo(String subject) {
        String respuesta = comandoEmail.evaluarYEjecutar(subject);
        return respuesta;
    }

    private static String evaluarSubject(String subject) {
        String query = parsearQuery(subject);
        String respuestaConsulta = "";
        // Verificar el patrón en el subject
        if (query != null && subject.contains("PATTERN")) {
            respuestaConsulta = "Entro a ejecutar consulta";
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
