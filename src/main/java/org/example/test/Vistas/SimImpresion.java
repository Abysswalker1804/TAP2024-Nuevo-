package org.example.test.Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.test.components.TableProgressBar;
import org.example.test.modelos.ArchivoImpresion;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class SimImpresion extends Stage {
    private Scene escena;
    private BorderPane bdpPrincipal;
    private Panel pnlPrincipal;
    private HBox hBotones;
    private Button btnAgregar, btnIniciar;
    private TableView<ArchivoImpresion> tbvImpresiones;
    private Revisar hiloRevisarArch;
    private boolean estaActivado=false;
    private int noArchivo=1, progresoActual=0;
    private String [] tipo={"Proyecto_A","Informe_Anual","Reporte_Ventas","Plan_Estrategico","Evaluacion_Desempeno","Analisis_Datos","Encuesta_Cliente","Requerimientos","Progreso_Mensual","Estudio_Mercado"};
    private String [] nombres={"Juan_Perez","Maria_Gomez","Carlos_Lopez","Ana_Fernandez","Jose_Rodriguez","Laura_Martinez","Luis_Garcia","Carmen_Hernandez","Miguel_Sanchez","Lucia_Ramirez"};
    private String [] terminaciones={".pdf",".doc",".docx",".xlsx"};
    public SimImpresion(){
        CrearUI();
        this.setTitle("Simulador de impresión");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        bdpPrincipal=new BorderPane();
        pnlPrincipal=new Panel("Simulador de impresión");
        pnlPrincipal.getStyleClass().add("panel-primary");
        pnlPrincipal.setBody(bdpPrincipal);

        btnAgregar=new Button("Agregar");
        btnAgregar.setOnAction(event -> AgregarArchivo(noArchivo));
        btnIniciar=new Button("Encender");
        btnIniciar.getStyleClass().add("btn-success");
        btnIniciar.setOnAction(event -> ActDesact());
        hBotones=new HBox(btnIniciar,btnAgregar);
        hBotones.setSpacing(10);
        hBotones.setAlignment(Pos.CENTER_LEFT);
        bdpPrincipal.setTop(hBotones);

        CrearTabla();
        bdpPrincipal.setCenter(tbvImpresiones);

        escena=new Scene(pnlPrincipal,605,400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
    private void CrearTabla(){
        tbvImpresiones=new TableView<>();

        TableColumn<ArchivoImpresion,Integer> tbcNoArchivo=new TableColumn<>("No. Archivo");
        tbcNoArchivo.setCellValueFactory(new PropertyValueFactory<>("noArchivo"));
        tbcNoArchivo.setSortable(false);

        TableColumn<ArchivoImpresion,String> tbcNombre=new TableColumn<>("Nombre Archivo");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcNombre.setMinWidth(250);
        tbcNombre.setSortable(false);

        TableColumn<ArchivoImpresion,Integer> tbcHojas=new TableColumn<>("No. Hojas");
        tbcHojas.setCellValueFactory(new PropertyValueFactory<>("noHojas"));
        tbcHojas.setSortable(false);

        TableColumn<ArchivoImpresion,LocalTime> tbcHora=new TableColumn<>("Hora de Acceso");
        tbcHora.setCellValueFactory(new PropertyValueFactory<>("horaAcceso"));
        tbcHora.setSortable(false);

        TableColumn<ArchivoImpresion,Double> tbcProgreso=new TableColumn<>("Progreso");
        tbcProgreso.setCellValueFactory(cellData -> cellData.getValue().progresoProperty().asObject());
        tbcProgreso.setCellFactory(column -> new TableProgressBar());

        tbvImpresiones.getColumns().addAll(tbcNoArchivo,tbcNombre,tbcHojas,tbcHora,tbcProgreso);
    }
    private void ActDesact(){//Activar/desactivar
        if(!estaActivado){//Si está desactivado
            hiloRevisarArch=new Revisar(tbvImpresiones, btnIniciar);
            hiloRevisarArch.start();
            btnIniciar.setText("Apagar");
            btnIniciar.getStyleClass().remove("btn-success");
            btnIniciar.getStyleClass().add("btn-danger");
            estaActivado=!estaActivado;
            btnIniciar.setDisable(true);
        }else{//Si está activado
            hiloRevisarArch.interrupt();
            btnIniciar.setText("Encender");
            btnIniciar.getStyleClass().remove("btn-danger");
            btnIniciar.getStyleClass().add("btn-success");
            estaActivado=!estaActivado;
        }
    }
    private void AgregarArchivo(int num){
        ArchivoImpresion arch=new ArchivoImpresion(num);
        String nombre=tipo[(int)(Math.random()*12345)%10]+nombres[(int)(Math.random()*12345)%10]+terminaciones[(int)(Math.random()*12345)%4];
        arch.setNombre(nombre);
        int hojas=(int)(Math.random()*12345)%31;
        arch.setNoHojas((hojas==0)?1:hojas);
        arch.setHoraAcceso(LocalTime.now());
        arch.setPgrBar(new ProgressBar(0));
        tbvImpresiones.getItems().add(arch);
        noArchivo++;
    }
    /*public void Revisar(){
        new Thread(()->{
                AtomicInteger hojas = new AtomicInteger();
                try {
                    ArchivoImpresion arch=tbvImpresiones.getItems().getFirst();
                    new Thread(()->{
                        if(estaActivado){
                            hojas.set(0);
                            while(hojas.get() < arch.getNoHojas() && estaActivado){
                                try{
                                    Thread.sleep(1000);
                                }catch (InterruptedException ie){}
                                if(progresoActual==0){
                                    hojas.getAndIncrement();
                                    arch.setProgreso(hojas.get() /((double)(arch.getNoHojas())));
                                }else{
                                    hojas.set(progresoActual);
                                    progresoActual=0;
                                }
                            }
                            if(estaActivado){
                                tbvImpresiones.getItems().removeFirst();
                            }
                        }else{
                            try{
                                Thread.sleep(1000);
                            }catch (InterruptedException ie){
                                progresoActual = hojas.get();}
                        }
                    }).start();
                }catch (NoSuchElementException ne){
                    System.out.println("No hay archivos :(");
                }
        }).start();
    }*/
}


class Revisar extends Thread{
    private TableView<ArchivoImpresion> tbvImpresion;
    private Button btnIniciar;
    public Revisar(TableView<ArchivoImpresion> tbvImpresion, Button btn){
        this.tbvImpresion=tbvImpresion;
        btnIniciar=btn;
    }
    @Override
    public void run() {
        super.run();
        try{
            while(!Thread.currentThread().isInterrupted()){
                try{//El hilo se está ejecutando
                    ArchivoImpresion archTemp=tbvImpresion.getItems().getFirst();
                    Imprimir impresion=new Imprimir(archTemp,btnIniciar);
                    impresion.run();
                    tbvImpresion.getItems().removeFirst();
                    Thread.sleep(1000);
                }catch (NoSuchElementException nsee){
                    System.out.println("No hay archivos :(");
                    btnIniciar.setDisable(false);
                    Thread.sleep(5000);
                }catch (Exception e){
                    System.out.println("Ocurrió un error inesperado");
                    e.printStackTrace();
                }
            }
        }catch (InterruptedException ie){}
    }
}

class Imprimir extends Thread{
    private final ArchivoImpresion arch;
    private Button btnIniciar;
    public Imprimir(ArchivoImpresion arch, Button btnIniciar){
        this.arch=arch;
        this.btnIniciar=btnIniciar;
    }
    @Override
    public void run() {
        super.run();
        int hojas=0;
        while(hojas< arch.getNoHojas()){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ie){}
            hojas++;
            arch.setProgreso(hojas/((double)(arch.getNoHojas())));
        }
    }
}

