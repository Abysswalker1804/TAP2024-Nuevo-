package org.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
    private HBox hContenedor1, hContenedor2;
    private GridPane gdpTablero;
    private TextField txtDimensiones;
    private Button btnGenerar, btnRevolver, btnIniciar;
    private Button[][] arBotones=new Button[2][4];
    private Timer tmrTiempo;

    public Memorama(){
        CrearUI();
        this.setTitle("Memorama");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){

        tmrTiempo=new Timer();

        //lblDimensiones=new Label("Dimensión del tablero");
        lblPares=new Label("Pares ");
        lblJugadores=new Label("Jugador 1\nJugador 2");
        lblTiempo=new Label("00:00");
        lblPuntaje=new Label("0\n0");


        txtDimensiones =new TextField("5x2");
        txtDimensiones.setMinWidth(10);

        gdpTablero=new GridPane();
        //CrearTablero(0);

        btnGenerar=new Button("Generar");
        //btnGenerar.setOnAction(event -> CrearTablero(1));
        btnRevolver=new Button("Revolver");
        btnRevolver.setOnAction(event -> Revolver());
        //btnIniciar.setOnAction(event -> Revolver());

        hContenedor1=new HBox(lblPares, txtDimensiones,btnGenerar, btnRevolver, lblTiempo);
        hContenedor1.setSpacing(20);
        hContenedor2=new HBox(gdpTablero, lblJugadores, lblPuntaje);
        hContenedor2.setSpacing(30);

        vNombres=new VBox();
        vContenedor=new VBox(hContenedor1, hContenedor2/*, btnIniciar*/);
        vContenedor.setSpacing(30);



        escena=new Scene(vContenedor, 600,300);
    }
    /*private void CrearTablero(int valor){
        //String texto=txtDimensiones.getText();
        if(valor == 0 ){
            for(int i=0; i<2; i++){
                for(int j=0; j<4; j++) {
                    arBotones[i][j]=new Button("");
                    arBotones[i][j].setPrefSize(50,50);
                    gdpTablero.add(arBotones[i][j],j,i);
                }
            }
        }
    }*/

    private void Revolver(){
        String[] arImagenes={"depth.png","groG.png", "rher.png", "sylvian.png"};
        arBotones=new Button[2][4];
        int posx=0, posy=0, cont= 0;
        ImageView imvCarta;
        for(int i=0; i < arImagenes.length; ){
            posx=(int)(Math.random()*2);
            posy=(int)(Math.random()*4);
            if(arBotones[posx][posy] == null){
                arBotones[posx][posy]= new Button();
                imvCarta=new ImageView(getClass().getResource("/images/"+arImagenes[i]).toString());
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                arBotones[posx][posy].setGraphic(imvCarta);
                arBotones[posx][posy].setPrefSize(100,150);
                gdpTablero.add(arBotones[posx][posy],posy,posx);
                cont++;
                if(cont == 2) {
                    i++;
                    cont=0;
                }
            }
        }
        Image[][] arCartas =new Image[2][5];
        Image carta;
        //ImageView imvCarta;
    }
}
