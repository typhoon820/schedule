package com.university.schedule.observers;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    List<Observer> observers = new ArrayList<>();
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
