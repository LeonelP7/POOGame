/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.crypto.AEADBadTagException;
import object.OBJ_CoinBronze;
import object.OBJ_Heart;

public class UI {

    private GamePanel gp;
    private Graphics2D g2;
    private Font maruMonica;
    private BufferedImage heart_full, heart_half, heart_blank, coin;
    private boolean messageOn = false;
    private ArrayList<String> message = new ArrayList<>();
    private ArrayList<Integer> messageCounter = new ArrayList<>();
    private boolean gameFinished = false;
    private String currentDialogue = "";
    private int commandNumber = 0;
    private int subState = 0;
    private int counter = 0;
    private Entity npc;

    private int playerSlotCol = 0;
    private int playerSlotRow = 0;
    private int npcSlotCol = 0;
    private int npcSlotRow = 0;

    private double playTime;
    private DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Crea objeto para el HUD
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.getImage();
        heart_half = heart.getImage2();
        heart_blank = heart.getImage3();
        Entity bronzeCoin = new OBJ_CoinBronze(gp);
        coin = bronzeCoin.getDown1();

    }

    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // estado: titulo
        if (gp.getGameState() == gp.getTitleState()) {
            drawTitleScreen();
        }

        // estado: jugando
        if (gp.getGameState() == gp.getPlayState()) {
            drawPlayerLife();
            drawMessage();
        }

        // estado: pausa
        if (gp.getGameState() == gp.getPauseState()) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // estado: dialogo
        if (gp.getGameState() == gp.getDialogueState()) {
            drawDialogueScreen();
        }

        // estado: estado del jugador
        if (gp.getGameState() == gp.getCharacterState()) {
            drawCharacterScreen();
            drawInventory(gp.getPlayer(), true);
        }

        // estado: opciones
        if (gp.getGameState() == gp.getOptionState()) {
            drawOptionsScreen();
        }

        // estado: fin del juego
        if (gp.getGameState() == gp.getGameOverState()) {
            drawGameOverScreen();
        }

        // estado: transicion
        if (gp.getGameState() == gp.getTransitionState()) {
            drawTransition();
        }

        // estado: comercio
        if (gp.getGameState() == gp.getTradeState()) {
            drawTradeScreen();
        }

    }

    public void drawPlayerLife() {

        int x = gp.getTileSize() / 2;
        int y = gp.getTileSize() / 2;
        int i = 0;

        //dibuja la vida maxima en corazones en blanco
        while (i < gp.getPlayer().getMaxLife() / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.getTileSize();
        }

        //resetea valores
        x = gp.getTileSize() / 2;
        y = gp.getTileSize() / 2;
        i = 0;

        //dibuja la vida actual
        while (i < gp.getPlayer().getLife()) {
            g2.drawImage(heart_half, x, y, null);
            i++;

            if (i < gp.getPlayer().getLife()) {
                g2.drawImage(heart_full, x, y, null);
                i++;
                x += gp.getTileSize();
            }
        }
    }

    public void drawMessage() {
        int messageX = gp.getTileSize();
        int messageY = gp.getTileSize() * 3;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; //messageCounter++
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }

            }
        }

    }

    public void drawTitleScreen() {

        //color del fondo
        g2.setColor(Color.black);

        //dibuja el titulo
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        String text = "Earth in Decline";
        int x = getXForCenteredText(text);
        int y = gp.getTileSize() * 3;

        //color de la sombra
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);

        //color principal del texto
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //imagen para la pantalla de titulo
        x = (gp.getScreenWidth() / 2) - (gp.getTileSize());
        y += gp.getTileSize() * 2;
        g2.drawImage(gp.getPlayer().getDown1(), x, y, gp.getTileSize() * 2, gp.getTileSize() * 2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NUEVO JUEGO";
        x = getXForCenteredText(text);
        y += gp.getTileSize() * 3.5;
        g2.drawString(text, x, y);
        if (commandNumber == 0) {

            g2.drawString(">", x - gp.getTileSize(), y);
        }

        text = "CARGAR JUEGO";
        x = getXForCenteredText(text);
        y += gp.getTileSize();
        g2.drawString(text, x, y);
        if (commandNumber == 1) {

            g2.drawString(">", x - gp.getTileSize(), y);
        }

        text = "SALIR";
        x = getXForCenteredText(text);
        y += gp.getTileSize();
        g2.drawString(text, x, y);
        if (commandNumber == 2) {

            g2.drawString(">", x - gp.getTileSize(), y);
        }

    }

    public void drawPauseScreen() {

        String text = "PAUSA";

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        int x = getXForCenteredText(text);

        int y = gp.getScreenHeight() / 2;

        g2.drawString(text, x, y);
    }

    public void drawOptionsScreen() {

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        //subventana
        int frameX = gp.getTileSize() * 6;
        int frameY = gp.getTileSize();
        int frameWidth = gp.getTileSize() * 8;
        int frameHeight = gp.getTileSize() * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                optionsTop(frameX, frameY);
                break;
            case 1:
//                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }

        gp.getKeyH().setEnterPressed(false);
    }

    public void drawGameOverScreen() {

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Has muerto";
        //sombra del texto
        g2.setColor(Color.gray);
        x = getXForCenteredText(text);
        y = gp.getTileSize() * 6;
        g2.drawString(text, x, y);

        //texto
        g2.setColor(Color.red);
        g2.drawString(text, x - 4, y - 4);

        //reintentar
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.setColor(Color.white);
        text = "Reintentar";
        x = getXForCenteredText(text);
        y += gp.getTileSize() * 2;
        g2.drawString(text, x, y);
        if (commandNumber == 0) {
            g2.drawString(">", x - 40, y);
        }

        //volver a la pantalla de titulo
        text = "Salir";
        x = getXForCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNumber == 1) {
            g2.drawString(">", x - 40, y);
        }

    }

    public void drawTradeScreen() {

        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }

        gp.getKeyH().setEnterPressed(false);

    }

    public void trade_select() {

        drawDialogueScreen();

        //dibujar subventana
        int x = gp.getTileSize() * 15;
        int y = gp.getTileSize() * 4;
        int width = gp.getTileSize() * 3;
        int height = (int) (gp.getTileSize() * 3.5);
        drawSubWindow(x, y, width, height);

        //dibujar texto
        x += gp.getTileSize() - 10;
        y += gp.getTileSize();
        g2.drawString("Comprar", x, y);
        if (commandNumber == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.getKeyH().isEnterPressed()) {
                subState = 1;
            }
        }
        y += gp.getTileSize();
        g2.drawString("Vender", x, y);
        if (commandNumber == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.getKeyH().isEnterPressed()) {
                subState = 2;
            }
        }
        y += gp.getTileSize();
        g2.drawString("Salir", x, y);
        if (commandNumber == 2) {
            g2.drawString(">", x - 24, y);
            if (gp.getKeyH().isEnterPressed()) {
                commandNumber = 0;
                gp.setGameState(gp.getDialogueState());
                currentDialogue = "Vuelve pronto...";
            }
        }
    }

    public void trade_buy() {

        //dibuja el inventario del jugador
        drawInventory(gp.getPlayer(), false);

        //dibuja el inventario del
        drawInventory(npc, true);

        //dibuja ventana de sugerencia 
        int x = gp.getTileSize() * 2;
        int y = gp.getTileSize() * 9;
        int width = gp.getTileSize() * 6;
        int height = gp.getTileSize() * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] atras", x + 24, y + 60);

        //dibuja la ventana de monedas del jugador
        x = gp.getTileSize() * 12;
        y = gp.getTileSize() * 9;
        width = gp.getTileSize() * 6;
        height = gp.getTileSize() * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Tus pesos: " + gp.getPlayer().getCoin(), x + 24, y + 60);

        //dibujar ventana de precios
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.getInventory().size()) {

            x = (int) (gp.getTileSize() * 5.5);
            y = (int) (gp.getTileSize() * 5.5);
            width = (int) (gp.getTileSize() * 2.5);
            height = gp.getTileSize();
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 7, 32, 32, null);

            int price = npc.getInventory().get(itemIndex).getPrice();
            String text = "" + price;
            x = getXForAlignToRightText(text, (gp.getTileSize() * 8) - 20);
            g2.drawString(text, x, y + 32);

            //comprar un objeto
            if (gp.getKeyH().isEnterPressed()) {
                if (npc.getInventory().get(itemIndex).getPrice() > gp.getPlayer().getCoin()) {
                    subState = 0;
                    gp.setGameState(gp.getDialogueState());
                    currentDialogue = "Te falta plata pelao!";
                } else {
                    if (gp.getPlayer().canObtainItem(npc.getInventory().get(itemIndex))) {
                        gp.getPlayer().setCoin(gp.getPlayer().getCoin() - npc.getInventory().get(itemIndex).getPrice());
                        gp.playSE(1);
                    } else {
                        subState = 0;
                        gp.setGameState(gp.getDialogueState());
                        currentDialogue = "No puedes llevar mas!";
                    }
                }
            }
        }

    }

    public void trade_sell() {

        //dibuja el inventario del jugador
        drawInventory(gp.getPlayer(), true);

        //dibuja ventana de sugerencia 
        int x = gp.getTileSize() * 2;
        int y = gp.getTileSize() * 9;
        int width = gp.getTileSize() * 6;
        int height = gp.getTileSize() * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] atras", x + 24, y + 60);

        //dibuja la ventana de monedas del jugador
        x = gp.getTileSize() * 12;
        y = gp.getTileSize() * 9;
        width = gp.getTileSize() * 6;
        height = gp.getTileSize() * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Tus pesos: " + gp.getPlayer().getCoin(), x + 24, y + 60);

        //dibujar ventana de precios
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.getPlayer().getInventory().size()) {

            x = (int) (gp.getTileSize() * 15.5);
            y = (int) (gp.getTileSize() * 5.5);
            width = (int) (gp.getTileSize() * 2.5);
            height = gp.getTileSize();
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 7, 32, 32, null);

            int price = gp.getPlayer().getInventory().get(itemIndex).getPrice() / 2;
            String text = "" + price;
            x = getXForAlignToRightText(text, (gp.getTileSize() * 18) - 20);
            g2.drawString(text, x, y + 32);

            //vender un objeto
            if (gp.getKeyH().isEnterPressed()) {
                if (gp.getPlayer().getInventory().get(itemIndex) == gp.getPlayer().getCurrentWeapon()
                        || gp.getPlayer().getInventory().get(itemIndex) == gp.getPlayer().getCurrentShield()) {
                    commandNumber = 0;
                    subState = 0;
                    gp.setGameState(gp.getDialogueState());
                    currentDialogue = "No puedes vender un objeto equipado";
                } else {
                    if(gp.getPlayer().getInventory().get(itemIndex).getAmount() > 1){
                        gp.getPlayer().getInventory().get(itemIndex).setAmount(gp.getPlayer().getInventory().get(itemIndex).getAmount()-1);
                    }
                    else{
                        gp.getPlayer().getInventory().remove(itemIndex);
                    }
                    gp.getPlayer().setCoin(gp.getPlayer().getCoin() + price);
                    gp.playSE(1);
                }
            }
        }
    }

    public void optionsTop(int frameX, int frameY) {

        int textX;
        int textY;

        //titulo
        String text = "Opciones";
        textX = getXForCenteredText(text);
        textY = frameY + gp.getTileSize();
        g2.drawString(text, textX, textY);

        //pantalla completa on/off
        textX = frameX + gp.getTileSize();
        textY += gp.getTileSize() * 2;
        g2.drawString("Pantalla comp", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
        }

        //musica
        textY += gp.getTileSize();
        g2.drawString("Musica", textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        //efectos de sonido
        textY += gp.getTileSize();
        g2.drawString("ES", textX, textY);
        if (commandNumber == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        //controles
        textY += gp.getTileSize();
        g2.drawString("Controles", textX, textY);
        if (commandNumber == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                subState = 2;
                commandNumber = 0;
            }
        }

        //salir de la partida
        textY += gp.getTileSize();
        g2.drawString("Salir al menu", textX, textY);
        if (commandNumber == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                subState = 3;
                commandNumber = 0;

            }
        }

        //atras
        textY += gp.getTileSize() * 2;
        g2.drawString("Atras", textX, textY);
        if (commandNumber == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                gp.setGameState(gp.getPlayState());
                commandNumber = 0;
            }
        }

        //pantalla completa check box
        textX = frameX + (int) (gp.getTileSize() * 4.5);
        textY = frameY + gp.getTileSize() * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);

        //volumen de la musica
        textY += gp.getTileSize();
        g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
        int volumeWidth = 24 * gp.getMusic().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);

        //volumen de los efectos de sonido
        textY += gp.getTileSize();
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.getSe().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);

        //guardando las configuraciones
        gp.getConfig().saveConfig();
    }

    public void options_control(int frameX, int frameY) {

        int textX;
        int textY;

        //titulo
        String text = "Controles";
        textX = getXForCenteredText(text);
        textY = frameY + gp.getTileSize();
        g2.drawString(text, textX, textY);

        textX = frameX + gp.getTileSize();
        textY += gp.getTileSize();
        g2.drawString("Moverse", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Confirmar/atq", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Pant del jugador", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Pausa", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Opciones", textX, textY);
        textY += gp.getTileSize();

        textX = frameX + gp.getTileSize() * 6;
        textY = gp.getTileSize() * 3;
        g2.drawString("WASD", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("ENTER", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("C", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("P", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("ESC", textX, textY);
        textY += gp.getTileSize();

        //atras
        textX = frameX + gp.getTileSize();
        textY = frameY + gp.getTileSize() * 9;
        g2.drawString("Atras", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                subState = 0;
                commandNumber = 3;
            }
        }

    }

    public void options_endGameConfirmation(int frameX, int frameY) {

        int textX = frameX + gp.getTileSize();
        int textY = frameY + gp.getTileSize() * 3;

        currentDialogue = "Salir del juego\n y volver a la pantalla \nde titulo?";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //si 
        String text = "Si";
        textX = getXForCenteredText(text);
        textY += gp.getTileSize() * 3;
        g2.drawString(text, textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                subState = 0;
                gp.setGameState(gp.getTitleState());
                gp.stopMusic();
            }
        }

        //no
        text = "No";
        textX = getXForCenteredText(text);
        textY += gp.getTileSize();
        g2.drawString(text, textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                subState = 0;
                commandNumber = 4;
            }
        }
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        if (counter == 50) {
            counter = 0;
            gp.setGameState(gp.getPlayState());
            gp.setCurrentMap(gp.geteHandler().getTempMap());
            gp.getPlayer().setWorldX(gp.getTileSize() * gp.geteHandler().getTempCol());
            gp.getPlayer().setWorldY(gp.getTileSize() * gp.geteHandler().getTempRow());
            gp.geteHandler().setPreviousEventX(gp.getPlayer().getWorldX());
            gp.geteHandler().setPreviousEventY(gp.getPlayer().getWorldY());
        }
    }

    public void drawDialogueScreen() {

        //Parametros para la ventana
        int x = gp.getTileSize() * 3;
        int y = gp.getTileSize() / 2;
        int width = gp.getScreenWidth() - (gp.getTileSize() * 6);
        int height = gp.getTileSize() * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26F));
        x += gp.getTileSize();
        y += gp.getTileSize();

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {

        //creando un cuadro
        final int frameX = gp.getTileSize() * 2;
        final int frameY = gp.getTileSize();
        final int frameWidth = gp.getTileSize() * 5;
        final int frameHeight = gp.getTileSize() * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.getTileSize();
        final int lineHeight = 35;

        //nombres
        g2.drawString("Nivel", textX, textY);
        textY += lineHeight;
        g2.drawString("Vida", textX, textY);
        textY += lineHeight;
        g2.drawString("Fuerza", textX, textY);
        textY += lineHeight;
        g2.drawString("Deztreza", textX, textY);
        textY += lineHeight;
        g2.drawString("Ataque", textX, textY);
        textY += lineHeight;
        g2.drawString("Defensa", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Sig Nivel", textX, textY);
        textY += lineHeight;
        g2.drawString("Pesos", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Arma", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Escudo", textX, textY);
        textY += lineHeight;

        //valores
        int tailX = (frameX + frameWidth) - 30;
        //restableciendo frameY
        textY = frameY + gp.getTileSize();
        String value = "";

        value = String.valueOf(gp.getPlayer().getLevel());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getLife() + "/" + gp.getPlayer().getMaxLife());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getStrength());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getDexterity());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getAttack());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getDefense());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getExp());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getNextLevelExp());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getCoin());
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.getPlayer().getCurrentWeapon().getDown1(),
                tailX - gp.getTileSize(), textY - 14, null);
        textY += gp.getTileSize();
        g2.drawImage(gp.getPlayer().getCurrentShield().getDown1(),
                tailX - gp.getTileSize(), textY - 14, null);
    }

    public void drawInventory(Entity entity, boolean cursor) {

        //cuadro
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.getPlayer()) {
            frameX = gp.getTileSize() * 12;
            frameY = gp.getTileSize();
            frameWidth = gp.getTileSize() * 6;
            frameHeight = gp.getTileSize() * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gp.getTileSize() * 2;
            frameY = gp.getTileSize();
            frameWidth = gp.getTileSize() * 6;
            frameHeight = gp.getTileSize() * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //espacios
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.getTileSize() + 3;

        //dibujar items del jugador
        for (int i = 0; i < entity.getInventory().size(); i++) {

            //cursor de equipamiento
            if (entity.getInventory().get(i) == entity.getCurrentWeapon()
                    || entity.getInventory().get(i) == entity.getCurrentShield() ||
                    entity.getInventory().get(i) == entity.getCurrentLight()) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.getTileSize(), gp.getTileSize(),
                        10, 10);
            }

            g2.drawImage(entity.getInventory().get(i).getDown1(),
                    slotX, slotY, null);

            if (entity== gp.getPlayer() && entity.getInventory().get(i).getAmount() > 1) {
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = "" + entity.getInventory().get(i).getAmount();
                amountX = getXForAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.getTileSize();

                //sombra de la cantidad
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);

                //cantidad
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }

        }

        //cursor
        if (cursor) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.getTileSize();
            int cursorHeight = gp.getTileSize();

            //dibujar cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //cuadro de descripcion
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.getTileSize() * 3;

            //dibuja el texto de la descripcion
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.getTileSize();
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < entity.getInventory().size()) {

                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for (String line : entity.getInventory()
                        .get(itemIndex).getDescription().split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }

            }
        }

    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public int getXForCenteredText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.getScreenWidth() / 2 - length / 2;
        return x;
    }

    public int getXForAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    //GETTERS Y SETTERS
    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public Font getMaruMonica() {
        return maruMonica;
    }

    public void setMaruMonica(Font maruMonica) {
        this.maruMonica = maruMonica;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public double getPlayTime() {
        return playTime;
    }

    public void setPlayTime(double playTime) {
        this.playTime = playTime;
    }

    public String getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public int getCommandNumber() {
        return commandNumber;
    }

    public void setCommandNumber(int commandNumber) {
        this.commandNumber = commandNumber;
    }

    public int getPlayerSlotCol() {
        return playerSlotCol;
    }

    public void setPlayerSlotCol(int slotCol) {
        this.playerSlotCol = slotCol;
    }

    public int getPlayerSlotRow() {
        return playerSlotRow;
    }

    public void setPlayerSlotRow(int slotRow) {
        this.playerSlotRow = slotRow;
    }

    public int getSubState() {
        return subState;
    }

    public void setSubState(int subState) {
        this.subState = subState;
    }

    public Entity getNpc() {
        return npc;
    }

    public void setNpc(Entity npc) {
        this.npc = npc;
    }

    public int getNpcSlotCol() {
        return npcSlotCol;
    }

    public void setNpcSlotCol(int npcSlotCol) {
        this.npcSlotCol = npcSlotCol;
    }

    public int getNpcSlotRow() {
        return npcSlotRow;
    }

    public void setNpcSlotRow(int npcSlotRow) {
        this.npcSlotRow = npcSlotRow;
    }

    
}
