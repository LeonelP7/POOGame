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
public class OBJ_ShieldWood extends Entity{
    
    public OBJ_ShieldWood(GamePanel gp) {
        super(gp);
        type = type_shield;
        name = "Escudo de Carton";
        down1 = setUp("/objects/shield_wood", gp.getTileSize(), gp.getTileSize());
        defenseValue = 1;
        description = "[" + name + "]\n Un carton Duro :/.";
    }
    
}
