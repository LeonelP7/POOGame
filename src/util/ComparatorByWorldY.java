/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import entity.Entity;
import java.util.Comparator;

/**
 *
 * @author ASUS TUF
 */
public class ComparatorByWorldY implements Comparator<Entity>{

    public ComparatorByWorldY() {
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        return Integer.compare(o1.getWorldY(), o2.getWorldY());
    }
    
}
