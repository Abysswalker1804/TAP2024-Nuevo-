package org.example.test.components;

import org.example.test.modelos.Conexion;

import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenGrafica {
    private int numOrden;
    private String nombre;
    private double precioUnit;
    private int cant;
    private int existencia;

    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(double precioUnit) {
        this.precioUnit = precioUnit;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public OrdenGrafica(char cve){
        String query="SELECT 'antojito' AS cve,nombre,precioUnitario,existencia FROM antojito WHERE cve = '"+cve+"' UNION ALL SELECT 'bebida' AS cve,nombre,precioUnitario,existencia FROM bebida WHERE cve = '"+cve+"'";
        try{
            Statement stmt= Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            res.next();
            nombre=res.getString("nombre");
            precioUnit=res.getDouble("precioUnitario");
            existencia=res.getInt("existencia");
        }catch(Exception e){}
        query="SELECT MAX(numOrden) FROM orden;";
        try{
            Statement stmt= Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            int orden=0;
            while(res.next()){orden=res.getInt(1);}
            numOrden=orden+1;
        }catch(Exception e){}
        cant=1;
    }
    public boolean ValidarOrden(){
        return (numOrden!=0 && nombre!=null && precioUnit!=0 && existencia!=0);
    }
}
