package org.example.test.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class TieneBDAO {
    private char bebida;
    private int orden;
    private byte cantBeb;

    public char getBebida() {
        return bebida;
    }

    public void setBebida(char bebida) {
        this.bebida = bebida;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public byte getCantBeb() {
        return cantBeb;
    }

    public void setCantBeb(byte cantBeb) {
        this.cantBeb = cantBeb;
    }
    public void INSERTAR(){
        String query="INSERT INTO tieneb VALUES('"+bebida+"',"+orden+","+cantBeb+")";
        try{
            Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE tieneb SET cantBeb="+cantBeb+" WHERE bebida='"+bebida+"' AND orden="+orden;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query="DELETE FROM tieneb WHERE bebida='"+bebida+"' AND orden="+orden;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<TieneBDAO> CONSULTAR(){
        ObservableList<TieneBDAO> listaTieneB= FXCollections.observableArrayList();
        String query="SELECT * FROM tieneb";
        try{
            TieneBDAO objTieneB;
            Statement stmt=Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            while(res.next()){
                objTieneB=new TieneBDAO();
                objTieneB.bebida=res.getString("bebida").charAt(0);
                objTieneB.orden=res.getInt("orden");
                objTieneB.cantBeb=res.getByte("cantBeb");
                listaTieneB.add(objTieneB);
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
        return  listaTieneB;
    }
}
