package org.example.test.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import org.example.test.components.ConvertidorImagen;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class BebidaDAO {
    private char cve;
    private double precioUnitario;
    private int existencia;
    private String descripcion;
    private byte[] imagen;//Cambiar a mediumblob
    private String nombre;//Añadir nombre a la tabla
    private String ruta;
    private Image img;
    private ConvertidorImagen conv;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public char getCve() {
        return cve;
    }

    public void setCve(char cve) {
        this.cve = cve;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void INSERTAR(){
        conv=new ConvertidorImagen(ruta);
        if(conv.existe){
            String query="INSERT INTO bebida VALUES('"+cve+"',"+precioUnitario+","+existencia+",'"+descripcion+"','"+conv.A_Base64()+"','"+nombre+"','"+ruta+"')";
            try{
                Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
                stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Algo salió mal...");
            alert.setContentText("No se encontró el archivo especificado. Revíse nuevamente.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){}
        }

    }
    public void ACTUALIZAR(){
        conv=new ConvertidorImagen(ruta);
        if(conv.existe){
            String query="UPDATE bebida SET precioUnitario="+precioUnitario+",existencia="+existencia+",descripcion='"+descripcion+"',imagen='"+conv.A_Base64()+"',nombre='"+nombre+"',ruta='"+ruta+"' WHERE cve='"+cve+"'";
            try{
                Statement stmt=Conexion.connection.createStatement();
                stmt.executeUpdate(query);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Algo salió mal...");
            alert.setContentText("No se encontró el archivo especificado. Revíse nuevamente.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){}
        }
    }
    public void ELIMINAR(){
        String query="DELETE FROM bebida WHERE cve='"+cve+"'";
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<BebidaDAO> CONSULTAR(){
        ObservableList<BebidaDAO> listaBeb= FXCollections.observableArrayList();
        String query="SELECT * FROM bebida";
        try{
            BebidaDAO objBeb;
            Statement stmt=Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            while(res.next()){
                objBeb=new BebidaDAO();
                objBeb.cve=res.getString("cve").charAt(0);
                objBeb.precioUnitario=res.getDouble("precioUnitario");
                objBeb.existencia=res.getInt("existencia");
                objBeb.descripcion=res.getString("descripcion");
                objBeb.nombre=res.getString("nombre");
                objBeb.ruta=(res.getString("ruta").replace("\\","\\\\"));
                conv=new ConvertidorImagen();
                objBeb.img=conv.A_Imagen(res.getString("imagen"));
                listaBeb.add(objBeb);
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
        return  listaBeb;
    }
    public ObservableList<BebidaDAO> CONSULTAR_ORDEN(char clave){
        ObservableList<BebidaDAO> listaBeb= FXCollections.observableArrayList();
        String query="SELECT nombre,precioUnitario FROM bebida WHERE cve='"+clave+"'";
        try{
            BebidaDAO objBeb;
            PreparedStatement pst=Conexion.connection.prepareStatement(query);
            ResultSet res=pst.executeQuery();
            while(res.next()){
                objBeb=new BebidaDAO();
                objBeb.precioUnitario=res.getDouble("precioUnitario");
                objBeb.nombre=res.getString("nombre");
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
        return  listaBeb;
    }
}
