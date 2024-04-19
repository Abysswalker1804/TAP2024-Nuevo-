package org.example.test.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;

public class BebidaDAO {
    private char cve;
    private double precioUnitario;
    private int existencia;
    private String descripcion;
    private byte[] imagen;//Cambiar a mediumblob
    private String nombre;//Añadir nombre a la tabla
    private String ruta;
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
        String query="INSERT INTO bebida(cve,precioUnitario,existencia,descripcion,imagen,nombre,ruta) VALUES('"+cve+"',"+precioUnitario+","+existencia+",'"+descripcion+"',LOAD_FILE('"+ruta+"'),'"+nombre+"','"+ruta+"')";
        try{
            Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE bebida SET precioUnitario="+precioUnitario+",existencia="+existencia+",descripcion='"+descripcion+"',imagen=LOAD_FILE('"+ruta+"'),nombre='"+nombre+"',ruta='"+ruta+"' WHERE cve="+cve;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query="DELETE FROM bebida WHERE cve="+cve;
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
                while(res.next()){
                    try{
                        objBeb.imagen=null;
                        Blob blob= res.getBlob("imagen");
                        objBeb.imagen= blob.getBytes(1,(int)blob.length());
                    }catch (NullPointerException npe){
                        System.out.println("Blob está vacío");
                    }
                    /* Para poner la imagen:
                    * Image img = new Image(new ByteArrayInputStream(objBeb.imagen));
                      imageView = new ImageView(img);*/
                }
                listaBeb.add(objBeb);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  listaBeb;
    }
}
