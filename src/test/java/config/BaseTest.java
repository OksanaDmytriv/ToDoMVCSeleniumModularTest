package config;

import com.codeborne.selenide.Configuration;
import core.ConciseAPI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

    static {
        Configuration.timeout = 20;
    }

    @BeforeClass
    public static void setUp(){
        ConciseAPI.setDriver(new FirefoxDriver());
    }

    @AfterClass
    public static void teardown() {
        ConciseAPI.getDriver().quit();
    }

}
