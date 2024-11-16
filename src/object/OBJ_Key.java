/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class OBJ_Key extends Entity{
    
    public OBJ_Key(GamePanel gp, int col, int row) {
        super(gp, col, row);
        setItemValues();
    }
    public OBJ_Key(GamePanel gp) {
        super(gp);
        setItemValues();
        
    }

    @Override
    public void setItemValues() {
        name = "Llave";
        type = type_consumable;
        down1 = setUp("/objects/key",gp.getTileSize(),gp.getTileSize());
        description = "[" + name + "]\n Pa´ abrir las \npuertas de tu corazon.";
        price = 10;
    }

    @Override
    public boolean use(Entity entity) {
        gp.setGameState(gp.getDialogueState());
        
        int objIndex = getDetected(entity, gp.getObj(), "door");
        
        if (objIndex != 999) {
            gp.getUi().setCurrentDialogue("Usas la " + name + " y abrea la puerta");
            gp.playSE(3);
            gp.getObj()[gp.getCurrentMap()][objIndex] = null;
            return true;
        }
        else{
            gp.getUi().setCurrentDialogue("Que haces viejo?");
        }
        return false;
    }
    
    
    
    
    
    
}
