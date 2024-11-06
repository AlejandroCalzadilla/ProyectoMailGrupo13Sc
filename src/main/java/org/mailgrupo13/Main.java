package org.mailgrupo13;

import org.mailgrupo13.sistema.modelo.ClienteM;
import org.mailgrupo13.sistema.negocio.ClienteN;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        ClienteM cliente=new ClienteM();
        System.out.println(cliente.obtenerClientes()+"aver debe estar vacio");

        System.out.println("Hello world!");
    }
}