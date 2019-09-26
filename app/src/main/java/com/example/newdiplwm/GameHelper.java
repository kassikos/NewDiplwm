package com.example.newdiplwm;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;


public class GameHelper {


    private static final String  rockDesc = "Επίλεξε κάθε φορά ποιο αντικείμενο (Πέτρα, Ψαλίδι, Χαρτί) νικά ή χάνει!";

    private static final String  numericDesc = "Σύγκρινε την αριστερή πλευρά με την δεξιά!";

    private static final String  MemoryDesc = "Θυμήσου σωστά το μοτίβο αριθμών!";

    private static final String  OsDesc = "Επίλεξε κάθε φορά διαφορετικό αντικείμενο!";

    private static final String  OgDesc = "Θυμήσου τα αντικείμενα της παραγγελίας!";

    private static final String  SuitcaseDesc = "Κλείσε τη βαλίτσα χωρίς να συγκρουστούν τα αντικείμενα που είναι μέσα!";

    private static final String  ShadowDesc = "Βρες το αντίστοιχο σκιασμένο αντικείμενο!";

    private static final String  PersonPickGameDesc = "Επίλεξε το σωστό συνδυασμό ρούχων σύμφωνα με το ηχητικό μήνυμα!";

    private static final String  SoundWordDesc = "Βρες το σωστό φθόγγο που ακούγεται στις λέξεις που εκφωνούνται!";

    private static final String  SoundimageDesc = "Βρες το σωστό αντικείμενο απο τον ήχο του!";

    private static final String  BucketDesc = "Τοποθέτησε τα αντικείμενα στα σωστά κουτιά!";

    private static final String RotationGameDesc = "Περίστρεψε σωστά τα αντικείμενα για να ταιριάξει το ζητούμενο σχήμα και χρώμα!";

    // private static final byte[] logoImageNumeric = GameHelper.getLogoImageToByteArray("/data/data/com.example.diplwm/images/numeric.png");//C:/Users/bill/AndroidStudioProjects/Diplwm/app


    private static HashMap<String, String> GameNamesHashmap = new HashMap<String, String>();




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

    public static void initHasmap(){
        GameNamesHashmap.put("Rock","Πέτρα Ψαλίδι Χαρτί");
        GameNamesHashmap.put("Calcution","Παιχνίδι Αριθμών");
        GameNamesHashmap.put("MemoryMatrix","Πίνακας Μνήμης");
        GameNamesHashmap.put("ObjectSelector","Επιλογή Αντικειμένων");
        GameNamesHashmap.put("OrderGame","Παιχνίδι Παραγγελιών");
        GameNamesHashmap.put("Suitcase","Κλείσιμο Βαλίτσας");
        GameNamesHashmap.put("ShadowGame","Παιχνίδι Σκιών");
        GameNamesHashmap.put("PersonPickGame","Συνδυασμοί Ρούχων");
        GameNamesHashmap.put("SoundWord","Παιχνίδι Φθόγγων");
        GameNamesHashmap.put("SoundImage","Ηχος - Εικόνα");
        GameNamesHashmap.put("RotationGame","Παιχνίδι Περιστροφής");
        GameNamesHashmap.put("BOX","Ταξινόμηση Αντικειμένων");

    }

    public static String getGreekName(String engName){
       return GameNamesHashmap.get(engName);

    }
    public static int sizeOfMap(){
        return GameNamesHashmap.size();
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
                new Game("PersonPickGame","memory",PersonPickGameDesc),
                new Game("SoundWord","memory",SoundWordDesc),
                new Game("SoundImage","memory",SoundimageDesc),
                new Game("RotationGame","memory",RotationGameDesc),
                new Game("BOX","memory",BucketDesc),
        };
    }


    public static class CSVWriter {
        private PrintWriter pw;
        private char separator;
        private char escapechar;
        private String lineEnd;
        private char quotechar;
        public static final char DEFAULT_SEPARATOR = ',';
        public static final char NO_QUOTE_CHARACTER = '\u0000';
        public static final char NO_ESCAPE_CHARACTER = '\u0000';
        public static final String DEFAULT_LINE_END = "\n";
        public static final char DEFAULT_QUOTE_CHARACTER = '"';
        public static final char DEFAULT_ESCAPE_CHARACTER = '"';
        public CSVWriter(Writer writer) {
            this(writer, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER,
                    DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END);
        }
        public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
            this.pw = new PrintWriter(writer);
            this.separator = separator;
            this.quotechar = quotechar;
            this.escapechar = escapechar;
            this.lineEnd = lineEnd;
        }
        public void writeNext(String[] nextLine) {
            if (nextLine == null)
                return;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nextLine.length; i++) {

                if (i != 0) {
                    sb.append(separator);
                }
                String nextElement = nextLine[i];
                if (nextElement == null)
                    continue;
                if (quotechar != NO_QUOTE_CHARACTER)
                    sb.append(quotechar);
                for (int j = 0; j < nextElement.length(); j++) {
                    char nextChar = nextElement.charAt(j);
                    if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
                        sb.append(escapechar).append(nextChar);
                    } else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
                        sb.append(escapechar).append(nextChar);
                    } else {
                        sb.append(nextChar);
                    }
                }
                if (quotechar != NO_QUOTE_CHARACTER)
                    sb.append(quotechar);
            }
            sb.append(lineEnd);
            pw.write(sb.toString());
        }
        public void close() throws IOException {
            pw.flush();
            pw.close();
        }
        public void flush() throws IOException {
            pw.flush();
        }
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
