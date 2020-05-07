//package ui;
//
//import model.RegularItem;
//import model.TodoItem;
//import model.TodoList;
//import model.UrgentItem;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.io.IOException;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TestLoadable {
//    TodoList testTodoList;
//    TodoItem testTodoItem1;
//    TodoItem testTodoItem2;
//
//
//    @BeforeEach
//    public void setup() {
//        testTodoList = new TodoList("testTodoList");
//        testTodoItem1 = new UrgentItem("study", "12/12/2019","");
//        testTodoItem2 = new RegularItem("buy milk", "12/12/2019","");
//    }
//
//    @Test
//    public void testLoadable() throws IOException, ClassNotFoundException {
//        testTodoList.load("testFile.ser");
//        assertEquals("study", ((TodoItem) testTodoList.getTodoSystemLevelList().get(0)).getTaskName());
//        assertEquals("buy milk", ((TodoItem) testTodoList.getTodoSystemLevelList().get(1)).getTaskName());
//        assertEquals(2, testTodoList.getTodoSystemLevelSize());
//    }
//
//}