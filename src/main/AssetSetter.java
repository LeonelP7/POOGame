/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
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

    public void setObject() {
        
    }
    
    public void setNPC(){
        
        gp.getNpc()[0] = new NPC_OldMan(gp);
        gp.getNpc()[0].setWorldX(gp.getTileSize()*21);
        gp.getNpc()[0].setWorldY(gp.getTileSize()*21);
    }

    public void setMoster(){
        gp.getMonster()[0] = new MON_GreenSlime(gp);
        gp.getMonster()[0].setWorldX(23*gp.getTileSize());
        gp.getMonster()[0].setWorldY(36*gp.getTileSize());
        
        gp.getMonster()[1] = new MON_GreenSlime(gp);
        gp.getMonster()[1].setWorldX(23*gp.getTileSize());
        gp.getMonster()[1].setWorldY(37*gp.getTileSize());
    }
}
