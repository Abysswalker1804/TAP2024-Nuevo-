package org.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.Timer;

public class Memorama extends Stage{
    private Scene escena;
    private VBox vContenedor, vNombres;
    private Label lblDimensiones, lblJugadores, lblPares, lblTiempo, lblPuntaje;
    private HBox hContenedor1, hContenedor2, hContenedor3;
    private GridPane gdpTablero;
    private TextField txtDimensiones;
    private Button btnGenerar, btnRevolver, btnIniciar, btnDestapado;
    private Button[][] arBotones;
    private Timer tmrTiempo;
    private byte filas, columnas, pares;
    private boolean blnDestapado=false;
    private String rutaDestapada;

    public Memorama(){
        CrearUI();
        this.setTitle("Memorama");
        this.setScene(escena);
        this.setMaximized(true);
        this.show();
    }
    private void CrearUI(){

        tmrTiempo=new Timer();

        //lblDimensiones=new Label("Dimensión del tablero");
        lblPares=new Label("Número de pares ");
        lblJugadores=new Label("Jugador 1\nJugador 2");
        lblTiempo=new Label("00:00");
        lblPuntaje=new Label("0\n0");


        txtDimensiones =new TextField("5");
        txtDimensiones.setMinWidth(10);

        gdpTablero=new GridPane();
        CrearTablero();

        btnGenerar=new Button("Generar");
        //btnGenerar.setOnAction(event -> CrearTablero(1));
        btnRevolver=new Button("Revolver");
        btnRevolver.setOnAction(event -> Revolver_N());
        //btnIniciar.setOnAction(event -> Revolver());

        hContenedor1=new HBox(lblPares, txtDimensiones,btnGenerar, btnRevolver, lblTiempo);
        hContenedor1.setSpacing(20);
        hContenedor1.setPadding(new Insets(10));
        hContenedor3=new HBox(lblJugadores,lblPuntaje);
        hContenedor3.setSpacing(20);
        vNombres=new VBox(lblTiempo,hContenedor3);
        vNombres.setSpacing(20);
        hContenedor2=new HBox(gdpTablero, vNombres);
        hContenedor2.setSpacing(30);


        vContenedor=new VBox(hContenedor1, hContenedor2/*, btnIniciar*/);
        vContenedor.setSpacing(30);



        escena=new Scene(vContenedor, 600,300);
    }
    private void CrearTablero(){
        arBotones=new Button[3][10];
        for(int i=0; i<arBotones.length; i++){
            for(int j=0; j<arBotones[0].length; j++){
                arBotones[i][j]=new Button();
                arBotones[i][j].setVisible(false);
                gdpTablero.add(arBotones[i][j],j,i);
            }
        }
    }
    private void LimpiarTablero(){
        for(int i=0; i<arBotones.length; i++){
            for(int j=0; j<arBotones[0].length; j++){
                arBotones[i][j].setVisible(false);
                //System.out.println("("+i+","+j+") es invisible");
            }
        }
    }

