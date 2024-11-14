/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class OBJ_PotionRed extends Entity{
    
    public OBJ_PotionRed(GamePanel gp, int col, int row) {
        super(gp, col, row);
        type = type_consumable;
        value = 5;
        name = "Cafe aguila roja";
        down1 = setUp("/objects/potion_red", gp.getTileSize(), gp.getTileSize());
        description = "["+name+"]"+"\nTomémonos un tinto...\nSana 2.5 corazones.";
    }
    
    @Override
    public void use(Entity entity){
        gp.setGameState(gp.getDialogueState());
        gp.getUi().setCurrentDialogue("Bebes " + name + "!\nTu vida se ha recuperado.");
        entity.setLife(entity.getLife()+value);
        gp.playSE(2);
    }
}
