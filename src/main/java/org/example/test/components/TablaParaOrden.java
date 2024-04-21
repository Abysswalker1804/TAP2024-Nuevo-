package org.example.test.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.test.modelos.*;

public class TablaParaOrden {
    private AntojitoDAO objAnt;
    private BebidaDAO objBeb;
    private OrdenDAO objOrd;
    private TieneADAO objTieneA;
    private TieneBDAO objTieneB;

    public TablaParaOrden(){}

    public ObservableList<TablaParaOrden> CONSULTAR(){
        ObservableList<TablaParaOrden> listaOrden= FXCollections.observableArrayList();
        //Pendiente
        return listaOrden;
    }
    public void CrearTable(){
        objAnt=new AntojitoDAO();
        objBeb=new BebidaDAO();
        objOrd=new OrdenDAO();
        objTieneA=new TieneADAO();
        objTieneB=new TieneBDAO();
        TableView<TablaParaOrden> tbvParaOrden=new TableView<TablaParaOrden>();

        TableColumn<OrdenDAO,Integer> tbcNoOrden=new TableColumn<>("No. Orden");
        tbcNoOrden.setCellValueFactory(new PropertyValueFactory<>("numOrden"));

        //Continúa agregando las columnas que necesites
    }
}
