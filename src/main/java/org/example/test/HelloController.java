package org.example.test;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class HelloController {
    private TextField txtPantalla;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void restringirCampoTexto(KeyEvent event){
        if(event.getCharacter().matches("[^\\e\t\r\\d+S]")){
            event.consume();
            txtPantalla.setStyle("-fx-border-color: red");
        }else
            txtPantalla.setStyle("-fx-border-color: blue");
    }
}