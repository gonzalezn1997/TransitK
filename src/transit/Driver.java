/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Runs the application
 *
 * In order to use this Application it requires the GMapsFX API 2.12.0
 * To get the library in Intellij: File --> Project Structure --> Libraries--> + --> from Mavern -->
 * search for com.lynden:GMapsFX:2.12.0 --> magnifying glass --> drop down arrow --> select it --> okay
 *
 *
 * @author Alexander Gnabasik
 * @version 1.0
 * @created 05-Oct-2017 11:52:06 AM
 */
public class Driver extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the FMXL file.
     *
     * @param primaryStage The primary Stage
     * @throws IOException If FXML is not found
     * @author Noe Gonzalez
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        File file = new File("src/pepe.jpg");
        Image image = new Image(file.toURI().toString());
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("GTFS Manipulator");
        primaryStage.setScene(new Scene(root, 1200, 525));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
