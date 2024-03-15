package org.example.test;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.*;

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
    private boolean flag_generar=false;
    public CuadroMagico(){
        CrearUI();
        this.setTitle("Cuadro Mágico");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        //GridPanes
        gdpCuadroMagico=new GridPane();
        gdpCuadroMagico.setAlignment(Pos.CENTER);
        gdpCuadroMagico.setHgap(10);
        gdpCuadroMagico.setVgap(10);
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
            if(posx>=11){
                this.setMaximized(true);
            }else{
                escena.getWindow().setHeight(500);
            }
            flag_generar=true;
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
        flag_generar=false;
        gdpCuadroMagico.getChildren().clear();
        escena.getWindow().setHeight(300);
        escena.getWindow().setWidth(400);
    }
    private void CrearCuadro(){
        gdpCuadroMagico.getChildren().clear();
        Label etiqueta;
        try{

            fileOut=new FileOutputStream("C:\\Users\\tadeo\\JavaProjects\\Test\\src\\main\\java\\org\\example\\test\\cuadro.dat");//Cambiar ruta en ordenador

            fileOut=new FileOutputStream("C:\\Users\\tadeo\\JavaProjects\\Test\\src\\main\\java\\org\\example\\test\\cuadro.dat");//Cambiar ruta en ordenador

            salida_oos=new ObjectOutputStream(fileOut);
            CeldaArchivos celda;
            for(int i=0, j=0, v_cont=1; i<posx && j<posy;){
                celda=new CeldaArchivos(v_cont);
                etiqueta=new Label(String.valueOf(v_cont));
                gdpCuadroMagico.add(etiqueta, i, j);
                salida_oos.writeObject(celda);
                i++;
                if(i==posx){
                    i=0;
                    j++;
                }
                v_cont++;
            }
            salida_oos.close();
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error con los archivos!","Error!", JOptionPane.ERROR_MESSAGE);
            //System.out.println(ioe.toString());
            ioe.printStackTrace();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Ocurrió un error no relacionado con archivos!","Error!", JOptionPane.ERROR_MESSAGE);
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
        if(flag_generar){
            int cant_celdas=posx*posy, px=posx/2-1, py=1;
            Label etiqueta;
            ObservableList<Node> children = gdpCuadroMagico.getChildren();
            try{

                fileIn=new FileInputStream("C:\\Users\\tadeo\\JavaProjects\\Test\\src\\main\\java\\org\\example\\test\\cuadro.dat");//Cambiar ruta en ordenador
                fileIn=new FileInputStream("C:\\Users\\tadeo\\JavaProjects\\Test\\src\\main\\java\\org\\example\\test\\cuadro.dat");//Cambiar ruta en ordenador

                entrada_ois=new ObjectInputStream(fileIn);
                Object objeto;
                CeldaArchivos celda;
                for(int i=0; i<posx; i++){
                    for(int j=0; j<posy; j++){
                        py--;
                        if(py<0){py=posy-1;}
                        px++;
                        if(px>=posx){px=0;}
                        objeto=entrada_ois.readObject();
                        celda=(CeldaArchivos)objeto;
                        etiqueta=(Label)children.get(indexToInt(px+","+py));
                        etiqueta.setText(celda.getContenido());
                    }
                    px--;
                    py=py+2;
                }

                entrada_ois.close();
            }catch(IOException ioe){
                JOptionPane.showMessageDialog(null, "Ocurrió un error con los archivos!","Error!", JOptionPane.ERROR_MESSAGE);
                ioe.printStackTrace();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Ocurrió un error no relacionado con archivos!","Error!", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "No se ha definido el cuadro!","ERROR!",JOptionPane.ERROR_MESSAGE);
        }
    }
    private int indexToInt(String v_index){
        int v_int;
        String px="",py="";
        char car;
        boolean flag_coma=false;
        for(int i=0; i<v_index.length();i++){
            car=v_index.charAt(i);
            switch(car) {
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
                    if (!flag_coma)
                        px=px+car;
                    else
                        py=py+car;
                    break;
                case ',':
                    flag_coma=true;
            }
        }
        v_int=Integer.parseInt(py)*posx+Integer.parseInt(px);
        return v_int;
    }
}
class CeldaArchivos implements Serializable{
    private String posicion;
    private int contenido;

    public CeldaArchivos(String v_pos, int v_cont){
        posicion=v_pos;
        contenido=v_cont;
    }
    public CeldaArchivos(int v_cont){
        contenido=v_cont;
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
