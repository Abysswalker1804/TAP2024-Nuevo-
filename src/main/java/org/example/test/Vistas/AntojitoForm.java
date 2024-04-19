package org.example.test.Vistas;

import com.mysql.cj.protocol.Resultset;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.test.modelos.AntojitoDAO;
import org.example.test.modelos.Conexion;
import org.example.test.modelos.EmpleadoDAO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AntojitoForm extends Stage {
    private TableView<AntojitoDAO> tbvAntojitos;
    private AntojitoDAO objAnt;
    private Scene escena;
    private VBox vPrincipal;
    private TextField [] arrTxtCampos=new TextField[6];
    private String[] arPrompts={"Nombre del producto","Clave de 1 caracter","Precio unitario","Existencia","Descripcion","Ruta absoluta de la imagen"};
    private Button btnGuardar;
    private boolean flag=false, sobreEscribir;
    public AntojitoForm(TableView<AntojitoDAO> tbvAntoj, AntojitoDAO objAnt){
        tbvAntojitos=tbvAntoj;
        this.objAnt=(objAnt==null)?new AntojitoDAO():objAnt;
        CrearUI();
        this.setTitle("Insertar Antojito");
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
        btnGuardar.setOnAction(event -> GuardarAntojito());
        vPrincipal.getChildren().add(btnGuardar);
        escena=new Scene(vPrincipal);
    }
    private void LlenarForm(){
        arrTxtCampos[0].setText(objAnt.getNombre());
        arrTxtCampos[1].setText(objAnt.getCve()+"");
        arrTxtCampos[2].setText(String.valueOf(objAnt.getPrecioUnitario()));
        arrTxtCampos[3].setText(String.valueOf(objAnt.getExistencia()));
        arrTxtCampos[4].setText(objAnt.getDescripcion());
        arrTxtCampos[5].setText(objAnt.getRuta());
    }
    private void GuardarAntojito() {
        objAnt.setNombre((arrTxtCampos[0].getText()));
        flag = !objAnt.getNombre().isEmpty();//Revisar que no esté vacío
        objAnt.setCve(arrTxtCampos[1].getText().charAt(0));
        sobreEscribir = CompararCve();
        try {
            objAnt.setPrecioUnitario(Double.parseDouble(arrTxtCampos[2].getText()));
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        try {
            objAnt.setExistencia(Integer.parseInt(arrTxtCampos[3].getText()));
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        objAnt.setDescripcion(arrTxtCampos[4].getText());
        flag = !objAnt.getDescripcion().isEmpty();//Revisar que no esté vacío
        objAnt.setRuta(arrTxtCampos[5].getText().replace("\\","\\\\"));
        flag = ExisteImagen();//Revisar que la imagen exista
        if (sobreEscribir){
            objAnt.INSERTAR();
            tbvAntojitos.setItems(objAnt.CONSULTAR());
            tbvAntojitos.refresh();
            arrTxtCampos[0].clear();
            arrTxtCampos[1].clear();
            arrTxtCampos[2].clear();
            arrTxtCampos[3].clear();
            arrTxtCampos[4].clear();
        }else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ATENCIÓN!");
            alert.setHeaderText("Clave Existente");
            alert.setContentText("La clave que ha ingresado ya existe.\n¿Desea sobreescribir el registro asociado a esta clave?");
            Optional<ButtonType> result = alert.showAndWait();
            if(!(result.get()==ButtonType.OK)){
                objAnt.ACTUALIZAR();
                tbvAntojitos.setItems(objAnt.CONSULTAR());
                tbvAntojitos.refresh();
                arrTxtCampos[0].clear();
                arrTxtCampos[1].clear();
                arrTxtCampos[2].clear();
                arrTxtCampos[3].clear();
                arrTxtCampos[4].clear();
            }
        }

    }
    private boolean ExisteImagen(){
        File file=new File(objAnt.getRuta());
        return file.exists();
    }
    private boolean CompararCve(){
        boolean flag=true;
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            String query = "SELECT cve FROM antojito";
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

            char datoComparar = objAnt.getCve(); // Dato a comparar

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
