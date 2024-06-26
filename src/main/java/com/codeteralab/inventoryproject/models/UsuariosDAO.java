package com.codeteralab.inventoryproject.models;

import java.sql.*;

public class UsuariosDAO extends conexion {

    // identifica el usuario login
    public usuarios identificarUsuario(usuarios user) throws Exception {
        usuarios usuario = null;
        conexion con;
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        String sql = "SELECT u.idUsuario, u.nombre, r.idRol, r.nombre AS nombreRol FROM usuarios u "
                + "INNER JOIN roles r ON u.idRol = r.idRol "
                + "WHERE u.estatus = 1 AND u.correo = '" + user.getCorreo() + "' "
                + "AND u.contrasena = '"+ user.getContrasena() + "'";

        con = new conexion();
        try{
            cn = con.conectar();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                usuario = new usuarios();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(user.getCorreo());
                usuario.setContrasena(user.getContrasena());
                usuario.setRol(new roles());
                usuario.getRol().setIdRole(rs.getInt("idRol"));
                usuario.getRol().setNombre(rs.getString("nombreRol"));
                usuario.setEstatus(true);
            }
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }finally{
            if (rs !=null && !rs.isClosed()){
                rs.close();
            }
            rs = null;
            if(st!= null && !st.isClosed()){
                st.close();

            }
            st = null;
            if(cn != null && !cn.isClosed()){
                cn.close();
            }
            cn = null;
        }
        return usuario;
    }

    // obtiene algunos datos de usuario por su id
    public usuarios getUsuarioById(int idUsuario) throws Exception {
        conexion con = new conexion();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        usuarios usuario = null;
        String sql = "SELECT idUsuario, nombre, correo FROM usuarios WHERE idUsuario = ?";

        try {
            cn = con.conectar();
            pst = cn.prepareStatement(sql);
            pst.setInt(1, idUsuario);
            rs = pst.executeQuery();

            if (rs.next()) {
                usuario = new usuarios();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
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

        return usuario;
    }
}