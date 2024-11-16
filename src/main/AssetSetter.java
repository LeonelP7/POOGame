/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_PotionRed;
import object.OBJ_ShieldBlue;
import tiles_interactive.IT_DryTree;

/**
 *
 * @author ASUS TUF
 */
public class AssetSetter {

    /*
    para calcular facilmente la fila y la columna viendo desde el ide
    rowEvento = rowIde-1
    colEvento = colIde/3 - 1
     */
    
    private GamePanel gp;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

    public void setObject() {
        
        int mapNum = 0;
        
        int i = 0;
        gp.getObj()[mapNum][i] = new OBJ_Key(gp,25,23);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Key(gp,21,19);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Key(gp,26,21);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Axe(gp,33,21);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_ShieldBlue(gp,35,21);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_PotionRed(gp,22,27);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Heart(gp,22,29);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door(gp,14,28);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door(gp,12,12);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp,30,29,new OBJ_Key(gp));
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Lantern(gp,18,20);
        i++;
    }
    
    public void setNPC(){
        
        int mapNum = 0;
        int i = 0;
        
        gp.getNpc()[mapNum][i] = new NPC_OldMan(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize()*21);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize()*21);
        i++;
        
        //mapa 2
        mapNum = 1;
        i = 0;
        gp.getNpc()[mapNum][i] = new NPC_Merchant(gp,12,7);
        i++;
        
    }

    public void setMoster(){
        
        int mapNum = 0;
        
        gp.getMonster()[mapNum][0] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][0].setWorldX(23*gp.getTileSize());
        gp.getMonster()[mapNum][0].setWorldY(36*gp.getTileSize());
        
        gp.getMonster()[mapNum][1] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][1].setWorldX(23*gp.getTileSize());
        gp.getMonster()[mapNum][1].setWorldY(37*gp.getTileSize());
    }
    
    public void setInteractiveTile(){
        
        int mapNum = 0;
        
        int i = 0;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 27,12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,28,12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,29,12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,30,12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,31,12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,32,12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,33,12);
        i++;
        
        
    }
}
