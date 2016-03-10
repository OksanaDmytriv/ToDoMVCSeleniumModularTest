package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomConditions {

    public static ExpectedCondition<List<WebElement>> textsOf(final By locator, final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private List<String> currentTexts;
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                currentTexts = new ArrayList<String>();
                for (int i = 0; i < elements.size(); ++i) {
                    currentTexts.add(i, elements.get(i).getText());
                }
                if (currentTexts.size() != texts.length) {
                    return null;
                } else {
                    for (int i = 0; i < texts.length; ++i) {
                        if (!currentTexts.get(i).contains(texts[i])) {
                            return null;
                        }
                    }
                    return elements;
                }
            }

            public String toString() {
                return String.format("\ntext of list: \n should be: %s\n while actual text is: %s\n", Arrays.toString(texts), Arrays.toString(currentTexts.toArray()));
            }
        };
    }

    public static ExpectedCondition<WebElement> listNthElementHasText(final By locator,
                                                                      final int index, final String text) {
        return new ExpectedCondition<WebElement>() {
            private String currentText;
            private List<WebElement> elements;

            public WebElement apply(WebDriver webDriver) {
                try {
                    elements = webDriver.findElements(locator);
                    WebElement element = elements.get(index);
                    currentText = element.getText();
                    return (currentText.contains(text)) ? element : null;
                } catch (IndexOutOfBoundsException ex) {
                    return null;
                }
            }

            public String toString() {
                return String.format("\ntext of element should be: %s\n while actual text is: %s\n", text, currentText);

            }
        };
    }

    public static ExpectedCondition<List<WebElement>> sizeOf(final By elementsLocator, final int expectedSize) {
        return new ExpectedCondition<List<WebElement>>() {
            private int listSize;
            private List<WebElement> results;

            public List<WebElement> apply(WebDriver webDriver) {
                results = webDriver.findElements(elementsLocator);
                listSize = results.size();
                return (listSize == expectedSize) ? results : null;
            }

            public String toString() {
                return String.format("\nsize of list: %s\n should be: %s\n while actual size is: %s\n", results, expectedSize, listSize);

            }
        };
    }

    public static ExpectedCondition<List<WebElement>> minimumSizeOf(final By elementsLocator, final int minimumSize) {
        return new ExpectedCondition<List<WebElement>>() {
            private int listSize;
            private List<WebElement> results;

            public List<WebElement> apply(WebDriver webDriver) {
                results = webDriver.findElements(elementsLocator);
                listSize = results.size();
                return (listSize >= minimumSize) ? results : null;
            }

            public String toString() {
                return String.format("\nsize of list: %s\n minimum size should be: %s\n while actual size is: %s\n", results, minimumSize, listSize);

            }
        };
    }
}
