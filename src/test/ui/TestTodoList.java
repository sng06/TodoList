package ui;


import exceptions.ListAlreadyExistException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTodoList {
    private TodoList testTodoList;
    private TodoSystemLevel testTodoItem1;
    private TodoSystemLevel testTodoItem2;
    private TodoSystemLevel testTodoItem3;
    private TodoSystemLevel testTodoSubList1;
    private TodoSystemLevel testTodoSubList2;

    @BeforeEach
    public void setup() {
        testTodoList = new TodoList("test todoList");
        testTodoItem1 = new UrgentItem("complete assignment", "11/11/2019", "");
        testTodoItem2 = new RegularItem("buy groceries", "12/12/2019", "");
        testTodoItem3 = new RegularItem("ski", "12/12/2019", "");
        testTodoSubList1 = new TodoList("todo subList1");
        testTodoSubList2 = new TodoList("todo subList2");
    }

    @Test
    public void testCreateTodoItem() {
        TodoSystemLevel todoItem1 = testTodoList.createTodoItem("Y", "study", "12/12/2019", "");
        TodoSystemLevel todoItem2 = testTodoList.createTodoItem("N", "do laundry", "16/12/2019", "");
        testTodoList.addNewTodos(todoItem1, "default list");
        testTodoList.addNewTodos(todoItem2, "default list");
        assertEquals(2, testTodoList.getTodoSystemLevelSize());
        assertEquals("Urgent", ((TodoItem) testTodoList.getTodoSystemLevelList().get(0)).getPriorityLevel());
        assertEquals("Regular", ((TodoItem) testTodoList.getTodoSystemLevelList().get(1)).getPriorityLevel());
    }

    @Test
    public void testCreateTodoSubList() throws ListAlreadyExistException {
        TodoSystemLevel testSubList1 = testTodoList.createTodoSubList("school list");
        testTodoList.addNewTodos(testSubList1, "default list");
        assertEquals(1, testTodoList.getTodoSystemLevelSize());
    }


    @Test
    public void testAddNewTodos() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoSubList1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "todo subList1");
        testTodoList.addNewTodos(testTodoSubList2, "todo subList1");
        testTodoList.addNewTodos(testTodoItem3, "todo subList2");
        assertEquals(2, testTodoList.getTodoSystemLevelSize());
        assertEquals(2, ((TodoList) testTodoSubList1).getTodoSystemLevelSize());
        assertEquals(1, ((TodoList) testTodoSubList2).getTodoSystemLevelSize());
    }

    @Test
    public void testSearch() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoSubList1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "todo subList1");
        testTodoList.addNewTodos(testTodoSubList2, "todo subList1");
        testTodoList.addNewTodos(testTodoItem3, "todo subList2");

        TodoSystemLevel subList1 = testTodoList.searchList("todo subList1");
        assertTrue(testTodoSubList1.equals(subList1));
        TodoSystemLevel subList2 = testTodoList.searchList("todo subList2");
        assertTrue(testTodoSubList2.equals(subList2));
        assertNull(testTodoList.searchList("todo subList3"));
    }

    @Test
    public void testGenerateData() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoSubList1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "todo subList1");
        testTodoList.addNewTodos(testTodoSubList2, "todo subList1");
        testTodoList.addNewTodos(testTodoItem3, "todo subList2");

        List<String[]> data = testTodoList.generateData();
        assertEquals(3, data.size());
    }

    @Test
    public void testEditTodoItemDefaultList() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoSubList1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "todo subList1");

        assertEquals(2, testTodoList.getTodoSystemLevelSize());
        testTodoList.editTodoItem("default list", 0, 0, "Buy cereal");
        assertEquals("Buy cereal", ((TodoItem) testTodoList.getTodoSystemLevelList().get(0)).getTaskName());
        testTodoList.editTodoItem("default list", 0, 4, "15/12/2019");
        assertEquals("15/12/2019", ((TodoItem) testTodoList.getTodoSystemLevelList().get(0)).getDueDate());
        testTodoList.editTodoItem("default list", 0, 1, "cpsc210");
        assertEquals("cpsc210", ((TodoItem) testTodoList.getTodoSystemLevelList().get(0)).getTaskDescription());
        testTodoList.editTodoItem("default list", 0, 3, "complete");
        assertEquals(1, testTodoList.getTodoSystemLevelSize());
    }

    @Test
    public void testEditTodoItemSubList() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoSubList1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "todo subList1");
        testTodoList.addNewTodos(testTodoSubList2, "todo subList1");
        testTodoList.addNewTodos(testTodoItem3, "todo subList2");

        assertEquals(2, testTodoList.getTodoSystemLevelSize());
        testTodoList.editTodoItem("todo subList1", 0, 0, "interview");
        assertEquals("interview", ((TodoItem) ((TodoList) testTodoSubList1).getTodoSystemLevelList().get(0)).getTaskName());
        testTodoList.editTodoItem("todo subList1", 0, 4, "13/12/2019");
        assertEquals("13/12/2019", ((TodoItem) ((TodoList) testTodoSubList1).getTodoSystemLevelList().get(0)).getDueDate());
        testTodoList.editTodoItem("todo subList1", 0, 1, "coop");
        assertEquals("coop", ((TodoItem) ((TodoList) testTodoSubList1).getTodoSystemLevelList().get(0)).getTaskDescription());
        testTodoList.editTodoItem("todo subList1", 0, 3, "Complete");
        assertEquals(1, ((TodoList) testTodoSubList1).getTodoSystemLevelSize());
    }


    @Test
    public void testRemoveSpecificItemDefaultList() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoSubList1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "todo subList1");
        testTodoList.addNewTodos(testTodoSubList2, "todo subList1");
        testTodoList.addNewTodos(testTodoItem3, "todo subList2");
        assertEquals(2, testTodoList.getTodoSystemLevelSize());

        testTodoList.removeSpecificTodoItem("default list", 1);
        assertEquals(1, testTodoList.getTodoSystemLevelSize());
    }

    @Test
    public void testRemoveSpecificItemSubList() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoSubList1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "todo subList1");
        testTodoList.addNewTodos(testTodoSubList2, "todo subList1");
        testTodoList.addNewTodos(testTodoItem3, "todo subList2");
        assertEquals(2, testTodoList.getTodoSystemLevelSize());
        assertEquals(2, ((TodoList) testTodoSubList1).getTodoSystemLevelSize());
        assertEquals(1, ((TodoList) testTodoSubList2).getTodoSystemLevelSize());

        testTodoList.removeSpecificTodoItem("todo subList1", 1);
        assertEquals(2, testTodoList.getTodoSystemLevelSize());
        assertEquals(1, ((TodoList) testTodoSubList1).getTodoSystemLevelSize());
        assertNull(testTodoList.searchList("todo subList2"));

        testTodoList.removeSpecificTodoItem("default list", 1);
        assertEquals(1, testTodoList.getTodoSystemLevelSize());
        assertNull(testTodoList.searchList("todo subList1"));

    }

    @Test
    public void testClearSpecificList() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoSubList1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "todo subList1");
        testTodoList.addNewTodos(testTodoSubList2, "todo subList1");
        testTodoList.addNewTodos(testTodoItem3, "todo subList2");
        assertEquals(2, testTodoList.getTodoSystemLevelSize());
        assertEquals(2, ((TodoList) testTodoSubList1).getTodoSystemLevelSize());
        assertEquals(1, ((TodoList) testTodoSubList2).getTodoSystemLevelSize());


        testTodoList.clearTodoList("todo subList2");
        assertEquals(0, ((TodoList) testTodoSubList2).getTodoSystemLevelSize());
        testTodoList.clearTodoList("todo subList1");
        assertEquals(0, ((TodoList) testTodoSubList1).getTodoSystemLevelSize());
        testTodoList.clearTodoList("default list");
        assertEquals(0, testTodoList.getTodoSystemLevelSize());
    }

    @Test
    public void testRemoveTodoItemWhenCompleted() {
        testTodoList.addNewTodos(testTodoItem1, "default list");
        testTodoList.addNewTodos(testTodoItem2, "default list");
        testTodoList.removeTodoItemWhenCompleted((TodoItem) testTodoItem1);
        assertEquals(1, testTodoList.getTodoSystemLevelSize());
    }


}
