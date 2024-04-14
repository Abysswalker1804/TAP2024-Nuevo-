package org.example.test.modelos;

import java.sql.Statement;

public class BebidaDAO {
    private char cve;
    private double precioUnitario;
    private String descripcion;
    private byte[] imagen;//Cambiar a mediumblob
    private String nombre;//Añadir nombre a la tabla

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
    public void INSERTAR(String ruta){
        String query="INSERT INTO antojito(cve,precioUnitario,descripcion,imagen,nombre) VALUES('"+cve+"',"+precioUnitario+",'"+descripcion+"',LOAD_FILE('"+ruta+"'),'"+nombre+"')";
        try{
            Statement stmt=Conexion.connection.createStatement();//El statement se usa para interactuar con sql
            stmt.executeUpdate(query);//Usar para insertar, actualizar o eliminar
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query="UPDATE antojito SET precioUnitario="+precioUnitario+",descripcion='"+descripcion+"',salario="+imagen+",nombre='"+nombre+"' WHERE cve="+cve;
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
