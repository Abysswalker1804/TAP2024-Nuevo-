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
        txtPantalla=new TextField("0");
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
        if(txtPantalla.getText().equals("0.0") || txtPantalla.getText().equals("ERROR Math")
                || flagIgual || txtPantalla.getText().equals("ERROR Syntax")){
            txtPantalla.setText("");
            txtPantalla.setText(simbolo+"");
            flagIgual=false;
        }else{
            String anterior=txtPantalla.getText();
            txtPantalla.setText(anterior+simbolo);
        }
    }// No manejar excepciones

    private boolean isThatANumber(String txt){
        boolean flagExito=true;
        if(txt.equals("ERROR Syntax") || txt.equals("ERROR Math")){
            flagExito=false;
        }else{
            short puntos=0;
            for (int i=0; i<txt.length(); i++){
                if(txt.charAt(i)=='.')
                    puntos++;
            }
            if(puntos>1)
                flagExito=false;
        }
        return flagExito;
    }
    private void sumar(){
        if(isThatANumber(txtPantalla.getText()) && !txtPantalla.getText().isEmpty()){
            numAnterior=Double.parseDouble(txtPantalla.getText());
            chrOperacion='s';
            txtPantalla.setText("");
        }else{
            txtPantalla.setText("ERROR Syntax");
        }
    }
    private void restar(){
        if(isThatANumber(txtPantalla.getText()) && !txtPantalla.getText().isEmpty()){
            numAnterior=Double.parseDouble(txtPantalla.getText());
            chrOperacion='r';
            txtPantalla.setText("");
        }else{
            txtPantalla.setText("ERROR Syntax");
        }
    }
    private void multiplicar(){
        if(isThatANumber(txtPantalla.getText()) && !txtPantalla.getText().isEmpty()){
            numAnterior=Double.parseDouble(txtPantalla.getText());
            chrOperacion='m';
            txtPantalla.setText("");
        }else{
            txtPantalla.setText("ERROR Syntax");
        }
    }
    private void dividir(){
        if(isThatANumber(txtPantalla.getText()) && !txtPantalla.getText().isEmpty()){
            numAnterior=Double.parseDouble(txtPantalla.getText());
            chrOperacion='d';
            txtPantalla.setText("");
        }else{
            txtPantalla.setText("ERROR Syntax");
        }
    }

    private void punto(){
        String num=txtPantalla.getText();
        if(num.equals(".") || num.isEmpty() || !isThatANumber(num) || num.equals("ERROR Math") || num.equals("ERROR Syntax")){
            txtPantalla.setText("0.");
        }else{
            setValue(arEtiquetas[13]);
        }
    }
    private void igual(){
        if(txtPantalla.getText().equals("ERROR Syntax") || txtPantalla.getText().equals("ERROR Math") || !isThatANumber(txtPantalla.getText())){
            txtPantalla.setText("ERROR Syntax");
        }else{
            if(numAnterior!=0 || txtPantalla.getText().isEmpty()){
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
                numAnterior=0;
                flagIgual=true;
                if(flagError)
                    txtPantalla.setText("ERROR Math");
                else
                    txtPantalla.setText(numResultado+"");
            }else{
                if(Double.parseDouble(txtPantalla.getText())==0){
                    txtPantalla.setText("ERROR Math");
                }
            }
        }
    }
}
