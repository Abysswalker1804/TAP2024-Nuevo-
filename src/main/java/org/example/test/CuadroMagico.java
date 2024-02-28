package org.example.test;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import java.io.Serializable;

public class CuadroMagico extends Stage{
    private Scene escena;
    private VBox VContenedor;
    public CuadroMagico(){
        CrearUI();
        this.setTitle("Cuadro Mágico");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        VContenedor=new VBox();
        escena=new Scene(VContenedor);
    }
}
class CeldaArchivos implements Serializable{//Plantear bien la estructura
    private String posicion;
    private int contenido;
    public CeldaArchivos(String v_pos, int v_cont){
        posicion=v_pos;
        contenido=v_cont;
    }
    public String getPosicion(){
        return posicion;
    }
    public int getContenido() {
        return contenido;
    }
    public void setContenido(int contenido) {
        this.contenido = contenido;
    }
}