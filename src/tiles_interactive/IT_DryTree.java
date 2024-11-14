/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiles_interactive;

import entity.Entity;
import java.awt.Color;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class IT_DryTree extends InteractiveTile{
    
    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);

        down1 = setUp("/tiles_interactive/drytree", gp.getTileSize(), gp.getTileSize());
        destructible = true;
    }

    @Override
    public InteractiveTile getDestroyedFrom() {
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.getTileSize(), worldY/gp.getTileSize());
        return tile;
    }

    @Override
    public void playSE() {
        gp.playSE(9);
    }
    
    

    @Override
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrect = false;
        if(entity.getCurrentWeapon().getType() == type_axe){
            isCorrect = true;
        }
        
        return isCorrect;
    }
    
    @Override
    public Color getParticleColor(){
        Color color = new Color(65,50,30);
        return color;
    }
    
    @Override
    public int getParticleSize(){
        int size = 6; //6 pixeles
        return size;
    }
    
    @Override
    public int getParticleSpeed(){
        int particleSpeed = 1;
        return particleSpeed;
    }
    
    @Override
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
    
    
    
    
}
