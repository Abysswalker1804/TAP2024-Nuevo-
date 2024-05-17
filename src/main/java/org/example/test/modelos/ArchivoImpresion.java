package org.example.test.modelos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ArchivoImpresion extends Thread{
    private int noArchivo;
    private String nombre;
    private int noHojas;
    private String horaAcceso;
    private ProgressBar pgrBar;
    private final DoubleProperty progreso= new SimpleDoubleProperty();

    public double getProgreso() {
        return progreso.get();
    }

    public DoubleProperty progresoProperty() {
        return progreso;
    }

    public void setProgreso(double progreso) {
        this.progreso.set(progreso);
    }

    public int getNoArchivo() {
        return noArchivo;
    }

    public void setNoArchivo(int noArchivo) {
        this.noArchivo = noArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNoHojas() {
        return noHojas;
    }

    public void setNoHojas(int noHojas) {
        this.noHojas = noHojas;
    }

    public String getHoraAcceso() {
        return horaAcceso;
    }

    public void setHoraAcceso(LocalTime horaAcceso) {
        DateTimeFormatter formato=DateTimeFormatter.ofPattern("HH:mm:ss");
        this.horaAcceso=horaAcceso.format(formato);
    }

    public ProgressBar getPgrBar() {
        return pgrBar;
    }

    public void setPgrBar(ProgressBar pgrBar) {
        this.pgrBar = pgrBar;
    }
    public ArchivoImpresion(int noArchivo){this.noArchivo=noArchivo;}
    /*public void Imprimir(TableView tbv){

        super.run();
        int hojas=0;
        while(hojas<noHojas){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ie){}
            hojas++;
            pgrBar.setProgress(hojas/((double)(noHojas)));
            tbv.refresh();
        }
    }*/
}
