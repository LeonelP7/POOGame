/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_ShieldWood;
import object.OBJ_SwordNormal;

public class Player extends Entity {

    private KeyHandler keyH;

    //variables para la posicion del jugador en la pantalla
    private final int screenX;
    private final int screenY;

    //contador para actualizar el sprite si se queda quieto el personaje
    private int standCounter;
    private boolean attackCancel;
    
    //iluminacion
    private boolean lightUpdated;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);

        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        attackCancel = false;
        
        lightUpdated = false;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.getTileSize() * 23;
        worldY = gp.getTileSize() * 21;
//        worldX = gp.getTileSize() * 12;
//        worldY = gp.getTileSize() * 12;
        speed = 4;
        direction = "down";

        //estado del jugador
        level = 1;
        strength = 1; //entre mas fuerza tenga mas daño hace
        dexterity = 1; //entre mas destreza tenga menos daño recibe
        exp = 0;
        nextLevelExp = 5;
        coin = 100;
        currentWeapon = new OBJ_SwordNormal(gp);
        currentShield = new OBJ_ShieldWood(gp);
        attack = getTotalAttack();
        defense = getTotalDefense();
        maxLife = 6;
        life = maxLife;
    }

    public void setDefaultPositions() {

        worldX = gp.getTileSize() * 23;
        worldY = gp.getTileSize() * 21;
        direction = "down";
    }
    
    public void restoreLife(){
        life = maxLife;
        
    }

    @Override
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        canObtainItem(new OBJ_Axe(gp));
        canObtainItem(new OBJ_Key(gp));
        canObtainItem(new OBJ_Key(gp));
        canObtainItem(new OBJ_Key(gp));
    }

    public int getTotalAttack() {
        attackArea = currentWeapon.getAttackArea();
        return attack = strength * currentWeapon.getAttackValue();
    }

    public int getTotalDefense() {
        return defense = dexterity * currentShield.getDefenseValue();
    }

    public void getPlayerImage() {

        up1 = setUp("/player/boy_up_1", gp.getTileSize(), gp.getTileSize());
        up2 = setUp("/player/boy_up_2", gp.getTileSize(), gp.getTileSize());
        down1 = setUp("/player/boy_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setUp("/player/boy_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setUp("/player/boy_left_1", gp.getTileSize(), gp.getTileSize());
        left2 = setUp("/player/boy_left_2", gp.getTileSize(), gp.getTileSize());
        right1 = setUp("/player/boy_right_1", gp.getTileSize(), gp.getTileSize());
        right2 = setUp("/player/boy_right_2", gp.getTileSize(), gp.getTileSize());
    }

    public void getPlayerAttackImage() {

        if (currentWeapon.type == type_sword) {
            attackUp1 = setUp("/player/boy_attack_up_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackUp2 = setUp("/player/boy_attack_up_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown1 = setUp("/player/boy_attack_down_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown2 = setUp("/player/boy_attack_down_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackLeft1 = setUp("/player/boy_attack_left_1", gp.getTileSize() * 2, gp.getTileSize());
            attackLeft2 = setUp("/player/boy_attack_left_2", gp.getTileSize() * 2, gp.getTileSize());
            attackRight1 = setUp("/player/boy_attack_right_2", gp.getTileSize() * 2, gp.getTileSize());
            attackRight2 = setUp("/player/boy_attack_right_2", gp.getTileSize() * 2, gp.getTileSize());
        }

        if (currentWeapon.type == type_axe) {
            attackUp1 = setUp("/player/boy_axe_up_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackUp2 = setUp("/player/boy_axe_up_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown1 = setUp("/player/boy_axe_down_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown2 = setUp("/player/boy_axe_down_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackLeft1 = setUp("/player/boy_axe_left_1", gp.getTileSize() * 2, gp.getTileSize());
            attackLeft2 = setUp("/player/boy_axe_left_2", gp.getTileSize() * 2, gp.getTileSize());
            attackRight1 = setUp("/player/boy_axe_right_2", gp.getTileSize() * 2, gp.getTileSize());
            attackRight2 = setUp("/player/boy_axe_right_2", gp.getTileSize() * 2, gp.getTileSize());
        }

    }

    @Override
    public void update() {

        if (attacking) {

            attacking();

        } else if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed() || keyH.isEnterPressed()) {
            //en java la esquina superior izquierda es x:0 y:0
            if (keyH.isUpPressed()) {
                direction = "up";

            } else if (keyH.isDownPressed()) {
                direction = "down";

            } else if (keyH.isLeftPressed()) {
                direction = "left";

            } else if (keyH.isRightPressed()) {
                direction = "right";

            }

            //revisa la colision del tile
            collisionOn = false;
            gp.getcChecker().checkTile(this);

            //revisa la colision de objetos
            int objectIndex = gp.getcChecker().checkObject(this, true);
            pickUpObject(objectIndex);

            //revisa la colision con npcs
            int npcIndex = gp.getcChecker().checkEntity(this, gp.getNpc());
            interactNPC(npcIndex);

            //revisa la colision con monstruos
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
            contactMonster(monsterIndex);

            //revisa la colision con tiles interactivos
            int iTileIndex = gp.getcChecker().checkEntity(this, gp.getiTile());

            //revisa los eventos
            gp.geteHandler().checkEvent();

            //si collisionOn es false, el jugador puede moverse
            if (!collisionOn && !keyH.isEnterPressed()) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            if (keyH.isEnterPressed() && !attackCancel) {
                attacking = true;
                spriteCounter = 0;
            }

            attackCancel = false;
            gp.getKeyH().setEnterPressed(false);

            //esto hace que el sprite cambie cada 12 frames
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;

            }
        } else {
            standCounter++;

            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        //frames de invulnerabilidad para el jugador
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter >= 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (life > maxLife) {
            life = maxLife;
        }

        if (life <= 0) {
            gp.setGameState(gp.getGameOverState());
            gp.getUi().setCommandNumber(-1);
            gp.stopMusic();
            gp.playSE(10);
        }
    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }

        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // el area de ataque se vuelve parte del area solida del jugador
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // revisa la colision con monstruos con las actualizadas worldX, worldY y solidArea
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
            damageMonster(monsterIndex);

            int iTileIndex = gp.getcChecker().checkEntity(this, gp.getiTile());
            damageInteractiveTile(iTileIndex);

            // despues de revisar la colision, restaura a los datos originales
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }

        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

            //Items que solo se pueden recoger
            if (gp.getObj()[gp.getCurrentMap()][i].getType() == type_pickUpOnly) {

                gp.getObj()[gp.getCurrentMap()][i].use(this);
                gp.getObj()[gp.getCurrentMap()][i] = null;
            }
            //obstaculos
            else if(gp.getObj()[gp.getCurrentMap()][i].getType() == type_obstacle){
                if(gp.getKeyH().isEnterPressed()){
                    gp.getObj()[gp.getCurrentMap()][i].interact();
                }
            }            
            //Items que van al inventario
            else {
                String text = "";
                if (canObtainItem(gp.getObj()[gp.getCurrentMap()][i])) {
                    gp.playSE(1);
                    text = "Recogiste " + gp.getObj()[gp.getCurrentMap()][i].getName() + "!";
                    gp.getObj()[gp.getCurrentMap()][i] = null;
                } else {
                    text = "Ya no puedes llevar mas!";
                }

                gp.getUi().addMessage(text);
            }
        }

    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gp.getMonster()[gp.getCurrentMap()][i].isDying()) {
                gp.playSE(6);
                int damage = gp.getMonster()[gp.getCurrentMap()][i].getAttack() - defense;
                if (damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }

        }
    }

    public void damageMonster(int i) {
        if (i != 999) {
            if (!gp.getMonster()[gp.getCurrentMap()][i].isInvincible()) {
                gp.playSE(5);

                int damage = attack - gp.getMonster()[gp.getCurrentMap()][i].getDefense();
                if (damage < 0) {
                    damage = 0;
                }
                gp.getMonster()[gp.getCurrentMap()][i].setLife(gp.getMonster()[gp.getCurrentMap()][i].getLife() - damage);
                gp.getMonster()[gp.getCurrentMap()][i].setInvincible(true);
                gp.getMonster()[gp.getCurrentMap()][i].damageReaction();

                if (gp.getMonster()[gp.getCurrentMap()][i].getLife() <= 0) {
                    gp.getMonster()[gp.getCurrentMap()][i].setDying(true);
                    exp += gp.getMonster()[gp.getCurrentMap()][i].getExp();
                    checkLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.getiTile()[gp.getCurrentMap()][i].isDestructible()
                && gp.getiTile()[gp.getCurrentMap()][i].isCorrectItem(this)) {
            gp.getiTile()[gp.getCurrentMap()][i].playSE();
            generateParticle(gp.getiTile()[gp.getCurrentMap()][i], gp.getiTile()[gp.getCurrentMap()][i]);
            gp.getiTile()[gp.getCurrentMap()][i] = gp.getiTile()[gp.getCurrentMap()][i].getDestroyedFrom();

        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(7);
            gp.setGameState(gp.getDialogueState());
            gp.getUi().setCurrentDialogue("Has subido a nivel " + level + "\n"
                    + "Te sientes mas fuerte!");
        }
    }

    public void interactNPC(int i) {

        if (gp.getKeyH().isEnterPressed()) {
            if (i != 999) {
                attackCancel = true;
                gp.setGameState(gp.getDialogueState());
                gp.getNpc()[gp.getCurrentMap()][i].speak();

            }
        }

    }

    public void selectItem() {
        int itemIndex = gp.getUi().getItemIndexOnSlot(gp.getUi().getPlayerSlotCol(),gp.getUi().getPlayerSlotRow());

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getTotalAttack();
                getPlayerAttackImage();
            }

            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getTotalDefense();
            }

            if(selectedItem.type == type_light){
                if (currentLight == selectedItem) {
                    currentLight = null;
                }
                else{
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            
            if (selectedItem.type == type_consumable) {
                if(selectedItem.use(this)){
                    
                    if (selectedItem.getAmount() > 1) {
                        selectedItem.setAmount(selectedItem.getAmount()-1);
                    }
                    else {
                        inventory.remove(itemIndex);
                    }
                }   
            }
        }
    }
    
    public int searchItemInInventory(String itemName){
        
        int itemIndex = 999;
        for (int i = 0; i < inventory.size(); i++) {
            
            if (inventory.get(i).getName().equalsIgnoreCase(itemName)) {
                itemIndex = i;
                break;
            }
        }
        
        return itemIndex;
    }

    public boolean canObtainItem(Entity item){
        
        boolean canObtain = false;
        
        //revisa si es stackeable (apilable)
        if (item.isStackeable()) {
            int index = searchItemInInventory(item.getName());
            
            //revisa si el item ya esta en el inventario
            if(index != 999){
                inventory.get(index).setAmount(inventory.get(index).getAmount()+1);
                canObtain = true;
            }
            else {
                if (inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else {//si no es stackeable entonces vemos si hay espacio en el inventario
            if (inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
        }
        
        return canObtain;
    }
    
    @Override
    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.getTileSize(), gp.getTileSize());

        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }

                if (attacking) {
                    tempScreenY = screenY - gp.getTileSize();
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }

                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }

                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }

                break;
            case "left":

                if (!attacking) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }

                if (attacking) {
                    tempScreenX = screenX - gp.getTileSize();
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }

                break;
            case "right":

                if (!attacking) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }

                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }

                break;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //restablecer alpha (opacidad)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
                break;
            }
        }
        return currentWeaponSlot;
    }
    
    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentShield) {
                currentShieldSlot = i;
                break;
            }
        }
        return currentShieldSlot;
    }
    
    
    //GETTERS Y SETTERS
    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public boolean isAttackCancel() {
        return attackCancel;
    }

    public void setAttackCancel(boolean attackCancel) {
        this.attackCancel = attackCancel;
    }

    public boolean isLightUpdated() {
        return lightUpdated;
    }

    public void setLightUpdated(boolean lightUpdated) {
        this.lightUpdated = lightUpdated;
    }

    
}
