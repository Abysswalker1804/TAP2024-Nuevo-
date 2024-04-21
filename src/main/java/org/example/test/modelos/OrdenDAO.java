package org.example.test.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class OrdenDAO {
    private int numOrden;
    private double total;
    private int empleado;
    private String mesa;//2 char
    private  int cliente;

    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        if(mesa.length()>2)
            this.mesa=mesa.charAt(0)+""+mesa.charAt(1);
        else
            this.mesa = mesa;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public void INSERTAR(){
        String query="INSERT INTO orden VALUES("+numOrden+","+total+","+empleado+",'"+mesa+"',"+cliente+")";
        try{
            Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE orden SET total="+total+",empleado="+empleado+",mesa='"+mesa+"',cliente="+cliente+" WHERE numOrden="+numOrden;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query="DELETE FROM orden WHERE numOrden="+numOrden;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<OrdenDAO> CONSULTAR(){
        ObservableList<OrdenDAO> listaOrd= FXCollections.observableArrayList();
        String query="SELECT * FROM orden";
        try{
            OrdenDAO objOrd;
            Statement stmt=Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            while(res.next()){
                objOrd=new OrdenDAO();
                objOrd.numOrden=res.getInt("numOrden");
                objOrd.total=res.getDouble("total");
                objOrd.empleado=res.getInt("empleado");
                objOrd.mesa=res.getString("mesa");
                objOrd.cliente=res.getInt("cliente");
                listaOrd.add(objOrd);
            }
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Algo salió mal...");
            alert.setContentText("Ha ocurrido algún error al intentar acceder a la base de datos.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){}
        }
        return  listaOrd;
    }
}
