package ro.mta.se.lab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to read information about the location
 * from the input file, "countries.txt"
 *
 * @author Frîncu Andreea
 */

public class ReadFile {

    /**
     * Class members description
     *
     * <b>inputFile</b> is an object of type File which takes the path
     * of countries.txt to extract the information
     *
     * <b>Scanner</b> is an object of type Scanner used in method parse()
     * to read from inputFile
     *
     * <b>countryID</b> is a list used for storing the countries' IDs
     *
     * <b>cityName</b> is a list used for storing the cities' names
     *
     * <b>countryLat</b> is a list used for storing the cities' latitude
     *
     * <b>countryLon</b> is a list used for storing the cities' longitude
     *
     * <b>countryCode</b> is an observable list used for storing the countries' codes
     *
     * <b>countryCode2</b> is a list used for storing the countries' codes
     *
     * <b>noDupCountryCode</b> is a list used for storing the countries' codes
     * but no duplicates
     *
     * <b>test</b> is a string used in a test for this class
     */
    private File inputFile;
    private Scanner countryData;
    private ArrayList<String> countryID;
    private ArrayList<String> cityName;
    private ArrayList<String> cityLat;
    private ArrayList<String> cityLon;
    private ObservableList<String> countryCode;
    private ArrayList<String> countryCode2;
    private ArrayList<String> noDupCountryCode;
    private String test;

    /**
     * ReadFile's Constructor
     * @param mockTest
     * @throws FileNotFoundException
     */
    public ReadFile(ReadData mockTest) throws FileNotFoundException {
        this.inputFile = new File(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources\\countries.txt");
        this.countryData = new Scanner(inputFile);
        this.countryID = new ArrayList<>();
        this.cityName = new ArrayList<>();
        this.cityLat = new ArrayList<>();
        this.cityLon = new ArrayList<>();
        this.countryCode = FXCollections.observableArrayList();
        this.countryCode2 = new ArrayList<>();
        this.noDupCountryCode = new ArrayList<>();
    }

    /**
     * This is a method used for testing if the path of the inputFile
     * is correct
     * @return
     */
    public String setupPath() {
        return inputFile.toString();
    }

    /**
     * This method is used for parsing inputFile by space
     * and storing the information in the class' lists
     * In jumps over the first line because it contains the
     * categories
     */
    public void parse() {
        countryData.nextLine();
        while (countryData.hasNextLine()) {
            String data = countryData.nextLine();
            String tokens[] = data.split(" ");
            countryID.add(tokens[0]);
            cityName.add(tokens[1]);
            cityLat.add(tokens[2]);
            cityLon.add(tokens[3]);
            countryCode.add(tokens[4]);
            countryCode2.add(tokens[4]);
        }
    }

    /**
     * This method gets rid of the duplicate codes in the list,
     * so that we don't see them in the interface
     */
    public void noDuplicateCodes() {
        for( String code : countryCode2) {
            if(noDupCountryCode.contains(code))
                continue;
            else
                noDupCountryCode.add(code);
        }
        System.out.println(noDupCountryCode);
    }

    /**
     * Returns the list with countries' IDs
     * @return
     */
    public ArrayList<String> getCountryID() {
        return countryID;
    }

    /**
     * Returns the list with cities' names
     * @return
     */
    public ArrayList<String> getCityName() {
        return cityName;
    }

    /**
     * Returns the list with cities' latitude
     * @return
     */
    public ArrayList<String> getCityLat() {
        return cityLat;
    }

    /**
     * Returns the list with cities' longitude
     * @return
     */
    public ArrayList<String> getCityLon() {
        return cityLon;
    }

    /**
     * Returns the observable list with countries' codes
     * @return
     */
    public ObservableList<String> getCountryCode() {
        return countryCode;
    }

    /**
     * Returns the list with countries' codes
     * @return
     */
    public ArrayList<String> getCountryCode2() {
        return countryCode2;
    }

    /**
     * Returns the list with countries' codes without duplicates
     * @return
     */
    public ArrayList<String> getNoDupCountryCode() {
        return noDupCountryCode;
    }

    /**
     * Method used for class ReadFileTest
     * @param verif
     * @return
     */
    public boolean verify(String verif) {
        test = "Rome";
        return verif == test;
    }

}