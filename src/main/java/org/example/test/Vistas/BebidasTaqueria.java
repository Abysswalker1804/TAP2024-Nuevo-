package org.example.test.Vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.util.Callback;
import org.example.test.components.ButtonCell;
import org.example.test.components.ButtonCellAnt;
import org.example.test.components.ButtonCellBeb;
import org.example.test.components.ImageViewCell;
import org.example.test.modelos.BebidaDAO;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class BebidasTaqueria extends Stage {
    private Panel pnlPrincipal;
    private Scene escena;
    private ToolBar tlbMenu;
    private BorderPane bdpPrincipal;
    private Button btnAgregarComida, btnRegresar;
    private TableView<BebidaDAO> tbvBebida;
    public BebidasTaqueria(){
        CrearUI();
        this.setTitle("Taquería los Inges :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        ImageView imvComida=new ImageView(getClass().getResource("/images/bebida.png").toString());
        imvComida.setFitHeight(50);
        imvComida.setFitWidth(50);
        btnAgregarComida=new Button();
        btnAgregarComida.setGraphic(imvComida);
        btnAgregarComida.setPrefSize(50, 50);
        btnAgregarComida.setOnAction(event -> new BebidaForm(tbvBebida,null));
        //Regresar
        btnRegresar=new Button("Regresar");
        btnRegresar.setOnAction(event -> Regresar());

        tlbMenu=new ToolBar(btnAgregarComida);
        CrearTable();
        bdpPrincipal=new BorderPane();
        bdpPrincipal.setTop(tlbMenu);
        bdpPrincipal.setCenter(tbvBebida);
        bdpPrincipal.setBottom(btnRegresar);

        pnlPrincipal=new Panel("Bebidas de la Taqueria");
        pnlPrincipal.getStyleClass().add("panel-primary");
        pnlPrincipal.setBody(bdpPrincipal);

        escena=new Scene(pnlPrincipal,700,400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
    private void CrearTable(){
        BebidaDAO objEmp=new BebidaDAO();
        tbvBebida=new TableView<BebidaDAO>();

        TableColumn<BebidaDAO,String> tbcNombre=new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<BebidaDAO, Character> tbcCve=new TableColumn<>("Clave del producto");
        tbcCve.setCellValueFactory(new PropertyValueFactory<>("cve"));

        TableColumn<BebidaDAO, Double> tbcPrecio=new TableColumn<>("Precio Unitario");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));

        TableColumn<BebidaDAO, Integer> tbcExistencia=new TableColumn<>("Existencia");
        tbcExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));

        TableColumn<BebidaDAO, String> tbcDescripcion=new TableColumn<>("Descripción");
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        /*TableColumn<BebidaDAO, String> tbcImagen=new TableColumn<>("Imagen");
        tbcImagen.setCellFactory(new Callback<TableColumn<BebidaDAO, String>, TableCell<BebidaDAO, String>>() {
            @Override
            public TableCell<BebidaDAO, String> call(TableColumn<BebidaDAO, String> BebidaDAOStringTableColumn) {
                return new ImageViewCell();
            }
        });*/

        TableColumn<BebidaDAO,String> tbcEditar = new TableColumn<BebidaDAO,String>("EDITAR");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<BebidaDAO, String>, TableCell<BebidaDAO, String>>() {
                    @Override
                    public TableCell<BebidaDAO, String> call(TableColumn<BebidaDAO, String> BebidaDAOStringTableColumn) {
                        return new ButtonCellBeb(1);//Crea un botón celda
                    }
                }
        );

        TableColumn<BebidaDAO, String> tbcEliminar = new TableColumn<BebidaDAO,String>("ELIMINAR");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<BebidaDAO, String>, TableCell<BebidaDAO, String>>() {
                    @Override
                    public TableCell<BebidaDAO, String> call(TableColumn<BebidaDAO, String> BebidaDAOStringTableColumn) {
                        return new ButtonCellBeb(2);//
                    }
                }
        );
        //...
        tbvBebida. getColumns().addAll(tbcNombre,tbcCve,tbcPrecio,tbcExistencia,tbcDescripcion/*,tbcImagen*/,tbcEditar,tbcEliminar);
        tbvBebida.setItems(objEmp.CONSULTAR());

    }
    private void Regresar(){
        new Administracion();
        this.close();
    }
}
