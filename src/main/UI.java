/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import object.OBJ_Key;

public class UI {

    private GamePanel gp;
    private Graphics2D g2;
    private Font arial_30;
    private Font arial_70B;
//    private BufferedImage keyImage;
    private boolean messageOn = false;
    private String message = "";
    private int messageCounter;
    private boolean gameFinished = false;

    private double playTime;
    private DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_70B = new Font("Arial", Font.BOLD, 70);
//        OBJ_Key objKey = new OBJ_Key(gp);
//        keyImage = objKey.getImage();
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        
        g2.setFont(arial_30);
        g2.setColor(Color.white);
        
        if(gp.getGameState() == gp.getPlayState()){
            //hacer las cosas del gamestate despues
        }
        if(gp.getGameState() == gp.getPauseState()){
            drawPauseScreen();
        }
        
    }
    
    public void drawPauseScreen(){
        
        String text = "PAUSA";
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        
        int x = getXForCenteredText(text);

        int y = gp.getScreenHeight()/2;
        
        g2.drawString(text,x,y);
    }

    public int getXForCenteredText(String text){
        
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.getScreenWidth()/2 - length/2;
        return x;
    }
    
    
    //GETTERS Y SETTERS
    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public Font getArial_30() {
        return arial_30;
    }

    public void setArial_30(Font arial_30) {
        this.arial_30 = arial_30;
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

}
