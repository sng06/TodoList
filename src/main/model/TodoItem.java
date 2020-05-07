package model;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class TodoItem extends TodoSystemLevel implements Serializable {
    public String taskName;
    private String status;
    private String dueDate;
    protected String priorityLevel;
    private String taskDescription;
    private TodoList listWhichItemBelongedTo;


    // EFFECTS: construct todoItem with given parameters
    public TodoItem(String taskName, String dueDate, String taskDescription) {
        super(taskName);
        this.taskName = taskName;
        this.status = "Incomplete";
        this.dueDate = dueDate;
        this.priorityLevel = "";
        this.taskDescription = taskDescription;
    }

    // MODIFIES: this
    // EFFECTS: update the list that this item belonged to
    public void setListWhichItemBelongedTo(TodoList todoList) {
        this.listWhichItemBelongedTo = todoList;
    }

    public TodoList getListWhichItemBelongedTo() {
        return this.listWhichItemBelongedTo;
    }

    // MODIFIES: this
    // EFFECTS: set the todo item name
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    // MODIFIES: this
    // EFFECTS: set the todo item status;
    //  emove the item from the list it belonged to when the status is changed to complete
    public void setStatus(String status) {
        this.status = status;
        if (status.equalsIgnoreCase("Complete")) {
            listWhichItemBelongedTo.removeTodoItemWhenCompleted(this);
        }
    }

    public String getStatus() {
        return status;
    }

    // MODIFIES: this
    // EFFECTS: set the todo item due date
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }


    public String getPriorityLevel() {
        return priorityLevel;
    }

    // MODIFIES: this
    // EFFECTS: set the todo item detailed description
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    // EFFECTS: get the todo item fields and store them in a string array,
    //  add the result to array list and return the list
    @Override
    public List<String[]> generateData() {
        String[] ret = {taskName, taskDescription, priorityLevel, status, dueDate};
        List<String[]> var = new ArrayList<>();
        var.add(ret);
        return var;
    }



    //    public void display(String indentLevel) {
//        System.out.println(indentLevel + "Todo item: " + this.taskName
//                + "\n" + indentLevel + "Priority: " + this.priorityLevel
//                + "\n" + indentLevel + "Status: " + this.status
//                + "\n" + indentLevel + "Due Date: " + this.dueDate);
//        System.out.println();
//    }
}

