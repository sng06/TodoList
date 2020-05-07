//package model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class Subject {
//
//    private List<Observer> observer;
//
//    public Subject() {
//        observer = new ArrayList<>();
//    }
//
//    public void addObserver(Observer o) {
//        observer.add(o);
//    }
//
//    public void notify(TodoSystemLevel todoItem, String listName) {
//        for (Observer i : observer) {
//            i.update(todoItem, listName);
//        }
//    }
//}
