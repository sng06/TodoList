//package ui;
//
//import model.RegularItem;
//import model.TodoItem;
//import model.TodoList;
//import model.TodoListManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public abstract class TestItem {
//
//    TodoItem todoItem;
//    TodoList schoolTodoList;
//
////    @BeforeEach
////    public void setup() {
////        schoolTodoList = new TodoList("school todoList");
////    }
//
//
////    @Test
////    public void updateTodoItemTest() {
////        this.updateTodoItem("Study", "25/09/2019", "Y", "chapter 2");
////        assertEquals("Study", this.getTaskName());
////        assertTrue(this.getTaskName().equals("Study"));
////        assertEquals("25/09/2019", this.getDueDate());
////        assertEquals(1, this.getPriorityLevel());
////        assertEquals("chapter 2", this.getTaskDescription());
////        assertEquals("Incomplete", this.getStatus());
////        //assertEquals("home", todoItem.getTaskLocation());
////    }
////
////    @Test
////    public void testSetStatusIncomplete() {
////        todoItem.setStatus("Incomplete");
////        assertEquals("Incomplete", todoItem.getStatus());
////    }
////
////
////    @Test
////    public void testSetListWhichItemBelongedto() {
//////        todoItem.setListWhichItemBelongedTo(schoolTodoList);
//////        //schoolTodoList.addTodoItem("Y", "study midterm", "10/10/2019", "module 1-8");
//////        assertTrue(todoItem.getListWhichItemBelongedTo().equals(schoolTodoList));
////////        assertTrue(schoolTodoList.isContaintodoItemInArray(todoItem));
////////        assertTrue(schoolTodoList.getTodoItemFromMap(todoItem.getTaskName()).equals(todoItem));
//////        //todoItem.setStatus("Complete");
//////        //assertEquals("Incomplete", todoItem.getStatus());
//////    }
////    }
////
////
//////    @Test
//////    public void isCompletedTest() {
//////        assertFalse(todoItem.isCompleted("Incomplete"));
//////        assertFalse(todoItem.isCompleted("In progress"));
//////        assertTrue(todoItem.isCompleted("Completed"));
//////    }
////
////    @Test
////    public void isNotPassedDueDateTest() {
////        assertFalse(todoItem.isPassedDueDate());
////    }
////
////    @Test
////    public void isPassedDueDateTest() {
////        todoItem.setDueDate("25/09/2019");
////        assertTrue(todoItem.isPassedDueDate());
////    }
////
////    @Test
////    public void setTaskCreatedAtTest() {
////        assertEquals(LocalDateTime.now(), todoItem.getTaskCreatedAt());
////    }
//
//    }
