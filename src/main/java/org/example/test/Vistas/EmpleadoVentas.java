package org.example.test.Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.test.components.GeneratePDFFileIText;
import org.example.test.modelos.Conexion;
import org.example.test.modelos.EmpleadoDAO;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class EmpleadoVentas extends Stage {
    private Scene escena;
    private TableView<EmpleadoDAO> tbvEmp;
    private Panel pnlPrincipal;
    private VBox vPrincipal;
    private Button btnRegresar, btnRegistro;
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
        btnRegistro=new Button("PDF");
        btnRegistro.setOnAction(event -> CrearPDF());
        vPrincipal=new VBox(tbvEmp,btnRegistro,btnRegresar);
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
    private void CrearPDF(){
        File arch=new File("C:\\Users\\tadeo\\JavaProjects\\Test\\recibosTaqueria\\graficasEmpleados\\MejorEmpleado.pdf");
        GeneratePDFFileIText pdf=new GeneratePDFFileIText();
        EmpleadoDAO objEmp=new EmpleadoDAO();
        try{
            String query="SELECT idEmpleado,nomEmpleado,rfcEmpleado,ventas FROM empleado where ventas=(SELECT MAX(ventas) FROM empleado)";
            Statement stmt= Conexion.connection.createStatement();
            ResultSet res= stmt.executeQuery(query);
            while(res.next()){
                objEmp.setIdEmpleado(res.getInt(1));
                objEmp.setNomEmpleado(res.getString(2));
                objEmp.setRfcEmpleado(res.getString(3));
                objEmp.setVentas(res.getInt(4));
            }
        }catch(Exception e){e.printStackTrace();}
        pdf.createPDF(arch, "Mejor Empleado", "Nombre: "+objEmp.getNomEmpleado()+"\nID: "+objEmp.getIdEmpleado()+"\nRFC: "+objEmp.getRfcEmpleado()+"\nVentas: "+objEmp.getVentas());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje del Sistema");
        alert.setHeaderText("Creación de PDF");
        alert.setContentText("El PDF ha sido creado correctamente en: \nC:\\Users\\tadeo\\JavaProjects\\Test\\recibosTaqueria\\graficasEmpleados\\");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {}
    }
}
