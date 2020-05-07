package model;


public class UrgentItem extends TodoItem {

    // EFFECTS: construct urgent item with the given parameter and set priority level to urgent
    public UrgentItem(String taskName, String dueDate, String taskDescription) {
        super(taskName, dueDate, taskDescription);
        this.priorityLevel = "Urgent";
    }

}
