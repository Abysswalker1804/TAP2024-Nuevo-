package org.example.test.components;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.test.modelos.AntojitoDAO;

import java.io.ByteArrayInputStream;

public class ImageViewCell extends TableCell<AntojitoDAO,String> {
    ImageView imvImagen;
    AntojitoDAO objAnt;
    public ImageViewCell(){
        TomarImagen();//Desde la BD
        Image img = new Image(new ByteArrayInputStream(objAnt.getImagen()));
        imvImagen= new ImageView(img);
    }
    private void TomarImagen(){
        TableView<AntojitoDAO> tbvAntojitos=ImageViewCell.this.getTableView();//Toma como el objeto contenido en el renglón para hacer referencia a este después
        objAnt=tbvAntojitos.getItems().get(ImageViewCell.this.getIndex());
    }
        //
    @Override
    protected void updateItem(String item, boolean empty){
        super.updateItem(item,empty);
        if(!empty)
            this.setGraphic(imvImagen);
    }
}
