package org.example.test;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.example.test.Vistas.Administracion;
import org.example.test.Vistas.Taqueria;
import org.example.test.components.ButtonCell;
import org.example.test.modelos.EmpleadoDAO;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import org.example.test.EmpleadosForm;

import java.awt.*;

public class EmpleadoTaqueria extends Stage{
    private Panel pnlPrincipal;
    private BorderPane bpPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private Button btnAgregarEmpleado, btnRegresar;
    private TableView<EmpleadoDAO> tbvEmpleados;
    public EmpleadoTaqueria(){
        CrearUI();
        this.setTitle("Taquería los Inges :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        ImageView imvEmp= new ImageView(getClass().getResource("/images/empleado.png").toString());
        imvEmp.setFitWidth(50);
        imvEmp.setFitHeight(50);
        btnAgregarEmpleado=new Button();
        btnAgregarEmpleado.setGraphic(imvEmp);
        btnAgregarEmpleado.setPrefSize(50, 50);
        btnAgregarEmpleado.setOnAction(event -> new EmpleadosForm(tbvEmpleados,null));
        //Regresar
        btnRegresar=new Button("Regresar");
        btnRegresar.setOnAction(event -> Regresar());

        tlbMenu=new ToolBar(btnAgregarEmpleado);
        CrearTable();
        bpPrincipal=new BorderPane();
        bpPrincipal.setTop(tlbMenu);
        bpPrincipal.setCenter(tbvEmpleados);
        bpPrincipal.setBottom(btnRegresar);

        pnlPrincipal=new Panel("Empleados de Taquería");
        pnlPrincipal.getStyleClass().add("panel-primary");
        pnlPrincipal.setBody(bpPrincipal);

        escena=new Scene(pnlPrincipal,600, 400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
    private void CrearTable(){
        EmpleadoDAO objEmp=new EmpleadoDAO();
        tbvEmpleados=new TableView<EmpleadoDAO>();
        TableColumn<EmpleadoDAO, String> tbcNomEmp = new TableColumn<>("Empleado");
        tbcNomEmp.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));

        TableColumn<EmpleadoDAO, String> tbcRfcEmp = new TableColumn<>("RFC");
        tbcRfcEmp.setCellValueFactory(new PropertyValueFactory<>("rfcEmpleado"));

        TableColumn<EmpleadoDAO, Float> tbcSueldoEmp= new TableColumn<>("Sueldo");
        tbcSueldoEmp.setCellValueFactory(new PropertyValueFactory<>("salario"));

        TableColumn<EmpleadoDAO, String> tbcTelEmp = new TableColumn<>("Telefono");
        tbcTelEmp.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<EmpleadoDAO, String> tbcDirEmp = new TableColumn<>("Direccion");
        tbcDirEmp.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<EmpleadoDAO, String> tbcEditar = new TableColumn<EmpleadoDAO,String>("EDITAR");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
                    @Override
                    public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> empleadoDAOStringTableColumn) {
                        return new ButtonCell(1);//Crea un botón celda
                    }
                }
        );
        TableColumn<EmpleadoDAO, String> tbcEliminar = new TableColumn<EmpleadoDAO,String>("ELIMINAR");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
                    @Override
                    public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> empleadoDAOStringTableColumn) {
                        return new ButtonCell(2);
                    }
                }
        );
        //...
        tbvEmpleados. getColumns().addAll(tbcNomEmp,tbcRfcEmp,tbcSueldoEmp,tbcTelEmp,tbcDirEmp,tbcEditar,tbcEliminar);
        tbvEmpleados.setItems(objEmp.CONSULTAR());
    }

    private void Regresar(){
        new Administracion();
        this.close();
    }
}
