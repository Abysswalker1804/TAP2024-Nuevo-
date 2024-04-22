package org.example.test.Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.test.modelos.EmpleadoDAO;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class EmpleadoVentas extends Stage {
    private Scene escena;
    private TableView<EmpleadoDAO> tbvEmp;
    private Panel pnlPrincipal;
    private VBox vPrincipal;
    private Button btnRegresar;
    public EmpleadoVentas() {
        CrearUI();
        this.setTitle("");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        CrearTabla();
        btnRegresar=new Button("Regresar");
        btnRegresar.setOnAction(event -> Regresar());
        vPrincipal=new VBox(tbvEmp,btnRegresar);
        vPrincipal.setAlignment(Pos.CENTER);
        vPrincipal.setSpacing(15);
        pnlPrincipal=new Panel("Empleado con mayor venta");
        pnlPrincipal.getStyleClass().add("panel-primary");
        pnlPrincipal.setBody(vPrincipal);
        escena=new Scene(pnlPrincipal,250,250);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
    private void CrearTabla(){
        EmpleadoDAO objEmp=new EmpleadoDAO();
        tbvEmp=new TableView<EmpleadoDAO>();

        TableColumn<EmpleadoDAO,Integer> tbcId=new TableColumn<>("ID");
        tbcId.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));

        TableColumn<EmpleadoDAO,String> tbcNom=new TableColumn<>("Nombre");
        tbcNom.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));

        TableColumn<EmpleadoDAO,Integer> tbcVentas=new TableColumn<>("Ventas");
        tbcVentas.setCellValueFactory(new PropertyValueFactory<>("ventas"));

        tbvEmp.getColumns().addAll(tbcId,tbcNom,tbcVentas);
        tbvEmp.setItems(objEmp.CONSULTAR_EMP_VENTAS());
    }
    private void Regresar(){
        new Administracion();
        this.close();
    }
}
