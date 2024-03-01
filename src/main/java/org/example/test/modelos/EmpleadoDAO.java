package org.example.test.modelos;
import com.mysql.cj.protocol.Resultset;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmpleadoDAO implements Serializable{
    int idEmpleado;
    String nomEmpleado;
    String rfcEmpleado;
    float salario;
    String telefono;
    String direccion;

    public void INSERTAR(){
        String query="INSERT INTO empleado(nomEmpleado, rfcEmpleado, salario, telefono, direccion) VALUES('"+nomEmpleado+"','"+rfcEmpleado+"',"+salario+" ,'"+telefono+"','"+direccion+"')";
        try{
            Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE empleado SET nomEmpleado='"+nomEmpleado+"',rfcEmpleado='"+rfcEmpleado+"',salario="+salario+",telefono='"+telefono+"',direccion='"+direccion+"' WHERE idEmpleado="+idEmpleado;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query="DELETE FROM empleado WHERE idEmpleado="+idEmpleado;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<EmpleadoDAO> CONSULTAR(){
        ObservableList<EmpleadoDAO> listaEmp= FXCollections.observableArrayList();
        String query="SELECT * FROM empleado";
        try{
            EmpleadoDAO objEmp;
            Statement stmt=Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            while(res.next()){
                objEmp=new EmpleadoDAO();
                objEmp.idEmpleado=res.getInt("idEmpleado");
                objEmp.nomEmpleado=res.getString("nomEmpleado");
                objEmp.rfcEmpleado=res.getString("rfcEmpleado");
                objEmp.salario=res.getFloat("salario");
                objEmp.telefono=res.getString("telefono");
                objEmp.direccion=res.getString("direccion");
                listaEmp.add(objEmp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  listaEmp;
    }
}
