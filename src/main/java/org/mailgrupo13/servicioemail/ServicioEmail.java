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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServicioEmail {

    private Socket socket;
    private BufferedReader entrada;
    private DataOutputStream salida;
    private boolean conectado = true;

    private ClientePOP clientePOP;

    public ServicioEmail() {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
        this.conectado = true;
        this.clientePOP = new ClientePOP();
    }

    public void iniciarClientePOP() throws IOException {
        clientePOP = new ClientePOP();
        clientePOP.conectar();
        int totalCorreos = clientePOP.obtenerTotalDeCorreos();
        System.out.println("S : Total de correos: " + totalCorreos);
        clientePOP.desconectar();
    }

    public void revisarCorreos() {
        try {
            clientePOP = new ClientePOP();
            clientePOP.conectar();
            clientePOP.revisarCorreos();
            clientePOP.desconectar();
        } catch (Exception e) {
            System.out.println("Error al revisar correos: " + e.getMessage());
        }
    }

    public void desconectar() {
        clientePOP.desconectar();
    }

    public void detener() {
        conectado = false;
        System.out.println("S : El cliente POP se ha detenido automáticamente.");
    }

    public static void main(String[] args) throws IOException {
        ServicioEmail servicioEmail = new ServicioEmail();
        servicioEmail.iniciarClientePOP();

        // Programar el apagado automático después de 15 segundos
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(servicioEmail::detener, 25, TimeUnit.SECONDS);

        while (servicioEmail.conectado) {
            servicioEmail.revisarCorreos();

            try {
                Thread.sleep(10000); // Esperar 10 segundos entre revisiones
            } catch (InterruptedException e) {
                System.out.println("Interrupción en el ciclo de revisión: " + e.getMessage());
            }
        }

        // Desconectar antes de salir
        servicioEmail.desconectar();
        scheduler.shutdown();
    }
}
