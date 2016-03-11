package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import static core.ConciseAPI.*;
import static core.CustomConditions.*;
import static core.Helpers.hover;

public class Base {

    public static By tasks = byCSS("#todo-list li");

    @Step
    public static void add(String... taskTexts) {
        for (String text : taskTexts) {
            $(byCSS("#new-todo")).sendKeys(text + Keys.ENTER);
        }
    }

    @Step
    public static void assertItemsLeft(int number) {
       assertThat(elementHasText("#todo-count>strong", Integer.toString(number)));
    }

    @Step
    public static void сlearCompleted() {
        $(byCSS("#clear-completed")).click();
    }

    @Step
    public static void toggle(String taskText) {
        $(listElementWithText(tasks, taskText), ".toggle").click();
    }

    @Step
    public static void toggleAll() {
        $(byCSS(("#toggle-all"))).click();
    }

    @Step
    public static WebElement startEditing(String oldText, String newText) {
        //я не понимаю почему у меня не работает моя версия doubleclick, если оно выходит одинаково:
        //doubleClick($(listElementWithText(tasks, oldText), "label"))жж
        //просто не даблкликается
        actions.doubleClick($(listElementWithText(tasks, oldText), "label")).perform();
        findElementByCSS(".editing", ".edit").clear();
        findElementByCSS(".editing", ".edit").sendKeys(newText);
        return findElementByCSS(".editing", ".edit");
    }

    @Step
    public static void filterAll() {
        $(byCSS("[href='#/']")).click();
    }

    @Step
    public static void filterActive() {
        $(byCSS("[href='#/active']")).click();
    }

    @Step
    public static void filterCompleted() {
        $(byCSS("[href='#/completed']")).click();
    }

    @Step
    public static void delete(String taskText) {
        hover($(listElementWithText(tasks, taskText), ".destroy")).click();
    }

    @Step
    public static void assertTasks(String... taskTexts) {
        assertThat(textsOf(tasks, taskTexts));
    }

    @Step
    public static void assertTasksEmpty() {
        assertThat(listOfElementsIsEmpty(tasks));
    }

    @Step
    public static void assertVisibleTasks(String... taskTexts) {
        assertThat(visibleTextsOf(tasks, taskTexts));
    }

    @Step
    public static void assertEmptyVisibleTasks() {
        assertThat(listOfVisibleElementsIsEmpty(tasks));
    }
}
