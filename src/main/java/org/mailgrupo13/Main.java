package org.mailgrupo13;

import org.mailgrupo13.sistema.modelo.NotaVentaM;
import org.mailgrupo13.sistema.negocio.almacenes.AlmacenesN;
import org.mailgrupo13.sistema.negocio.almacenes.MedicamentosN;
import org.mailgrupo13.sistema.negocio.categorias.CategoriasN;
import org.mailgrupo13.sistema.negocio.clientes.ClientesN;
import org.mailgrupo13.sistema.negocio.consultas.ConsultasMedicasN;
import org.mailgrupo13.sistema.negocio.consultas.TratamientosN;
import org.mailgrupo13.sistema.negocio.mascotas.EspeciesN;
import org.mailgrupo13.sistema.negocio.mascotas.MascotasN;
import org.mailgrupo13.sistema.negocio.mascotas.RazasN;
import org.mailgrupo13.sistema.negocio.notacompra.DetalleNotaCompraN;
import org.mailgrupo13.sistema.negocio.notacompra.InventarioN;
import org.mailgrupo13.sistema.negocio.notacompra.NotaCompraN;
import org.mailgrupo13.sistema.negocio.notaventa.DetalleNotaVentaN;
import org.mailgrupo13.sistema.negocio.notaventa.NotaVentaN;
import org.mailgrupo13.sistema.negocio.proveedores.ProveedoresN;
import org.mailgrupo13.sistema.negocio.usuarios.UsuariosN;
import org.mailgrupo13.sistema.negocio.vacunas.HistorialVacunasN;
import org.mailgrupo13.sistema.negocio.vacunas.VacunasN;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        NotaCompraN notaCompraN=  new NotaCompraN();
        NotaVentaN notaVentaN=new NotaVentaN();
        NotaVentaM notaVentaM=new NotaVentaM();
        InventarioN inventarioN=new InventarioN();
        //System.out.println(notaVentaM.buscarPorCliente(2));


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


            /*pagos */

            //System.out.println(cliente.datospagos(2));









            /* mascotas*/
            //System.out.println(mascota.agregarMascota("dexter", 12.5f, "marron", "2021-01-01", "https://images.saymedia-content.com/.image/c_limit%2Ccs_srgb%2Cq_auto:eco%2Cw_700/MTk2MTIwNDk0MzY4MzY4MTMz/the-10-most-popular-poodle-mixed-breed-dogs.webp", 2, 6));
            //System.out.println(mascota.agregarMascota("chocolate", 12.5f, "marron", "2021-01-01", "https://google.com", 2, 6));

            //System.out.println(mascota.actualizarMascota(1,"dexterr", 12.5f, "marron", "2021-01-01", "https://google.com", 2, 6));
            //System.out.println(mascota.eliminarMascota(2));
            //System.out.println(mascota.leerMascota(1));
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

            //System.out.println(proveedor.agregarProveedor("Browser","Argentina","79803692", "Brouwer@gmail.com","calle  3 falsa"));
            //proveedor.actualizarProveedor(1,"Brol","Mexico","79803692", "aranda@gmail.com", "calle  3 falsa");
            //System.out.println(proveedor.eliminarProveedor(1));
            //System.out.println(proveedor.obtenerProveedores());
            //System.out.println(proveedor.leerProveedor(2).toString());



             /*medicamento */

            //System.out.println(medicamento.agregarMedicamento("paracetamol", "500mg", "bayer", "2022-01-01", false, 1));
            //System.out.println(medicamento.agregarMedicamento("ibuprofeno", "500mg", "bayer", "2022-01-01", false, 1));
            //System.out.println(medicamento.actualizarMedicamento(1,"paracetamol", "500mg", "bayer", "2022-01-01", false, 1));
            //System.out.println(medicamento.eliminarMedicamento(2));
            //System.out.println(medicamento.leerMedicamento(1).toString());
            //System.out.println(medicamento.obtenerMedicamentos());





           /* vacunas*/
             //System.out.println(vacunas.agregarVacuna("rabia", 550, "vacuna para la rabia"));
             //System.out.println(vacunas.agregarVacuna("garrapatas", 250, "vacuna para las garrapatas"));
            //System.out.println(vacunas.actualizarVacuna(1,"garrapata", 350,"vacuna para las garrapatas"));
            //System.out.println(vacunas.eliminarVacuna(4));
            //System.out.println(vacunas.leerVacuna(1).toString());
            //System.out.println(vacunas.obtenerVacunas());


            /*historial vacunas*/
            //System.out.println(historialVacunasN.agregarHistorialVacuna(2,1, Date.valueOf("2021-01-01")));
            //System.out.println(historialVacunasN.agregarHistorialVacuna("2021-01-01", "2021-01-01", 1, 1));
            //System.out.println(historialVacunasN.actualizarHistorialVacuna(1,"2021-01-01", "2021-01-01", 1, 1));
            //System.out.println(historialVacunasN.eliminarHistorialVacuna(3));
            //System.out.println(historialVacunasN.leerHistorialVacuna(1).toString());
            //System.out.println(historialVacunasN.obtenerHistorialesVacunas());




            /* consultas*/


            // Create an instance of ConsultasMedicasN
            //ConsultasMedicasN consultasMedicasN = new ConsultasMedicasN();

            //List<TratamientosN> tratamientos = new ArrayList<>();
             //tratamientos.add(new TratamientosN("Medicamento 1", "Notas 1"));
             //tratamientos.add(new TratamientosN("Medicamento 2", "Notas 2"));
            // Call the agregarConsultaMedica method
            //String result = consultasMedicasN.agregarConsultaMedica("2023-10-01", "Routine check-up","Healthy" , 50.0f, 1, 2, tratamientos);
            //System.out.println(result);
           //System.out.println(consultasMedicasN.obtenerConsultasMedicas());
            //System.out.println(consultasMedicasN.leerConsulta(1));

            //String result2 = consultasMedicasN.actualizarConsultaMedica(1,"2023-10-01", "Routine check-up","Healthy" , 50.0f, 1, 1, tratamientos);
           // System.out.println(result2);



            //float subtotal1 = 400;
            //float subtotal2 = 440;
            //List<DetalleNotaCompraN> detalleNotaCompraNS = new ArrayList<>();
            //detalleNotaCompraNS.add(new DetalleNotaCompraN(2,200,50,subtotal1,2));
            //detalleNotaCompraNS.add(new DetalleNotaCompraN(2,220,50,subtotal2,3));



            /*nota compra*/

           //System.out.println(notaCompraN.crearNotaCompra( "2023-10-01",2,3,2,detalleNotaCompraNS));
            //System.out.println( notaCompraN.actualizarNotaCompra(2,"2022-11-01",2,3,2,detalleNotaCompraNS));
             //System.out.println(notaCompraN.leerNotaCompra(2));
            //System.out.println(notaCompraN.eliminarNotaCompra(3));
            System.out.println(notaCompraN.obtenerNotaCompras());





            /**inventario */

            System.out.println(inventarioN.obtenerInventarios());


             /*nota venta */


            float subtotalventas1 = 400;
            float subtotalventas2 = 440;
            //List<DetalleNotaVentaN> detalleNotaVentaNS = new ArrayList<>();
            //detalleNotaVentaNS.add(new DetalleNotaVentaN(2,200,subtotalventas1,2));
           // detalleNotaVentaNS.add(new DetalleNotaVentaN(2,220,subtotalventas2,3));

            //System.out.println(notaVentaN.crearNotaVenta( "2023-10-01",2,2,2,detalleNotaVentaNS));
            //System.out.println(notaVentaN.obtenerNotaVentas());
            //System.out.println(notaCompraN.);
            //System.out.println(notaVentaN.actualizarNotaVenta(2,"2022-11-01",2,3,2,detalleNotaVentaNS));





         }catch (Exception e) {
            System.out.println(e.getMessage());
            //System.out.println(almacen.obtenerAlmacenes());



        }

















    }
}