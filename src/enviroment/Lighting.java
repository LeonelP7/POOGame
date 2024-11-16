/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enviroment;

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

    public Lighting(GamePanel gp, int circleSize) {
        this.gp = gp;
        
        //crea la BufferedImage
        darknessFilter = new BufferedImage(gp.getScreenWidth(), gp.getScreenHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
        
        //creaun area rectangular del tamaño de la pantalla
        Area screenArea = new Area(new Rectangle2D.Double(0,0,gp.getScreenWidth(),gp.getScreenHeight()));
        
        //Obtenemos el centro en "x" y el centro en "y" del circulo de luz
        int centerX = gp.getPlayer().getScreenX() + (gp.getTileSize())/2;
        int centerY = gp.getPlayer().getScreenY() + (gp.getTileSize())/2;
        
        //obtenemos la posicion en "x" y "y" de la esquina superior izquierda del circulo de luz
        double x = centerX - (circleSize/2);
        double y = centerY - (circleSize/2);
        
        //crea la silueta del circulo de luz
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);
        
        //crea el area del circulo de luz
        Area lightArea = new Area(circleShape);
        
        
        //quita el area del circulo del area rectangulo de la pantalla
        screenArea.subtract(lightArea);
        
        //crea una efecto de graduacion dentro del circulo de luz
        Color color[] = new Color[5]; //colores para la graduacion
        float fraction[] = new float[5]; //distancia entre colores
        
        color[0] = new Color(0, 0, 0, 0f);
        color[1] = new Color(0, 0, 0, 0.25f);
        color[2] = new Color(0, 0, 0, 0.5f);
        color[3] = new Color(0, 0, 0, 0.75f);
        color[4] = new Color(0, 0, 0, 0.98f);
        
        //estos dos arreglos tienen que ser del mismo tamaño
        fraction[0] = 0f;
        fraction[1] = 0.25f;
        fraction[2] = 0.5f;
        fraction[3] = 0.75f;
        fraction[4] = 1f;
        
        //crea la configuracion de graduacion para el circulo de luz
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleSize/2), fraction, color);
        
        //coloca los datos del gradiente en g2
        g2.setPaint(gPaint);
        
        //dibuja el circulo de luz
        g2.fill(lightArea);
        
        
        //coloca color (negro) para dibujar el rectangulo
//        g2.setColor(new Color(0,0,0,0.95f));
        
        //dibuja el rectangulo sin el area del circulo de luz
        g2.fill(screenArea);
        
        g2.dispose();
    }
    
    public void draw(Graphics2D g2){
        
        g2.drawImage(darknessFilter, 0, 0, null);
    }
    
    
}
