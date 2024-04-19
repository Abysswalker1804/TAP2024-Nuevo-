package org.example.test.Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.test.EmpleadoTaqueria;

public class Administracion extends Stage {
    private Scene escena;
    private HBox hAdmin;
    private VBox vAdmin;
    private Button btnEmpleados, btnAntojitos, btnBebidas, btnSalir;
    public Administracion(){
        CrearUI();
        this.setTitle("Administración");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/estilos/taqueria.css").toString());
        this.show();
    }
    private void CrearUI(){
        //Botones
        btnEmpleados=new Button("Empleados");
        btnEmpleados.setOnAction(event -> AdminEmpleados());
        btnAntojitos=new Button("Antojitos");
        btnAntojitos.setOnAction(event -> AdminAntojitos());
        btnBebidas=new Button("Bebidas");
        btnBebidas.setOnAction(event -> AdminBebidas());
        btnSalir=new Button("Salir");
        btnSalir.setOnAction(event -> this.close());

        hAdmin=new HBox(btnEmpleados, btnAntojitos, btnBebidas);
        hAdmin.setAlignment(Pos.CENTER);
        hAdmin.setSpacing(10);
        vAdmin=new VBox(hAdmin,btnSalir);
        vAdmin.setAlignment(Pos.CENTER);
        vAdmin.setId("admin");
        vAdmin.setSpacing(20);
        escena=new Scene(vAdmin, 300, 150);
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
}