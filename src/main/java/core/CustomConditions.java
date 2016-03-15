package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Arrays;
import java.util.List;

import static core.ConciseAPI.*;

public class
CustomConditions {

    public static ExpectedCondition<WebElement> listElementWithCssClass(final By elementsLocator, final String cssClass) {
        return new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;
            private WebElement element;
            private int i;

            public WebElement apply(WebDriver webDriver) {
                elements = webDriver.findElements(elementsLocator);
                element = elements.get(i);
                for (i = 0; i < elements.size(); ++i) {
                    if (!elements.get(i).getAttribute("class").contains(cssClass)) {
                        return null;
                    }
                    return element;
                }
                return element;
            }

            public String toString() {
                return String.format("\nelement with cssClass is: %s\n", element);

            }

        };
    }

    public static ExpectedCondition<Boolean> hidden(final String cssSelector) {
        return new ExpectedCondition<Boolean>() {
            private WebElement element;

            public Boolean apply(WebDriver webDriver) {
                element = webDriver.findElement(By.cssSelector(cssSelector));
                return (!element.isDisplayed()) ? true : false;
            }

            public String toString() {
                return String.format("\n element: \n should be: hidden\n while current state is: %s\n", !element.isDisplayed());
            }
        };
    }

    public static ExpectedCondition<List<WebElement>> empty(final List<WebElement> elements) {
        return sizeOf(elements, 0);
    }

    public static ExpectedCondition<List<WebElement>> empty(final By locator) {
        return sizeOf(locator, 0);
    }

    public static ExpectedCondition<List<WebElement>> listOfVisibleElementsIsEmpty(final List<WebElement> elements) {
        return sizeOfVisible(elements, 0);
    }

    public static ExpectedCondition<List<WebElement>> listOfVisibleElementsIsEmpty(final By locator) {
        return sizeOfVisible(locator, 0);
    }

    public static ExpectedCondition<WebElement> listElementWithText(final By locator,
                                                                    final String text) {
        return new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;

            public WebElement apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.getText().equals(text)) {
                        return element;
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("\ntext of element should be: %s\n", text);

            }
        };
    }

    public static ExpectedCondition<WebElement> listElementWithText(final List<WebElement> elements,
                                                                    final String text) {
        return new ExpectedCondition<WebElement>() {

            public WebElement apply(WebDriver webDriver) {
                for (WebElement element : elements) {
                    if (element.getText().equals(text)) {
                        return element;
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("\ntext of element should be: %s\n", text);

            }
        };
    }

    public static ExpectedCondition<WebElement> elementHasText(final String cssSelector,
                                                               final String text) {
        return new ExpectedCondition<WebElement>() {
            private String currentText;
            private WebElement element;

            public WebElement apply(WebDriver webDriver) {
                try {
                    element = webDriver.findElement(byCSS(cssSelector));
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

    public static ExpectedCondition<List<WebElement>> textsOf(final List<WebElement> elements, final String...
            texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private List<String> currentTexts;

            public List<WebElement> apply(WebDriver webDriver) {
                currentTexts = textsOfElements(elements);
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

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final By locator,
                                                                     final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private List<String> currentTexts;
            private List<WebElement> visibleElements;
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                visibleElements = listOfVisibleElements(elements);
                currentTexts = textsOfElements(visibleElements);
                if (currentTexts.size() != texts.length) {
                    return null;
                } else {
                    for (int i = 0; i < texts.length; ++i) {
                        if (!currentTexts.get(i).contains(texts[i])) {
                            return null;
                        }
                    }
                    return visibleElements;
                }
            }

            public String toString() {
                return String.format("\ntexts of visible elements list: \n should be: %s\n while actual texts are: %s\n", Arrays.toString(texts), Arrays.toString(currentTexts.toArray()));
            }
        };
    }

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final List<WebElement> elements,
                                                                     final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private List<String> currentTexts;
            private List<WebElement> visibleElements;

            public List<WebElement> apply(WebDriver webDriver) {
                visibleElements = listOfVisibleElements(elements);
                currentTexts = textsOfElements(elements);
                if (currentTexts.size() != texts.length) {
                    return null;
                } else {
                    for (int i = 0; i < texts.length; ++i) {
                        if (!currentTexts.get(i).contains(texts[i])) {
                            return null;
                        }
                    }
                    return visibleElements;
                }
            }

            public String toString() {
                return String.format("\ntexts of visible elements list: \n should be: %s\n while actual texts are: %s\n", Arrays.toString(texts), Arrays.toString(currentTexts.toArray()));
            }
        };
    }

    public static ExpectedCondition<List<WebElement>> textsOf(final By locator, final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private List<String> currentTexts;
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                currentTexts = textsOfElements(elements);
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

    public static ExpectedCondition<List<WebElement>> sizeOf(final List<WebElement> results, final int expectedSize) {
        return new ExpectedCondition<List<WebElement>>() {
            private int listSize;

            public List<WebElement> apply(WebDriver webDriver) {
                listSize = results.size();
                return (listSize == expectedSize) ? results : null;
            }

            public String toString() {
                return String.format("\nsize of list: %s\n should be: %s\n while actual size is: %s\n", results, expectedSize, listSize);

            }
        };
    }

    public static ExpectedCondition<List<WebElement>> sizeOfVisible(final List<WebElement> results, final int expectedSize) {
        return new ExpectedCondition<List<WebElement>>() {
            private int listSize;
            private List<WebElement> visibleElements;

            public List<WebElement> apply(WebDriver webDriver) {
                visibleElements = listOfVisibleElements(results);
                listSize = visibleElements.size();
                return (listSize == expectedSize) ? results : null;
            }

            public String toString() {
                return String.format("\nsize of list visible elements: %s\n should be: %s\n while actual size is: %s\n", visibleElements, expectedSize, listSize);

            }
        };
    }

    public static ExpectedCondition<List<WebElement>> sizeOfVisible(final By locator, final int expectedSize) {
        return new ExpectedCondition<List<WebElement>>() {
            private int listSize;
            private List<WebElement> visibleElements;
            private List<WebElement> results;

            public List<WebElement> apply(WebDriver webDriver) {
                results=webDriver.findElements(locator);
                visibleElements = listOfVisibleElements(results);
                listSize = visibleElements.size();
                return (listSize == expectedSize) ? results : null;
            }

            public String toString() {
                return String.format("\nsize of list visible elements: %s\n should be: %s\n while actual size is: %s\n", visibleElements, expectedSize, listSize);

            }
        };
    }

    public static ExpectedCondition<List<WebElement>> minimumSizeOf(final By elementsLocator,
                                                                    final int minimumSize) {
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

