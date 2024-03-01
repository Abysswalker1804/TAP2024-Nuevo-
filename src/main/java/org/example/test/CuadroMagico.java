package org.example.test;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.*;
import java.security.spec.ECField;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javax.swing.JOptionPane;

public class CuadroMagico extends Stage{
    private Scene escena;
    private VBox VContenedor;
    private TextField txtCuadro;
    private Label lblCuadro;
    private Button btnGenerar, btnLlenar, btnBorrar;
    private GridPane gdpCuadroMagico;
    private int posx, posy;
    private ObjectInputStream entrada_ois;
    private FileInputStream fileIn;
    private ObjectOutputStream salida_oos;
    private FileOutputStream fileOut;
    public CuadroMagico(){
        CrearUI();
        this.setTitle("Cuadro Mágico");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        //Labels
        lblCuadro=new Label("Ingrese las dimensiones del cuadro mágico:");
        lblCuadro.setId("color-label-cuadro");
        //TextFields
        txtCuadro=new TextField("3x3");
        txtCuadro.setMaxWidth(200);
        //Buttons
        btnGenerar=new Button("Generar");
        btnGenerar.setOnAction(event -> Generar());
        btnGenerar.setId("color-boton-generar");
        btnBorrar=new Button("Borrar Cuadro");
        btnBorrar.setOnAction(event -> BorrarGP());
        btnBorrar.setId("color-boton-borrar");
        btnLlenar=new Button("Llenar cuadro");
        btnLlenar.setOnAction(event -> LlenarCuadro());
        btnLlenar.setId("color-boton-llenar");
        //GridPanes
        gdpCuadroMagico=new GridPane();
        gdpCuadroMagico.setAlignment(Pos.CENTER);
        gdpCuadroMagico.setHgap(10);
        gdpCuadroMagico.setVgap(10);
        //Contenedor Principal
        VContenedor=new VBox(lblCuadro,txtCuadro,btnGenerar,btnBorrar,btnLlenar,gdpCuadroMagico);
        VContenedor.setSpacing(20);
        VContenedor.setAlignment(Pos.CENTER);
        escena=new Scene(VContenedor,400 ,300);
        escena.getStylesheets().add(getClass().getResource("/estilos/CuadroMagico.css").toString());
    }
    private void Generar(){
        setXY();
        if(ValidarCuadrado()){
            txtCuadro.setText(posx+"x"+posy);
            CrearCuadro();
            escena.getWindow().setHeight(500);
        }else{
            txtCuadro.setText("Entrada no válida");
            BorrarGP();
        }
    }
    private void setXY(){
        boolean hayNum=false;
        String v_txt=txtCuadro.getText()+"x", num="";
        for(int i=0, j=0; i<v_txt.length() && j<2; i++){
            char caracter= v_txt.charAt(i);
            switch(caracter) {
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
                    num = num + caracter;
                    hayNum=true;
                    break;
                case 'x':
                case 'X':
                    if(hayNum){
                        if (j == 0){
                            posy = Integer.parseInt(num);
                        }else {
                            if (j == 1) {
                                posx = Integer.parseInt(num);
                            }
                        }
                        num="";
                        j++;
                    }else{
                        txtCuadro.setText("Entrada no válida");
                        i=v_txt.length()+1;//Terminar el ciclo
                    }
                    break;
                default:
                    txtCuadro.setText("Entrada no válida");
                    i=v_txt.length()+1;//Terminar el ciclo
            }
        }
    }
    private void BorrarGP(){
        gdpCuadroMagico.getChildren().clear();
        escena.getWindow().setHeight(300);
    }
    private void CrearCuadro(){
        gdpCuadroMagico.getChildren().clear();
        int px=0, py=0;
        Label etiqueta;
        try{
            fileOut=new FileOutputStream("C:\\Users\\tadeo\\JavaProjects\\Test\\src\\main\\java\\org\\example\\test\\cuadro.dat");
            salida_oos=new ObjectOutputStream(fileOut);
            int cant_celdas=posx*posy;
            CeldaArchivos celda;
            for(int i=0; i<cant_celdas; i++){
                celda=new CeldaArchivos(String.valueOf(i), i+1);
                etiqueta=new Label(String.valueOf(i+1));
                gdpCuadroMagico.add(etiqueta, px, py);
                salida_oos.writeObject(celda);
                px++;
                if(px==posx){
                    px=0;
                    py++;
                }
            }
            salida_oos.close();
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error con los archivos!","Error!", JOptionPane.PLAIN_MESSAGE);
            //System.out.println(ioe.toString());
            //ioe.printStackTrace();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Ocurrió un error no relacionado con archivos!","Error!", JOptionPane.PLAIN_MESSAGE);
            //System.out.println(e.toString());
        }
    }
    private boolean ValidarCuadrado(){
        if(posx==posy && posx>=3 && posx%2!=0   )
            return true;
        else
            return false;
    }
    private void LlenarCuadro(){
        Label etiqueta=new Label();
        int px=((posx/2)+1)*12,py=0;
        BorrarGP();
        try{
            fileIn=new FileInputStream("C:\\Users\\tadeo\\JavaProjects\\Test\\src\\main\\java\\org\\example\\test\\cuadro.dat");
            entrada_ois=new ObjectInputStream(fileIn);
            Object objeto;
            CeldaArchivos celda;
            //entrada_ois.skip(24);
            objeto=entrada_ois.readObject();
            celda=(CeldaArchivos) (objeto);
            etiqueta.setText(celda.getContenido());
            gdpCuadroMagico.add(etiqueta, px, py);

            //entrada_ois.skipBytes(12);
            objeto=entrada_ois.readObject();
            celda=(CeldaArchivos) (objeto);
            etiqueta.setText(celda.getContenido());
            gdpCuadroMagico.add(etiqueta,0,0);//Investiga como modificar los label
            /*
            for(int i=1; i<=(posx*posx); i++){
                entrada_ois.skipBytes(i*12);
                //Pendiente de solucionar cómo llenar el cuadro
            }*/
            entrada_ois.close();
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error con los archivos!","Error!", JOptionPane.PLAIN_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ocurrió un error no relacionado con archivos!","Error!", JOptionPane.PLAIN_MESSAGE);
            e.printStackTrace();
        }
    }
}
class CeldaArchivos implements Serializable{//Plantear bien la estructura
    private String posicion;
    private int contenido;
    //Parece ser que cada objeto medirá 12 bytes = String (8 bytes) + int (4 bytes)
    public CeldaArchivos(String v_pos, int v_cont){
        posicion=v_pos;
        contenido=v_cont;
    }
    public CeldaArchivos(String v_pos){
        posicion=v_pos;
    }
    public String getPosicion(){
        return posicion;
    }
    public String getContenido() {
        return String.valueOf(contenido);
    }
    public void setContenido(int contenido) {
        this.contenido = contenido;
    }
    public void setPosicion(String posicion) {this.posicion = posicion;}
}