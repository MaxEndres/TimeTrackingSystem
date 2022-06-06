package entities;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Export {

    public static void exportPWasPDF(User user, String password) throws IOException, DocumentException {

        String text = "\n\n\n\n\n\n Hello " + user.getForename() + user.getSurname() + "\n Welcome to your first day at /Company_Name/. \n " +
                "Our time-tracking solution is provided by onPoint. \n" +
                "The credentials for your first login will be following: \n" +
                "email: " + user.getEmail() +
                "\npassword: " + password;

        Document doc = new Document();

        Image img_onPoint = Image.getInstance("src/main/resources/assets/img.png");
        img_onPoint.setAbsolutePosition(470f,730f);
        img_onPoint.scaleAbsolute(50f,50f);
        //Image img_company = Image.getInstance("");

        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
                "C:\\Users\\Public\\Downloads\\" +
                user.getEmail() + "credentials" + ".pdf"));

        doc.open();
        doc.add(img_onPoint);
        doc.add(new Paragraph(text));

        doc.close();
        writer.close();

    }

}
