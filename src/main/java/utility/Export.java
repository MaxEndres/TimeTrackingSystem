package utility;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import entities.UserEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import utility.DatabaseService;

public class Export {

    public static void exportPWasPDF(UserEntity userEntity, String password) throws IOException, DocumentException, SQLException {

        DatabaseService databaseService = new DatabaseService();
        String text = "\n\n\n\n\n\n Hello " + userEntity.getForename() + " "+ userEntity.getSurname() + ",\n Welcome to your first day at /Company_Name/. \n " +
                "Our time-tracking solution is provided by onPoint. \n" +
                "The credentials for your first login will be following: \n" +
                "ID: " + databaseService.getMaxID() +
                "\npassword: " + password;

        Document doc = new Document();

        Image img_onPoint = Image.getInstance("src/main/resources/assets/img.png");
        img_onPoint.setAbsolutePosition(470f,730f);
        img_onPoint.scaleAbsolute(100f,100f);
        //Image img_company = Image.getInstance("");

        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
                "C:\\Users\\Public\\Downloads\\" +
                        databaseService.getMaxID() + "credentials" + ".pdf"));

        doc.open();
        doc.add(img_onPoint);
        doc.add(new Paragraph(text));

        doc.close();
        writer.close();

    }

    public static void exportAllTimeStamps(int userID, String _month, String year) throws IOException, SQLException {

        DatabaseService databaseService = new DatabaseService();
        ResultSet queryOutput = databaseService.timeStampsForCSV(userID, _month, year);
        CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator("\n").withHeader(queryOutput);
        LocalDate today = LocalDate.now();
        // file name
        final String FILE_NAME = "C:\\Users\\Public\\Downloads\\user" +userID+"_timestamps_"+ _month+"_"+ year + ".csv";
        // creating the file object
        File file = new File(FILE_NAME);
        // creating file writer object
        FileWriter fw = new FileWriter(file);

        // creating the csv printer object
        CSVPrinter printer = new CSVPrinter(fw, format);
        // printing the result in 'CSV' file
        //printer.print(columNames);
       // printer.println();
        printer.printRecords(queryOutput);



        fw.close();
        printer.close();

    }

}
