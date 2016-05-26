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
public class Window extends PApplet {

    ArrayList<DataModel> data;
    int[] position = {100, 400};
    int width = 300;
    int length = 300;
    
    int x1, x2, x3;
    int y1, y2, y3;
    
    int[] regressionValue;
    
    int[] minMaxEig1;
    float[] minMaxEig2;
    ArrayList<DataModel> mappedData;
    PImage image;

    public void setup() {
        size(800, 500);
        DataReader dr = new DataReader();
        try {
            data = dr.parser();
            mappedData = mapData(data, position, length, width);
            regressionValue = calcRegressionLine(data);
            calcAxis();
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void draw() {
        drawScatterGraph(position, length, width, "Eig1", "Eig2");
    }

    private void calcAxis() {
        ArrayList<Integer> eig1 = new ArrayList();
        ArrayList<Float> eig2 = new ArrayList();

        for (DataModel datas : data) {
            eig1.add(datas.getEig1());
            eig2.add(datas.getEig2());
        }

        int[] minMaxEig1 = getMinMaxInteger(eig1);
        float[] minMaxEig2 = getMinMaxFloat(eig2);
        
        x1 = minMaxEig1[0];
        x2 = ((minMaxEig1[0] + minMaxEig1[1]) / 2);
        x3 = minMaxEig1[1];
        
        y1 = (int) minMaxEig2[0];
        y2 = (int) ((minMaxEig2[0] + minMaxEig2[1]) / 2);
        y3 = (int) minMaxEig2[1];
    }
    
    public void drawScatterGraph(int[] position, int length, int width, String xLabel, String yLabel) {
        line(position[0], position[1], position[0] + width, position[1]);
        line(position[0], position[1], position[0], position[1] - length);

        fill(0, 0, 0);

        text(xLabel, position[0] + width + 10, position[1]);
        text(yLabel, position[0], position[1] - length - 10);

        text(x1, position[0], position[1] + 15);
        text(x2, position[0] + (0.50f * width), position[1] + 15);
        text(x3, position[0] + width, position[1] + 15);

        text(y1, position[0] - 30, position[1]);
        text(y2, position[0] - 30, position[1] - (0.5f * length));
        text(y3, position[0] - 30, position[1] - length);
        

        for (DataModel mapData : mappedData) {

            point(mapData.getEig1(), mapData.getEig2());

        }

        drawRegressionLine(position, length, width, minMaxEig1, minMaxEig2);
    }
    
    public int[] calcRegressionLine(ArrayList<DataModel> data){
        ArrayList<Integer> eig1 = new ArrayList();
        ArrayList<Float> eig2 = new ArrayList();
          
        for (DataModel datas : data) {
            eig1.add(datas.getEig1());
            eig2.add(datas.getEig2());
        }
        
        int[] minMaxX = getMinMaxInteger(eig1);
        float[] minMaxY = {0, 0};
        
        for(DataModel datas : data){
            if(datas.getEig1() == minMaxX[0]){
                minMaxY[0] = datas.getEig2();    
            }
            
            if(datas.getEig1() == minMaxX[1]){
                minMaxY[1] = datas.getEig2();
            }
        }      
        
        int x = eig1.get(0);
        float y = eig2.get(0);
        
        float a = ((minMaxY[1] - minMaxY[0]) / (minMaxX[1] - minMaxX[0]));
        float temp = a * x; 
        float b = (temp * -1) + y;

        
        
        int beginPosY =  (int) ((a * minMaxX[0]) + b);
        System.out.println(beginPosY);
        int temp2 = (int) (minMaxY[0] - b);
        int beginPosX = (int) (temp2 / a);
        //int beginPosX =  (int) ((a * minMaxY[0]) + b);
        
        int endPosY =  (int) ((a * minMaxX[1]) + b);
        System.out.println(beginPosY);
        int temp3 = (int) (minMaxY[1] - b);
        int endPosX = (int) (temp3 / a);

     
        int[] regressionValues = {beginPosX, beginPosY, endPosX, endPosY};
        System.out.println(regressionValues[0] + " : " + regressionValues[1]);
        
        return regressionValues;
    }

    public void drawRegressionLine(int[] position, int length, int width, int[] minMaxEig1, float[] minMaxEig2){
  
        
        int mappedBeginX = (int) map(regressionValue[0], minMaxEig1[0], minMaxEig1[1], position[0], position[0] + width);
        int mappedBeginY = (int) map(regressionValue[1], minMaxEig2[0], minMaxEig2[1], position[1], position[1] - length);
        
        int mappedEndX = (int) map(regressionValue[2], minMaxEig1[0], minMaxEig1[1], position[0], position[0] + width);
        int mappedEndY = (int) map(regressionValue[3], minMaxEig2[0], minMaxEig2[1], position[1], position[1] - length);
        
        System.out.println(mappedBeginX + " : " + mappedBeginY);
        
        line(mappedBeginX, mappedBeginY, mappedEndX, mappedEndY);
    }
    
    public ArrayList<DataModel> mapData(ArrayList<DataModel> data, int[] position, int length, int width) {
        ArrayList<Integer> eig1 = new ArrayList();
        ArrayList<Float> eig2 = new ArrayList();

        for (DataModel datas : data) {
            eig1.add(datas.getEig1());
            eig2.add(datas.getEig2());
        }

        minMaxEig1 = getMinMaxInteger(eig1);
        minMaxEig2 = getMinMaxFloat(eig2);

        ArrayList<DataModel> mapped = new ArrayList();

        for (DataModel datas : data) {
            DataModel model = new DataModel();
            int mappedEig1 = (int) map(datas.getEig1(), minMaxEig1[0], minMaxEig1[1], position[0], position[0] + width);
            float mappedEig2 = map(datas.getEig2(), minMaxEig2[0], minMaxEig2[1], position[1], position[1] - length);
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
