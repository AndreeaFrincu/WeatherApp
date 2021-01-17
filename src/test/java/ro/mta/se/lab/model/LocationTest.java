package ro.mta.se.lab.model;

import org.junit.Test;
import ro.mta.se.lab.ReadData;
import ro.mta.se.lab.ReadFile;

import java.io.FileNotFoundException;

import static org.mockito.Mockito.mock;

public class LocationTest {

    ReadData mockTest;
    ReadFile input = new ReadFile(mockTest);

    public LocationTest() throws FileNotFoundException {
    }

    @org.junit.Before
    public void setUp(){
        System.out.println("before");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        System.out.println("after");
    }

    /**
     * Test to check if it gets data correct from file
     */
    @Test
    public void getCountryCode() {
        input.parse();
        input.getCityName().get(0).equals("Sheffield");
    }

}