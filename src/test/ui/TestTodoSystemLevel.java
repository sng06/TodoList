package ui;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTodoSystemLevel {

    private TodoSystemLevel testTodoSystemLevel;


    @BeforeEach
    public void setup() {
        testTodoSystemLevel = new TodoList("general todoList");
    }

    @Test
    public void testGetName() {
        assertEquals("general todoList", testTodoSystemLevel.getName());
    }
}
