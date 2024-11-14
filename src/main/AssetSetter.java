/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;

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
        int i = 0;
        gp.getObj()[i] = new OBJ_Key(gp);
        gp.getObj()[i].setWorldX(gp.getTileSize()*25);
        gp.getObj()[i].setWorldY(gp.getTileSize()*23);
        i++;
        gp.getObj()[i] = new OBJ_Key(gp);
        gp.getObj()[i].setWorldX(gp.getTileSize()*21);
        gp.getObj()[i].setWorldY(gp.getTileSize()*19);
        i++;
        gp.getObj()[i] = new OBJ_Key(gp);
        gp.getObj()[i].setWorldX(gp.getTileSize()*26);
        gp.getObj()[i].setWorldY(gp.getTileSize()*21);
        i++;
        gp.getObj()[i] = new OBJ_Axe(gp);
        gp.getObj()[i].setWorldX(gp.getTileSize()*33);
        gp.getObj()[i].setWorldY(gp.getTileSize()*21);
        i++;
        gp.getObj()[i] = new OBJ_Shield_Blue(gp);
        gp.getObj()[i].setWorldX(gp.getTileSize()*35);
        gp.getObj()[i].setWorldY(gp.getTileSize()*21);
        i++;
        gp.getObj()[i] = new OBJ_Potion_Red(gp);
        gp.getObj()[i].setWorldX(gp.getTileSize()*22);
        gp.getObj()[i].setWorldY(gp.getTileSize()*27);
        i++;
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
