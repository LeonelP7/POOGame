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
public class OBJ_Axe extends Entity{
    
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        type = type_axe;
        name = "Hacha de leñador";
        down1 = setUp("/objects/axe", gp.getTileSize(), gp.getTileSize());
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "["+name+"]"+"\nUn aShAa";
    }
    
}
