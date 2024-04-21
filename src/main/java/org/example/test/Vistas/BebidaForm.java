package org.example.test.Vistas;

import com.mysql.cj.protocol.Resultset;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.test.components.ConvertidorImagen;
import org.example.test.modelos.BebidaDAO;
import org.example.test.modelos.Conexion;
import org.example.test.modelos.EmpleadoDAO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BebidaForm extends Stage {
    private TableView<BebidaDAO> tbvBebidas;
    private BebidaDAO objBeb;
    private Scene escena;
    private VBox vPrincipal;
    private TextField [] arrTxtCampos=new TextField[6];
    private String[] arPrompts={"Nombre del producto","Clave de 1 caracter","Precio unitario","Existencia","Descripcion","Ruta absoluta de la imagen"};
    private Button btnGuardar;
    private boolean flag=false, sobreEscribir;
    public BebidaForm(TableView<BebidaDAO> tbvBeb, BebidaDAO objBeb){
        tbvBebidas=tbvBeb;
        this.objBeb=(objBeb==null)?new BebidaDAO():objBeb;
        CrearUI();
        this.setTitle("Insertar Bebida");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        vPrincipal=new VBox();
        vPrincipal.setPadding(new Insets(10));
        vPrincipal.setSpacing(10);
        vPrincipal.setAlignment(Pos.CENTER);
        for(int i=0; i<arrTxtCampos.length; i++){
            arrTxtCampos[i]=new TextField();
            arrTxtCampos[i].setPromptText(arPrompts[i]);
            vPrincipal.getChildren().add(arrTxtCampos[i]);
        }
        LlenarForm();
        btnGuardar=new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarBebida());
        vPrincipal.getChildren().add(btnGuardar);
        escena=new Scene(vPrincipal);
    }
    private void LlenarForm(){
        arrTxtCampos[0].setText((objBeb.getCve()==0)?"":objBeb.getNombre());
        arrTxtCampos[1].setText((objBeb.getCve()==0)?"":objBeb.getCve()+"");
        arrTxtCampos[2].setText((objBeb.getCve()==0)?"":String.valueOf(objBeb.getPrecioUnitario()));
        arrTxtCampos[3].setText((objBeb.getCve()==0)?"":String.valueOf(objBeb.getExistencia()));
        arrTxtCampos[4].setText((objBeb.getCve()==0)?"":objBeb.getDescripcion());
        arrTxtCampos[5].setText((objBeb.getCve()==0)?"":objBeb.getRuta().replace("\\\\","\\"));
    }
    private void GuardarBebida() {
        objBeb.setNombre((arrTxtCampos[0].getText()));
        flag = !objBeb.getNombre().isEmpty();//Revisar que no esté vacío
        objBeb.setCve(arrTxtCampos[1].getText().charAt(0));
        sobreEscribir = CompararCve();
        try {
            objBeb.setPrecioUnitario(Double.parseDouble(arrTxtCampos[2].getText()));
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        try {
            objBeb.setExistencia(Integer.parseInt(arrTxtCampos[3].getText()));
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        objBeb.setDescripcion(arrTxtCampos[4].getText());
        flag = !objBeb.getDescripcion().isEmpty();//Revisar que no esté vacío
        objBeb.setRuta(arrTxtCampos[5].getText().replace("\\","\\\\"));
        flag = ValidarImagen(objBeb.getRuta());//Revisar que la imagen exista
        if(flag){
            if (sobreEscribir){
                objBeb.INSERTAR();
                tbvBebidas.setItems(objBeb.CONSULTAR());
                tbvBebidas.refresh();
                arrTxtCampos[0].clear();
                arrTxtCampos[1].clear();
                arrTxtCampos[2].clear();
                arrTxtCampos[3].clear();
                arrTxtCampos[4].clear();
                arrTxtCampos[5].clear();
            }else{
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ATENCIÓN!");
                alert.setHeaderText("Clave Existente");
                alert.setContentText("La clave que ha ingresado ya existe.\n¿Desea sobreescribir el registro asociado a esta clave?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    objBeb.ACTUALIZAR();
                    tbvBebidas.setItems(objBeb.CONSULTAR());
                    tbvBebidas.refresh();
                }
            }
        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setHeaderText("Datos incorrectos");
            alert.setContentText("Puede que algún dato que ingresó no corresponde a lo esperado o la imagen es demasiado pesada.\nRevise que la información sea correcta y que la imagen sea menor a 65KB");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.OK){}
        }
    }
    private boolean ValidarImagen(String ruta){
        boolean flag=false;
        ConvertidorImagen conv=new ConvertidorImagen(ruta);
        if(conv.existe){
            String base64=conv.A_Base64();
            int i;
            for(i=0; i<base64.length();i++){}
            if(i<65500)//Corroborar que sea posible guardar la imagen en el text
                flag=true;
        }
        return flag;
    }
    private boolean CompararCve(){
        boolean flag=true;
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            String query = "SELECT cve FROM bebida";
            preparedStatement = Conexion.connection.prepareStatement(query);
            res = preparedStatement.executeQuery();

            List<Character> projection = new ArrayList<>();

            while (res.next()) {
                String value = res.getString("cve");
                if (value != null && !value.isEmpty()) {
                    char charValue = value.charAt(0);
                    projection.add(charValue);
                }
            }
            // Ahora `projection` contiene la proyección de la columna en caracteres
            // Puedes comparar estos caracteres con un dato de tipo `char`

            char datoComparar = objBeb.getCve(); // Dato a comparar

            for (char c : projection) {
                if (c == datoComparar) {
                    flag=false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
