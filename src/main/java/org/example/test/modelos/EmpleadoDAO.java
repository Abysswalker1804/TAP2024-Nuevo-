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
    private int idEmpleado;
    private String nomEmpleado;
    private String rfcEmpleado;
    private float salario;
    private String telefono;
    private String direccion;
    private int ventas;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNomEmpleado() {
        return nomEmpleado;
    }

    public void setNomEmpleado(String nomEmpleado) {
        this.nomEmpleado = nomEmpleado;
    }

    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public void INSERTAR(){
        String query="INSERT INTO empleado(nomEmpleado, rfcEmpleado, salario, telefono, direccion, ventas) VALUES('"+nomEmpleado+"','"+rfcEmpleado+"',"+salario+" ,'"+telefono+"','"+direccion+"',"+0+")";
        try{
            Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE empleado SET nomEmpleado='"+nomEmpleado+"',rfcEmpleado='"+rfcEmpleado+"',salario="+salario+",telefono='"+telefono+"',direccion='"+direccion+"',ventas="+ventas+" WHERE idEmpleado="+idEmpleado;
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
