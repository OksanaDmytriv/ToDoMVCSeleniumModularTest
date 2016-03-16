package config;

import com.codeborne.selenide.Configuration;
import core.ConciseAPI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

import static core.ConciseAPI.executeJavaScript;
import static core.ConciseAPI.open;

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
        executeJavaScript("localStorage.clear()");
    }

    @AfterClass
    public static void setUp() {
        ConciseAPI.setDriver(new FirefoxDriver());
    }
    public static void teardown() {
        ConciseAPI.getDriver().quit();
    }
}
