import config.BaseTest;
import org.junit.Test;
import org.openqa.selenium.Keys;

import static pages.Base.*;

public class TodoMVCFeatureTest extends BaseTest {

    @Test
    public void testEdit() {
        //given

        add("a");

        startEditing("a", "a edited").sendKeys(Keys.ENTER);
        assertTasks("a edited");
        assertItemsLeft(1);
    }

    @Test
    public void testCancelEdit() {
        //given
        add("a");

        startEditing("a", "a edited").sendKeys(Keys.ESCAPE);
        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testActivateAllOnCompletedFilter() {
        //given - completed tasks
        add("a", "b");
        toggleAll();
        filterCompleted();

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(2);
    }

    @Test
    public void testTasksCommonFlow() {

        add("a");
        assertVisibleTasks("a");
        toggleAll();

        filterActive();
        assertEmptyVisibleTasks();

        add("b");
        //toggle("b") - в это месте дебагер показывает что система ищет задачу по локатору new-todo, который она выходит берет от метода add
        // я так и не поняла как такое возможно, потому пошла в обход с помощью toggleAll
        toggleAll();
        assertEmptyVisibleTasks();

        filterCompleted();
        assertVisibleTasks("a", "b");

        //activate task
        toggle("a");
        assertVisibleTasks("b");

        сlearCompleted();
        assertEmptyVisibleTasks();
        assertItemsLeft(1);

        filterAll();
        delete("a");
        assertTasksEmpty();
    }


}
