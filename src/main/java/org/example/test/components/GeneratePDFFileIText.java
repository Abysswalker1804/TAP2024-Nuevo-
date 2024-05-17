package org.example.test.components;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

public class GeneratePDFFileIText {
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    private static final String iTextExampleImage = "/home/xules/codigoxules/iText-Example-image.png";
    /**
     * We create a PDF document with iText using different elements to learn
     * to use this library.
     * Creamos un documento PDF con iText usando diferentes elementos para aprender
     * a usar esta librería.
     * @param pdfNewFile  <code>String</code>
     *      pdf File we are going to write.
     *      Fichero pdf en el que vamos a escribir.
     */
    public void createPDF(File pdfNewFile, String title, String content,double totalOrden) {
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No se encontró el fichero para generar el pdf" + fileNotFoundException);
            }
            document.open();
            // Añadimos los metadatos del PDF
            document.addTitle("Recibo1");
            //document.addSubject("Using iText (usando iText)");
            //document.addKeywords("Java, PDF, iText");
            //document.addAuthor("Código Xules");
            //document.addCreator("Código Xules");
            // Primera página
            Chunk chunk = new Chunk(title, chapterFont);
            chunk.setBackground(BaseColor.GRAY);
            //Creemos el primer capítulo
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph(content+"\nTotal: $"+totalOrden, paragraphFont));
            //Añadimos una imagen
            /*Image image;
            try {
                image = Image.getInstance(iTextExampleImage);
                image.setAbsolutePosition(2, 150);
                chapter.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" +  ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " +  ex);
            }*/
            document.add(chapter);

            document.close();
            System.out.println("¡Se ha generado tu hoja PDF!");
        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento: " + documentException);
        }
    }
    public void createPDF(File pdfNewFile, String title, String content) {
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No se encontró el fichero para generar el pdf" + fileNotFoundException);
            }
            document.open();
            // Añadimos los metadatos del PDF
            document.addTitle("Recibo1");
            //document.addSubject("Using iText (usando iText)");
            //document.addKeywords("Java, PDF, iText");
            //document.addAuthor("Código Xules");
            //document.addCreator("Código Xules");
            // Primera página
            Chunk chunk = new Chunk(title, chapterFont);
            chunk.setBackground(BaseColor.GRAY);
            //Creemos el primer capítulo
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph(content, paragraphFont));
            //Añadimos una imagen
            /*Image image;
            try {
                image = Image.getInstance(iTextExampleImage);
                image.setAbsolutePosition(2, 150);
                chapter.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" +  ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " +  ex);
            }*/
            document.add(chapter);

            document.close();
            System.out.println("¡Se ha generado tu hoja PDF!");
        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento: " + documentException);
        }
    }
}
