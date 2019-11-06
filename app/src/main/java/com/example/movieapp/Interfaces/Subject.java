package com.example.movieapp.Interfaces;

public interface Subject {
    public void register(Observer observer);

    public void notifyObservers();
}
