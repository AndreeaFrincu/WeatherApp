package ro.mta.se.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import ro.mta.se.lab.model.Location;
import ro.mta.se.lab.ReadData;
import ro.mta.se.lab.ReadFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    ReadData mockTest;

    private Location loc;
    private ReadFile inputFile;
    private String selectedCode;
    private String selectedCity;
    private HttpURLConnection connection;
    private ToggleGroup tempConvert;
    private String cTemp;
    private String fTemp;

    @FXML
    private Button start;
    @FXML
    private Button search;
    @FXML
    private Button restart;
    @FXML
    private Label cityShow;
    @FXML
    private ComboBox<String> countryCode;
    @FXML
    private ComboBox<String> cityName;
    @FXML
    private Label comment;
    @FXML
    private Label date;
    @FXML
    private Label temp;
    @FXML
    private Label humidity;
    @FXML
    private Label pressure;
    @FXML
    private Label windSpeed;
    @FXML
    private RadioButton celsius;
    @FXML
    private RadioButton fahrenheit;
    @FXML
    private ImageView weatherIcon;

    public AppController() throws FileNotFoundException {
        this.inputFile = new ReadFile(mockTest);
        this.cityShow = new Label();
        this.countryCode = new ComboBox<String>();
        this.cityName = new ComboBox<String>();
        this.comment = new Label();
        this.date = new Label();
        this.temp = new Label();
        this.humidity = new Label();
        this.pressure = new Label();
        this.windSpeed = new Label();
        this.celsius = new RadioButton();
        this.fahrenheit = new RadioButton();
        this.weatherIcon = new ImageView();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        this.countryCode.setDisable(true);
        this.cityName.setDisable(true);
        this.cityShow.setText(" ");
        this.temp.setText("");
        this.comment.setText("");
        this.date.setText("");
        this.humidity.setText("");
        this.pressure.setText("");
        this.windSpeed.setText("");
        this.celsius.setDisable(true);
        this.fahrenheit.setDisable(true);
        this.inputFile.parse();
        this.inputFile.noDuplicateCodes();

        //toggle group for Radio Buttons
        this.tempConvert = new ToggleGroup();
        this.celsius.setToggleGroup(tempConvert);
        this.fahrenheit.setToggleGroup(tempConvert);
    }

    public void startApp() {
        start.setOnAction(e -> {
            countryCode.setDisable(false);
            cityName.setDisable(true);
            temp.setText("");
            celsius.setDisable(true);
            fahrenheit.setDisable(true);
            countryCode.setPromptText("Select Country");
            cityName.setPromptText("Select City");
            countryCode.setItems(setNoDupCodes());
        });
        start.fire();
        start.setDisable(true);
        search.setDisable(true);
    }

    public void searchApp() {
        search.setOnAction(e -> {
            countryCode.setDisable(true);
            cityName.setDisable(true);
            temp.setText("");
            celsius.setDisable(false);
            fahrenheit.setDisable(false);
            cityShow.setText(selectedCode + ",  " + selectedCity);

            try {
                getCityDataAPI();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        search.fire();
        search.setDisable(true);
    }

    public void restartApp() {
        restart.setOnAction(e -> {
            countryCode.setDisable(false);
            cityName.getSelectionModel().clearSelection();
            cityName.setDisable(true);
            temp.setText("");
            celsius.setSelected(false);
            fahrenheit.setSelected(false);
            celsius.setDisable(true);
            fahrenheit.setDisable(true);
            humidity.setText("");
            pressure.setText("");
            windSpeed.setText("");
            cityShow.setText("");
            comment.setText("");
            date.setText("");
            URL file = null;
            try {
                file = new URL("http://openweathermap.org/img/w/.png");
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
            Image image = new Image(file.toString());
            weatherIcon.setImage(image);
        });
        //search.setDisable(true);
        restart.fire();
        if(restart.isPressed()) {
            search.setDisable(true);
        }
    }

    public void setCountries() throws MalformedURLException {
        String codePicked = countryCode.getSelectionModel().getSelectedItem();
        cityShow.setText("");
        temp.setText("");
        comment.setText("");
        date.setText("");
        humidity.setText("");
        pressure.setText("");
        windSpeed.setText("");
        URL file = new URL("http://openweathermap.org/img/w/.png");
        Image image = new Image(file.toString());
        weatherIcon.setImage(image);
        this.cityName.setDisable(false);

        selectedCode = codePicked;
        cityName.setItems(setCities());
    }

    public void setLocation() {
        String cityPicked = cityName.getSelectionModel().getSelectedItem();
        this.cityShow.setText("");
        selectedCity = cityPicked;
        search.setDisable(false);
    }

    public ObservableList<String> setNoDupCodes() {
        ObservableList<String> correctCodes = FXCollections.observableArrayList();
        ArrayList<String> coList = inputFile.getNoDupCountryCode();
        for(String code : coList) {
            correctCodes.add(code);
        }
        return correctCodes;
    }

    public ObservableList<String> setCities() {
        ObservableList<String> correctCities = FXCollections.observableArrayList();
        ArrayList<String> idRow = inputFile.getCountryCode2();
        ArrayList<String> cityN = inputFile.getCityName();
        int counter = 0;
        for(String id : idRow){
            if(selectedCode.equals(id)){
                correctCities.add(cityN.get(counter));
            }
            counter++;
        }

        return correctCities; //lista cu toate orasele pentru un anumit cod
    }

    public void getCityDataAPI() throws IOException {
        BufferedReader jsonReader;
        String row;
        StringBuffer jsonContent = new StringBuffer();

        URL currentWeatherData = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + selectedCity + "&APPID=75767842dbfa16e1fcf58b349e6d9943");
        connection = (HttpURLConnection) currentWeatherData.openConnection();

        //set request for conn1
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        //get response from the server, must be 200 to be connected
        int status = connection.getResponseCode();

        //check if connection fails
        if(status != 200) {
            jsonReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((row = jsonReader.readLine()) != null) jsonContent.append(row);
        }
        else {
            jsonReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((row = jsonReader.readLine()) != null) jsonContent.append(row);
        }

        connection.disconnect();
        parseJson(jsonContent.toString());
    }

    public void parseJson(String content) throws MalformedURLException {
        JSONObject obj = new JSONObject(content);

        float wSpeed = obj.getJSONObject("wind").getFloat("speed");
        float pres = obj.getJSONObject("main").getFloat("pressure");
        float hum = obj.getJSONObject("main").getFloat("humidity");

        String desc = null;
        String icon = null;
        JSONArray weatherArr = obj.getJSONArray("weather");
        for (int i = 0; i < weatherArr.length(); i++) {
            desc = weatherArr.getJSONObject(i).getString("description");
            icon = weatherArr.getJSONObject(i).getString("icon");
        }

        int dt = (int) obj.get("dt");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = Instant.ofEpochSecond(dt).atZone(ZoneId.of("GMT+2")).format(formatter);

        float tmp = obj.getJSONObject("main").getFloat("temp");
        calcTemp(tmp);

        URL file = new URL("http://openweathermap.org/img/w/" + icon + ".png");

        loc = new Location(selectedCode, selectedCity, Float.toString(tmp), file.toString(), desc,
                formattedDate, Float.toString(hum), Float.toString(pres), Float.toString(wSpeed));

        initSearch();
    }

    public void initSearch() {
        //temp.setText(loc.cityTemperatureProperty().getValue());
        weatherIcon.setImage(new Image(loc.iconProperty().getValue()));
        comment.setText("Description:\n" + loc.descProperty().getValue());
        date.setText("Date and Time:\n" + loc.dateProperty().getValue());
        humidity.setText(loc.humidityProperty().getValue() + "%");
        pressure.setText(loc.pressureProperty().getValue() + " hPa");
        windSpeed.setText(loc.windSpeedProperty().getValue() + " m/s");
    }

    public void calcTemp(float tmp) {
        float auxFloat = 0;
        auxFloat = (float) (tmp - 273.15);
        auxFloat = Math.round(auxFloat);
        cTemp = Float.toString(auxFloat);

        auxFloat = (float) ((tmp - 273.15) * 9/5 + 32);
        auxFloat = Math.round(auxFloat);
        fTemp = Float.toString(auxFloat);
    }

    public void radioButtonChange() throws IOException {
        if(this.tempConvert.getSelectedToggle().equals(this.celsius)){
            temp.setText(cTemp + " C");
        }
        else if(this.tempConvert.getSelectedToggle().equals(this.fahrenheit)) {
            temp.setText(fTemp + " F");
        }
    }

}