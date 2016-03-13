package config;

import com.codeborne.selenide.Configuration;
import core.ConciseAPI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;

import static core.ConciseAPI.*;

public class BaseTest {

    static {
        Configuration.timeout = 20;
    }

    @Before
    public void openSite() {
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @After
    public void clearData() {
        executeScript("localStorage.clear()");
    }

    @BeforeClass
    public static void setUp() {
        ConciseAPI.setDriver(new FirefoxDriver());
    }

    @AfterClass
    public static void teardown() {
        ConciseAPI.getDriver().quit();
    }
}
