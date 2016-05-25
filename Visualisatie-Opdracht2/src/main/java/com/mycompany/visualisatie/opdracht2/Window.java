/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visualisatie.opdracht2;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PImage;
import processing.core.PApplet;

/**
 *
 * @author Kevin
 */
public class Window extends PApplet {

    ArrayList<DataModel> data;
    int[] position = {100, 400};
    int width = 300;
    int length = 300;
    ArrayList<DataModel> mappedData;
    PImage image;

    public void setup() {
        size(800, 500);
        DataReader dr = new DataReader();
        try {
            data = dr.parser();
            mappedData = mapData(data, position, length, width);
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void draw() {
        drawScatterGraph(position, length, width, "Eig1", "Eig2");

    }

    public void drawScatterGraph(int[] position, int length, int width, String xLabel, String yLabel) {
        line(position[0], position[1], position[0] + width, position[1]);
        line(position[0], position[1], position[0], position[1] - length);
        
        fill(0,0,0);
        
        text(xLabel, position[0] + width + 10, position[1]);
        text(yLabel, position[0], position[1] - length - 10);
        
        for (DataModel mapData : mappedData) {

            point(mapData.getEig1(), mapData.getEig2());

        }

    }

    public ArrayList<DataModel> mapData(ArrayList<DataModel> data, int[] position, int length, int width) {
        ArrayList<Integer> eig1 = new ArrayList();
        ArrayList<Float> eig2 = new ArrayList();

        for (DataModel datas : data) {
            eig1.add(datas.getEig1());
            eig2.add(datas.getEig2());
        }

        int[] minMaxEig1 = getMinMaxInteger(eig1);
        float[] minMaxEig2 = getMinMaxFloat(eig2);

        ArrayList<DataModel> mapped = new ArrayList();
        
        for (DataModel datas : data) {
            DataModel model = new DataModel();
            int mappedEig1 = (int) map(datas.getEig1(), minMaxEig1[0], minMaxEig1[1], position[0], position[0] + width);
            float mappedEig2 = map(datas.getEig2(), minMaxEig2[0], minMaxEig2[1], position[1], position[1] - length);
            System.out.println("X: " + mappedEig1);
            System.out.println("Y: " + mappedEig2);
            model.setEig1(mappedEig1);
            model.setEig2(mappedEig2);

            mapped.add(model);

        }

        return mapped;
    }

    public float[] getMinMaxFloat(ArrayList<Float> data) {
        float min = data.get(0);
        float max = data.get(0);

        for (float i : data) {
            if (i < min) {
                min = i;
            }
            if (i > max) {
                max = i;
            }
        }

        float[] minMax = {min, max};

        return minMax;
    }
    
    public int[] getMinMaxInteger(ArrayList<Integer> data) {
        int min = data.get(0);
        int max = data.get(0);

        for (int i : data) {
            if (i < min) {
                min = i;
            }
            if (i > max) {
                max = i;
            }
        }

        int[] minMax = {min, max};

        return minMax;
    }
}
