package org.mailgrupo13;

import org.mailgrupo13.sistema.negocio.almacenes.AlmacenesN;
import org.mailgrupo13.sistema.negocio.almacenes.MedicamentosN;
import org.mailgrupo13.sistema.negocio.categorias.CategoriasN;
import org.mailgrupo13.sistema.negocio.clientes.ClientesN;
import org.mailgrupo13.sistema.negocio.mascotas.EspeciesN;
import org.mailgrupo13.sistema.negocio.mascotas.MascotasN;
import org.mailgrupo13.sistema.negocio.mascotas.RazasN;
import org.mailgrupo13.sistema.negocio.proveedores.ProveedoresN;
import org.mailgrupo13.sistema.negocio.usuarios.UsuariosN;
import org.mailgrupo13.sistema.negocio.vacunas.HistorialVacunasN;
import org.mailgrupo13.sistema.negocio.vacunas.VacunasN;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        //ClienteM cliente = new ClienteM();

        EspeciesN especie= new EspeciesN();
        RazasN raza=new RazasN();
        MascotasN mascota=new MascotasN();


        AlmacenesN almacen=new AlmacenesN();
        ClientesN cliente=new ClientesN();
        UsuariosN usuario=new UsuariosN();



        CategoriasN categoria=new CategoriasN();
        ProveedoresN proveedor=new ProveedoresN();
        MedicamentosN medicamento=new MedicamentosN();
        VacunasN vacunas=new VacunasN();
        HistorialVacunasN historialVacunasN=new HistorialVacunasN();
        try {







             /* especies crud  1*/
            // especie.agregarEspecie("perro");
            // especie.eliminarEspecie(1);
            // especie.actualizarEspecie(100,"aves");
             //System.out.println(especie.leerEspecie(1).toString());
             //System.out.println(especie.obtenerEspecies());


            /* razas crud  2*/

            //raza.agregarRaza("Labrador Retriever", 12);

            //raza.eliminarRaza(1);
            //raza.actualizarRaza(2,"raza 10",1);
            //System.out.println(raza.obtenerRazas());
            //System.out.println(raza.leerRaza(2).toString());







            /*usuarios  3 */

            //usuario.agregarUsuario("juanperez@gmail.com", "juanperez", "juan Perez ");
            //usuario.agregarUsuario("juanperon@gmail.com", "juanperez", "juan Peron ");
            //System.out.println(usuario.actualizarUsuario(1,"daniel@gmail.com", "juanperez", "juan Perez "));
            //System.out.println(usuario.eliminarUsuario(1));
            //System.out.println(usuario.obtenerUsuarios());
            //System.out.println(usuario.leerUsuario(4).toString());


             //usuario.
            /* clientes*/


            //System.out.println(cliente.agregarCliente("Juan", "Pérez", "555-1234", "Masculino", "1990-05-15", 2));
            //System.out.println(cliente.agregarCliente("Juanop", "Pérez", "555-1234", "Masculino", "1990-05-15", 2));

            //cliente.actualizarCliente(1,"Danielo", "Pérez", "555-1234", "Masculino", "1990-05-15", 2);
            //cliente.eliminarCliente(3);
            //System.out.println(cliente.leerCliente(1).toString());
            //System.out.println(cliente.obtenerClientes());









            /* mascotas*/
            //System.out.println(mascota.agregarMascota("dexter", 12.5f, "marron", "2021-01-01", "https://images.saymedia-content.com/.image/c_limit%2Ccs_srgb%2Cq_auto:eco%2Cw_700/MTk2MTIwNDk0MzY4MzY4MTMz/the-10-most-popular-poodle-mixed-breed-dogs.webp", 2, 6));
            //System.out.println(mascota.agregarMascota("chocolate", 12.5f, "marron", "2021-01-01", "https://google.com", 2, 6));

            //System.out.println(mascota.actualizarMascota(1,"dexterr", 12.5f, "marron", "2021-01-01", "https://google.com", 2, 6));
            //System.out.println(mascota.eliminarMascota(2));
            //System.out.println(mascota.leerMascota(1).toString());
            //System.out.println(mascota.obtenerMascotas());






             /* almacenes crud*/
             //almacen.agregarAlmacen("almacen 1","direccion 1","telefono 1");
             // almacen.agregarAlmacen("almacen 2","direccion 2","telefono 2");
             //  System.out.println(almacen.eliminarAlmacen(1));
             //System.out.println(almacen.actualizarAlmacen(2,"almacen 10","direccion 3","telefono 3"));
             // System.out.println(almacen.leerAlmacen(2).toString());
             //System.out.println(almacen.obtenerAlmacenes());




            /* categorias crud*/

            //categoria.agregarCategoria("Antibióticos");
            //categoria.agregarCategoria("Antiparasitarios");
            //categoria.agregarCategoria("Analgésicos");
            //categoria.agregarCategoria("Antiinflamatorios");
            //categoria.agregarCategoria("Vacunas");
            //categoria.agregarCategoria("Suplementos");
            //categoria.agregarCategoria("Antifúngicos");
            //categoria.agregarCategoria("Antivirales");
            //categoria.agregarCategoria("Hormonas");
            //categoria.agregarCategoria("Sedantes");
            //System.out.println(categoria.actualizarCategoria(1,"laminados"));
            //System.out.println(categoria.eliminarCategoria(1));
            //System.out.println(categoria.obtenerCategorias());

            //System.out.println(categoria.leerCategoria(3).toString());




            /* proveedor crud*/

            System.out.println(proveedor.agregarProveedor("Brouwer","Argentina","79803692", "Brouwer@gmail.com","calle  3 falsa"));
            proveedor.actualizarProveedor(1,"Brouwer","Argentina","79803692", "", "calle  3 falsa");
            //System.out.println(proveedor.eliminarProveedor(1));
            //System.out.println(proveedor.obtenerProveedores());
            //System.out.println(proveedor.leerProveedor(1).toString());



             /*medicamento */

            //System.out.println(medicamento.agregarMedicamento("paracetamol", "500mg", "bayer", "2022-01-01", false, 1));
            //System.out.println(medicamento.agregarMedicamento("ibuprofeno", "500mg", "bayer", "2022-01-01", false, 1));
            //System.out.println(medicamento.actualizarMedicamento(1,"paracetamol", "500mg", "bayer", "2022-01-01", false, 1));
            //System.out.println(medicamento.eliminarMedicamento(2));
            //System.out.println(medicamento.leerMedicamento(1).toString());
            //System.out.println(medicamento.obtenerMedicamentos());





           /* vacunas*/
             //System.out.println(vacunas.agregarVacuna("rabia", "2021-01-01", "2021-01-01", 1));
                //System.out.println(vacunas.agregarVacuna("rabia", "2021-01-01", "2021-01-01", 1));
            //System.out.println(vacunas.actualizarVacuna(1,"rabia", "2021-01-01", "2021-01-01", 1));
            //System.out.println(vacunas.eliminarVacuna(2));
            //System.out.println(vacunas.leerVacuna(1).toString());
            //System.out.println(vacunas.obtenerVacunas());


            /*historial vacunas*/
            //System.out.println(historialVacunasN.agregarHistorialVacuna("2021-01-01", "2021-01-01", 1, 1));
            //System.out.println(historialVacunasN.agregarHistorialVacuna("2021-01-01", "2021-01-01", 1, 1));
            //System.out.println(historialVacunasN.actualizarHistorialVacuna(1,"2021-01-01", "2021-01-01", 1, 1));
            //System.out.println(historialVacunasN.eliminarHistorialVacuna(2));
            //System.out.println(historialVacunasN.leerHistorialVacuna(1).toString());
            //System.out.println(historialVacunasN.obtenerHistorialesVacunas());







         }catch (Exception e) {
            System.out.println(e.getMessage());
            //System.out.println(almacen.obtenerAlmacenes());



        }

















    }
}