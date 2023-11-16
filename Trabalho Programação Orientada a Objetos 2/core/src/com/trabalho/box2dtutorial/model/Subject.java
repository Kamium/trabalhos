package com.trabalho.box2dtutorial.model;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
    void attachLvl(Observer observer);
    void detachLvl(Observer observer);
    void notifyLvlObservers();
}