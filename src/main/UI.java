/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.OBJ_Key;

public class UI {

    private GamePanel gp;
    private Graphics2D g2;
    private Font maruMonica;
    private boolean messageOn = false;
    private String message = "";
    private int messageCounter;
    private boolean gameFinished = false;
    private String currentDialogue = "";
    private int commandNumber = 0;

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
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);
        
        // estado: titulo
        if(gp.getGameState() == gp.getTitleState()){
            drawTitleScreen();
        }

        // estado: jugando
        if (gp.getGameState() == gp.getPlayState()) {
            //hacer las cosas del gamestate despues
        }

        // estado: pausa
        if (gp.getGameState() == gp.getPauseState()) {
            drawPauseScreen();
        }

        // estado: dialogo
        if (gp.getGameState() == gp.getDialogueState()) {
            drawDialogueScreen();
        }

    }
    
    public void drawTitleScreen(){
        
        //color del fondo
        g2.setColor(Color.black);
        
        //dibuja el titulo
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
        String text = "Earth in Decline";
        int x = getXForCenteredText(text);
        int y = gp.getTileSize()*3;
        
        //color de la sombra
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);
        
        //color principal del texto
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        
        //imagen para la pantalla de titulo
        x = (gp.getScreenWidth()/2) - (gp.getTileSize());
        y += gp.getTileSize()*2;
        g2.drawImage(gp.getPlayer().getDown1(), x, y,gp.getTileSize()*2, gp.getTileSize()*2, null);
        
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        
        text = "NUEVO JUEGO";
        x = getXForCenteredText(text);
        y += gp.getTileSize()*3.5;
        g2.drawString(text, x, y);
        if (commandNumber == 0) {
            
            g2.drawString(">", x-gp.getTileSize(), y);
        }
        
        text = "CARGAR JUEGO";
        x = getXForCenteredText(text);
        y += gp.getTileSize();
        g2.drawString(text, x, y);
        if (commandNumber == 1) {
            
            g2.drawString(">", x-gp.getTileSize(), y);
        }
        
        text = "SALIR";
        x = getXForCenteredText(text);
        y += gp.getTileSize();
        g2.drawString(text, x, y);
        if (commandNumber == 2) {
            
            g2.drawString(">", x-gp.getTileSize(), y);
        }
        
    }

    public void drawPauseScreen() {

        String text = "PAUSA";

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        int x = getXForCenteredText(text);

        int y = gp.getScreenHeight() / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        //Parametros para la ventana
        int x = gp.getTileSize() * 2;
        int y = gp.getTileSize() / 2;
        int width = gp.getScreenWidth() - (gp.getTileSize() * 4);
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
    
    

}
