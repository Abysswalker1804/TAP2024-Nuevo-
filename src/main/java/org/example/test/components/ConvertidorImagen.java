package org.example.test.components;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public class ConvertidorImagen {
    public boolean existe;
    byte[] imageBytes;
    File file;
    String base64Image;
    public ConvertidorImagen(){}
    public ConvertidorImagen(String ruta){
        file=new File(ruta);
        existe=file.exists();
    }
    public String A_Base64(){
        base64Image="";
        if(existe){
            try{
                imageBytes = Files.readAllBytes(file.toPath());
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
            }catch(Exception e){}
        }
        return base64Image;
    }
    public Image A_Imagen(){
        Image javafxImage=null;
        try{
            byte[] decodedImageBytes = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedImageBytes);
            javafxImage= new Image(inputStream);
        }catch(Exception e){}
        return javafxImage;
    }
    public Image A_Imagen(String base64){
        Image javafxImage=null;
        try{
            byte[] decodedImageBytes = Base64.getDecoder().decode(base64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedImageBytes);
            javafxImage= new Image(inputStream);
        }catch(Exception e){}
        return javafxImage;
    }
}
