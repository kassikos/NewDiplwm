package com.example.newdiplwm;


import android.content.res.Resources;
import android.graphics.Color;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class GameHelper {

    //private static final byte[] logoImageRock = GameHelper.getLogoImageToByteArray("/data/data/com.example.diplwm/images/stone.png");

    private static final String  rockDesc = "Παίξε Πέτρα, Ψαλίδι, Χαρτί και επέλεξε σωστά ποίος κερδίζει ή χάνει!";

    private static final String  numericDesc = "Σύγκρινε την αριστερή πλευρά με την δεξία!";

    private static final String  MemoryDesc = "Θυμήσου σωστά το μοτίβο αριθμών!";

    private static final String  OsDesc = "Επέλεξε κάθε φορα καινούριο αντικείμενο!";

    private static final String  OgDesc = "Θυμήσου τα αντικείμενα της παραγγελίας!";

    private static final String  SuitcaseDesc = "Κλείσε τη βαλίτσα χωρίς να συγκρουστούν τα αντικέιμενα που είναι μέσα!";
    
    private static final String  ShadowDesc = "Βρες το σκιασμένο αντικείμενο!";

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
                new Game("Rock","skill", rockDesc),
                new Game("Calcution","skill",numericDesc),
                new Game("MemoryMatrix","memory",MemoryDesc),
                new Game("ObjectSelector","memory",OsDesc),
                new Game("OrderGame","memory",OgDesc),
                new Game("Suitcase","attention",SuitcaseDesc),
                new Game("ShadowGame","memory",ShadowDesc),

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
