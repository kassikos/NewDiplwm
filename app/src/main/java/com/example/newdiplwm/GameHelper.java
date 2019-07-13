package com.example.newdiplwm;


import android.graphics.Color;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class GameHelper {

    private static final String  rockDesc = "Εμφανίζονται δύο επιλογές στο χρήστη (Πέτρα, Ψαλίδι, Χαρτί) και ο χρήστης πρέπει να επιλέξει ποία πλευρά νικάει ή χάνει, ανάλογα με την ερώτηση.";

    //private static final byte[] logoImageRock = GameHelper.getLogoImageToByteArray("/data/data/com.example.diplwm/images/stone.png");

    private static final String  numericDesc = "Ο χρήστης πρέπει να επιλέξει για το αν η αριστερή πλευρά είναι μεγαλύτερη/μικρότερη/ίση από την δεξιά.";

    private static final String  MemoryDesc = "Στην οθόνη εμφανίζεται ένα μοτίβο αριθμών, το οποίο μετά εξαφανίζεται και ο χρήστης καλείται να επιλέξει με τη σειρά τους αριθμούς που εμφανίστηκαν.";

    private static final String  OsDesc = "περιγραφή παιχνιδιού";
    // private static final byte[] logoImageNumeric = GameHelper.getLogoImageToByteArray("/data/data/com.example.diplwm/images/numeric.png");//C:/Users/bill/AndroidStudioProjects/Diplwm/app





    public static byte[] getLogoImageToByteArray(String url) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        File imageFile = new File(url);
        FileInputStream fis =null;
        int bytesRead;

        byte[] chunk = new byte[4096];
        try {
            fis = new FileInputStream(imageFile);

            while ((bytesRead = fis .read(chunk)) > 0) {//!= -1
                outputStream.write(chunk, 0, bytesRead);
            }
            fis.close();
            chunk = outputStream.toByteArray();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return chunk;//outputStream.toByteArray();

//        File imagefile = new File(url);
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(imagefile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Bitmap bm = BitmapFactory.decodeStream(fis);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
//        byte[] b = baos.toByteArray();
//        return b;

    }



    public static Game[] createInstances(){
        return new Game[]{
                new Game("Rock","skill",rockDesc),
                new Game("Calcution","skill",numericDesc),
                new Game("MemoryMatrix","memory",MemoryDesc),
                new Game("ObjectSelector","memory",OsDesc),
                new Game("OrderGame","memory",OsDesc),
                new Game("Suitcase","attention",OsDesc),
                new Game("ShadowGame","memory",OsDesc),

        };

    }


   /* public void countPoints()
    {

        int currentPoints=0;

        if (!missPoints && trueCounter == 1)
        {
            currentPoints += 10;
            currentPoints += pointsHashMap.get(currentDifficulty);
        }
        else if(!missPoints && trueCounter == 2){
            currentPoints += 20;
            currentPoints += pointsHashMap.get(currentDifficulty);
        }
        else if (!missPoints && trueCounter >= 3)
        {
            currentPoints += 30;
            currentPoints += pointsHashMap.get(currentDifficulty);
        }
        else if (missPoints)
        {
            trueCounter = 0;
        }
        totalPoints += currentPoints;
        test.setText("+ " +String.valueOf(currentPoints));
        if (currentPoints == 0)
        {
            test.setTextColor(Color.RED);
        }
        else
            test.setTextColor(Color.GREEN);
    }*/



    //gia na thn kaneis set se ena  image view prepei na einai bitmap
    //Bitmap bitmap = BitmapFactory.decodeByteArray(logoImage , 0, logoImage .length);

}
