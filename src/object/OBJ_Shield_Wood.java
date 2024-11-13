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
public class OBJ_Shield_Wood extends Entity{
    
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        name = "Wood Shield";
        down1 = setUp("/objects/shield_wood", gp.getTileSize(), gp.getTileSize());
        defenseValue = 1;
    }
    
}
