package org.example.test.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

public class BotonOrden extends TableCell<OrdenGrafica,String> {
    public Button btnCelda;
    int opc;
    OrdenGrafica objOrd;
    double total;
    Label lblTotal;

    public double getTotal() {
        return total;
    }

    public BotonOrden(int opc, double total, Label lblTotal){
        this.lblTotal=lblTotal;
        this.total=total;
        this.opc=opc;
        String text=(opc==1)?"Agregar":"Eliminar";
        btnCelda=new Button(text);

    }
    public double AccionBoton(double total){
        this.total=total;
        TableView<OrdenGrafica> tbvOrden=BotonOrden.this.getTableView();
        objOrd=tbvOrden.getItems().get(BotonOrden.this.getIndex());
        if(opc==1){//Agregar
            objOrd.setCant(objOrd.getCant()+1);
            this.total=this.total+objOrd.getPrecioUnit();
            lblTotal.setText("Total: $"+this.total);
            tbvOrden.refresh();
        }else{
            if(objOrd.getCant()==1)
                tbvOrden.getItems().remove(objOrd);
            else
                objOrd.setCant(objOrd.getCant()-1);
            this.total=this.total-objOrd.getPrecioUnit();
            lblTotal.setText("Total: $"+this.total);
            tbvOrden.refresh();
        }
        return this.total;
    }
    @Override
    protected void updateItem(String item, boolean empty){
        super.updateItem(item,empty);
        if(!empty)
            this.setGraphic(btnCelda);
    }
}
