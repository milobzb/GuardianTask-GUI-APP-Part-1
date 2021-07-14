import org.junit.jupiter.api.Test;
import ucf.assignments.Task;
import ucf.assignments.TodoList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoListTest {

    @Test
    public void testToDoList() {
        TodoList list = new TodoList("Title");
        assertEquals("Title", list.getTitle());
        assertEquals(0, list.getTasks().size());
    }

    @Test
    public void testAddTask() {
        TodoList list = new TodoList("Title");
        Task task = new Task("Description", "2021-12-11");
        list.addTask(task);
        assertEquals(1, list.getTasks().size());
        assertEquals(task, list.getTasks().get(0));
    }

    @Test
    public void testEditTitle() {
        TodoList list = new TodoList("Title");
        list.setTitle("Title2");
        assertEquals("Title2", list.getTitle());
    }

}
