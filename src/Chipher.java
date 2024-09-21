import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Singleton class
public class Chipher {

    private int key;
    private int decryptKey;
    private Path inputPath;
    private Path outputPath;
    private boolean skippingForeignSymbols;
    private List<String> brutForceDecryptingWords = new ArrayList<>();

    private static final Chipher CHIPHER = new Chipher();

    private Chipher() {
    }

    public static Chipher getInstance() {
        return CHIPHER;
    }

    public File encrypt(Path encryptingPath) {
        Validator.isOutputFileExistsAndCreateIfNot(outputPath);

        File encryptingFile = new File(String.valueOf(encryptingPath));
        File resultFile = new File(String.valueOf(outputPath));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(encryptingFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFile))) {
            while (bufferedReader.ready()) {
                char[] inputChars = bufferedReader.readLine().toCharArray();                //reading line from file
                char[] resultChars = new char[inputChars.length];
                boolean upperCaseFlag = false;
                for (int i = 0; i < inputChars.length; i++) {
                    if (Character.isUpperCase(inputChars[i])) {                              //saving uppercase status
                        upperCaseFlag = true;
                        inputChars[i] = Character.toLowerCase(inputChars[i]);
                    }
                    if (Alphabet.ALPHABET.contains(inputChars[i])) {                        // encrypt for alphabetic symbols
                        switch (Validator.isKeyPositive()) {
                            case "Yes": { //encrypting when key > 0
                                if ((Alphabet.ALPHABET.indexOf(inputChars[i]) + key) > (Alphabet.ALPHABET.size() - 1)) {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + key - Alphabet.ALPHABET.size());
                                } else {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + key);
                                }
                                break;
                            }
                            case "No": {  //encrypting when key < 0
                                if ((Alphabet.ALPHABET.indexOf(inputChars[i])) < Math.abs(key)) {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + key + Alphabet.ALPHABET.size());
                                } else {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + key);
                                }
                                break;
                            }
                        }
                    } else {
                        if (isSkippingForeignSymbols())
                            resultChars[i] = inputChars[i]; //non-alphabet symbols encrypting option
                    }
                    if (upperCaseFlag) {
                        resultChars[i] = Character.toUpperCase(resultChars[i]);
                        //inputChars[i] = Character.toUpperCase(inputChars[i]); //  - just to check is that feature working
                    }
                    upperCaseFlag = false;
                }
                bufferedWriter.write(resultChars);                                      // write result to output file
                // console comparator of input-output (easy to compare texts):
                // System.out.print("Input:  " + String.valueOf(inputChars)+"\n");
                // System.out.print("Output: " + String.valueOf(resultChars)+"\n");
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так с файлом");
            e.printStackTrace();
        }
        return resultFile;
    }

    public File decrypt(Path decryptingPath) {
        Validator.isOutputFileExistsAndCreateIfNot(outputPath);
        decryptKey = key * (-1);

        File decryptingFile = new File(String.valueOf(decryptingPath));
        File resultFile = new File(String.valueOf(outputPath));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(decryptingFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFile))) {
            while (bufferedReader.ready()) {
                char[] inputChars = bufferedReader.readLine().toCharArray();                //reading line from file
                char[] resultChars = new char[inputChars.length];
                boolean upperCaseFlag = false;
                for (int i = 0; i < inputChars.length; i++) {
                    if (Character.isUpperCase(inputChars[i])) {                              //saving uppercase status
                        upperCaseFlag = true;
                        inputChars[i] = Character.toLowerCase(inputChars[i]);
                    }
                    if (Alphabet.ALPHABET.contains(inputChars[i])) {                        // decrypt for alphabetic symbols
                        switch (Validator.isKeyPositive()) {
                            case "Yes": { // decrypting when key > 0
                                if ((Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey) < 0) {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey + Alphabet.ALPHABET.size());
                                } else {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey);
                                }
                                break;
                            }
                            case "No": {  // decrypting when key < 0
                                if (((Alphabet.ALPHABET.indexOf(inputChars[i])) + decryptKey) > Alphabet.ALPHABET.size()) {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey - Alphabet.ALPHABET.size());
                                } else {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey);
                                }
                                break;
                            }
                        }
                    } else {
                        if (isSkippingForeignSymbols())
                            resultChars[i] = inputChars[i]; //non-alphabet symbols decrypt option
                    }
                    if (upperCaseFlag) {
                        resultChars[i] = Character.toUpperCase(resultChars[i]);
                        inputChars[i] = Character.toUpperCase(inputChars[i]); //  - just to check is that feature working
                    }
                    upperCaseFlag = false;
                }
                bufferedWriter.write(resultChars);                                      // write result yo output file
                // console comparator of input-output (easy to compare texts):
                // System.out.print("Input:  " + String.valueOf(inputChars)+"\n");
                // System.out.print("Output: " + String.valueOf(resultChars)+"\n");
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так с файлом");
            e.printStackTrace();
        }
        return resultFile;
    }

    public File brutForceDecrypt() {

        Validator.isOutputFileExistsAndCreateIfNot(outputPath);

        File decryptingFile = new File(String.valueOf(inputPath));
        File resultFile = new File(String.valueOf(outputPath));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(decryptingFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFile))) {
            while (bufferedReader.ready()) {
                char[] inputChars = bufferedReader.readLine().toCharArray();                //reading line from file
                char[] resultChars = new char[inputChars.length];
                boolean upperCaseFlag = false;

                for (key = (Alphabet.ALPHABET.size() * (-1)) +1; key < Alphabet.ALPHABET.size(); key++) {
                    decryptKey = key * (-1);

                    for (int i = 0; i < inputChars.length; i++) {
                        if (Character.isUpperCase(inputChars[i])) {                              //saving uppercase status
                            upperCaseFlag = true;
                            inputChars[i] = Character.toLowerCase(inputChars[i]);
                        }
                        if (Alphabet.ALPHABET.contains(inputChars[i])) {                        // decrypt for alphabetic symbols
                            switch (Validator.isKeyPositive()) {
                                case "Yes": { // decrypting when key > 0
                                    if ((Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey) < 0) {
                                        resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey + Alphabet.ALPHABET.size());
                                    } else {
                                        resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey);
                                    }
                                    break;
                                }
                                case "No": {  // decrypting when key < 0
                                    if (((Alphabet.ALPHABET.indexOf(inputChars[i])) + decryptKey) > Alphabet.ALPHABET.size()) {
                                        resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey - Alphabet.ALPHABET.size());
                                    } else {
                                        resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + decryptKey);
                                    }
                                    break;
                                }
                            }
                        } else {
                            if (isSkippingForeignSymbols())
                                resultChars[i] = inputChars[i]; //non-alphabet symbols decrypt option
                        }

