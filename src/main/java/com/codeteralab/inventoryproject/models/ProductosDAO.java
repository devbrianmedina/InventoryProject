package com.codeteralab.inventoryproject.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {

    // agrega un producto nuevo
    public boolean agregarProducto(productos producto) throws Exception {
        conexion con = new conexion();
        Connection cn = null;
        PreparedStatement pst = null;
        String sql = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";

        try {
            cn = con.conectar();
            pst = cn.prepareStatement(sql);
            pst.setString(1, producto.getNombre());
            pst.setDouble(2, producto.getPrecio());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            if (pst != null && !pst.isClosed()) {
                pst.close();
            }
            if (cn != null && !cn.isClosed()) {
                cn.close();
            }
        }
    }

    // obtiene todos los productos con filtro
    public List<productos> getAllProductos(String filtro) throws Exception {
        List<productos> productosList = new ArrayList<>();
        conexion con = new conexion();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM productos WHERE estatus=1";
        if(filtro.equalsIgnoreCase("Activo")){
            sql = "SELECT * FROM productos WHERE estatus=1";
        } else if(filtro.equalsIgnoreCase("Inactivo")){
            sql = "SELECT * FROM productos WHERE estatus=2";
        } else if(filtro.equalsIgnoreCase("all")){
            sql = "SELECT * FROM productos";
        }

        try {
            cn = con.conectar();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                productos producto = new productos();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setEstatus(rs.getString("estatus"));
                productosList.add(producto);
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

        return productosList;
    }

    // registra una entrada de producto
    public boolean entradaProducto(int idProducto, int cantidad, int idUsuario) throws Exception {
        conexion con = new conexion();
        Connection cn = null;
        PreparedStatement pst = null;
        String sqlUpdate = "UPDATE productos SET cantidad = cantidad + ? WHERE idProducto = ?";
        String sqlSetUser = "SET @current_user_id = ?";

        try {
            cn = con.conectar();
            cn.setAutoCommit(false); // iniciar transaccion sql

            // establece la variable de sesión con el ID del usuario actual para el TIGGER
            pst = cn.prepareStatement(sqlSetUser);
            pst.setInt(1, idUsuario);
            pst.executeUpdate();

            // actualizar la cantidad del producto
            pst = cn.prepareStatement(sqlUpdate);
            pst.setInt(1, cantidad);
            pst.setInt(2, idProducto);
            int rowsAffected = pst.executeUpdate();

            cn.commit(); // confirmar transaccion sql
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (cn != null) {
                cn.rollback(); // revertir transaccion en caso de error
            }
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            if (pst != null && !pst.isClosed()) {
                pst.close();
            }
            if (cn != null && !cn.isClosed()) {
                cn.close();
            }
        }
    }

    // registra una salida de producto
    public boolean salidaProducto(int idProducto, int cantidad, int idUsuario) throws Exception {
        conexion con = new conexion();
        Connection cn = null;
        PreparedStatement pst = null;
        String sqlUpdate = "UPDATE productos SET cantidad = cantidad - ? WHERE idProducto = ?";
        String sqlSetUser = "SET @current_user_id = ?";

        try {
            cn = con.conectar();
            cn.setAutoCommit(false); // iniciar transaccion sql

            // establecer la variable de sesión con el ID del usuario actual para el TIGGER
            pst = cn.prepareStatement(sqlSetUser);
            pst.setInt(1, idUsuario);
            pst.executeUpdate();

            // actualizar la cantidad del producto
            pst = cn.prepareStatement(sqlUpdate);
            pst.setInt(1, cantidad);
            pst.setInt(2, idProducto);
            int rowsAffected = pst.executeUpdate();

            cn.commit(); // confirmar transaccion sql
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (cn != null) {
                cn.rollback(); // revertir transaccion en caso de error
            }
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            if (pst != null && !pst.isClosed()) {
                pst.close();
            }
            if (cn != null && !cn.isClosed()) {
                cn.close();
            }
        }
    }

    // para cambiar el estatus de un producto activo/inactivo
    public boolean toggleEstatusProducto(int idProducto) throws Exception {
        conexion con = new conexion();
        Connection cn = null;
        PreparedStatement pst = null;
        String sql = "UPDATE productos SET estatus = CASE estatus WHEN 'Activo' THEN 'Inactivo' ELSE 'Activo' END WHERE idProducto = ?";

        try {
            cn = con.conectar();
            pst = cn.prepareStatement(sql);
            pst.setInt(1, idProducto);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            if (pst != null && !pst.isClosed()) {
                pst.close();
            }
            if (cn != null && !cn.isClosed()) {
                cn.close();
            }
        }
    }

    // obtiene un producto por su id
    public productos getProductoById(int idProducto) throws Exception {
        conexion con = new conexion();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        productos producto = null;
        String sql = "SELECT * FROM productos WHERE idProducto = ?";

        try {
            cn = con.conectar();
            pst = cn.prepareStatement(sql);
            pst.setInt(1, idProducto);
            rs = pst.executeQuery();

            if (rs.next()) {
                producto = new productos();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setEstatus(rs.getString("estatus"));
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

        return producto;
    }
}