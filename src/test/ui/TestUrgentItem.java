package ui;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUrgentItem {
    protected TodoItem testTodoItem;
    private TodoList generalTodoList;

    @BeforeEach
    public void setup() {
        testTodoItem = new UrgentItem("", "","");
        generalTodoList = new TodoList("general todoList");
    }

    @Test
    public void testTaskName() {
        testTodoItem.setTaskName("study for exam");
        assertEquals("study for exam", testTodoItem.getTaskName());
    }

    @Test
    public void testDueDate() {
        testTodoItem.setDueDate("12/12/2019");
        assertEquals("12/12/2019", testTodoItem.getDueDate());
    }

    @Test
    public void testDescription() {
        testTodoItem.setTaskDescription("chapter 10");
        assertEquals("chapter 10", testTodoItem.getTaskDescription());
    }

    @Test
    public void testPriorityLevel() {
        assertEquals("Urgent", testTodoItem.getPriorityLevel());
    }

    @Test
    public void testSetStatusIncomplete() {
        testTodoItem.setStatus("Incomplete");
        assertEquals("Incomplete",testTodoItem.getStatus());
        testTodoItem.setStatus("In progress");
        assertEquals("In progress",testTodoItem.getStatus());
    }

    @Test
    public void testSetListWhichItemBelongedto() {
        testTodoItem.setListWhichItemBelongedTo(generalTodoList);
        assertEquals(generalTodoList, testTodoItem.getListWhichItemBelongedTo());
    }

    @Test
    public void testSetStatusToComplete() {
        TodoItem testTodoItem1 = new UrgentItem("buy groceries", "12/12/2019", "");
        TodoItem testTodoItem2 = new UrgentItem("pay bills", "12/12/2019", "");
        generalTodoList.addNewTodos(testTodoItem1,"default list");
        generalTodoList.addNewTodos(testTodoItem2,"default list");
        assertEquals(2, generalTodoList.getTodoSystemLevelSize());
        ((TodoItem) generalTodoList.getTodoSystemLevelList().get(0)).setStatus("Complete");
        assertEquals(1, generalTodoList.getTodoSystemLevelSize());
    }

    @Test
    public void testGenerateData() {
        TodoItem testTodoItem1 = new UrgentItem("buy milk", "12/12/2019", "");
        List<String[]> data = testTodoItem1.generateData();
        assertEquals(1, data.size());
    }
//    @Test
//    public void testDisplay() {
//    testTodoItem.display("");
//    }

}