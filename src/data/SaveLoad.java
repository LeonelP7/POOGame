/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import entity.Entity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_CoinBronze;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_PotionRed;
import object.OBJ_ShieldBlue;
import object.OBJ_ShieldWood;
import object.OBJ_SwordNormal;

/**
 *
 * @author ASUS TUF
 */
public class SaveLoad {
    
    private GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }
    
    public Entity getObject(String itemName){
        
        Entity obj = null;
        
        switch(itemName){
            case "Hacha de leñador": obj = new OBJ_Axe(gp); break;
            case "Botas": obj = new OBJ_Boots(gp); break;
            case "1 peso": obj = new OBJ_CoinBronze(gp); break;
            case "Llave": obj = new OBJ_Key(gp); break;
            case "Linterna": obj = new OBJ_Lantern(gp); break;
            case "Cafe aguila roja": obj = new OBJ_PotionRed(gp); break;
            case "Escudo Azul": obj = new OBJ_ShieldBlue(gp); break;
            case "Escudo de Carton": obj = new OBJ_ShieldWood(gp); break;
            case "Machete Normal": obj = new OBJ_SwordNormal(gp); break;
            case "door": obj = new OBJ_Door(gp);
            case "chest": obj = new OBJ_Chest(gp);
        }
        
        return obj;
    }
    
    public void save(){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("save.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            DataStorage ds = new DataStorage();
            
            ds.level = gp.getPlayer().getLevel();
            ds.maxLife = gp.getPlayer().getMaxLife();
            ds.life = gp.getPlayer().getLife();
            ds.strenth = gp.getPlayer().getStrength();
            ds.dexterity = gp.getPlayer().getDexterity();
            ds.exp = gp.getPlayer().getExp();
            ds.nextLevelExp = gp.getPlayer().getNextLevelExp();
            ds.coin = gp.getPlayer().getCoin();
            
            //inventario del jugador
            for (int i = 0; i < gp.getPlayer().getInventory().size(); i++) {
                
                ds.itemsNames.add(gp.getPlayer().getInventory().get(i).getName());
                ds.itemsAmounts.add(gp.getPlayer().getInventory().get(i).getAmount());
            }
            
            //equipamiento del jugador
            ds.currentWeaponSlot = gp.getPlayer().getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.getPlayer().getCurrentShieldSlot();
            
            //objetos en el mapa
            ds.mapObjectNames = new String[gp.getMaxMap()][gp.getObj()[1].length];
            ds.mapObjectWorldX = new int[gp.getMaxMap()][gp.getObj()[1].length];
            ds.mapObjectWorldY = new int[gp.getMaxMap()][gp.getObj()[1].length];
            ds.mapObjectLootNames = new String[gp.getMaxMap()][gp.getObj()[1].length];
            ds.mapObjectOpened = new boolean[gp.getMaxMap()][gp.getObj()[1].length];
            
            for (int mapNum = 0; mapNum < gp.getMaxMap(); mapNum++) {
                
                for (int i = 0; i < gp.getObj()[1].length; i++) {
                    
                    if( gp.getObj()[mapNum][i] == null){
                        ds.mapObjectLootNames[mapNum][i] = "NA";
                        
                    }
                    else{
                        ds.mapObjectNames[mapNum][i] = gp.getObj()[mapNum][i].getName();
                        ds.mapObjectWorldX[mapNum][i] = gp.getObj()[mapNum][i].getWorldX();
                        ds.mapObjectWorldY[mapNum][i] = gp.getObj()[mapNum][i].getWorldY();
                        
                        if(gp.getObj()[mapNum][i].getLoot() != null){
                            ds.mapObjectLootNames[mapNum][i] = gp.getObj()[mapNum][i].getLoot().getName();
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.getObj()[mapNum][i].isOpened();
                    }
                    
                }
            }
            
            //escribir el objeto DataStorage ds
            oos.writeObject(ds);
            
            fos.close();
            oos.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void load(){
        
        FileInputStream fis = null;
        try {
            ObjectInputStream ois = null;
            fis = new FileInputStream(new File("save.dat"));
            ois = new ObjectInputStream(fis);
            
            //leer el DataStorage ds
            DataStorage ds = (DataStorage)ois.readObject();
            
            gp.getPlayer().setLevel(ds.level);
            gp.getPlayer().setMaxLife(ds.maxLife);
            gp.getPlayer().setLife(ds.life);
            gp.getPlayer().setStrength(ds.strenth);
            gp.getPlayer().setDexterity(ds.dexterity);
            gp.getPlayer().setExp(ds.exp);
            gp.getPlayer().setNextLevelExp(ds.nextLevelExp);
            gp.getPlayer().setCoin(ds.coin);
            
            //inventario del jugador
            gp.getPlayer().getInventory().clear();
            for (int i = 0; i < ds.itemsNames.size(); i++) {
                
                gp.getPlayer().getInventory().add(getObject(ds.itemsNames.get(i)));
                gp.getPlayer().getInventory().get(i).setAmount(ds.itemsAmounts.get(i));
            }
            
            //equipamiento del jugador
            gp.getPlayer().setCurrentWeapon(gp.getPlayer().getInventory().get(ds.currentWeaponSlot));
            gp.getPlayer().setCurrentShield(gp.getPlayer().getInventory().get(ds.currentShieldSlot));
            gp.getPlayer().getTotalAttack();
            gp.getPlayer().getTotalDefense();
            gp.getPlayer().getPlayerAttackImage();
            
            //objetos en el mapa
            for (int mapNum = 0; mapNum < gp.getMaxMap(); mapNum++) {
                for (int i = 0; i < gp.getObj()[1].length; i++) {
                    
                    if(ds.mapObjectLootNames[mapNum][i].equals("NA")){
                        gp.getObj()[mapNum][i] = null;
                    }else{
                         gp.getObj()[mapNum][i] = getObject(ds.mapObjectNames[mapNum][i]);
                         gp.getObj()[mapNum][i].setWorldX(ds.mapObjectWorldX[mapNum][i]);
                         gp.getObj()[mapNum][i].setWorldY(ds.mapObjectWorldY[mapNum][i]);
                         if(ds.mapObjectLootNames[mapNum][i] != null){
                             gp.getObj()[mapNum][i].setLoot(getObject(ds.mapObjectLootNames[mapNum][i]));
                         }
                          gp.getObj()[mapNum][i].setOpened(ds.mapObjectOpened[mapNum][i]);
                          if (gp.getObj()[mapNum][i].isOpened()) {
                            gp.getObj()[mapNum][i].setDown1(gp.getObj()[mapNum][i].getImage2());
                        }
                    }
                }
            }
            
            fis.close();
            ois.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
}
