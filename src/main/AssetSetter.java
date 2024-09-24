/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

/**
 *
 * @author ASUS TUF
 */
public class AssetSetter {
    
    private GamePanel gp;

    public AssetSetter(GamePanel gp) {
        
        this.gp = gp;
    }
    
    public void setObject(){
        
        gp.getObj()[0] = new OBJ_Key();
        gp.getObj()[0].setWorldX(23*gp.getTileSize());
        gp.getObj()[0].setWorldY(7*gp.getTileSize());
        
        gp.getObj()[1] = new OBJ_Key();
        gp.getObj()[1].setWorldX(23*gp.getTileSize());
        gp.getObj()[1].setWorldY(40*gp.getTileSize());
        
        gp.getObj()[2] = new OBJ_Key();
        gp.getObj()[2].setWorldX(38*gp.getTileSize());
        gp.getObj()[2].setWorldY(8*gp.getTileSize());
        
        gp.getObj()[3] = new OBJ_Door();
        gp.getObj()[3].setWorldX(10*gp.getTileSize());
        gp.getObj()[3].setWorldY(11*gp.getTileSize());
        
        gp.getObj()[4] = new OBJ_Door();
        gp.getObj()[4].setWorldX(8*gp.getTileSize());
        gp.getObj()[4].setWorldY(28*gp.getTileSize());
        
        gp.getObj()[5] = new OBJ_Door();
        gp.getObj()[5].setWorldX(12*gp.getTileSize());
        gp.getObj()[5].setWorldY(22*gp.getTileSize());
        
        gp.getObj()[6] = new OBJ_Chest();
        gp.getObj()[6].setWorldX(10*gp.getTileSize());
        gp.getObj()[6].setWorldY(7*gp.getTileSize());
        
        gp.getObj()[7] = new OBJ_Boots();
        gp.getObj()[7].setWorldX(37*gp.getTileSize());
        gp.getObj()[7].setWorldY(42*gp.getTileSize());
    }
    
}
