/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import java.awt.Rectangle;

/**
 *
 * @author ASUS TUF
 */
public class CollisionChecker {

    private GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityLeftWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityTopRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.getObj()[1].length; i++) {

            if (gp.getObj()[gp.getCurrentMap()][i] != null) {

                //obtenemos la posicion del area solida de la entidad
                //System.out.println("entity solidArea x: " + entity.getSolidArea().x);
                entity.getSolidArea().x += entity.getWorldX();
                //System.out.println("entity solidArea x: " + entity.getSolidArea().x);
                entity.getSolidArea().y += entity.getWorldY();
                //System.out.println("entity solidArea y: " + entity.getSolidArea().y);

                //obtenemos la posicion del area solida del objeto
//                System.out.println(gp.getObj()[i].getName());
//                System.out.println("object solidArea x: " + gp.getObj()[i].getSolidArea().x);
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x += gp.getObj()[gp.getCurrentMap()][i].getWorldX();
//                System.out.println("object solidArea x: " + gp.getObj()[i].getSolidArea().x);
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y += gp.getObj()[gp.getCurrentMap()][i].getWorldY();
                switch (entity.getDirection()) {
                    case "up":
//                        System.out.println("entity solid area y: " + entity.getSolidArea().y);
                        entity.getSolidArea().y -= entity.getSpeed();
//                        System.out.println(gp.getObj()[i].getSolidArea().toString());
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();

                        break;
                }
                if (entity.getSolidArea().intersects(gp.getObj()[gp.getCurrentMap()][i].getSolidArea())) {
                    if (gp.getObj()[gp.getCurrentMap()][i].isCollision()) {
                        entity.setCollisionOn(true);
                    }
                    if (player) {
                        index = i;
                    }
                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x = gp.getObj()[gp.getCurrentMap()][i].getSolidAreaDefaultX();
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y = gp.getObj()[gp.getCurrentMap()][i].getSolidAreaDefaultY();
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[][] target) {

        int index = 999;

        for (int i = 0; i < target[1].length; i++) {

            if (target[gp.getCurrentMap()][i] != null) {

                //obtenemos la posicion del area solida de la entidad
                entity.getSolidArea().x += entity.getWorldX();
                entity.getSolidArea().y += entity.getWorldY();

                //obtenemos la posicion del area solida de la entidad objetivo
                target[gp.getCurrentMap()][i].getSolidArea().x += target[gp.getCurrentMap()][i].getWorldX();
                target[gp.getCurrentMap()][i].getSolidArea().y += target[gp.getCurrentMap()][i].getWorldY();
                switch (entity.getDirection()) {
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();

                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        break;
                }
                if (entity.getSolidArea().intersects(target[gp.getCurrentMap()][i].getSolidArea())) {
                    if (target[gp.getCurrentMap()][i] != entity) {
                        entity.setCollisionOn(true);
                        index = i;
                    }
                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                target[gp.getCurrentMap()][i].getSolidArea().x = target[gp.getCurrentMap()][i].getSolidAreaDefaultX();
                target[gp.getCurrentMap()][i].getSolidArea().y = target[gp.getCurrentMap()][i].getSolidAreaDefaultY();
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        //obtenemos la posicion del area solida de la entidad
        entity.getSolidArea().x += entity.getWorldX();
        entity.getSolidArea().y += entity.getWorldY();

        //obtenemos la posicion del area solida de la entidad objetivo
        gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
        gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();
        switch (entity.getDirection()) {
            case "up":
                entity.getSolidArea().y -= entity.getSpeed();
                break;
            case "down":
                entity.getSolidArea().y += entity.getSpeed();
                break;
            case "left":
                entity.getSolidArea().x -= entity.getSpeed();
                break;
            case "right":
                entity.getSolidArea().x += entity.getSpeed();
                break;
        }
        if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
            entity.setCollisionOn(true);
            contactPlayer = true;
        }
        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();
        
        return contactPlayer;
    }

}
