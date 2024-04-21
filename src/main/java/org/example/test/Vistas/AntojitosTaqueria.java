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
import org.example.test.components.ImageViewCell;
import org.example.test.modelos.AntojitoDAO;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class AntojitosTaqueria extends Stage {
    private Panel pnlPrincipal;
    private Scene escena;
    private ToolBar tlbMenu;
    private BorderPane bdpPrincipal;
    private Button btnAgregarComida, btnRegresar;
    private TableView<AntojitoDAO> tbvAntojito;
    public AntojitosTaqueria(){
        CrearUI();
        this.setTitle("Taquería los Inges :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        ImageView imvComida=new ImageView(getClass().getResource("/images/comida_boton.png").toString());
        imvComida.setFitHeight(50);
        imvComida.setFitWidth(50);
        btnAgregarComida=new Button();
        btnAgregarComida.setGraphic(imvComida);
        btnAgregarComida.setPrefSize(50, 50);
        btnAgregarComida.setOnAction(event -> new AntojitoForm(tbvAntojito,null));
        //Regresar
        btnRegresar=new Button("Regresar");
        btnRegresar.setOnAction(event -> Regresar());

        tlbMenu=new ToolBar(btnAgregarComida);
        CrearTable();
        bdpPrincipal=new BorderPane();
        bdpPrincipal.setTop(tlbMenu);
        bdpPrincipal.setCenter(tbvAntojito);
        bdpPrincipal.setBottom(btnRegresar);

        pnlPrincipal=new Panel("Antojitos de la Taqueria");
        pnlPrincipal.getStyleClass().add("panel-primary");
        pnlPrincipal.setBody(bdpPrincipal);

        escena=new Scene(pnlPrincipal,800,400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
    private void CrearTable(){
        AntojitoDAO objAnt=new AntojitoDAO();
        tbvAntojito=new TableView<AntojitoDAO>();

        TableColumn<AntojitoDAO,String> tbcNombre=new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<AntojitoDAO, Character> tbcCve=new TableColumn<>("Clave del producto");
        tbcCve.setCellValueFactory(new PropertyValueFactory<>("cve"));

        TableColumn<AntojitoDAO, Double> tbcPrecio=new TableColumn<>("Precio Unitario");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));

        TableColumn<AntojitoDAO, Integer> tbcExistencia=new TableColumn<>("Existencia");
        tbcExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));

        TableColumn<AntojitoDAO, String> tbcDescripcion=new TableColumn<>("Descripción");
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<AntojitoDAO, String> tbcImagen=new TableColumn<>("Imagen");
        /*tbcImagen.setCellFactory(new Callback<TableColumn<AntojitoDAO, String>, TableCell<AntojitoDAO, String>>() {
            @Override
            public TableCell<AntojitoDAO, String> call(TableColumn<AntojitoDAO, String> antojitoDAOStringTableColumn) {
                return new ImageViewCell();
            }
        });*/

        TableColumn<AntojitoDAO,String> tbcVerImagen = new TableColumn<AntojitoDAO,String>("Imagen");
        tbcVerImagen.setCellFactory(
                new Callback<TableColumn<AntojitoDAO, String>, TableCell<AntojitoDAO, String>>() {
                    @Override
                    public TableCell<AntojitoDAO, String> call(TableColumn<AntojitoDAO, String> antojitoDAOStringTableColumn) {
                        return new ButtonCellAnt(3);
                    }
                }
        );

        TableColumn<AntojitoDAO,String> tbcEditar = new TableColumn<AntojitoDAO,String>("EDITAR");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<AntojitoDAO, String>, TableCell<AntojitoDAO, String>>() {
                    @Override
                    public TableCell<AntojitoDAO, String> call(TableColumn<AntojitoDAO, String> antojitoDAOStringTableColumn) {
                        return new ButtonCellAnt(1);//Crea un botón celda
                    }
                }
        );

        TableColumn<AntojitoDAO, String> tbcEliminar = new TableColumn<AntojitoDAO,String>("ELIMINAR");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<AntojitoDAO, String>, TableCell<AntojitoDAO, String>>() {
                    @Override
                    public TableCell<AntojitoDAO, String> call(TableColumn<AntojitoDAO, String> antojitoDAOStringTableColumn) {
                        return new ButtonCellAnt(2);//
                    }
                }
        );
        //...
        tbvAntojito. getColumns().addAll(tbcNombre,tbcCve,tbcPrecio,tbcExistencia,tbcDescripcion,tbcVerImagen,tbcEditar,tbcEliminar);
        tbvAntojito.setItems(objAnt.CONSULTAR());

    }
    private void Regresar(){
        new Administracion();
        this.close();
    }
}
