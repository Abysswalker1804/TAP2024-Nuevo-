package org.example.test;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.example.test.modelos.EmpleadoDAO;


public class EmpleadosForm extends Stage {
    private TableView<EmpleadoDAO> tbvEmpleados;
    private EmpleadoDAO objEmp;
    private Scene escena;
    private VBox vPrincipal;
    private TextField[] arTxtCampos=new TextField[5];
    private String[] arPrompts={"Nombre del Empleado","RFC del Empleado", "Sueldo del Empleado", "Telefono del Empleado", "Dirección del Empleado",};
    private Button btnGuardar;
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
        objEmp.setNomEmpleado(arTxtCampos[0].getText());
        objEmp.setRfcEmpleado(arTxtCampos[1].getText());
        objEmp.setSalario(Float.parseFloat(arTxtCampos[2].getText()));
        objEmp.setTelefono(arTxtCampos[3].getText());
        objEmp.setDireccion(arTxtCampos[4].getText());
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
    }
}
