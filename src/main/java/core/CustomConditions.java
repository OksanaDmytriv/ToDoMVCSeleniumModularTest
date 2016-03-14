package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.*;

import static core.ConciseAPI.$;
import static core.ConciseAPI.byCSS;

public class
CustomConditions {

    public static ExpectedCondition<WebElement> listElementWithCssClass(final By elementsLocator, final String cssClass) {
        return new ExpectedCondition<WebElement>() {
            //private WebElement element;
            private WebElement newElement;

            public WebElement apply(WebDriver webDriver) {
                //element = webDriver.findElement(elementsLocator);
                //newElement = webDriver.findElement(elementsLocator).findElement(byCSS(cssClass));
                //return newElement;
                newElement = $(elementsLocator, cssClass);
                return newElement;
            }

            public String toString() {
                return String.format("\nelement with cssClass is: %s\n", newElement);

            }

        };
    }

    public static ExpectedCondition<Boolean> elementsIsHidden(final String cssSelector) {
        return new ExpectedCondition<Boolean>() {
            private WebElement element;

            public Boolean apply(WebDriver webDriver) {
                element = $(byCSS(cssSelector));
                return (!element.isDisplayed()) ? true : false;
            }

            public String toString() {
                return String.format("\n element: \n should be: hidden\n while current state is: %s\n", !element.isDisplayed());
            }
        };
    }

    public static ExpectedCondition<Boolean> listOfElementsIsEmpty(final List<WebElement> elements) {
        return new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver webDriver) {
                return (elements.size() == 0) ? true : false;
            }

            public String toString() {
                return String.format("\nlist of elements: \n should be: empty\n while actual size are: %s\n", elements.size());
            }
        };
    }

    public static ExpectedCondition<Boolean> listOfElementsIsEmpty(final By locator) {
        return new ExpectedCondition<Boolean>() {
            private List<WebElement> elements;

            public Boolean apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                return (elements.size() == 0) ? true : false;
            }

            public String toString() {
                return String.format("\nlist of elements: \n should be: empty\n while actual size are: %s\n", elements.size());
            }
        };
    }

    public static ExpectedCondition<Boolean> listOfVisibleElementsIsEmpty(final List<WebElement> elements) {
        return new ExpectedCondition<Boolean>() {
            private List<WebElement> visibleElements;

            public Boolean apply(WebDriver webDriver) {
                visibleElements = new ArrayList<WebElement>();
                for (int i = 0; i < elements.size(); ++i) {
                    if (elements.get(i).isDisplayed()) {
                        visibleElements.add(i, elements.get(i));
                    }
                }
                return (visibleElements.size() == 0) ? true : false;
            }

            public String toString() {
                return String.format("\nlist of elements: \n should be: empty\n while actual size are: %s\n", visibleElements.size());
            }
        };
    }

    public static ExpectedCondition<Boolean> listOfVisibleElementsIsEmpty(final By locator) {
        return new ExpectedCondition<Boolean>() {
            private List<WebElement> visibleElements;
            private List<WebElement> elements;

            public Boolean apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                visibleElements = new ArrayList<WebElement>();
                for (int i = 0; i < elements.size(); ++i) {
                    if (elements.get(i).isDisplayed()) {
                        visibleElements.add(i, elements.get(i));
                    }
                }
                return (visibleElements.size() == 0) ? true : false;
            }

            public String toString() {
                return String.format("\nlist of elements: \n should be: empty\n while actual size are: %s\n", visibleElements.size());
            }
        };
    }

    public static ExpectedCondition<WebElement> listElementWithText(final By locator,
                                                                    final String text) {
        return new ExpectedCondition<WebElement>() {
            private String currentText;
            private WebElement element;
            private List<WebElement> elements;

            public WebElement apply(WebDriver webDriver) {
                try {
                    elements = webDriver.findElements(locator);
                    for (int i = 0; i < webDriver.findElements(locator).size(); ++i) {
                        element = elements.get(i);
                        currentText = element.getText();
                        if (currentText.contains(text)){
                            return element;
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    return null;
                }
                return element;
            }

            public String toString() {
                return String.format("\ntext of element should be: %s\n while actual text is: %s\n", text, currentText);

            }
        };
    }

    public static ExpectedCondition<WebElement> listElementWithText(final List<WebElement> elements,
                                                                    final String text) {
        return new ExpectedCondition<WebElement>() {
            private String currentText;
            private WebElement element;

            public WebElement apply(WebDriver webDriver) {
                try {
                    for (int i = 0; i < elements.size(); ++i) {
                        element = elements.get(i);
                        currentText = element.getText();
                        if (currentText.contains(text)){
                            return element;
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    return null;
                }
                return element;
            }

            public String toString() {
                return String.format("\ntext of element should be: %s\n while actual text is: %s\n", text, currentText);

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

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final By locator,
                                                                     final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private List<String> currentTexts;
            private List<WebElement> visibleElements;
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver webDriver) {
                elements = webDriver.findElements(locator);
                visibleElements = new ArrayList<WebElement>();
                for (int i = 0; i < elements.size(); ++i) {
                    if (elements.get(i).isDisplayed()) {
                        visibleElements.add(elements.get(i));
                    }
                }
                currentTexts = new ArrayList<String>();
                for (int i = 0; i < visibleElements.size(); ++i) {
                    currentTexts.add(i, visibleElements.get(i).getText());

                }
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
                visibleElements = new ArrayList<WebElement>();
                for (int i = 0; i < elements.size(); ++i) {
                    if (elements.get(i).isDisplayed()) {
                        visibleElements.add(i, elements.get(i));
                    }
                }
                currentTexts = new ArrayList<String>();
                for (int i = 0; i < visibleElements.size(); ++i) {
                    currentTexts.add(i, visibleElements.get(i).getText());

                }
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

