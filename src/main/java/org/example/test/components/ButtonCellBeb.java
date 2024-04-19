package org.example.test.components;

import javafx.scene.control.*;
import org.example.test.EmpleadosForm;
import org.example.test.Vistas.BebidaForm;
import org.example.test.modelos.BebidaDAO;

import java.util.Optional;

public class ButtonCellBeb extends TableCell<BebidaDAO, String> {
    Button btnCelda;
    int opc;
    BebidaDAO objBeb;
    public ButtonCellBeb(int opc){
        this.opc=opc;
        String textButton=(opc==1)?"Editar":"Eliminar";
        btnCelda=new Button(textButton);
        btnCelda.setOnAction(event -> AccionBoton(this.opc));
    }
    private void AccionBoton(int opc) {
        TableView<BebidaDAO> tbvBebidas = ButtonCellBeb.this.getTableView();
        objBeb = tbvBebidas.getItems().get(ButtonCellBeb.this.getIndex());
        if (opc == 1) {//Editar
            new BebidaForm(tbvBebidas, objBeb);
        } else {//Eliminar
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del Sistema");
            alert.setHeaderText("Confirmación de Acción");
            alert.setContentText("¿Desea borrar al producto " + objBeb.getNombre() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                objBeb.ELIMINAR();
                tbvBebidas.setItems(objBeb.CONSULTAR());
                tbvBebidas.refresh();
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
