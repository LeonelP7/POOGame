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
public class OBJ_CoinBronze extends Entity{
    
    public OBJ_CoinBronze(GamePanel gp) {
        super(gp);
        type = type_pickUpOnly;
        name = "1 peso";
        value = 1;
        down1 = setUp("/objects/coin_bronze", gp.getTileSize(), gp.getTileSize());
    }
    
    @Override
    public void use(Entity entity){
        gp.playSE(1);
        gp.getUi().addMessage("Pesos + " + value);
        gp.getPlayer().setCoin(gp.getPlayer().getCoin()+value);
    }
}
