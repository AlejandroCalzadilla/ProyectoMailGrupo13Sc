package org.mailgrupo13;

import org.mailgrupo13.sistema.modelo.CategoriasM;
import org.mailgrupo13.sistema.modelo.ClienteM;
import org.mailgrupo13.sistema.modelo.EspecieM;
import org.mailgrupo13.sistema.modelo.MedicamentosM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws SQLException {

        ClienteM cliente = new ClienteM();
        EspecieM especie = new EspecieM();
        especie.setNombre("ave");
        especie.setCreadoEn(Timestamp.valueOf(LocalDateTime.now()));
        especie.setActualizadoEn(Timestamp.valueOf(LocalDateTime.now()));
        boolean exito = especie.crearEspecie();
        System.out.println(exito+"posi o nega");

        /*especie.crearEspecie();
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setTelefono("555-1234");
        cliente.setGenero("Masculino");
        cliente.setFechaNacimiento(Date.valueOf(LocalDate.of(1990, 5, 15)));
        cliente.setIdUsuario(1); // Asegúrate de que este id_user exista en la tabla users
        cliente.setCreadoEn(Timestamp.valueOf(LocalDateTime.now()));
        cliente.setActualizadoEn(Timestamp.valueOf(LocalDateTime.now()));
          */
        // Insertar el cliente en la base de datos
        //boolean exito = cliente.crearCliente();
        //System.out.println(exito+"posi o nega");


        CategoriasM categoria = new CategoriasM();
        categoria.setNombre("Laminado");
        categoria.setCreadoEn(new Timestamp(System.currentTimeMillis()));
        categoria.setActualizadoEn(new Timestamp(System.currentTimeMillis()));

        if (categoria.crearCategoria()) {
            System.out.println("Categoría creada correctamente.");
        } else {
            System.out.println("Error al crear la categoría.");
        }



        //medicamentos
        MedicamentosM medicamento = new MedicamentosM();
        medicamento.setNombre("Ibuprofeno");
        medicamento.setDosis("200mg");
        medicamento.setFabricante("Laboratorios XYZ");
        medicamento.setFechaCaducidad(Date.valueOf("2026-11-01"));
        medicamento.setSustanciaControlada(false);
        medicamento.setCategoriaId(1); // ID de la categoría
        medicamento.setCreadoEn(new Timestamp(System.currentTimeMillis()));
        medicamento.setActualizadoEn(new Timestamp(System.currentTimeMillis()));

        boolean resultado = medicamento.crearMedicamento();
        System.out.println(medicamento.obtenerMedicamentos());
        System.out.println(medicamento.leerMedicamento(1));





    }
}