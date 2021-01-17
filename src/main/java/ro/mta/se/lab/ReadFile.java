package ro.mta.se.lab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {

    private File inputFile;
    private Scanner countryData;
    private ArrayList<String> countryID;
    private ArrayList<String> cityName;
    private ArrayList<String> countryLat;
    private ArrayList<String> countryLon;
    private ObservableList<String> countryCode;
    private ArrayList<String> countryCode2;
    private ArrayList<String> noDupCountryCode;
    private String test;


    public ReadFile(ReadData mockTest) throws FileNotFoundException {
        this.inputFile = new File(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources\\countries.txt");
        this.countryData = new Scanner(inputFile);
        this.countryID = new ArrayList<>();
        this.cityName = new ArrayList<>();
        this.countryLat = new ArrayList<>();
        this.countryLon = new ArrayList<>();
        this.countryCode = FXCollections.observableArrayList();
        this.countryCode2 = new ArrayList<>();
        this.noDupCountryCode = new ArrayList<>();
    }

    public String setupPath() {
        //System.out.println(inputFile);
        return inputFile.toString();
    }

    public void parse() {
        countryData.nextLine(); //sare peste prima linie
        while (countryData.hasNextLine()) {
            String data = countryData.nextLine();
            String tokens[] = data.split(" ");
            countryID.add(tokens[0]);
            cityName.add(tokens[1]);
            countryLat.add(tokens[2]);
            countryLon.add(tokens[3]);
            countryCode.add(tokens[4]);
            countryCode2.add(tokens[4]);
        }
    }

    public void noDuplicateCodes() {
        for( String code : countryCode2) {
            if(noDupCountryCode.contains(code))
                continue;
            else
                noDupCountryCode.add(code);
        }
        System.out.println(noDupCountryCode);
    }

    public ArrayList<String> getCountryID() {
        return countryID;
    }

    public ArrayList<String> getCityName() {
        return cityName;
    }

    public ArrayList<String> getCountryLat() {
        return countryLat;
    }

    public ArrayList<String> getCountryLon() {
        return countryLon;
    }

    public ObservableList<String> getCountryCode() {
        return countryCode;
    }

    public ArrayList<String> getCountryCode2() {
        return countryCode2;
    }

    public ArrayList<String> getNoDupCountryCode() {
        return noDupCountryCode;
    }

    public boolean verify(String verif) {
        test = "Rome";
        return verif == test;
    }

}