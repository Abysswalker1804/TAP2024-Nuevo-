package org.example.test.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class MesaDAO {
    private String numMesa;

    public String getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(String numMesa) {
        this.numMesa = numMesa;
    }
    public void INSERTAR(){
        String query="INSERT INTO mesa VALUES('"+numMesa+"')";
        try{
            Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE mesa SET numMesa='"+numMesa+"' WHERE numMesa='"+numMesa+"'";
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query="DELETE FROM mesa WHERE numMesa='"+numMesa+"'";
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<MesaDAO> CONSULTAR(){
        ObservableList<MesaDAO> listaMesa= FXCollections.observableArrayList();
        String query="SELECT * FROM mesa";
        try{
            MesaDAO objMesa;
            Statement stmt=Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            while(res.next()){
                objMesa=new MesaDAO();
                objMesa.numMesa=res.getString("antojito");
                listaMesa.add(objMesa);
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
        return  listaMesa;
    }
}
