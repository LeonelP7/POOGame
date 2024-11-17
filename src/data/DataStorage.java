/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ASUS TUF
 */
public class DataStorage implements Serializable{
    
    /*
    atributos sin encapsular ya que solo los va a usar la clase SaveLoad
    con la que comparte paquete
    */
    //estado del jugador
    int level;
    int maxLife;
    int life;
    int strenth;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;
    
    //inventario del jugador
    ArrayList<String> itemsNames = new ArrayList<>();
    ArrayList<Integer> itemsAmounts = new ArrayList<>();
    
    int currentWeaponSlot;
    int currentShieldSlot;
    
    //objetos en el mapa
    String mapObjectNames[][];
    int mapObjectWorldX[][];
    int mapObjectWorldY[][];
    String mapObjectLootNames[][];
    boolean mapObjectOpened[][];
}
