package org.example.test.components;

import javafx.scene.control.*;
import org.example.test.EmpleadosForm;
import org.example.test.modelos.EmpleadoDAO;

import java.util.Optional;

public class ButtonCell extends TableCell<EmpleadoDAO,String>{
    Button btnCelda;
    int opc;
    EmpleadoDAO objEmp;
    public ButtonCell(int opc){
        this.opc=opc;
        String textButton=(opc==1)?"Editar":"Eliminar";
        btnCelda=new Button(textButton);
        btnCelda.setOnAction(event -> AccionBoton(this.opc));
    }
    private void AccionBoton(int opc){
        TableView<EmpleadoDAO> tbvEmpleados=ButtonCell.this.getTableView();
        objEmp=tbvEmpleados.getItems().get(ButtonCell.this.getIndex());
        if(opc==1){//Editar
            new EmpleadosForm(tbvEmpleados,objEmp);
        }else{//Eliminar
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del Sistema");
            alert.setHeaderText("Confirmación de Acción");
            alert.setContentText("¿Desea borrar al empleado "+objEmp.getNomEmpleado()+"?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.OK){
                objEmp.ELIMINAR();
                tbvEmpleados.setItems(objEmp.CONSULTAR());
                tbvEmpleados.refresh();
            }
        }
    }
    @Override
    protected void updateItem(String item, boolean empty){
        super.updateItem(item,empty);
        if(!empty)
            this.setGraphic(btnCelda);
    }
}
