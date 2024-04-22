package org.example.test.Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.test.EmpleadoTaqueria;

public class Administracion extends Stage {
    private Scene escena;
    private BorderPane bdpPricipal;
    private MenuBar mnbPrincipal;
    private Menu menEmpleados, menProductos, menSalir;
    private MenuItem mitEmpleados, mitVentas, mitAntojitos, mitBebidas, mitSalir;
    public Administracion(){
        CrearUI();
        this.setTitle("Administración");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/estilos/taqueria.css").toString());
        this.show();
    }
    private void CrearUI(){
        //Menu
        mitEmpleados=new MenuItem("Empleados");
        mitEmpleados.setOnAction(event -> AdminEmpleados());
        mitVentas=new MenuItem("Mayor ventas");
        mitVentas.setOnAction(event -> AdminVentas());
        mitAntojitos=new MenuItem("Antojitos");
        mitAntojitos.setOnAction(event -> AdminAntojitos());
        mitBebidas=new MenuItem("Bebidas");
        mitBebidas.setOnAction(event -> AdminBebidas());
        mitSalir=new MenuItem("Salir");
        mitSalir.setOnAction(event -> this.close());

        menEmpleados=new Menu("Empleados");
        menEmpleados.getItems().addAll(mitEmpleados, mitVentas);
        menProductos=new Menu("Productos");
        menProductos.getItems().addAll(mitAntojitos, mitBebidas);
        menSalir=new Menu("Salir");
        menSalir.getItems().add(mitSalir);

        mnbPrincipal=new MenuBar();
        mnbPrincipal.getMenus().addAll(menEmpleados, menProductos, menSalir);

        bdpPricipal=new BorderPane();
        bdpPricipal.setTop(mnbPrincipal);
        bdpPricipal.setId("admin");
        escena=new Scene(bdpPricipal, 300, 70);
    }

    private void AdminEmpleados(){
        new EmpleadoTaqueria();
        this.close();
    }
    private void AdminAntojitos(){
        new AntojitosTaqueria();
        this.close();
    }
    private void AdminBebidas(){
        new BebidasTaqueria();
        this.close();
    }
    private void AdminVentas(){
        new EmpleadoVentas();
        this.close();
    }
}