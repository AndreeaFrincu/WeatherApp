package ro.mta.se.lab.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {

    StringProperty countryCode;
    StringProperty cityName;
    StringProperty temperature;
    StringProperty icon;
    StringProperty description;
    StringProperty date;
    StringProperty humidity;
    StringProperty pressure;
    StringProperty windSpeed;

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

    public StringProperty countryCodeProperty() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode.set(countryCode);
    }

    public String getCityName() {
        return cityName.toString();
    }

    public StringProperty cityNameProperty() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName.set(cityName);
    }

    public String getTemperature() {
        return temperature.toString();
    }

    public StringProperty cityTemperatureProperty() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature.set(temperature);
    }

    public String getIcon() {
        return icon.toString();
    }

    public StringProperty iconProperty() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon.set(icon);
    }

    public String getDescription() {
        return description.toString();
    }

    public StringProperty descProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getDate() {
        return date.toString();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getHumidity() {
        return humidity.toString();
    }

    public StringProperty humidityProperty() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity.set(humidity);
    }

    public String getPressure() {
        return pressure.toString();
    }

    public StringProperty pressureProperty() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure.set(pressure);
    }

    public String getWindSpeed() {
        return windSpeed.toString();
    }

    public StringProperty windSpeedProperty() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed.set(windSpeed);
    }
}
