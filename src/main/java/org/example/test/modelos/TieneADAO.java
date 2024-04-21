package org.example.test.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class TieneADAO {
    private char antojito;
    private int orden;
    private byte cantAnt;

    public char getAntojito() {
        return antojito;
    }

    public void setAntojito(char antojito) {
        this.antojito = antojito;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public byte getCantAnt() {
        return cantAnt;
    }

    public void setCantAnt(byte cantAnt) {
        this.cantAnt = cantAnt;
    }

    public void INSERTAR(){
        String query="INSERT INTO tienea VALUES('"+antojito+"',"+orden+","+cantAnt+")";
        try{
            Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE tienea SET cantAnt="+cantAnt+" WHERE antojito='"+antojito+"' AND orden="+orden;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query="DELETE FROM tienea WHERE antojito='"+antojito+"' AND orden="+orden;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<TieneADAO> CONSULTAR(){
        ObservableList<TieneADAO> listaTieneA= FXCollections.observableArrayList();
        String query="SELECT * FROM tienea";
        try{
            TieneADAO objTieneA;
            Statement stmt=Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            while(res.next()){
                objTieneA=new TieneADAO();
                objTieneA.antojito=res.getString("antojito").charAt(0);
                objTieneA.orden=res.getInt("orden");
                objTieneA.cantAnt=res.getByte("cantAnt");
                listaTieneA.add(objTieneA);
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
        return  listaTieneA;
    }
}
