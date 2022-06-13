package utility;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import entities.UserEntity;

import java.io.FileOutputStream;
import java.io.IOException;

public class Export {

    public static void exportPWasPDF(UserEntity userEntity, String password) throws IOException, DocumentException {

        String text = "\n\n\n\n\n\n Hello " + userEntity.getForename() + " "+ userEntity.getSurname() + ",\n Welcome to your first day at /Company_Name/. \n " +
                "Our time-tracking solution is provided by onPoint. \n" +
                "The credentials for your first login will be following: \n" +
                "email: " + userEntity.getEmail() +
                "\npassword: " + password;

        Document doc = new Document();

        Image img_onPoint = Image.getInstance("src/main/resources/assets/img.png");
        img_onPoint.setAbsolutePosition(470f,730f);
        img_onPoint.scaleAbsolute(100f,100f);
        //Image img_company = Image.getInstance("");

        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
                "C:\\Users\\Public\\Downloads\\" +
                userEntity.getEmail() + "credentials" + ".pdf"));

        doc.open();
        doc.add(img_onPoint);
        doc.add(new Paragraph(text));

        doc.close();
        writer.close();

    }

}
