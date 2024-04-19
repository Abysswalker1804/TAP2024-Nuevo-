package org.example.test.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AntojitoDAO {
    private char cve;
    private double precioUnitario;
    private int existencia;
    private String descripcion;
    private byte[] imagen; //Cambiar a mediumblob
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
        //String query="INSERT INTO antojito(cve,precioUnitario,existencia,descripcion,imagen,nombre,ruta) VALUES('"+cve+"',"+precioUnitario+","+existencia+",'"+descripcion+"',LOAD_FILE('"+ruta+"'),'"+nombre+"','"+ruta+"')";
        String query="INSERT INTO antojito VALUES('"+cve+"',"+precioUnitario+","+existencia+",'"+descripcion+"',?,'"+nombre+"','"+ruta+"')";
        try{
            FileInputStream fis=new FileInputStream(ruta);
            PreparedStatement pst=Conexion.connection.prepareStatement(query);
            pst.setBlob(1,fis);
            //Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            //stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
            pst.execute();
            fis.close();
            pst.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE antojito SET precioUnitario="+precioUnitario+",existencia="+existencia+",descripcion='"+descripcion+"',imagen=LOAD_FILE('"+ruta+"'),nombre='"+nombre+"',ruta='"+ruta+"' WHERE cve="+cve;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query="DELETE FROM antojito WHERE cve='"+cve+"'";
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<AntojitoDAO> CONSULTAR(){
        ObservableList<AntojitoDAO> listaAnt= FXCollections.observableArrayList();
        String query="SELECT * FROM antojito";
        try{
            AntojitoDAO objAnt;
            //Statement stmt=Conexion.connection.createStatement();
            PreparedStatement pst=Conexion.connection.prepareStatement("SELECT * FROM antojito");
            //ResultSet res=stmt.executeQuery(query);
            ResultSet res=pst.executeQuery();
            while(res.next()){
                objAnt=new AntojitoDAO();
                InputStream []is=new InputStream[1];
                while(res.next()){
                    try{
                        objAnt.cve=res.getString("cve").charAt(0);
                        objAnt.precioUnitario=res.getDouble("precioUnitario");
                        objAnt.existencia=res.getInt("existencia");
                        objAnt.descripcion=res.getString("descripcion");
                        objAnt.nombre=res.getString("nombre");
                        objAnt.ruta=(res.getString("ruta").replace("\\","\\\\"));
                        Blob blob= res.getBlob(5);
                        //objAnt.imagen= blob.getBytes(1,(int)blob.length());
                        is[0]=blob.getBinaryStream();
                    }catch (NullPointerException npe){
                        System.out.println("Blob está vacío");
                    }
                    /* Para poner la imagen:
                    * Image img = new Image(new ByteArrayInputStream(objAnt.imagen));
                      imageView = new ImageView(img);*/
                }
                FileOutputStream fos=new FileOutputStream("copia_"+objAnt.getCve()+".jpg");
                byte[] bytes=new byte[1024];
                int leidos= is[0].read(bytes);
                while(leidos > 0){
                    fos.write(bytes);
                    leidos=is[0].read(bytes);
                }
                fos.close();
                is[0].close();
                listaAnt.add(objAnt);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  listaAnt;
    }

}