//                        String resultString = String.valueOf(resultChars);
//                        for (String searchingString : searchingStrings) {
//                            if (resultString.contains(searchingString)){
//                                System.out.println("Bingo, i found: " + searchingString + " with encrypting key: " + key);
//                                System.out.println("Decrypting key is: " + decryptKey);
//                            }
//                        }

                        if (upperCaseFlag) {
                            resultChars[i] = Character.toUpperCase(resultChars[i]);
                            inputChars[i] = Character.toUpperCase(inputChars[i]); //  - just to check is that feature working
                        }
                        upperCaseFlag = false;
                    }
                }
                bufferedWriter.write(resultChars);                                      // write result yo output file
                // console comparator of input-output (easy to compare texts):
                // System.out.print("Input:  " + String.valueOf(inputChars)+"\n");
                // System.out.print("Output: " + String.valueOf(resultChars)+"\n");
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так с файлом");
            e.printStackTrace();
        }
        return resultFile;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public void setInputPath(Path inputPath) {
        this.inputPath = inputPath;
    }

    public Path getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }

    public boolean isSkippingForeignSymbols() {
        return skippingForeignSymbols;
    }

    public void setSkippingForeignSymbols(boolean skippingForeignSymbols) {
        this.skippingForeignSymbols = skippingForeignSymbols;
    }
    public List<String> getBrutForceDecryptingWords() {
        return brutForceDecryptingWords;
    }

    public void setBrutForceDecryptingWords(List<String> brutForceDecryptingWords) {
        this.brutForceDecryptingWords = brutForceDecryptingWords;
    }
}
