/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Rectangle;
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
    
    protected int spriteCounter;
    protected int spriteNum;
    
    protected Rectangle solidArea;
    protected boolean collisionOn;

    public Entity() {
        
        spriteCounter = 0;
        spriteNum = 1;
        collisionOn = false;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
     
    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int x) {
        this.worldX = x;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int y) {
        this.worldY = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
    
    
    
}
