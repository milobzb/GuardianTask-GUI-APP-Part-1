import org.junit.jupiter.api.Test;
import ucf.assignments.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void testCreateTask() {
        Task task = new Task("Description", "2021-12-11");
        assertEquals("Description", task.getDescription());
        assertEquals("2021-12-11", task.getDueDate());
    }
}
