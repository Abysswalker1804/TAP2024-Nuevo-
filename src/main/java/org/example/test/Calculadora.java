package org.example.test;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Label;

public class Calculadora extends Stage{
    private Scene escena;
    private VBox vContenedor;
    private GridPane gdpTeclado;
    private TextField txtPantalla;
    private Button[][] arBotones=new Button[4][4];
    private char [] arEtiquetas= {'7','8','9','/','4','5','6','*','1','2','3','-','0','.','=','+'};
    private double numAnterior;
    private char chrOperacion;
    private boolean flagIgual=false;

    public Calculadora(){
        CrearUI();
        this.setTitle("Mi primer Calculadora :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        txtPantalla=new TextField("0.0");
        txtPantalla.setEditable(false);
        gdpTeclado=new GridPane();
        CrearTeclado();
        vContenedor=new VBox(txtPantalla, gdpTeclado);
        vContenedor.setSpacing(5);
        escena=new Scene(vContenedor, 200,200);
        escena.getStylesheets().add(getClass().getResource("/estilos/calculadora.css").toString());
    }

    private void CrearTeclado(){
        int pos=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++) {
                arBotones[i][j]=new Button(arEtiquetas[pos]+"");
                arBotones[i][j].setPrefSize(50,50);
                int finalPos = pos;
                if(i==3){
                    if(j==3){
                        arBotones[i][j].setOnAction(event -> sumar());
                    }else{
                        if(j==2){
                            arBotones[i][j].setOnAction(event -> igual());
                        }
                        else{
                            if(j==1){
                                arBotones[i][j].setOnAction(event -> punto());
                            }else{
                                arBotones[i][j].setOnAction(event -> setValue(arEtiquetas[finalPos]));
                            }
                        }
                    }
                }else{
                    if(i==2 && j==3){
                        arBotones[i][j].setOnAction(event -> restar());
                    } else{
                        if(i==1 && j==3){
                            arBotones[i][j].setOnAction(event -> multiplicar());
                        }else{
                            if(i==0 && j==3){
                                arBotones[i][j].setOnAction(event -> dividir());
                            }else{
                                arBotones[i][j].setOnAction(event -> setValue(arEtiquetas[finalPos]));
                            }
                        }
                    }
                }
                gdpTeclado.add(arBotones[i][j],j,i);

                if(arEtiquetas[pos]=='+' || arEtiquetas[pos]=='-' || arEtiquetas[pos]=='*' || arEtiquetas[pos]=='/' || arEtiquetas[pos]=='='){
                    arBotones[i][j].setId("color-operador");
                }
                pos++;
            }
        }
    }

    private void setValue(char simbolo) {
        if(txtPantalla.getText().equals("0.0") || txtPantalla.getText().equals("ERROR Math") || flagIgual){
            txtPantalla.setText("");
            txtPantalla.setText(simbolo+"");
            flagIgual=false;
        }else{
            String anterior=txtPantalla.getText();
            txtPantalla.setText(anterior+simbolo);
        }
    }// No manejar excepciones

    private void sumar(){//Falta controlar si son números muy grandes
        numAnterior=Double.parseDouble(txtPantalla.getText());
        chrOperacion='s';
        txtPantalla.setText("");
    }
    private void restar(){
        numAnterior=Double.parseDouble(txtPantalla.getText());
        chrOperacion='r';
        txtPantalla.setText("");
    }
    private void multiplicar(){
        numAnterior=Double.parseDouble(txtPantalla.getText());
        chrOperacion='m';
        txtPantalla.setText("");
    }
    private void dividir(){
        numAnterior=Double.parseDouble(txtPantalla.getText());
        chrOperacion='d';
        txtPantalla.setText("");
    }

    private void punto(){//Hay que controlar si hay Error Math en pantalla
        String num=txtPantalla.getText();
        for(int i=0; i<num.length();i++){
            if(num.charAt(i) == '.'){
                txtPantalla.setText(num.substring(0,i));
            }
        }
    }
    private void igual(){
        double numActual=Double.parseDouble(txtPantalla.getText()), numResultado=0;
        boolean flagError=false;
        txtPantalla.setText("");
        switch(chrOperacion){
            case 's':
                numResultado=numAnterior+numActual;
                break;
            case 'r':
                numResultado=numAnterior-numActual;
                break;
            case 'm':
                numResultado=numAnterior*numActual;
                break;
            case 'd':
                if(numActual !=0)
                    numResultado=numAnterior/numActual;
                else
                    flagError=true;
        }
        flagIgual=true;
        if(flagError)
            txtPantalla.setText("ERROR Math");
        else
            txtPantalla.setText(numResultado+"");
    }
}
