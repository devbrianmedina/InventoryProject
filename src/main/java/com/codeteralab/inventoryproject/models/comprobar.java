package com.codeteralab.inventoryproject.models;
// para comprobar si tenemos conexion con la bd
public class comprobar {
    public static void main(String[] args){
        try {
            conexion c = new conexion();
            if(c.conectar()!= null){
                System.out.println("conexion es correcta");
            }else{
                System.out.println("conexion erronea");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}