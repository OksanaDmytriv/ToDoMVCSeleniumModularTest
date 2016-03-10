package core;

import org.openqa.selenium.WebElement;
import static core.ConciseAPI.actions;

public class Helpers {

    public static WebElement hover(WebElement element) {
        actions.moveToElement(element).perform();
        return element;
    }

    public static WebElement doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
        return element;
    }
}
