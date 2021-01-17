package ro.mta.se.lab.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is used to store all the information of the location
 * selected from the interface
 * It's used to create an object with all the data for a certain city
 * and to populate the interface
 * @author Frîncu Andreea
 */
public class Location {

    /**
     * Class members description
     *
     * <b>countryCode</b> contains a country code
     *
     * <b>cityName</b> contains the selected city's name
     *
     * <b>temperature</b> contains the temperature
     *
     * <b>icon</b> contains the url for the icon specific
     * for the weather in the city
     *
     * <b>description</b> contains the description o the weather
     *
     * <b>date</b> contains the date in GMT+2 format
     *
     * <b>humidity</b> contains the humidity value
     *
     * <b>pressure</b> contains the pressure value
     *
     * <b>windSpeed</b> contains the wind speed value
     */
    StringProperty countryCode;
    StringProperty cityName;
    StringProperty temperature;
    StringProperty icon;
    StringProperty description;
    StringProperty date;
    StringProperty humidity;
    StringProperty pressure;
    StringProperty windSpeed;

    /**
     * Location's Constructor
     * @param countryCode
     * @param cityName
     * @param temperature
     * @param icon
     * @param description
     * @param date
     * @param humidity
     * @param pressure
     * @param windSpeed
     */
    public Location(String countryCode, String cityName, String temperature, String icon,
                    String description, String date, String humidity, String pressure, String windSpeed) {
        this.countryCode = new SimpleStringProperty(countryCode);
        this.cityName = new SimpleStringProperty(cityName);
        this.temperature = new SimpleStringProperty(temperature);
        this.icon = new SimpleStringProperty(icon);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.humidity = new SimpleStringProperty(humidity);
        this.pressure = new SimpleStringProperty(pressure);
        this.windSpeed = new SimpleStringProperty(windSpeed);
    }

    public String getCountryCode() {
        return countryCode.getValue();
    }

    /**
     * Returns the country code if preceded by .toString()
     * @return
     */
    public StringProperty countryCodeProperty() {
        return countryCode;
    }

    /**
     * Sets the country code in the object
     * @param countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode.set(countryCode);
    }

    /**
     * Gets the country code
     * @return
     */
    public String getCityName() {
        return cityName.toString();
    }

    /**
     * Returns the city name if preceded by .toString()
     * @return
     */
    public StringProperty cityNameProperty() {
        return cityName;
    }
    /**
     * Sets the city name in the object
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName.set(cityName);
    }

    /**
     * Gets the temperature
     * @return
     */
    public String getTemperature() {
        return temperature.toString();
    }

    /**
     * Returns the temperature if preceded by .toString()
     * @return
     */
    public StringProperty TemperatureProperty() {
        return temperature;
    }

    /**
     * Sets the temperature in the object
     * @param temperature
     */
    public void setTemperature(String temperature) {
        this.temperature.set(temperature);
    }

    /**
     * Gets the icon's url
     * @return
     */
    public String getIcon() {
        return icon.toString();
    }

    /**
     * Returns the icon's url if preceded by .toString()
     * @return
     */
    public StringProperty iconProperty() {
        return icon;
    }

    /**
     * Sets the url's icon in the object
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon.set(icon);
    }

    /**
     * Gets the getDescription
     * @return
     */
    public String getDescription() {
        return description.toString();
    }

    /**
     * Returns the description if preceded by .toString()
     * @return
     */
    public StringProperty descProperty() {
        return description;
    }

    /**
     * Sets the country code in the object
     * @param description
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Gets the get date and time
     * @return
     */
    public String getDate() {
        return date.toString();
    }

    /**
     * Returns the date time if preceded by .toString()
     * @return
     */
    public StringProperty dateProperty() {
        return date;
    }

    /**
     * Sets the date in the object
     * @param date
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    /**
     * Gets the getDescription
     * @return
     */
    public String getHumidity() {
        return humidity.toString();
    }

    /**
     * Returns the humidity if preceded by .toString()
     * @return
     */
    public StringProperty humidityProperty() {
        return humidity;
    }

    /**
     * Sets the humidity in the object
     * @param humidity
     */
    public void setHumidity(String humidity) {
        this.humidity.set(humidity);
    }

    public String getPressure() {
        return pressure.toString();
    }

    /**
     * Returns the pressure if preceded by .toString()
     * @return
     */
    public StringProperty pressureProperty() {
        return pressure;
    }

    /**
     * Sets the pressure in the object
     * @param pressure
     */
    public void setPressure(String pressure) {
        this.pressure.set(pressure);
    }

    public String getWindSpeed() {
        return windSpeed.toString();
    }

    /**
     * Returns the wind speed if preceded by .toString()
     * @return
     */
    public StringProperty windSpeedProperty() {
        return windSpeed;
    }

    /**
     * Sets the wind speed in the object
     * @param windSpeed
     */
    public void setWindSpeed(String windSpeed) {
        this.windSpeed.set(windSpeed);
    }
}
