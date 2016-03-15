package core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ConciseAPI {

    private static WebDriver driver;

    public static Actions actions;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        ConciseAPI.driver = driver;
        actions = new Actions(driver);
    }

    public static <V> V assertThat(ExpectedCondition<V> condition) {
        return assertThat(condition, Configuration.timeout);
    }

    public static <V> V assertThat(ExpectedCondition<V> condition, int timeout) {
        return (new WebDriverWait(getDriver(), timeout)).until(condition);
    }

    public static WebElement $(By locator) {
        return assertThat(visibilityOfElementLocated(locator));
    }

    public static WebElement $(String cssSelector) {
        return $(byCSS(cssSelector));
    }

    public static WebElement $(WebElement element) {
        return assertThat(visibilityOf(element));
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitParentElement, By innerElementLocator) {
        return $(assertThat(conditionToWaitParentElement).findElement(innerElementLocator));
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitParentElement, String innerElementCssSelector) {
        return $(conditionToWaitParentElement, byCSS(innerElementCssSelector));
    }

    public static WebElement $(WebElement parentElement, String innerElementCssSelector) {
        return $(parentElement, byCSS(innerElementCssSelector));
    }

    public static WebElement $(WebElement parentElement, By innerElementLocator) {
        return assertThat(visibilityOf(parentElement)).findElement(innerElementLocator);
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitElement) {
        return assertThat(conditionToWaitElement);
    }

    public static WebElement $(WebElement parentElement, String... cssSelectorsOfInnerElements) {
        assertThat(visibilityOf(parentElement));
        for (String selector : cssSelectorsOfInnerElements) {
            parentElement = $(parentElement, byCSS(selector));
        }
        return parentElement;
    }

    public static WebElement $(By locatorOfParentElement, String... cssSelectorsOfInnerElements) {
        WebElement element = getDriver().findElement(locatorOfParentElement);
        return $(element, cssSelectorsOfInnerElements);
    }

    public static WebElement $(String cssSelectorOfParentElement, String... cssSelectorsOfInnerElements) {
        return $(byCSS(cssSelectorOfParentElement), cssSelectorsOfInnerElements);
    }

    public static List<WebElement> $$(By locator) {
        return assertThat(visibilityOfAllElementsLocatedBy(locator));
    }

    public static List<WebElement> $$(String cssSelector) {
        return assertThat(visibilityOfAllElementsLocatedBy(byCSS(cssSelector)));
    }

    public static List<WebElement> $$(ExpectedCondition<List<WebElement>> conditionToWaitForListFilteredElements) {
        return assertThat(conditionToWaitForListFilteredElements);
    }

    public static By byText(String text) {
        return By.xpath("//*[text()[contains(.,'" + text + "')]]");
    }

    public static By byCSS(String cssSelector) {
        return By.cssSelector(cssSelector);
    }

    public static void open(String URL) {
        getDriver().get(URL);
    }

    public static WebElement hover(WebElement element) {
        assertThat(visibilityOf(element));
        actions.moveToElement(element).perform();
        return element;
    }

    public static WebElement doubleClick(WebElement element) {
        assertThat(visibilityOf(element));
        actions.doubleClick(element).perform();
        return element;
    }

    public static void executeJavaScript(String script) {
        if (getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) getDriver()).executeScript(script);
        }
    }

    public static WebElement setValue(WebElement element, String text) {
        assertThat(visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        return element;
    }

    public static List<WebElement> listOfVisibleElements(List<WebElement> elements) {
        List<WebElement> visibleElements = new ArrayList<WebElement>();
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                visibleElements.add(element);
            }
        }
        return visibleElements;
    }

    public static List<String> textsOfElements(List<WebElement> elements) {
        List<String> currentTexts = new ArrayList<String>();
        for (int i = 0; i < elements.size(); ++i) {
            currentTexts.add(i, elements.get(i).getText());

        }
        return currentTexts;
    }
}
