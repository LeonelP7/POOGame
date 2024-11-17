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
public class OBJ_Lantern extends Entity{
    
    public OBJ_Lantern(GamePanel gp) {
        super(gp);
        setItemValues();
    }

    public OBJ_Lantern(GamePanel gp, int col, int row) {
        super(gp, col, row);
        setItemValues();
    }

    @Override
    public void setItemValues() {
        type = type_light;
        name = "Linterna";
        down1 = setUp("/objects/lantern", gp.getTileSize(), gp.getTileSize());
        description = "["+name+"]"+"\nIlumina sonso";
        price = 80;
        lightRadius = 250;
    }
    
    
}
