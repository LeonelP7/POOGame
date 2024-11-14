/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class Particle extends Entity{
    
    private Entity generator;
    private Color color;
    int size;
    private int xd;
    private int yd;
    
    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed ,
            int maxLife, int xd, int yd) {
        super(gp);
        
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;
        
        life = maxLife;
        int offset = (gp.getTileSize()/2) - (size/2);
        worldX = generator.getWorldX() + offset;
        worldY = generator.getWorldY() + offset;
    }

    @Override
    public void update() {
        life--;
        
        if(life < maxLife/3){
            yd++;
        }
        
        worldX += xd*speed;
        worldY += yd*speed;
        
        if(life == 0){
            alive = false;
        }
        
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
        
        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }
    
    
    
}
