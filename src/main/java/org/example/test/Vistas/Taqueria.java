package org.example.test.Vistas;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
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
import org.example.test.components.ConvertidorImagen;
import org.example.test.modelos.AntojitoDAO;
import org.example.test.modelos.BebidaDAO;
import org.example.test.modelos.Conexion;
import org.example.test.modelos.EmpleadoDAO;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class Taqueria extends Stage {
    private Panel pnlTitulo;
    private Scene escena;
    private BorderPane bdpPrincipal;
    private VBox vLeft, vRight, vComida, vBebida;
    private HBox hProductos, hProdLabels;
    private ScrollPane scrpProductos;
    private GridPane gdpMesas;
    private Button[][] Mesas=new Button[4][3];
    private ButtonMenu[] btnBebida;
    private ButtonMenu[] btnComida;
    private Button btnMesaAnt;
    private MenuBar mbrPrincipal;
    private Menu menAdministrar, menActualizar, menSalir;
    private MenuItem mitAdmin, mitAct, mitSalir;
    private Label lblComida, lblBebida;
    private TableView tbvOrden;
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
        //Menu principal
        mitAdmin=new MenuItem("Administrar");
        mitAdmin.setOnAction(event -> new Login());
        mitAct=new MenuItem("Actualizar");
        mitAct.setOnAction(event -> ActualizarTaqueria());
        mitSalir=new MenuItem("Salir");
        mitSalir.setOnAction(event -> System.exit(0));

        menAdministrar=new Menu("Administrar");
        menAdministrar.getItems().add(mitAdmin);
        menActualizar=new Menu("Actualizar");
        menActualizar.getItems().add(mitAct);
        menSalir=new Menu("Salir");
        menSalir.getItems().add(mitSalir);

        mbrPrincipal=new MenuBar();
        mbrPrincipal.getMenus().addAll(menAdministrar,menActualizar,menSalir);
        bdpPrincipal.setTop(mbrPrincipal);
        //Mesas
        gdpMesas=new GridPane();
        InicializarMesas();
        //Izquierda
        vLeft=new VBox(gdpMesas);
        vLeft.setAlignment(Pos.CENTER);
        bdpPrincipal.setLeft(vLeft);
        //Antojitos
        InicializarProductos();
        //Derecha
        lblComida=new Label("Tacos");
        lblComida.setId("lbl-productos");
        lblBebida=new Label("Bebidas");
        lblBebida.setId("lbl-productos");
        hProdLabels=new HBox(lblComida,lblBebida);
        hProdLabels.setSpacing(150);
        vRight=new VBox(hProdLabels,scrpProductos);
        vRight.setAlignment(Pos.CENTER);
        bdpPrincipal.setRight(vRight);
        //Center - TableView
        CrearTablaOrden();

        //Panel
        pnlTitulo=new Panel("Taquería los Inges");
        pnlTitulo.getStyleClass().add("panel-primary");
        pnlTitulo.setBody(bdpPrincipal);
        escena=new Scene(pnlTitulo);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
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
    private void InicializarProductos(){
        AntojitoDAO [] comida=null;
        BebidaDAO [] bebida=null;
        try{
            String query="SELECT COUNT(*) AS total_registros FROM antojito";
            PreparedStatement pst=Conexion.connection.prepareStatement(query);
            ResultSet res= pst.executeQuery();
            while(res.next()){comida= new AntojitoDAO[res.getInt(1)];}
            //System.out.println("Comida: "+comida.length);
            btnComida=new ButtonMenu[comida.length];

            query="SELECT COUNT(*) AS total_registros FROM bebida";
            pst=Conexion.connection.prepareStatement(query);
            res= pst.executeQuery();
            while(res.next()){bebida= new BebidaDAO[res.getInt(1)];}
            //System.out.println("Comida: "+bebida.length);
            btnBebida=new ButtonMenu[bebida.length];

            query="SELECT * FROM antojito";
            pst=Conexion.connection.prepareStatement(query);
            res=pst.executeQuery();
            for(int i=0; i< comida.length && res.next();i++){
                comida[i]=new AntojitoDAO();
                comida[i].setCve(res.getString("cve").charAt(0));
                comida[i].setPrecioUnitario(res.getDouble("precioUnitario"));
                comida[i].setExistencia(res.getInt("existencia"));
                comida[i].setDescripcion(res.getString("descripcion"));
                comida[i].setNombre(res.getString("nombre"));
                comida[i].setRuta(res.getString("ruta").replace("\\","\\\\"));
                ConvertidorImagen conv=new ConvertidorImagen();
                comida[i].setImg(conv.A_Imagen(res.getString("imagen")));
                btnComida[i]=new ButtonMenu(comida[i].getCve(),comida[i].getNombre(),comida[i].getImg());
                btnComida[i].setPrefSize(180,70);
            }

            query="SELECT * FROM bebida";
            pst=Conexion.connection.prepareStatement(query);
            res=pst.executeQuery();
            for(int i=0; i< bebida.length && res.next();i++){
                bebida[i]=new BebidaDAO();
                bebida[i].setCve(res.getString("cve").charAt(0));
                bebida[i].setPrecioUnitario(res.getDouble("precioUnitario"));
                bebida[i].setExistencia(res.getInt("existencia"));
                bebida[i].setDescripcion(res.getString("descripcion"));
                bebida[i].setNombre(res.getString("nombre"));
                bebida[i].setRuta(res.getString("ruta").replace("\\","\\\\"));
                ConvertidorImagen conv=new ConvertidorImagen();
                bebida[i].setImg(conv.A_Imagen(res.getString("imagen")));
                btnBebida[i]=new ButtonMenu(bebida[i].getCve(),bebida[i].getNombre(),bebida[i].getImg());
                btnBebida[i].setPrefSize(180,70);
            }
            vComida=new VBox(btnComida);
            vBebida=new VBox(btnBebida);
            hProductos=new HBox(vComida,vBebida);
            scrpProductos=new ScrollPane(hProductos);
            scrpProductos.setMaxWidth(370);
            scrpProductos.setMaxHeight(210);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Hubo un error.");
        }
    }
    private void Venta(String id){
        //Hay que actualizar las ventas que tiene el empleado que vendió
        EmpleadoDAO objEmp=new EmpleadoDAO();
        String query="SELECT * FROM empleado where idEmpleado="+id;
        try{
            Statement stmt=Conexion.connection.createStatement();
            ResultSet res=stmt.executeQuery(query);
            while(res.next()){
                objEmp.setIdEmpleado(res.getInt("idEmpleado"));
                objEmp.setNomEmpleado(res.getString("nomEmpleado"));
                objEmp.setRfcEmpleado(res.getString("rfcEmpleado"));
                objEmp.setSalario(res.getFloat("salario"));
                objEmp.setTelefono(res.getString("telefono"));
                objEmp.setDireccion(res.getString("direccion"));
                objEmp.setVentas(res.getInt("ventas")+1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        query="UPDATE empleado SET nomEmpleado='"+objEmp.getNomEmpleado()+"',rfcEmpleado='"+objEmp.getRfcEmpleado()+"',salario="+objEmp.getSalario()+",telefono='"+objEmp.getTelefono()+"',direccion='"+objEmp.getDireccion()+"',ventas="+objEmp.getVentas()+" WHERE idEmpleado="+objEmp.getIdEmpleado();
        try{
            Statement stmt=Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void CrearTablaOrden(){
        //Pendiente
    }
    private void ActualizarTaqueria(){
        new Taqueria();
        this.close();
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
                    Confirmar();
                }
            }
        });

        btnConfirmar=new Button("Confirmar");
        btnConfirmar.setOnAction(event -> Confirmar());

        vPrincipal=new VBox(lblLogin,pwdfLogin,btnConfirmar);
        vPrincipal.setAlignment(Pos.CENTER);
        vPrincipal.setSpacing(10);
        vPrincipal.setId("login");
        escena=new Scene(vPrincipal,200,150);
    }
    private void Confirmar(){
        if(!pwdfLogin.getText().isEmpty() && pwdfLogin.getText().equals(passwd)){
            Acceder();
        }else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Login");
            alert.setHeaderText("La contraseña es incorrecta!");
            alert.setContentText("La contraseña no coincide. Inténtelo de nuevo!");
            Optional<ButtonType> result = alert.showAndWait();
            if(!(result.get()==ButtonType.OK)){
                this.close();
            }else{pwdfLogin.clear();}
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

class ButtonMenu extends Button{
    private char CVE;
    private String NOMBRE;
    public ButtonMenu(char cve, String nombre, Image img){
        super.setText(nombre);
        CVE=cve;
        NOMBRE=nombre;
        ImageView imv=new ImageView(img);
        imv.setFitHeight(60);
        imv.setFitWidth(60);
        this.setGraphic(imv);
    }
}