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
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_PotionRed;
import object.OBJ_ShieldBlue;
import tiles_interactive.IT_DryTree;

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
        gp.getObj()[i] = new OBJ_Key(gp,25,23);
        i++;
        gp.getObj()[i] = new OBJ_Key(gp,21,19);
        i++;
        gp.getObj()[i] = new OBJ_Key(gp,26,21);
        i++;
        gp.getObj()[i] = new OBJ_Axe(gp,33,21);
        i++;
        gp.getObj()[i] = new OBJ_ShieldBlue(gp,35,21);
        i++;
        gp.getObj()[i] = new OBJ_PotionRed(gp,22,27);
        i++;
        gp.getObj()[i] = new OBJ_Heart(gp,22,29);
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
    
    public void setInteractiveTile(){
        int i = 0;
        gp.getiTile()[i] = new IT_DryTree(gp, 27,12);
        i++;
        gp.getiTile()[i] = new IT_DryTree(gp,28,12);
        i++;
        gp.getiTile()[i] = new IT_DryTree(gp,29,12);
        i++;
        gp.getiTile()[i] = new IT_DryTree(gp,30,12);
        i++;
        gp.getiTile()[i] = new IT_DryTree(gp,31,12);
        i++;
        gp.getiTile()[i] = new IT_DryTree(gp,32,12);
        i++;
        gp.getiTile()[i] = new IT_DryTree(gp,33,12);
        i++;
        
        
    }
}
