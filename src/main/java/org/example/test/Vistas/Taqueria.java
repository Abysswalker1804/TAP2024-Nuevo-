package org.example.test.Vistas;

import com.mysql.cj.xdevapi.Table;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;
import org.example.test.components.BotonOrden;
import org.example.test.components.ConvertidorImagen;
import org.example.test.components.OrdenGrafica;
import org.example.test.modelos.*;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class Taqueria extends Stage {
    private Panel pnlTitulo;
    private Scene escena;
    private BorderPane bdpPrincipal;
    private VBox vLeft, vRight, vComida, vBebida, vCentro;
    private HBox hProductos, hProdLabels;
    private ScrollPane scrpProductos;
    private GridPane gdpMesas;
    private Button[][] Mesas = new Button[4][3];
    private ButtonMenu[] btnBebida;
    private ButtonMenu[] btnComida;
    private Button btnMesaAnt, btnVender;
    private MenuBar mbrPrincipal;
    private Menu menAdministrar, menActualizar, menSalir, menEmp;
    private MenuItem mitAdmin, mitAct, mitSalir;
    private MenuItem[] mitEmp;
    private String [][] nomEmp;
    private Label lblComida, lblBebida, lblTotal, lblEmpAtiende;
    private OrdenDAO objOrd;
    private String mesa;
    private double total = 0;
    private int cantAnt, empleado;
    private EmpleadoDAO objEmp = new EmpleadoDAO();
    private char charProducto;
    private TableView<OrdenGrafica> tbvOrden;

    public Taqueria() {
        CrearUI();
        this.setTitle("Taquería");
        escena.getStylesheets().add(getClass().getResource("/estilos/taqueria.css").toString());
        this.setScene(escena);
        this.setMaximized(true);
        this.show();
    }

    private void CrearUI() {
        //BorderPane
        bdpPrincipal = new BorderPane();
        //Menu principal
        mitAdmin = new MenuItem("Administrar");
        mitAdmin.setOnAction(event -> new Login());
        mitAct = new MenuItem("Actualizar");
        mitAct.setOnAction(event -> ActualizarTaqueria());
        SeleccionarEmpleado();//Inicializa a mitEmpleados[]
        mitSalir = new MenuItem("Salir");
        mitSalir.setOnAction(event -> System.exit(0));

        menAdministrar = new Menu("Administrar");
        menAdministrar.getItems().add(mitAdmin);
        menActualizar = new Menu("Actualizar");
        menActualizar.getItems().add(mitAct);
        menEmp= new Menu("Empleado");
        menEmp.getItems().addAll(mitEmp);
        menSalir = new Menu("Salir");
        menSalir.getItems().add(mitSalir);

        mbrPrincipal = new MenuBar();
        mbrPrincipal.getMenus().addAll(menAdministrar, menActualizar, menEmp, menSalir);
        bdpPrincipal.setTop(mbrPrincipal);
        //Mesas
        gdpMesas = new GridPane();
        InicializarMesas();
        //Izquierda
        lblEmpAtiende=new Label("Le atiende el empleado: -");
        lblEmpAtiende.setId("empleado-label");
        vLeft = new VBox(lblEmpAtiende, gdpMesas);
        vLeft.setSpacing(30);
        vLeft.setAlignment(Pos.CENTER);
        bdpPrincipal.setLeft(vLeft);
        //Antojitos
        InicializarProductos();
        //Derecha
        lblComida = new Label("Tacos");
        lblComida.setId("lbl-productos");
        lblBebida = new Label("Bebidas");
        lblBebida.setId("lbl-productos");
        hProdLabels = new HBox(lblComida, lblBebida);
        hProdLabels.setSpacing(150);
        vRight = new VBox(hProdLabels, scrpProductos);
        vRight.setSpacing(10);
        vRight.setAlignment(Pos.CENTER);
        bdpPrincipal.setRight(vRight);
        //Center - TableView
        btnVender = new Button("Vender");
        btnVender.setOnAction(event -> Venta());
        EspacioOrdenes();

        //Panel
        pnlTitulo = new Panel("Taquería los Inges");
        pnlTitulo.getStyleClass().add("panel-primary");
        pnlTitulo.setBody(bdpPrincipal);
        escena = new Scene(pnlTitulo);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void InicializarMesas() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                ImageView imv = new ImageView(getClass().getResource("/images/mesa.png").toString());
                imv.setFitWidth(50);
                imv.setFitHeight(50);
                String numMesa = "#" + (i * 3 + j + 1);
                Mesas[i][j] = new Button(numMesa);
                Mesas[i][j].setId("boton-available");
                Mesas[i][j].setGraphic(imv);
                Mesas[i][j].setPrefSize(100, 100);
                int finalI = i;
                int finalJ = j;
                Mesas[i][j].setOnAction(event -> AsignarOrdenAMesa(Mesas[finalI][finalJ], numMesa));
                gdpMesas.add(Mesas[i][j], j, i);
            }
        }
    }
    private void SeleccionarEmpleado(){
        String query="SELECT COUNT(*) AS total_registros FROM empleado";
        try{
            PreparedStatement pst = Conexion.connection.prepareStatement(query);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                mitEmp=new MenuItem[res.getInt(1)];
            }
            nomEmp=new String[mitEmp.length][mitEmp.length];
            query="SELECT nomEmpleado,idEmpleado from empleado";
            res= pst.executeQuery(query);
            for(int i=0; i< nomEmp.length && res.next(); i++){
                nomEmp[i][0]=res.getString(1);
                nomEmp[i][1]=res.getString(2);
            }
            for(int i=0; i< mitEmp.length; i++){
                mitEmp[i]=new MenuItem(nomEmp[i][0]);
                int finalI = i;
                mitEmp[i].setOnAction(event ->{
                    empleado=Integer.parseInt(nomEmp[finalI][1]);
                    lblEmpAtiende.setText("Le atiende el empleado: "+nomEmp[finalI][0]);
                });
            }
        }catch(Exception e){ e.printStackTrace();}
    }
    private void AsignarOrdenAMesa(Button btn, String numMesa) {
        if (btn != btnMesaAnt) {
            if (btn.getId().equals("boton-available")) {
                btn.setId("boton-NOT-available");
                if (btnMesaAnt != null)
                    btnMesaAnt.setId("boton-available");
            } else {
                btn.setId("boton-available");
                if (btnMesaAnt != null)
                    btnMesaAnt.setId("boton-NOT-available");
            }
            mesa = numMesa;
        } else {
            if (btn.getId().equals("boton-available")) {
                btn.setId("boton-NOT-available");
            } else {
                btn.setId("boton-available");
            }
            mesa = null;
        }
        btnMesaAnt = btn;
    }

    private void InicializarProductos() {
        AntojitoDAO[] comida = null;
        BebidaDAO[] bebida = null;
        try {
            String query = "SELECT COUNT(*) AS total_registros FROM antojito";
            PreparedStatement pst = Conexion.connection.prepareStatement(query);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                comida = new AntojitoDAO[res.getInt(1)];
            }
            //System.out.println("Comida: "+comida.length);
            btnComida = new ButtonMenu[comida.length];

            query = "SELECT COUNT(*) AS total_registros FROM bebida";
            pst = Conexion.connection.prepareStatement(query);
            res = pst.executeQuery();
            while (res.next()) {
                bebida = new BebidaDAO[res.getInt(1)];
            }
            //System.out.println("Comida: "+bebida.length);
            btnBebida = new ButtonMenu[bebida.length];

            query = "SELECT * FROM antojito";
            pst = Conexion.connection.prepareStatement(query);
            res = pst.executeQuery();
            for (int i = 0; i < comida.length && res.next(); i++) {
                comida[i] = new AntojitoDAO();
                comida[i].setCve(res.getString("cve").charAt(0));
                comida[i].setPrecioUnitario(res.getDouble("precioUnitario"));
                comida[i].setExistencia(res.getInt("existencia"));
                comida[i].setDescripcion(res.getString("descripcion"));
                comida[i].setNombre(res.getString("nombre"));
                comida[i].setRuta(res.getString("ruta").replace("\\", "\\\\"));
                ConvertidorImagen conv = new ConvertidorImagen();
                comida[i].setImg(conv.A_Imagen(res.getString("imagen")));
                btnComida[i] = new ButtonMenu(comida[i].getCve(), comida[i].getNombre(), comida[i].getImg());
                btnComida[i].setPrefSize(180, 70);
                int finalI = i;
                btnComida[i].setOnAction(event -> {
                    charProducto = btnComida[finalI].DarClave();
                    OrdenGrafica objOrd = new OrdenGrafica(charProducto);
                    total = total + objOrd.getPrecioUnit();
                    lblTotal.setText("Total: $" + total);
                    tbvOrden.getItems().add(objOrd);
                });
            }

            query = "SELECT * FROM bebida";
            pst = Conexion.connection.prepareStatement(query);
            res = pst.executeQuery();
            for (int i = 0; i < bebida.length && res.next(); i++) {
                bebida[i] = new BebidaDAO();
                bebida[i].setCve(res.getString("cve").charAt(0));
                bebida[i].setPrecioUnitario(res.getDouble("precioUnitario"));
                bebida[i].setExistencia(res.getInt("existencia"));
                bebida[i].setDescripcion(res.getString("descripcion"));
                bebida[i].setNombre(res.getString("nombre"));
                bebida[i].setRuta(res.getString("ruta").replace("\\", "\\\\"));
                ConvertidorImagen conv = new ConvertidorImagen();
                bebida[i].setImg(conv.A_Imagen(res.getString("imagen")));
                btnBebida[i] = new ButtonMenu(bebida[i].getCve(), bebida[i].getNombre(), bebida[i].getImg());
                btnBebida[i].setPrefSize(180, 70);
                int finalI = i;
                btnBebida[i].setOnAction(event -> {
                    charProducto = btnBebida[finalI].DarClave();
                    OrdenGrafica objOrd = new OrdenGrafica(charProducto);
                    total = total + objOrd.getPrecioUnit();
                    lblTotal.setText("Total: $" + total);
                    tbvOrden.getItems().add(objOrd);
                });
            }
            vComida = new VBox(btnComida);
            vBebida = new VBox(btnBebida);
            hProductos = new HBox(vComida, vBebida);
            scrpProductos = new ScrollPane(hProductos);
            scrpProductos.setMaxWidth(370);
            scrpProductos.setMaxHeight(210);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hubo un error.");
        }
    }

    private void Venta() {
        if (mesa != null && total != 0 && empleado!=0) {
            //empleado =;
            boolean siError = false;
            //Hay que actualizar las ventas que tiene el empleado que vendió
            String query = "SELECT * FROM empleado where idEmpleado=" + empleado;
            try {
                Statement stmt = Conexion.connection.createStatement();
                ResultSet res = stmt.executeQuery(query);
                while (res.next()) {
                    objEmp.setIdEmpleado(res.getInt("idEmpleado"));
                    objEmp.setNomEmpleado(res.getString("nomEmpleado"));
                    objEmp.setRfcEmpleado(res.getString("rfcEmpleado"));
                    objEmp.setSalario(res.getFloat("salario"));
                    objEmp.setTelefono(res.getString("telefono"));
                    objEmp.setDireccion(res.getString("direccion"));
                    objEmp.setVentas(res.getInt("ventas") + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (objEmp.getIdEmpleado() != 0) {
                query = "UPDATE empleado SET nomEmpleado='" + objEmp.getNomEmpleado() + "',rfcEmpleado='" + objEmp.getRfcEmpleado() + "',salario=" + objEmp.getSalario() + ",telefono='" + objEmp.getTelefono() + "',direccion='" + objEmp.getDireccion() + "',ventas=" + (objEmp.getVentas() + 1) + " WHERE idEmpleado=" + objEmp.getIdEmpleado();
                siError = false;
                try {
                    Statement stmt = Conexion.connection.createStatement();
                    stmt.executeUpdate(query);
                    objOrd = new OrdenDAO();
                    objOrd.setNumOrden(1);
                    objOrd.setTotal(total);
                    objOrd.setEmpleado(objEmp.getIdEmpleado());//Empleado
                    objOrd.setMesa(mesa.replace("#",""));
                    objOrd.INSERTAR();
                } catch (Exception e) {
                    e.printStackTrace();
                    siError = true;
                }
                if (!siError) {
                    total = 0;
                    lblTotal.setText("Total: $"+total);
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mensaje del Sistema");
            alert.setHeaderText("Valores faltantes!");
            alert.setContentText("No ha seleccionado el empleado que atiende y/o la mesa a la que irá la orden.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){}
        }
    }

    private void ActualizarTaqueria() {
        new Taqueria();
        this.close();
    }

    private void EspacioOrdenes() {
        tbvOrden = new TableView<>();

        TableColumn<OrdenGrafica, Integer> tbcOrden = new TableColumn<>("No. Orden");
        tbcOrden.setCellValueFactory(new PropertyValueFactory<>("numOrden"));
        tbcOrden.setReorderable(false);

        TableColumn<OrdenGrafica, String> tbcProducto = new TableColumn<>("Producto");
        tbcProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcProducto.setPrefWidth(100);
        tbcProducto.setReorderable(false);

        TableColumn<OrdenGrafica, Double> tbcPrecio = new TableColumn<>("Costo c/u");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnit"));
        tbcPrecio.setReorderable(false);

        TableColumn<OrdenGrafica, Integer> tbcCant = new TableColumn<>("Cantidad");
        tbcCant.setCellValueFactory(new PropertyValueFactory<>("cant"));
        tbcCant.setReorderable(false);

        TableColumn<OrdenGrafica, String> tbcAgregar = new TableColumn<>("AGREGAR");
        tbcAgregar.setCellFactory(
                new Callback<TableColumn<OrdenGrafica, String>, TableCell<OrdenGrafica, String>>() {
                    @Override
                    public TableCell<OrdenGrafica, String> call(TableColumn<OrdenGrafica, String> ordenGraficaStringTableColumn) {
                        BotonOrden btn = new BotonOrden(1, total, lblTotal);
                        btn.btnCelda.setOnAction(event -> total = btn.AccionBoton(total));//Checar como funciona total porque hay inconsistencias
                        return btn;
                    }
                }
        );

        TableColumn<OrdenGrafica, String> tbcEliminar = new TableColumn<>("ELIMINAR");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<OrdenGrafica, String>, TableCell<OrdenGrafica, String>>() {
                    @Override
                    public TableCell<OrdenGrafica, String> call(TableColumn<OrdenGrafica, String> ordenGraficaStringTableColumn) {
                        BotonOrden btn = new BotonOrden(2, total, lblTotal);
                        btn.btnCelda.setOnAction(event -> total = btn.AccionBoton(total));
                        return btn;
                    }
                }
        );

        tbvOrden.getColumns().addAll(tbcOrden, tbcProducto, tbcPrecio, tbcCant, tbcAgregar, tbcEliminar);

        lblTotal = new Label("Total: $" + total);
        lblTotal.setId("total");
        HBox hTotal = new HBox(lblTotal, btnVender);
        hTotal.setAlignment(Pos.CENTER_LEFT);
        hTotal.setSpacing(40);
        vCentro = new VBox(tbvOrden, hTotal);
        vCentro.setAlignment(Pos.CENTER);
        vCentro.setSpacing(10);
        vCentro.setMaxWidth(500);
        vCentro.setMaxHeight(450);
        bdpPrincipal.setCenter(vCentro);
    }
}
//Fin de Taquería
class Login extends Stage {
    private Scene escena;
    private VBox vPrincipal;
    private Label lblLogin;
    private PasswordField pwdfLogin;
    private Button btnConfirmar;
    private String passwd = "1234";

    public Login() {
        CrearUI();
        this.setTitle("Login");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/estilos/taqueria.css").toString());
        this.show();
    }

    private void CrearUI() {
        lblLogin = new Label("Contraseña:");

        pwdfLogin = new PasswordField();
        pwdfLogin.setMaxWidth(100);
        pwdfLogin.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Confirmar();
                }
            }
        });

        btnConfirmar = new Button("Confirmar");
        btnConfirmar.setOnAction(event -> Confirmar());

        vPrincipal = new VBox(lblLogin, pwdfLogin, btnConfirmar);
        vPrincipal.setAlignment(Pos.CENTER);
        vPrincipal.setSpacing(10);
        vPrincipal.setId("login");
        escena = new Scene(vPrincipal, 200, 150);
    }

    private void Confirmar() {
        if (!pwdfLogin.getText().isEmpty() && pwdfLogin.getText().equals(passwd)) {
            Acceder();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Login");
            alert.setHeaderText("La contraseña es incorrecta!");
            alert.setContentText("La contraseña no coincide. Inténtelo de nuevo!");
            Optional<ButtonType> result = alert.showAndWait();
            if (!(result.get() == ButtonType.OK)) {
                this.close();
            } else {
                pwdfLogin.clear();
            }
        }
    }
    private void Acceder() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login");
        alert.setHeaderText("Accediendo...");
        alert.setContentText("Bienvenido Administrador!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new Administracion();
            this.close();
        } else {
            this.close();
        }
    }
}
//Fin de Login
class ButtonMenu extends Button {
    private char CVE;
    private String NOMBRE;

    public char getCVE() {
        return CVE;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public ButtonMenu(char cve, String nombre, Image img) {
        super.setText(nombre);
        CVE = cve;
        NOMBRE = nombre;
        ImageView imv = new ImageView(img);
        imv.setFitHeight(60);
        imv.setFitWidth(60);
        this.setGraphic(imv);
    }
    public char DarClave() {
            return CVE;
    }
}
//Fin Button menu