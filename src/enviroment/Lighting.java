/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enviroment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class Lighting {
    
    private GamePanel gp;
    private BufferedImage darknessFilter;
    private int dayCounter;
    private float filterAlpha;
    
    //estado del dia
    private final int day = 0;
    private final int dusk = 1;
    private final int night = 2;
    private final int dawn = 3;
    private int dayState = day;
    
    public Lighting(GamePanel gp) {
        this.gp = gp;
        filterAlpha = 0f;
        setLightSource();
        
    }
    
    public void setLightSource() {
        //crea la BufferedImage
        darknessFilter = new BufferedImage(gp.getScreenWidth(), gp.getScreenHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
        
        if (gp.getPlayer().getCurrentLight() == null) {
            g2.setColor(new Color(0, 0, 1f, 0.98f));
        } else {
            //Obtenemos el centro en "x" y el centro en "y" del circulo de luz
            int centerX = gp.getPlayer().getScreenX() + (gp.getTileSize()) / 2;
            int centerY = gp.getPlayer().getScreenY() + (gp.getTileSize()) / 2;

            //crea una efecto de graduacion dentro del circulo de luz
            Color color[] = new Color[5]; //colores para la graduacion
            float fraction[] = new float[5]; //distancia entre colores
            
            color[0] = new Color(0, 0, 1f, 0f);
            color[1] = new Color(0, 0, 1f, 0.25f);
            color[2] = new Color(0, 0, 1f, 0.5f);
            color[3] = new Color(0, 0, 1f, 0.75f);
            color[4] = new Color(0, 0, 1f, 0.98f);

            //estos dos arreglos tienen que ser del mismo tamaño
            fraction[0] = 0f;
            fraction[1] = 0.25f;
            fraction[2] = 0.5f;
            fraction[3] = 0.75f;
            fraction[4] = 1f;

            //crea la configuracion de graduacion para el circulo de luz
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY,
                    gp.getPlayer().getCurrentLight().getLightRadius(), fraction, color);

            //coloca los datos del gradiente en g2
            g2.setPaint(gPaint);
        }
        
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        
        g2.dispose();
    }
    
    public void resetDay(){
        dayState = day;
        filterAlpha = 0f;
    }
    
    public void update(){
        if(gp.getPlayer().isLightUpdated()){
            setLightSource();
            gp.getPlayer().setLightUpdated(false);
        }
        
        //revisa el estado del dia
        if(dayState == day){
            dayCounter++;
            if (dayCounter > 600) {
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if(dayState == dusk){
            filterAlpha += 0.001f;
            if (filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if (dayState == night) {
            dayCounter++;
            if (dayCounter > 600) {
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if (dayState == dawn) {
            filterAlpha -= 0.001f;
            if (filterAlpha < 0) {
                filterAlpha = 0f;
                dayState = day;
            }
        }
        
    }
    
    public void draw(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        //DEBUG
        
        String situation = "";
        
        switch (dayState) {
            case day: situation = "Day"; break;
            case dusk: situation = "Dusk"; break;
            case night: situation = "Night"; break;
            case dawn: situation = "Dawn"; break;
        }
        
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.drawString(situation, 800, 500);
    }
    
}
