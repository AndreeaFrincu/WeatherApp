package ro.mta.se.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.AppController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Main extends Application {

    //private ObservableList<String> codes = FXCollections.observableArrayList();
    //private ReadFile input = new ReadFile();

    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        AppController myController = new AppController();

        //Parent loader = FXMLLoader.load(getClass().getResource("D:\\ATM stuff\\4th year\\sem1\\IP\\HW2\\weather-app\\src\\main\\resources\\view\\WeatherView.fxml"));
        FXMLLoader loader = new FXMLLoader(new File("D:\\ATM stuff\\4th year\\sem1\\IP\\HW2\\weather-app\\src\\main\\resources\\view\\WeatherView.fxml").toURI().toURL());
        //Parent root = loader.load();
        loader.setController(myController);
        Scene test = new Scene(loader.load());
        stage.setScene(test);
        stage.show();
    }
}