package config;

import core.ConciseAPI;
import core.Configuration;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

import static core.ConciseAPI.open;

public class BaseTest {

    static {
        Configuration.timeout = 20;
    }

    @Before
    public void setUp() {
        ConciseAPI.setDriver(new FirefoxDriver());
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @After
    public void teardown() {
        ConciseAPI.getDriver().quit();
    }
}
