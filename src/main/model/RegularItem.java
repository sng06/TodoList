package model;

public class RegularItem extends TodoItem {

    // EFFECTS: construct regular item with the given parameter and set priority level to regular
    public RegularItem(String taskName, String dueDate, String taskDescription) {
        super(taskName, dueDate, taskDescription);
        this.priorityLevel = "Regular";
    }

}
