/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Rectangle;

/**
 *
 * @author ASUS TUF
 */
public class EventRect extends Rectangle{
    
    private int eventRectDefaultX,eventRectDefaultY;
    boolean eventDone = false;

    public int getEventRectDefaultX() {
        return eventRectDefaultX;
    }

    public void setEventRectDefaultX(int eventRectDefaultX) {
        this.eventRectDefaultX = eventRectDefaultX;
    }

    public int getEventRectDefaultY() {
        return eventRectDefaultY;
    }

    public void setEventRectDefaultY(int eventRectDefaultY) {
        this.eventRectDefaultY = eventRectDefaultY;
    }

    public boolean isEventDone() {
        return eventDone;
    }

    public void setEventDone(boolean eventDone) {
        this.eventDone = eventDone;
    }
    
    
    
}
