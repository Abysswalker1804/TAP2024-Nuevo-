package org.example.test.Vistas;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import org.example.test.EmpleadoTaqueria;

import java.util.Optional;

public class Taqueria extends Stage {
    private Scene escena;
    private BorderPane bdpPrincipal;
    private VBox vLeft;
    private GridPane gdpMesas, gdpAntojitos, gdpBebidas;
    private Button[][] Mesas=new Button[4][3];
    private Button[] Bebidas=new Button[3];
    private Button[] Antojitos=new Button[3];
    private Button btnMesaAnt, btnLogin;
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
        //Login
        btnLogin=new Button("Administrar");
        btnLogin.setOnAction(event -> new Login());
        bdpPrincipal.setTop(btnLogin);
        //Mesas
        gdpMesas=new GridPane();
        InicializarMesas();
        //Izquierda
        vLeft=new VBox(gdpMesas);
        vLeft.setAlignment(Pos.CENTER);
        //Antojitos
        gdpAntojitos=new GridPane();
        InicializarBebidas();

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
        if(btn != btnMesaAnt){
            if(btn.getId().equals("boton-available")) {
                btn.setId("boton-NOT-available");
                if(btnMesaAnt!=null)
                    btnMesaAnt.setId("boton-available");
            }else {
                btn.setId("boton-available");
                if(btnMesaAnt!=null)
                    btnMesaAnt.setId("boton-NOT-available");
            }
        }else{
            if(btn.getId().equals("boton-available")){
                btn.setId("boton-NOT-available");
            }else{
                btn.setId("boton-available");
            }
        }
        btnMesaAnt=btn;
    }
    private void InicializarBebidas(){
        //Pendiente
    }
}

class Login extends Stage{
    private Scene escena;
    private VBox vPrincipal;
    private Label lblLogin;
    private PasswordField pwdfLogin;
    private Button btnConfirmar;
    private String passwd="1234";
    public Login(){
        CrearUI();
        this.setTitle("Login");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/estilos/taqueria.css").toString());
        this.show();
    }
    private void CrearUI(){
        lblLogin=new Label("Contraseña:");

        pwdfLogin=new PasswordField();
        pwdfLogin.setMaxWidth(100);
        pwdfLogin.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    Confirmar(pwdfLogin);
                }
            }
        });

        btnConfirmar=new Button("Confirmar");
        btnConfirmar.setOnAction(event -> Confirmar(pwdfLogin));

        vPrincipal=new VBox(lblLogin,pwdfLogin,btnConfirmar);
        vPrincipal.setAlignment(Pos.CENTER);
        vPrincipal.setSpacing(10);
        vPrincipal.setId("login");
        escena=new Scene(vPrincipal,200,150);
    }
    private void Confirmar(PasswordField pwdf){
        if(!pwdf.getText().isEmpty() && pwdf.getText().equals(passwd)){
            Acceder();
        }else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Login");
            alert.setHeaderText("La contraseña es incorrecta!");
            alert.setContentText("La contraseña no coincide. Inténtelo de nuevo!");
            Optional<ButtonType> result = alert.showAndWait();
            if(!(result.get()==ButtonType.OK)){
                this.close();
            }
        }
    }
    private void Acceder(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login");
        alert.setHeaderText("Accediendo...");
        alert.setContentText("Bienvenido Administrador!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){
            new Administracion();
            this.close();
        }else{
            this.close();
        }
    }
}

