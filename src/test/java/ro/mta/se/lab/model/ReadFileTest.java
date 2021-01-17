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

/**
 * This class contains tests for ReadFile
 */
public class ReadFileTest {

    private ReadData mkTest;
    ReadFile test;
    ReadFile input = new ReadFile(mkTest);

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

    /**
     * Test to verify the input file path
     */
    @Test
    public void setupPathTest() {
        assertEquals("D:\\ATM stuff\\4th year\\sem1\\IP\\HW2\\weather-app\\src\\main\\resources\\countries.txt",
                input.setupPath());
    }

    /**
     * Test to verify the components of the members
     * @throws IOException
     */
    @Test
    public void parseTest() throws IOException {
        input.parse();
        assertEquals("Shibuya", input.getCityName().get(1));
        assertEquals("DE", input.getCountryCode().get(5));
        assertEquals("2670781", input.getCountryID().get(2));
        assertEquals("47.7994", input.getCityLat().get(7));
        assertEquals("5.8667", input.getCityLon().get(4));
    }


    @Before
    public void setMockTest() throws FileNotFoundException {
        mkTest = mock(ReadData.class);
        when(mkTest.getCities()).thenReturn("Rome");
        test = new ReadFile(mkTest);
    }

    @Test
    public void verifyTest() {
        assertTrue(test.verify("Rome"));
    }
}