package ro.mta.se.lab.model;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ro.mta.se.lab.ReadData;
import ro.mta.se.lab.ReadFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFileTest {

    private ReadData mockTest;
    ReadFile test;
    ReadFile input = new ReadFile(mockTest);

    public ReadFileTest() throws FileNotFoundException {

    }

    @org.junit.Before
    public void setUp() {
        System.out.println("before");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        System.out.println("after");
    }

    @Test
    public void setupPathTest() {
        assertEquals("D:\\ATM stuff\\4th year\\sem1\\IP\\HW2\\weather-app\\src\\main\\resources\\countries.txt",
                input.setupPath());
    }

    @Test
    public void parseTest() throws IOException {
        input.parse();
        assertEquals("Shibuya", input.getCityName().get(1));
        assertEquals("DE", input.getCountryCode().get(5));
        assertEquals("2670781", input.getCountryID().get(2));
        assertEquals("47.7994", input.getCountryLat().get(7));
        assertEquals("5.8667", input.getCountryLon().get(4));
    }


    @Before
    public void setMockTest() throws FileNotFoundException {
        mockTest = mock(ReadData.class);
        when(mockTest.getCities()).thenReturn("Rome");
        test = new ReadFile(mockTest);
    }

    @Test
    public void verifyTest() {
        assertTrue(test.verify("Rome"));
    }
}