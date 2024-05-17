package org.example.test.components;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import org.example.test.modelos.ArchivoImpresion;

public class TableProgressBar extends TableCell<ArchivoImpresion,Double> {
    private ProgressBar pgrBar;

    public TableProgressBar() {
        this.pgrBar = new ProgressBar();
        setGraphic(pgrBar);
    }

    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // Oculta el ProgressBar si la celda está vacía
        } else {
            pgrBar.setProgress(item);
            setGraphic(pgrBar); // Muestra el ProgressBar con el progreso adecuado
        }
    }

    /*public TableProgressBar(){
        pgrBar=new ProgressBar(0);
    }
    public void updateProgress(double pgr){
        pgrBar.setProgress(pgr);
    }
    @Override
    protected void updateItem(Double item, boolean empty){
        super.updateItem(item,empty);
        if(!empty)
            this.setGraphic(pgrBar);
    }*/
}