    /*private void Revolver(){
        if(ValidarDimensiones()) {
            LimpiarTablero();
            setDisposicion();
            String[] arImagenes = {"depth.png", "groG.png", "rher.png", "sylvian.png", "alllMer.png", "fAndH.png", "logic.png", "sulfur.png", "vinushka.png", "moon.png", "gFAndH.png", "pinecone.png", "clock.png", "sylvian2.png", "groGoroth2.png"};
            arBotones = new Button[filas][columnas];
            int posx, posy, cont = 0;
            ImageView imvCarta;
            for (int i = 0; i < arImagenes.length; ) {
                posx = (int) (Math.random() * 41256 %(filas));
                posy = (int) (Math.random() * 28273 %(columnas));
                //System.out.println("Posición ("+posx+","+posy+") ocupada\ni: "+i);
                if (arBotones[posx][posy] == null) {
                    try{
                        arBotones[posx][posy] = new Button();
                        imvCarta = new ImageView(getClass().getResource("/images/" + arImagenes[i]).toString());
                        imvCarta.setFitHeight(150);
                        imvCarta.setFitWidth(100);
                        arBotones[posx][posy].setGraphic(imvCarta);
                        ImageView imvRuta=(ImageView) (arBotones[posx][posy].getGraphic());
                        arBotones[posx][posy].setPrefSize(100, 150);
                        gdpTablero.add(arBotones[posx][posy], posy, posx);
                        cont++;
                        if (cont == filas) {
                            //System.out.println("Imagen '"+arImagenes[i]+"' guardada");
                            i++;
                            cont = 0;
                        }
                    }catch (NullPointerException eN){
                        System.out.println("ERROR\n'"+arImagenes[i]+"' no encontrada");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }*/
    private void Revolver_N(){
        if(ValidarDimensiones()){
            String ruta;
            LimpiarTablero();
            setDisposicion();
            String[] arImagenes = {"depth.png", "groG.png", "rher.png", "sylvian.png", "alllMer.png", "fAndH.png", "logic.png", "sulfur.png", "vinushka.png", "moon.png", "gFAndH.png", "pinecone.png", "clock.png", "sylvian2.png", "groGoroth2.png"};
            int posx,posy,cont=0;
            ImageView imvCarta;
            int n_veces;
            if(filas==3){
                n_veces=pares;
            }else{
                n_veces=columnas;
            }
            for(int i=0; i<n_veces;){
                posy = (int) (Math.random() * 41256 %(filas));
                posx = (int) (Math.random() * 28273 %(columnas));
                if(!arBotones[posy][posx].isVisible()){
                    arBotones[posy][posx] = new Button();
                    imvCarta = new ImageView(getClass().getResource("/images/" + arImagenes[i]).toString());
                    imvCarta.setFitHeight(100);
                    imvCarta.setFitWidth(100);
                    arBotones[posy][posx].setGraphic(imvCarta);
                    arBotones[posy][posx].setPrefSize(100, 100);
                    arBotones[posy][posx].setVisible(true);

                    int finalPosy = posy, finalPosx = posx;
                    int finalI = i;

                    arBotones[posy][posx].setOnAction(event -> Destapar(finalPosy, finalPosx, arImagenes[finalI]));
                    gdpTablero.add(arBotones[posy][posx], posx, posy);
                    arBotones[posy][posx].getGraphic().setVisible(false);//Volver a los gráficos invisibles
                    cont++;
                    if(cont == 2){
                        i++;
                        cont=0;
                    }
                }else{
                    //System.out.println("("+posx+","+posy+") está visible");
                }
            }
        }
    }
    private boolean ValidarDimensiones(){
        String cad = txtDimensiones.getText(), strNum="";
        boolean flag=false;
        for(int i=0; i<cad.length(); i++){
            char num=cad.charAt(i);
            switch(num){
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    strNum=strNum+num;
                    flag=true;
                    break;
                default:
                    txtDimensiones.setText("Entrada no válida");
                    flag=false;
                    i=cad.length();
            }
        }
        if(flag && Byte.parseByte(strNum)<=15)
            pares=Byte.parseByte(strNum);
        else
            txtDimensiones.setText("Máximo 15 pares");
        return  flag;
    }
    private void setDisposicion(){
        if(pares%3==0){
            columnas=(byte)(pares*2/3);
            filas=3;
        }else{
            columnas=pares;
            filas=2;
        }
    }
    private void Destapar(int y, int x, String ruta){
        if(blnDestapado){//Revisar que no sea el mismo botón
            if(rutaDestapada.equals(ruta)){
                arBotones[y][x].getGraphic().setVisible(true);
                btnDestapado.getGraphic().setVisible(true);
                btnDestapado=null;
                rutaDestapada="";
                blnDestapado=false;
            }else{
                blnDestapado=false;
                arBotones[y][x].getGraphic().setVisible(false);
                btnDestapado.getGraphic().setVisible(false);
            }
        }else{
            arBotones[y][x].getGraphic().setVisible(true);
            btnDestapado=arBotones[y][x];
            rutaDestapada=ruta;
            blnDestapado=true;
        }
        //"C:\Users\tadeo\JavaProjects\Test\src\main\resources\images" ruta para comparar
    }
}
