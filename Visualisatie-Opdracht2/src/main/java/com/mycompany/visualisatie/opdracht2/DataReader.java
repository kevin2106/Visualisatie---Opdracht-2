/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visualisatie.opdracht2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin
 */
public class DataReader {
   public String reader() throws FileNotFoundException, IOException{
       ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("scatterdata.txt").getFile());
       BufferedReader br = new BufferedReader(new FileReader(file));    
           StringBuilder sb = new StringBuilder();
           String line  = br.readLine();
           
           while (line != null) {
           sb.append(line);
           sb.append(System.lineSeparator());
        line = br.readLine();
       }
           String data = sb.toString();
           br.close();
           return data;
   }
   
   public void parser() throws IOException{
       
       
       String data = reader();
       
       Scanner sc = new Scanner(data);
       while(sc.hasNextLine()){
           String line = sc.nextLine();
           Scanner lineReader = new Scanner(line);
            lineReader.useDelimiter(" ");

       while (lineReader.hasNext()) {
        System.out.println(lineReader.next());
        }
    }
       
   }
   
   
}
