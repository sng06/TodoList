package model;

import java.io.Serializable;
import java.util.List;

public abstract class TodoSystemLevel implements Serializable {
    protected String name;

    // construct todosystemlevel with the given name
    public TodoSystemLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected abstract List<String[]> generateData();

    //public abstract void display(String indentLevel);
}


