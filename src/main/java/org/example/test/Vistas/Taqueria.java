package org.example.test.Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Taqueria extends Stage {
    private Scene escena;
    private BorderPane bdpPrincipal;
    private VBox vLeft;
    private GridPane gdpMesas;
    private Button[][] Mesas=new Button[4][3];
    private Button btnMesaAnt;
    public Taqueria(){
        CrearUI();
        this.setTitle("Taquería");
        escena.getStylesheets().add(getClass().getResource("/estilos/taqueria.css").toString());
        this.setScene(escena);
        this.setMaximized(true);
        this.show();
    }
    private void CrearUI(){
        //BorderPane
        bdpPrincipal=new BorderPane();
        //Mesas
        gdpMesas=new GridPane();
        InicializarMesas();
        //Izquierda
        vLeft=new VBox(gdpMesas);
        vLeft.setAlignment(Pos.CENTER);

        bdpPrincipal.setLeft(vLeft);
        escena=new Scene(bdpPrincipal);
    }

    private void InicializarMesas(){
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                ImageView imv=new ImageView(getClass().getResource("/images/mesa.png").toString());
                imv.setFitWidth(50);
                imv.setFitHeight(50);
                Mesas[i][j]=new Button("#"+(i*3+j+1));
                Mesas[i][j].setId("boton-available");
                Mesas[i][j].setGraphic(imv);
                Mesas[i][j].setPrefSize(100, 100);
                int finalI = i;
                int finalJ = j;
                Mesas[i][j].setOnAction(event -> AsignarOrdenAMesa(Mesas[finalI][finalJ]));
                gdpMesas.add(Mesas[i][j],j,i);
            }
        }
    }
    private void AsignarOrdenAMesa(Button btn){
        if(btn.getId().equals("boton-available")) {
            btn.setId("boton-NOT-available");
            if(btnMesaAnt!=null)
                btnMesaAnt.setId("boton-available");
        }else {
            btn.setId("boton-available");
            if(btnMesaAnt!=null)
                btnMesaAnt.setId("boton-NOT-available");
        }
        btnMesaAnt=btn;
    }
}
