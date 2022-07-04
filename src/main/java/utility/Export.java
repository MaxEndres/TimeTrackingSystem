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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;

import utility.DatabaseService;

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

    public static void exportAllTimeStamps(int userID) throws IOException, SQLException {

        DatabaseService databaseService = new DatabaseService();
        ResultSet queryOutput = databaseService.timeStampsForCSV(userID);
        ResultSetMetaData rsmd = queryOutput.getMetaData();
        String columNames = "";
        columNames = rsmd.getColumnName(1)+",";
        for(int i = 2; i < 6; i++){
            columNames = columNames + rsmd.getColumnName(i) + ",";
        }
        columNames = columNames + rsmd.getColumnName(6);
        CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator("\n");
        LocalDate today = LocalDate.now();
        // file name
        final String FILE_NAME = "timestamp"+ today + ".csv";
        // creating the file object
        File file = new File(FILE_NAME);
        // creating file writer object
        FileWriter fw = new FileWriter(file);

        // creating the csv printer object
        CSVPrinter printer = new CSVPrinter(fw, format);
        // printing the result in 'CSV' file
        printer.printRecords(String.valueOf(columNames));
        printer.printRecords(queryOutput);



        fw.close();
        printer.close();
        System.out.println("Query has been executed successfully...");
    }

}
