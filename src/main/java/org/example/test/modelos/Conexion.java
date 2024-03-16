package org.example.test.modelos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion{
    static private String DB="taqueria";
    static private String USER="adminTacos";
    static private String PWD="1234";
    static public Connection connection;

   public static void crearConexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+DB+"?allowPublicKeyRetrieval=true&useSSL=false",USER,PWD);
             System.out.println("Conexion.crearConexion()> Conexión exitosa! :)");
        }catch(Exception e){
            System.out.println("Conexion.crearConexion()> Conexión fallida! :(");
            //e.printStackTrace();
        }
    }
}
