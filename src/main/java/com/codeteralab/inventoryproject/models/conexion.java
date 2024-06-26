package com.codeteralab.inventoryproject.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexion {
    private final String bdname = "inventory_db12";
    private final String server = "jdbc:mysql://localhost/" + bdname;
    private final String user = "root";
    private final String password = "";

    public Connection conectar(){
        Connection cn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(server, user, password);
        }catch(Exception e){
            System.out.println("Error al conectar" + e.getMessage());
        }
        return cn;
    }

}
