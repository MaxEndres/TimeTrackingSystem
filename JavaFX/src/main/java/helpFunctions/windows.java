package helpFunctions;

import com.example.javafx.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class windows {
    @FXML
    public static void changeWindow(Button button, String fxml) throws IOException {
        //CLOSE WINDOW
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        //NEW WINDOW
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public static void openWindow(String fxml) throws IOException
    {

        //NEW WINDOW
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public static void closeWindow(Button button)
    {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    @FXML
    public static void loadWindow(String fxml, AnchorPane page) throws IOException
    {
        AnchorPane NewPane =  FXMLLoader.load(Main.class.getResource(fxml));
        page.getChildren().add(NewPane);
    }
}
