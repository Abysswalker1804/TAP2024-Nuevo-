package org.example.test.components;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.test.EmpleadosForm;
import org.example.test.Vistas.AntojitoForm;
import org.example.test.Vistas.Taqueria;
import org.example.test.modelos.AntojitoDAO;

import java.io.ByteArrayInputStream;
import java.util.Optional;

public class ButtonCellAnt extends TableCell<AntojitoDAO, String> {
    Button btnCelda;
    int opc;
    AntojitoDAO objAnt;
    public ButtonCellAnt(int opc){
        this.opc=opc;
        String textButton=(opc==1)?"Editar":((opc==2)?"Eliminar":"Ver imagen");
        btnCelda=new Button(textButton);
        btnCelda.setOnAction(event -> AccionBoton(this.opc));
    }
    private void AccionBoton(int opc) {
        TableView<AntojitoDAO> tbvAntojitos = ButtonCellAnt.this.getTableView();
        objAnt = tbvAntojitos.getItems().get(ButtonCellAnt.this.getIndex());
        if (opc == 1) {//Editar
            new AntojitoForm(tbvAntojitos, objAnt);
        } else {//Eliminar
            if(opc==2){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje del Sistema");
                alert.setHeaderText("Confirmación de Acción");
                alert.setContentText("¿Desea borrar al producto " + objAnt.getNombre() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    objAnt.ELIMINAR();
                    tbvAntojitos.setItems(objAnt.CONSULTAR());
                    tbvAntojitos.refresh();
                }
            }else{//Mostrar imagen
                new VentanaImagen(objAnt.getImg());
            }
        }
    }
    @Override
    protected void updateItem(String item, boolean empty){
        super.updateItem(item,empty);
        if(!empty)
            this.setGraphic(btnCelda);
    }

    public AntojitoDAO getObjAnt() {
        return objAnt;
    }
}
class VentanaImagen extends Stage {
    private Image img;
    private ImageView imvImagen;
    private VBox vPrincipal;
    private Scene escena;
    public VentanaImagen(Image img){
        this.img= img;
        imvImagen=new ImageView(this.img);
        imvImagen.setFitWidth(100);
        imvImagen.setFitHeight(100);
        vPrincipal=new VBox(imvImagen);
        vPrincipal.setAlignment(Pos.CENTER);
        this.setTitle("Vista previa");
        escena=new Scene(vPrincipal);
        this.setScene(escena);
        this.show();

    }
}