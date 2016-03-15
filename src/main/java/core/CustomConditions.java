package core;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Arrays;
import java.util.List;

import static core.ConciseAPI.*;

public class
CustomConditions {

    public static <V> ExpectedCondition<V> elementExceptionsCatcher(final Function<? super WebDriver, V> condition) {
        return new ExpectedCondition<V>() {
            public V apply(WebDriver input) {
                try {
                    return condition.apply(input);
                } catch (StaleElementReferenceException e) {
                    return null;
                } catch (ElementNotVisibleException e) {
                    return null;
                }
            }

            public String toString() {
                return condition.toString();
            }
        };
    }

    public static ExpectedCondition<WebElement> listElementWithCssClass(final By elementsLocator, final String cssClass) {
        return new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;


            public WebElement apply(WebDriver webDriver) {
                elements = webDriver.findElements(elementsLocator);
                for (WebElement element : elements) {
                    String[] classes = element.getAttribute("class").split(" ");
                    for (int i = 0; i < classes.length; i++) {
                        if (classes[i].equals(cssClass)) {
                            return element;
                        }
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("\nThere should be list of elements %s\n with %s\n cssClass\n", elements, cssClass);

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
                return getElementWithText(elements, text);
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
                return getElementWithText(elements, text);
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
        if (texts.length == 0) {
            throw new IllegalArgumentException("Array of expected texts is empty.");
        }
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {

            public List<WebElement> apply(WebDriver webDriver) {
                return compareTexts(elements, texts);
            }

            public String toString() {
                return String.format("\ntext of list: \n should be: %s\n", Arrays.toString(texts));
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> textsOf(final By locator, final String... texts) {
        if (texts.length == 0) {
            throw new IllegalArgumentException("Array of expected texts is empty.");
        }
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                return compareTexts(elements, texts);
            }

            public String toString() {
                return String.format("\ntext of list: \n should be: %s\n", Arrays.toString(texts));
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final By locator,
                                                                     final String... texts) {
        if (texts.length == 0) {
            throw new IllegalArgumentException("Array of expected texts is empty.");
        }
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<WebElement> visibleElements;
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                visibleElements = listOfVisibleElements(elements);
                return compareTexts(visibleElements, texts);
            }

            public String toString() {
                return String.format("\ntexts of visible elements list: \n should be: %s\n", Arrays.toString(texts));
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final List<WebElement> elements,
                                                                     final String... texts) {
        if (texts.length == 0) {
            throw new IllegalArgumentException("Array of expected texts is empty.");
        }
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<WebElement> visibleElements;

            public List<WebElement> apply(WebDriver webDriver) {
                visibleElements = listOfVisibleElements(elements);
                return compareTexts(visibleElements, texts);
            }

            public String toString() {
                return String.format("\ntexts of visible elements list: \n should be: %s\n", Arrays.toString(texts));
            }
        });
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
                results = webDriver.findElements(locator);
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
        if (minimumSize == 0) {
            throw new IllegalArgumentException("Array of element's list minimum size is 0.");
        }
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
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
        });
    }
}

