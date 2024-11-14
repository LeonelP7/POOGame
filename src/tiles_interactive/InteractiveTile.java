/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiles_interactive;

import entity.Entity;
import java.awt.Graphics2D;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class InteractiveTile extends Entity{
    
    public boolean destructible = false;
    
    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.worldX = col*gp.getTileSize();
        this.worldY = row*gp.getTileSize();
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrect = false;
        return isCorrect;
    }
    
    public void playSE(){
        
    }
    
    public InteractiveTile getDestroyedFrom(){
        InteractiveTile tile = null;
        return tile;
    }
    
    @Override
    public void update() {
        
        //en caso de querer que un tile interactivo necesite mas golpes para romperse
        if(invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        if (worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX()
                && worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX()
                && worldY + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY()
                && worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {
            g2.drawImage(down1, screenX, screenY, null);
        }
    }
    
    
    
    public boolean isDestructible() {
        return destructible;
    }

    public void setDestructible(boolean destructible) {
        this.destructible = destructible;
    }
    
    
    
}
