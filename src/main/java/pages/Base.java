package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static core.ConciseAPI.*;
import static core.CustomConditions.*;
import static core.Helpers.doubleClick;
import static core.Helpers.hover;

public class Base {

    @FindBy(css = "#todo-list li")
    static public
    List<WebElement> tasks;

    @FindBy(css = "#new-todo")
    static public
    WebElement newTask;


    //ElementsCollection tasks = $$("#todo-list li");

    //SelenideElement newTask = $("#new-todo");

    @Step
    public static void add(String... taskTexts) {
        for (String text : taskTexts) {
            $(byCSS("#new-todo")).sendKeys(text + Keys.ENTER);
            //newTask.setValue(text).pressEnter();
        }
    }

    @Step
    public static void assertItemsLeft(int number) {
       assertThat(elementHasText("#todo-count>strong", Integer.toString(number)));
        // $("#todo-count>strong").shouldHave(exactText(Integer.toString(number)));
    }

    @Step
    public static void сlearCompleted() {
        $(byCSS("#clear-completed")).click();
        //$("#clear-completed").click();
        assertThat(elementsIsHidden("#clear-completed"));
        //$("#clear-completed").shouldBe(hidden);
    }

    @Step
    public static void toggle(String taskText) {
        $(tasks, taskText, ".toggle").click();
        //tasks.find(exactText(taskText)).$(".toggle").click();
    }

    @Step
    public static void toggleAll() {
        $(byCSS(("#toggle-all"))).click();
        //$("#toggle-all").click();
    }

    @Step
    public static WebElement startEditing(String oldText, String newText) {
        WebElement element = doubleClick(assertThat(listElementWithText(tasks, oldText)));
        element.findElement(byCSS("editing")).findElement(byCSS(".edit")).sendKeys(newText);
        return element;
        //tasks.find(exactText(oldText)).doubleClick();
        //return tasks.$(byCSS("editing")).$(".edit").setValue(newText);
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
        hover($(tasks, taskText, ".destroy")).click();
        //tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    @Step
    public static void assertTasks(String... taskTexts) {
        assertThat(textsOf(tasks, taskTexts));
        //tasks.shouldHave(exactTexts(taskTexts));
    }

    @Step
    public static void assertTasksEmpty() {
        assertThat(listOfElementsIsEmpty(tasks));
        //tasks.shouldBe(empty);
    }

    @Step
    public static void assertVisibleTasks(String... taskTexts) {
        assertThat(visibleTextsOf(tasks, taskTexts));
        //tasks.filter(visible).shouldHave(exactTexts((taskTexts)));
    }

    @Step
    public static void assertEmptyVisibleTasks() {
        assertThat(listOfVisibleElementsIsEmpty(tasks));
        //tasks.filter(visible).shouldBe(empty);
    }
}
