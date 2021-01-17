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

/**
 * This class is used to control the interface
 * @author Frîncu Andreea
 */
public class AppController implements Initializable {

    /**
     * Class members description
     *
     * <b>loc</b> is the object of type Location used to populate the interface
     *
     * <b>inputFile</b> is the input file
     *
     * <b>selectedCode</b> is the selected country code from interface
     *
     * <b>selectedCity</b> is the city selected from the interface
     *
     * <b>connection</b> is a http connection used to interrogate the API
     * for information on the weather state
     *
     * <b>tempConvert</b> is an object of type ToggleGroup used to group
     * the two RadioButtons for selecting in which format to show the temperature
     * value, Celsius or Fahrenheit
     *
     * <b>cTemp</b> is a member used to store the value in Celsius of the temperature
     *
     * <b>fTemp</b> is a member used to store the value in Fahrenheit of the temperature
     */
    ReadData mkTest;

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

    /**
     * AppController's Constructor
     * @throws FileNotFoundException
     */
    public AppController() throws FileNotFoundException {
        this.inputFile = new ReadFile(mkTest);
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

    /**
     * This method is used to initialize the application's window when the program starts
     * All the buttons and combo boxes are disabled, just the button <b>start</b> is enabled
     * @param location
     * @param resources
     */
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

    /**
     * This method is called when the <b>start</b> button is clicked
     * The combo box with country codes is enabled
     * The start button becomes inactive, it's only active when we first start the application
     * The combo box with countries codes based of the list with no duplicate codes
     */
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

    /**
     * This method is called when the <b>search</b> button is clicked
     * The connection is made through the method @getCityDataAPI
     */
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

    /**
     * This method is called when the <b>restart</b> button is clicked
     * All the members of the interface are cleared and ready for another search
     */
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

    /**
     * This method is called when selecting a country code from the <b>countryCode</b> combo box
     * The combo box with the city names is enabled after selecting the country code
     * @throws MalformedURLException
     */
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

    /**
     * This method is called when selecting a city from the <b>cityName</b> combo box
     * The <b>search</b> button is enabled so the app can connect to the API and retrieve the data
     */
    public void setLocation() {
        String cityPicked = cityName.getSelectionModel().getSelectedItem();
        this.cityShow.setText("");
        selectedCity = cityPicked;
        search.setDisable(false);
    }

    /**
     * This method is used to populate the ObservableList with the
     * country codes from the ArrayList with no duplicates
     * @return
     */
    public ObservableList<String> setNoDupCodes() {
        ObservableList<String> correctCodes = FXCollections.observableArrayList();
        ArrayList<String> coList = inputFile.getNoDupCountryCode();
        for(String code : coList) {
            correctCodes.add(code);
        }
        return correctCodes;
    }

    /**
     * This method is called when selecting a country code and is used to populate
     * the combo box with just the city names from the selected country
     * @return
     */
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

    /**
     * This method is called when the search button is clicked
     * It is used to form a connection with the API
     * The method sends a request, getting back a JSON file
     * @throws IOException
     */
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

    /**
     * This method is used to parse the JSON file received from the API
     * The data is used to populate the object of type Location
     * @param content
     * @throws MalformedURLException
     */
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

    /**
     * In this method are set all the values from the interface
     */
    public void initSearch() {
        weatherIcon.setImage(new Image(loc.iconProperty().getValue()));
        comment.setText("Description:\n" + loc.descProperty().getValue());
        date.setText("Date and Time:\n" + loc.dateProperty().getValue());
        humidity.setText(loc.humidityProperty().getValue() + "%");
        pressure.setText(loc.pressureProperty().getValue() + " hPa");
        windSpeed.setText(loc.windSpeedProperty().getValue() + " m/s");
    }

    /**
     * This method is used to convert the temperature from Kelvin (default from API)
     * in Celsius and Fahrenheit
     * @param tmp
     */
    public void calcTemp(float tmp) {
        float auxFloat = 0;
        auxFloat = (float) (tmp - 273.15);
        auxFloat = Math.round(auxFloat);
        cTemp = Float.toString(auxFloat);

        auxFloat = (float) ((tmp - 273.15) * 9/5 + 32);
        auxFloat = Math.round(auxFloat);
        fTemp = Float.toString(auxFloat);
    }

    /**
     * This method is called when the RadioButtons are changed
     * For each selection, the label with the temperature is changing
     * @throws IOException
     */
    public void radioButtonChange() throws IOException {
        if(this.tempConvert.getSelectedToggle().equals(this.celsius)){
            temp.setText(cTemp + " C");
        }
        else if(this.tempConvert.getSelectedToggle().equals(this.fahrenheit)) {
            temp.setText(fTemp + " F");
        }
    }

}