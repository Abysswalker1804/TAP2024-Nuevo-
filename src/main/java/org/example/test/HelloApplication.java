package org.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private MenuBar mnbPrincipal;
    private Menu menParcial1, menParcial2, menSalir;
    private MenuItem mitCalculadora, mitSalir;
    private BorderPane bdpPanel;
    @Override
    public void start(Stage stage) throws IOException {
        crearMenu();
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        bdpPanel=new BorderPane();
        bdpPanel.setTop(mnbPrincipal);
        Scene scene=new Scene(bdpPanel);
        scene.getStylesheets().add(getClass().getResource("/estilos/main.css").toString());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);


    }
    private void crearMenu(){
        //Menú primer parcial
        mitCalculadora=new MenuItem("Calculadora");
        menParcial1=new Menu("Primer Parcial");
        menParcial1.getItems().addAll(mitCalculadora);
        mitCalculadora.setOnAction(event -> new Calculadora());

        //Menú segundo parcial
        menParcial2=new Menu("Segundo Parcial");

        //Menú salir
        mitSalir=new MenuItem("Salir");
        menSalir=new Menu("Salir");
        menSalir.getItems().add(mitSalir);
        mitSalir.setOnAction(event -> System.exit(0));

        //Menu Principal
        mnbPrincipal=new MenuBar();
        mnbPrincipal.getMenus().addAll(menParcial1, menParcial2, menSalir);
    }

    public static void main(String[] args) {
        launch();
    }
}