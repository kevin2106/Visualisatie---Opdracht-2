/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visualisatie.opdracht2;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PImage;
import processing.core.PApplet;

/**
 *
 * @author Kevin
 */
public class Window extends PApplet{
    
    ArrayList<DataModel> data;
        
    PImage image;
    
   public void setup(){
       size(800, 500);  
       DataReader dr = new DataReader();
        try {
            data = dr.parser();
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
} 
   public void draw(){ 
       background(224);  
       line(85, 10, 85, 450);
       line(85, 450, 750, 450);
       drawScatter(data);
   }  
   
   public void drawScatter(ArrayList<DataModel> data){
       for(DataModel datalist : data ){
           int cat = datalist.getCat();
           int eig1 = datalist.getEig1();
           float eig2 = datalist.getEig2();
           point(eig1, eig2);
       }
       
   }
}
