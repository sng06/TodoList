//package ui;
//
////import exceptions.ItemAlreadyExistException;
////import exceptions.TooManyThingsUndoneException;
//import model.RegularItem;
//import model.TodoItem;
//import model.UrgentItem;
//import model.TodoList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.io.IOException;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TestSavable {
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
//    public void testSavable() throws IOException, ClassNotFoundException { //TooManyThingsUndoneException, ItemAlreadyExistException {
//        testTodoList.addNewTodos(testTodoItem1,"default");
//        testTodoList.addNewTodos(testTodoItem2,"default");
//        testTodoList.save("testFile.ser");
//        testTodoList.load("testFile.ser");
//        assertEquals("study", ( (TodoItem) testTodoList.getTodoSystemLevelList().get(0)).getTaskName());
//        assertEquals("buy milk", ( (TodoItem) testTodoList.getTodoSystemLevelList().get(1)).getTaskName());
//        assertEquals(2, testTodoList.getTodoSystemLevelSize());
//    }
//
//}

