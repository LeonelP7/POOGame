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
public class OBJ_Potion_Red extends Entity{
    
    int value = 5;
    
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        type = type_consumable;
        name = "Cafe aguila roja";
        down1 = setUp("/objects/potion_red", gp.getTileSize(), gp.getTileSize());
        description = "["+name+"]"+"\nTomémonos un tinto, seamos amigos\nSana 2.5 corazones.";
    }
    
    @Override
    public void use(Entity entity){
        gp.setGameState(gp.getDialogueState());
        gp.getUi().setCurrentDialogue("Bebes " + name + "!\nTu vida se ha recuperado.");
        entity.setLife(entity.getLife()+value);
        if(entity.getLife() > entity.getMaxLife()){
            entity.setLife(entity.getMaxLife());
        }
        gp.playSE(2);
    }
}
