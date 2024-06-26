package com.codeteralab.inventoryproject.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimientosDAO {

    // obtiene todos los movimientos filtro entrada/salida
    public List<movimientos> getAllMovimientos(String filtro) throws Exception {
        List<movimientos> movimientosList = new ArrayList<>();
        conexion con = new conexion();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM movimientos";

        if (filtro == null || filtro.isEmpty()) {
            filtro = "all";
        }

        if(filtro.equalsIgnoreCase("all")){
            sql = "SELECT * FROM movimientos";
        } else if (filtro.equalsIgnoreCase("Entrada")) {
            sql = "SELECT * FROM movimientos WHERE tipo = 1";
        } else if (filtro.equalsIgnoreCase("Salida")) {
            sql = "SELECT * FROM movimientos WHERE tipo = 2";
        }


        try {
            cn = con.conectar();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                movimientos movimiento = new movimientos();
                movimiento.setIdMovimiento(rs.getInt("idMovimiento"));
                movimiento.setProducto((new ProductosDAO()).getProductoById(rs.getInt("idProducto")));
                movimiento.setTipo(rs.getString("tipo"));
                movimiento.setCantidad(rs.getInt("cantidad"));
                movimiento.setFechaHora(rs.getString("fechaHora"));
                movimiento.setUsuario((new UsuariosDAO()).getUsuarioById(rs.getInt("idUsuario")));
                movimientosList.add(movimiento);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (pst != null && !pst.isClosed()) {
                pst.close();
            }
            if (cn != null && !cn.isClosed()) {
                cn.close();
            }
        }

        return movimientosList;
    }
}