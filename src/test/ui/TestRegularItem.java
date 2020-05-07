package ui;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestRegularItem {
    protected TodoItem testTodoItem;
    private TodoList generalTodoList;

    @BeforeEach
    public void setup() {
        testTodoItem = new RegularItem("", "","");
        generalTodoList = new TodoList("general todoList");
    }

    @Test
    public void testTaskName() {
        testTodoItem.setTaskName("buy milk");
        assertEquals("buy milk", testTodoItem.getTaskName());
    }

    @Test
    public void testDueDate() {
        testTodoItem.setDueDate("12/12/2019");
        assertEquals("12/12/2019", testTodoItem.getDueDate());
    }

    @Test
    public void testDescription() {
        testTodoItem.setTaskDescription("skim milk");
        assertEquals("skim milk", testTodoItem.getTaskDescription());
    }

    @Test
    public void testPriorityLevel() {
        assertEquals("Regular", testTodoItem.getPriorityLevel());
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
        TodoItem testTodoItem1 = new RegularItem("buy milk", "12/12/2019", "");
        TodoItem testTodoItem2 = new RegularItem("do laundry", "12/12/2019", "");
        generalTodoList.addNewTodos(testTodoItem1,"default list");
        generalTodoList.addNewTodos(testTodoItem2,"default list");
        assertEquals(2, generalTodoList.getTodoSystemLevelSize());
        ((TodoItem) generalTodoList.getTodoSystemLevelList().get(0)).setStatus("Complete");
        assertEquals(1, generalTodoList.getTodoSystemLevelSize());
    }

//    @Test
//    public void testDisplay() {
//        testTodoItem.display("");
//    }

    @Test
    public void testGenerateData() {
        TodoItem testTodoItem1 = new RegularItem("buy milk", "12/12/2019", "");
        List<String[]> data = testTodoItem1.generateData();
        assertEquals(1, data.size());
        //assertTrue(data.contains(new String[]{"buy milk", "", "Regular", "Incomplete", "12/12/2019"}));
    }

}
