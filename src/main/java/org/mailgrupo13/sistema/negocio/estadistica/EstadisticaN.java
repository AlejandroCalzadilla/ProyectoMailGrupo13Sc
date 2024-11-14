package org.mailgrupo13.sistema.negocio.estadistica;


import org.mailgrupo13.sistema.modelo.NotaVentaM;
import org.mailgrupo13.sistema.modelo.NotaCompraM;

import java.sql.SQLException;

public class EstadisticaN {

        private NotaVentaM notaVentaM;
        private NotaCompraM notaCompraM;

        public EstadisticaN() throws SQLException {
            this.notaVentaM = new NotaVentaM();
            this.notaCompraM = new NotaCompraM();
        }

        public String obtenerVentasPorMes() throws SQLException {
            return notaVentaM.obtenerVentasPorMes();
        }

        public String obtenerVentasPorCliente() throws SQLException {
            return notaVentaM.obtenerVentasPorCliente();
        }



        public String obtenerComprasPorMes() throws SQLException {
            return notaCompraM.obtenerComprasPorMes();
        }



        public String obtenerComprasPorProveedor() throws SQLException {
            return notaCompraM.obtenerComprasPorProveedor();
        }



}
