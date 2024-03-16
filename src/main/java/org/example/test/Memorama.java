package org.example.test;

import javafx.application.Application;
import  javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Memorama extends Stage {
    private Scene escena;
    private VBox vContenedor, vNombres, vDatos;
    private Label lblDimensiones, lblJugador1, lblJugador2, lblPares, lblTiempo;
    private HBox hContenedor1, hContenedor2, hContenedor3;
    private GridPane gdpTablero;
    private TextField txtDimensiones;
    private Button btnRevolver, btnDetener, btnDestapado;
    private Button[][] arBotones;
    private Timer tmrTiempo, tmrCuentaRegresiva;
    private TimerTask tmtskCuentaRegresiva;
    private byte filas, columnas, pares, pntj1=0, pntj2=0;
    private boolean blnDestapado = false, blnJugador;//blnJugador: true ->Jugador1 ;false->Jugador2
    private String rutaDestapada;
    private int botonesRestantes, contTurno;

    public Memorama() {
        CrearUI();
        this.setTitle("Memorama");
        this.setScene(escena);
        this.setMaximized(true);
        this.show();
    }

    private void CrearUI() {

        tmrTiempo = new Timer();

        lblPares = new Label("Número de pares ");
        lblJugador1 = new Label("Jugador 1      Puntos:"+pntj1);
        lblJugador1.setId("color-Jugador-Desactivado");
        lblJugador2 = new Label("Jugador 2      Puntos:"+pntj2);
        lblJugador2.setId("color-Jugador-Desactivado");
        lblTiempo = new Label("00:00");
        lblTiempo.setId("color-Timer-Desactivado");

        txtDimensiones = new TextField("5");
        txtDimensiones.setMinWidth(10);

        gdpTablero = new GridPane();
        CrearTablero();

        btnRevolver = new Button("Revolver");
        btnRevolver.setOnAction(event -> Revolver_N());
        btnDetener=new Button("Detener");
        btnDetener.setVisible(false);
        btnDetener.setOnAction(event -> Detener());

        hContenedor1 = new HBox(lblPares, txtDimensiones, btnRevolver, btnDetener, lblTiempo);
        hContenedor1.setSpacing(20);
        hContenedor1.setPadding(new Insets(10));
        vNombres=new VBox(lblJugador1,lblJugador2);
        hContenedor3 = new HBox(vNombres);//Aqui van los nombres y puntaje
        hContenedor3.setSpacing(20);
        vDatos = new VBox(lblTiempo, hContenedor3);
        vDatos.setSpacing(20);
        hContenedor2 = new HBox(gdpTablero, vDatos);
        hContenedor2.setSpacing(30);

        vContenedor = new VBox(hContenedor1, hContenedor2/*, btnIniciar*/);
        vContenedor.setSpacing(30);

        escena = new Scene(vContenedor, 600, 300);
        escena.getStylesheets().add(getClass().getResource("/estilos/memorama.css").toString());
    }

    private void CrearTablero() {
        arBotones = new Button[3][10];
        for (int i = 0; i < arBotones.length; i++) {
            for (int j = 0; j < arBotones[0].length; j++) {
                arBotones[i][j] = new Button();
                arBotones[i][j].setVisible(false);
                gdpTablero.add(arBotones[i][j], j, i);
            }
        }
    }

    private void LimpiarTablero() {
        for (int i = 0; i < arBotones.length; i++) {
            for (int j = 0; j < arBotones[0].length; j++) {
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
    private void Revolver_N() {
        if (ValidarDimensiones()) {
            contTurno=0;
            blnJugador=true;
            pntj1 = pntj2= 0;
            lblJugador1.setText("Jugador 1      Puntos:0");
            lblJugador2.setText("Jugador 2      Puntos:0");
            lblJugador1.setId("color-Jugador-Turno");
            lblJugador2.setId("color-Jugador-NoTurno");
            btnDetener.setVisible(true);
            btnRevolver.setDisable(true);
            CuentaRegresiva();
            botonesRestantes = pares;
            LimpiarTablero();
            setDisposicion();
            String[] arImagenes = {"depth.png", "groG.png", "rher.png", "sylvian.png", "alllMer.png", "fAndH.png", "logic.png", "sulfur.png", "vinushka.png", "moon.png", "gFAndH.png", "pinecone.png", "clock.png", "sylvian2.png", "groGoroth2.png"};
            int posx, posy, cont = 0;
            ImageView imvCarta;
            int n_veces;
            if (filas == 3) {
                n_veces = pares;
            } else {
                n_veces = columnas;
            }
            for (int i = 0; i < n_veces; ) {
                posy = (int) (Math.random() * 41256 % (filas));
                posx = (int) (Math.random() * 28273 % (columnas));
                if (!arBotones[posy][posx].isVisible()) {
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
                    if (cont == 2) {
                        i++;
                        cont = 0;
                    }
                } else {
                    //System.out.println("("+posx+","+posy+") está visible");
                }
            }
        }
    }

    private boolean ValidarDimensiones() {
        String cad = txtDimensiones.getText(), strNum = "";
        boolean flag = false;
        for (int i = 0; i < cad.length(); i++) {
            char num = cad.charAt(i);
            switch (num) {
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
                    strNum = strNum + num;
                    flag = true;
                    break;
                default:
                    strNum="*"+strNum;
                    flag = false;
                    i = cad.length();
            }
        }
        if (flag && strNum.length()<=2 && Byte.parseByte(strNum) <= 15 && Byte.parseByte(strNum)>=3) {
            pares = Byte.parseByte(strNum);
        }else{
            if(strNum.charAt(0)=='*'){
                txtDimensiones.setText("Entrada no válida");
            }else{
                txtDimensiones.setText("Máximo 15, mínimo 3");
            }
            flag=false;
        }
        return flag;
    }

    private void setDisposicion() {
        if (pares % 3 == 0) {
            columnas = (byte) (pares * 2 / 3);
            filas = 3;
        } else {
            columnas = pares;
            filas = 2;
        }
    }

    private void Destapar(int y, int x, String ruta) {
        if (blnDestapado) {//Revisar que no sea el mismo botón
            if (rutaDestapada.equals(ruta) && btnDestapado != arBotones[y][x]) {//El par se encontró
                if(contTurno>0){contTurno--;}
                botonesRestantes--;
                if(blnJugador) {
                    pntj1++;
                    lblJugador1.setText("Jugador 1      Puntos:"+pntj1);
                }else{
                    pntj2++;
                    lblJugador2.setText("Jugador 2      Puntos:"+pntj2);
                }
                arBotones[y][x].getGraphic().setVisible(true);
                btnDestapado.getGraphic().setVisible(true);
                btnDestapado = null;
                rutaDestapada = "";
                blnDestapado = false;
                btnDetener.setVisible(false);
                tmrCuentaRegresiva.cancel();
                CuentaRegresiva();
                if (botonesRestantes == 0) {
                    tmrCuentaRegresiva.cancel();
                    lblTiempo.setId("color-Timer-Desactivado");
                    lblJugador1.setId("color-Timer-Desactivado");
                    lblJugador2.setId("color-Timer-Desactivado");
                    lblTiempo.setText("00:00");
                    MensajeGanador();
                }
            } else {//No era el par correcto
                contTurno++;
                if(contTurno==2){
                    if(blnJugador){
                        lblJugador1.setId("color-Jugador-NoTurno");
                        lblJugador2.setId("color-Jugador-Turno");
                        blnJugador=false;
                        tmrCuentaRegresiva.cancel();
                        CuentaRegresiva();
                    }else{
                        lblJugador1.setId("color-Jugador-Turno");
                        lblJugador2.setId("color-Jugador-NoTurno");
                        blnJugador=true;
                        tmrCuentaRegresiva.cancel();
                        CuentaRegresiva();
                    }
                    contTurno=0;
                }
                arBotones[y][x].getGraphic().setVisible(true);
                tmrTiempo.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //System.out.println("2 segundos");
                        blnDestapado = false;
                        arBotones[y][x].getGraphic().setVisible(false);
                        btnDestapado.getGraphic().setVisible(false);
                    }
                }, 500);
            }
        } else {
            arBotones[y][x].getGraphic().setVisible(true);
            btnDestapado = arBotones[y][x];
            rutaDestapada = ruta;
            blnDestapado = true;

        }
        //"C:\Users\tadeo\JavaProjects\Test\src\main\resources\images" ruta para comparar
    }

    private void MensajeGanador() {
        String jugador;
        byte pntj;
        if(pntj1>pntj2) {
            jugador = "jugador 1";
            pntj=pntj1;
        }else{
            jugador="jugador 2";
            pntj=pntj2;
        }
        Alert alertaGanador = new Alert(Alert.AlertType.INFORMATION);
        try {
            alertaGanador.setTitle("Mensaje");
            alertaGanador.setHeaderText("Felicidades, el "+jugador+" ha ganado!!");//Poner cual jugador gano
            alertaGanador.setContentText("Ha encontrado "+pntj+" pares");//Mencionar los pares que descubrio
            Optional<ButtonType> result = alertaGanador.showAndWait();
            if (result.get() == ButtonType.OK) {
                Revolver_N();
            }
        } catch (NoSuchElementException nSEE) {
        }
    }

    private void CuentaRegresiva() {
        tmrCuentaRegresiva=new Timer();
        lblTiempo.setId("color-Timer-Activado");
        tmtskCuentaRegresiva=new TimerTask() {
            int segundos=30;
            @Override
            public void run() {
                if(segundos>0){
                    //if(blnParEncontrado){
                      //  segundos=30;
                    //}
                    if(segundos<11)
                        Platform.runLater(() ->lblTiempo.setText("00:0"+segundos));
                    else
                        Platform.runLater(() ->lblTiempo.setText("00:"+segundos));
                    segundos--;
                }else{
                    //Platform.runLater(()->System.out.println());
                    if(blnJugador){
                        lblJugador1.setId("color-Jugador-NoTurno");
                        lblJugador2.setId("color-Jugador-Turno");
                        blnJugador=false;
                        tmrCuentaRegresiva.cancel();
                        CuentaRegresiva();
                    }else{
                        lblJugador1.setId("color-Jugador-Turno");
                        lblJugador2.setId("color-Jugador-NoTurno");
                        blnJugador=true;
                        tmrCuentaRegresiva.cancel();
                        CuentaRegresiva();
                    }
                    //tmrCuentaRegresiva.cancel();
                }
            }
        };
        tmrCuentaRegresiva.scheduleAtFixedRate(tmtskCuentaRegresiva,0,1000);
    }
    private void Detener(){
        LimpiarTablero();
        tmrCuentaRegresiva.cancel();
        lblTiempo.setId("color-Timer-Desactivado");
        lblTiempo.setText("00:00");
        lblJugador1.setId("color-Timer-Desactivado");
        lblJugador1.setText("Jugador 1      Puntos:0");
        lblJugador2.setId("color-Timer-Desactivado");
        lblJugador2.setText("Jugador 2      Puntos:0");
        btnDetener.setVisible(false);
        btnRevolver.setDisable(false);
    }
}
