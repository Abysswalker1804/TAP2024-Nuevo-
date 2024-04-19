package org.example.test;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.test.modelos.EmpleadoDAO;

import java.util.Optional;


public class EmpleadosForm extends Stage {
    private TableView<EmpleadoDAO> tbvEmpleados;
    private EmpleadoDAO objEmp;
    private Scene escena;
    private VBox vPrincipal;
    private TextField[] arTxtCampos=new TextField[5];
    private String[] arPrompts={"Nombre del Empleado","RFC del Empleado", "Sueldo del Empleado", "Telefono del Empleado", "Dirección del Empleado",};
    private Button btnGuardar;
    private boolean flag;
    public EmpleadosForm(TableView<EmpleadoDAO> tbvEmp, EmpleadoDAO objEmp){
        tbvEmpleados=tbvEmp;
        this.objEmp=(objEmp==null)?new EmpleadoDAO():objEmp;
        CrearUI();
        this.setTitle("Insertar Usuario");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        vPrincipal=new VBox();
        vPrincipal.setPadding(new Insets(10));
        vPrincipal.setSpacing(10);
        vPrincipal.setAlignment(Pos.CENTER);
        for(int i=0; i< arTxtCampos.length; i++){
            arTxtCampos[i]=new TextField();
            arTxtCampos[i].setPromptText(arPrompts[i]);
            vPrincipal.getChildren().add(arTxtCampos[i]);
        }
        LlenarForm();
        btnGuardar=new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarEmpleado());
        vPrincipal.getChildren().add(btnGuardar);
        escena=new Scene(vPrincipal, 300,300);
    }
    public void LlenarForm(){
        arTxtCampos[0].setText(objEmp.getNomEmpleado());
        arTxtCampos[1].setText(objEmp.getRfcEmpleado());
        arTxtCampos[2].setText(String.valueOf(objEmp.getSalario()));
        arTxtCampos[3].setText(objEmp.getTelefono());
        arTxtCampos[4].setText(objEmp.getDireccion());
    }
    private void GuardarEmpleado(){
        //Controlar entradas
        objEmp.setNomEmpleado((arTxtCampos[0].getText()));
        flag=!objEmp.getNomEmpleado().isEmpty();
        if(arTxtCampos[1].getText().length()==13){
            objEmp.setRfcEmpleado(arTxtCampos[1].getText());
            flag=true;
        }else{flag=false;}
        try{
            objEmp.setSalario(Float.parseFloat(arTxtCampos[2].getText()));
            flag=true;
        }catch(Exception e){flag=false;}
        try{
            int integer=Integer.parseInt(arTxtCampos[3].getText());
            if(arTxtCampos[3].getText().length()==10){
                objEmp.setTelefono(arTxtCampos[3].getText());
                flag=true;
            }else{flag=false;}
        }catch(Exception e){flag=false;}
        objEmp.setDireccion(arTxtCampos[4].getText());
        flag=!objEmp.getDireccion().isEmpty();
        if(flag){
            objEmp.setVentas(0);
            if(objEmp.getIdEmpleado()>0)
                objEmp.ACTUALIZAR();
            else
                objEmp.INSERTAR();
            tbvEmpleados.setItems(objEmp.CONSULTAR());
            tbvEmpleados.refresh();
            arTxtCampos[0].clear();
            arTxtCampos[1].clear();
            arTxtCampos[2].clear();
            arTxtCampos[3].clear();
            arTxtCampos[4].clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Algo salió mal...");
            alert.setContentText("Alguno de los campos no fue llenado correctamente.\nRevíse de nuevo");
            Optional<ButtonType> result = alert.showAndWait();
            if (!(result.get() == ButtonType.OK)) {
                this.close();
                /*arTxtCampos[0].clear();
                arTxtCampos[1].clear();
                arTxtCampos[2].clear();
                arTxtCampos[3].clear();
                arTxtCampos[4].clear();*/
            }
        }
    }
}
