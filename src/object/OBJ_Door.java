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
public class OBJ_Door extends Entity{
    
    public OBJ_Door(GamePanel gp) {
            super(gp);
        setItemValues();
        
    }

    public OBJ_Door(GamePanel gp, int col, int row) {
        super(gp, col, row);
        setItemValues();
    }

    @Override
    public void setItemValues() {
        name = "door";
        down1= setUp("/objects/door",gp.getTileSize(),gp.getTileSize());
        type = type_obstacle;
        collision = true;
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact() {
        
        gp.setGameState(gp.getDialogueState());
        gp.getUi().setCurrentDialogue("Necesitas una llave.");
    }
    
    
    
    
}
