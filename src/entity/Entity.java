/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.image.BufferedImage;

/**
 *
 * @author ASUS TUF
 */
public class Entity {
    
    protected int x;
    protected int y;
    protected int speed;
    
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected String direction;
    
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
}
