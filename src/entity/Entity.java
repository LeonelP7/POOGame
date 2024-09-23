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
    
    protected int worldX;
    protected int worldY;
    protected int speed;
    
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected String direction;
    
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    /**
     * @return the x
     */
    public int getWorldX() {
        return worldX;
    }

    /**
     * @param x the x to set
     */
    public void setWorldX(int x) {
        this.worldX = x;
    }

    /**
     * @return the y
     */
    public int getWorldY() {
        return worldY;
    }

    /**
     * @param y the y to set
     */
    public void setWorldY(int y) {
        this.worldY = y;
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
