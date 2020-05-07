package model;


import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

import exceptions.ListAlreadyExistException;


public class TodoList extends TodoSystemLevel implements Serializable {
    private List<TodoSystemLevel> todoSystemLevel;


    // EFFECTS: constructs a TodoList
    public TodoList(String name) {
        super(name);
        this.todoSystemLevel = new ArrayList<>();
        //addObserver(new StatusChecker());
    }

    // MODIFIES: this, todos (parameter)
    // EFFECTS: add new todolist/ todoitem and set which list the item belonged to if
    // it is the instance of todoitem; search for the  list recursively
    // if it is not default list then add
    public void addNewTodos(TodoSystemLevel todos, String currentList) {
        if (currentList.equalsIgnoreCase("default list")) {
            todoSystemLevel.add(todos);
            if (todos instanceof TodoItem) {
                ((TodoItem) todos).setListWhichItemBelongedTo(this);
            }
        } else {
            TodoList list = searchList(currentList);
            list.addNewTodos(todos, "default list");
        }
    }

    // EFFECTS: create new regular or urgent item and return the item
    public TodoSystemLevel createTodoItem(String isUrgent, String itemName, String itemDueDate,
                                          String itemDescription) { //throws ItemAlreadyExistException {
        if (isUrgent.equalsIgnoreCase("Y")) {
            TodoSystemLevel urgentItemTemp = new UrgentItem(itemName, itemDueDate,
                    itemDescription);
            return urgentItemTemp;
        } else {
            TodoSystemLevel regularItemTemp = new RegularItem(itemName, itemDueDate,
                    itemDescription);
            return regularItemTemp;
        }
    }

    // EFFECTS: check if the sublist already exists, if so throw exception;
    // else create the sublist and return the list
    public TodoSystemLevel createTodoSubList(String subListName) throws ListAlreadyExistException {
        TodoList match = searchList(subListName);
        if (match != null && match.getName().equalsIgnoreCase(subListName)) {
            throw new ListAlreadyExistException();
        } else {
            TodoSystemLevel subTodoListTemp = new TodoList(subListName);
            return subTodoListTemp;
        }
    }

    // EFFECTS: if it is an instance of todolist, check if it contains the sublist recursively
    // and return it
    public TodoList searchList(String stringParent) {
        for (TodoSystemLevel i : todoSystemLevel) {
            if (i instanceof TodoList) {
                if (i.getName().equalsIgnoreCase(stringParent)) {
                    return (TodoList) i;
                } else {
                    TodoList sub = ((TodoList) i).searchList(stringParent);
                    if (sub != null) {
                        return sub;
                    }
                }
            }
        }
        return null;
    }

    // EFFECTS: appends all of the elements in the collection recursively and return the list
    @Override
    public List<String[]> generateData() {
        List<String[]> ret = new ArrayList<>();
        for (TodoSystemLevel sl : todoSystemLevel) {
            ret.addAll(sl.generateData());
        }
        return ret;
    }

    // MODIFIES: this
    // EFFECTS: edit the todo item if it is in the default list,
    //  else, search for the list recursively then edit the todo item
    public void editTodoItem(String parentList, int itemNum, int column, String updatedValue) {
        if (parentList.equalsIgnoreCase("default list")) {
            //int index = itemNum - 1;
            if (column == 0) {
                ((TodoItem) todoSystemLevel.get(itemNum)).setTaskName(updatedValue);
            } else if (column == 1) {
                ((TodoItem) todoSystemLevel.get(itemNum)).setTaskDescription(updatedValue);
            } else if (column == 3) {
                ((TodoItem) todoSystemLevel.get(itemNum)).setStatus(updatedValue);
            } else if (column == 4) {
                ((TodoItem) todoSystemLevel.get(itemNum)).setDueDate(updatedValue);
            }
        } else {
            TodoList foundList = searchList(parentList);
            foundList.editTodoItem("default list", itemNum, column, updatedValue);
        }
    }


    // MODIFIES: this
    // EFFECTS: remove the todo item if it is in the default list,
    //  else, search for the list recursively then remove the todo item
    public void removeSpecificTodoItem(String parentList, int itemNum) { //throws ItemNotOnListException {
        if (parentList.equalsIgnoreCase("default list")) {
            todoSystemLevel.remove(itemNum);
        } else {
            TodoList foundList = searchList(parentList);
            foundList.removeSpecificTodoItem("default list", itemNum);
        }
    }

    // MODIFIES: this
    // EFFECTS: remove the todo item when the item is completed
    public void removeTodoItemWhenCompleted(TodoItem todoItem) {
        for (TodoSystemLevel i : todoSystemLevel) {
            if (i.equals(todoItem)) {
                todoSystemLevel.remove(i);
                break;
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: remove all items from the the default list,
    //  else, search for the list recursively then remove all the todo item
    public void clearTodoList(String parentList) {
        if (parentList.equalsIgnoreCase("default list")) {
            todoSystemLevel.clear();
        } else {
            TodoList foundList = searchList(parentList);
            foundList.clearTodoList("default list");
        }
    }

    // EFFECTS: return todoSystemLevel
    public List<TodoSystemLevel> getTodoSystemLevelList() {
        return todoSystemLevel;
    }

    // EFFECTS: return todoSystemLevel size
    public int getTodoSystemLevelSize() {
        return todoSystemLevel.size();
    }


    //     EFFECTS: print out all items in the todoList
//    public void printTodoList(String parentList) {
//        if (parentList.equalsIgnoreCase("default list")) {
//            display("");
//        } else {
//            TodoList foundList = searchParentList(parentList);
//            foundList.printTodoList("default list");
//        }
//    }

//    public void display(String indentLevel) {
//        System.out.println(indentLevel + "List: " + getName());
//        for (TodoSystemLevel child : todoSystemLevel) {
//            child.display(indentLevel + indentLevel);
//        }
//    }


//    public void load(String file) throws IOException, ClassNotFoundException {
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            ObjectInputStream restore = new ObjectInputStream(fis);
//            todoSystemLevel = (ArrayList) restore.readObject();
//            restore.close();
//        } catch (FileNotFoundException exec) {
//            exec.printStackTrace();
//        }
//    }
//
//
//    public void save(String file) throws IOException {
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            ObjectOutputStream save = new ObjectOutputStream(fos);
//            save.writeObject(todoSystemLevel);
//            save.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}





