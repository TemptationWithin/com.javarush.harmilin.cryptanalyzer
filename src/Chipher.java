import java.io.*;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;

//Singleton class
public class Chipher {

    private int key;
    private int decryptKey;
    private int statisticTrueKey;
    private Path inputPath;
    private Path outputPath;
    private boolean skippingForeignSymbols;
    private List<String> brutForceDecryptingWords = new ArrayList<>();
    List alphabet = Alphabet.ALPHABET_RUS;
    List<String> popularParts_RUS = Alphabet.POPULAR_PARTS_RUS_2;
    private List<Double> statiscticPercents = new ArrayList<>();
    Map<Integer, Integer> matchesMap = new HashMap<>();

    private static final Chipher CHIPHER = new Chipher();

    private Chipher() {
    }

    public static Chipher getInstance() {
        return CHIPHER;
    }

    public File encrypt(Path encryptingPath, Path outputPath, int key) {
        Validator.isOutputFileExistsAndCreateIfNot(outputPath);

        File encryptingFile = new File(String.valueOf(encryptingPath));
        File resultFile = new File(String.valueOf(outputPath));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(encryptingFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFile))) {
            while (bufferedReader.ready()) {
                char[] inputChars = bufferedReader.readLine().toCharArray();                //reading line from file
                char[] resultChars = encryptProcess(inputChars, key);                        // encrypting process
                bufferedWriter.write(resultChars);                                      // write result to output file
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так с файлом");
            e.printStackTrace();
        }
        return resultFile;
    }

    public File decrypt(Path decryptingPath, Path outputPath, int encryptKey) {
        Validator.isOutputFileExistsAndCreateIfNot(outputPath);
        decryptKey = encryptKey * (-1);

        File decryptingFile = new File(String.valueOf(decryptingPath));
        File resultFile = new File(String.valueOf(outputPath));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(decryptingFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFile))) {
            while (bufferedReader.ready()) {
                char[] inputChars = bufferedReader.readLine().toCharArray();                //reading line from file
                char[] resultChars = decryptProcess(inputChars, encryptKey);
                bufferedWriter.write(resultChars);                                      // write result to output file
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так с файлом");
            e.printStackTrace();
        }
        return resultFile;
    }

    public File brutForceDecrypt(Path inputPath, Path outputPath) {

        Validator.isOutputFileExistsAndCreateIfNot(outputPath);

        File decryptingFile = new File(String.valueOf(inputPath));
        File resultFile = new File(String.valueOf(outputPath));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(decryptingFile))) {
            while (bufferedReader.ready()) {
                char[] inputChars = bufferedReader.readLine().toCharArray();                //reading line from file

                for (key = ((-1) * (alphabet.size()) + 1); key < alphabet.size(); key++) {
                    decryptKey = key * (-1);
                    boolean bingo = false;

                    String resultString = String.valueOf(decryptProcess(inputChars, key));       //decryptProcess

                    for (String searchingString : brutForceDecryptingWords) {               // looking for matches
                        if (resultString.contains(searchingString.toLowerCase())) {
                            bingo = true;
                            System.out.print("Bingo, i found: \"" + searchingString + "\" with encrypting key: " + key + ". ");
                            System.out.println("Decrypting key is: " + decryptKey);
                        }
                    }
                    if (bingo) {
                        Chipher.getInstance().decrypt(inputPath, outputPath, key);                          // decrypt through "bingo" key
                        break;
                    } else if (key == alphabet.size() - 1) {
                        System.out.println("Не было найдено ни одного введенного слова.");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так с файлом");
            e.printStackTrace();
        }
        return resultFile;
    }

    public void statisticalAnalysisDecrypt(Path inputPath, Path outputPath) {
        Validator.isOutputFileExistsAndCreateIfNot(outputPath);

        File decryptingFile = new File(String.valueOf(inputPath));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(decryptingFile))) {
            while (bufferedReader.ready()) {
                char[] inputChars = bufferedReader.readLine().toCharArray();                //reading line from file
                int matches = 0;
                for (key = 0; key < alphabet.size(); key++) {
                    decryptKey = key * (-1);
                    matchesMap.put(key, matches);

                    String resultString = String.valueOf(decryptProcess(inputChars, key));       //decryptProcess

                    for (String searchingString : popularParts_RUS) {                      // looking for matches
                        if (resultString.contains(searchingString.toLowerCase())) {
                            matches++;
                        }
                    }
                    matchesMap.put(key, matches);
                    if (Collections.max(matchesMap.values()) == 0) {                                       // if no matches
                        System.out.println("Не было найдено ни одного совпадения.");
                        break;
                    }
                    matches = 0;
                }

                double sumMatches = 0;
                for (Map.Entry<Integer, Integer> entry : matchesMap.entrySet()) {
                    sumMatches = sumMatches + entry.getValue();
                }

                System.out.println("Обнаружено " + sumMatches + " совпадений:");          //total sum of matching parts
                System.out.println("Ключ   | Совпадений | Вероятность");
                System.out.println("---".repeat(10));
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                for (Map.Entry<Integer, Integer> entry : matchesMap.entrySet()) {
                    statiscticPercents.add(entry.getValue()/sumMatches);
                    System.out.println(entry.getKey() + "      |   " + entry.getValue()+ "   |  " + Double.valueOf(decimalFormat.format(entry.getValue()/sumMatches*100)) +"% |");
                    System.out.println("---".repeat(10));
                }
                for (Map.Entry<Integer, Integer> entry : matchesMap.entrySet()) {
                    if (entry.getValue().equals(Collections.max(matchesMap.values()))){
                        statisticTrueKey = entry.getKey();
                        break;
                    }
                }

                decrypt(inputPath, outputPath, statisticTrueKey);
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так с файлом");
            e.printStackTrace();
        }

    }

    private char[] encryptProcess(char[] inputChars, int key) {
        boolean upperCaseFlag = false;
        char[] resultChars = new char[inputChars.length];
        for (int i = 0; i < inputChars.length; i++) {
            if (Character.isUpperCase(inputChars[i])) {                              //saving uppercase status
                upperCaseFlag = true;
                inputChars[i] = Character.toLowerCase(inputChars[i]);
            }
            if (alphabet.contains(inputChars[i])) {                        // encrypt for alphabetic symbols
                switch (Validator.isKeyPositive()) {
                    case "Yes": { //encrypting when key > 0
                        if ((alphabet.indexOf(inputChars[i]) + key) > (alphabet.size() - 1)) {
                            resultChars[i] = (Character) alphabet.get(alphabet.indexOf(inputChars[i]) + key - alphabet.size());
                        } else {
                            resultChars[i] = (Character) alphabet.get(alphabet.indexOf(inputChars[i]) + key);
                        }
                        break;
                    }
                    case "No": {  //encrypting when key < 0
                        if ((alphabet.indexOf(inputChars[i])) < Math.abs(key)) {
                            resultChars[i] = (Character) alphabet.get(alphabet.indexOf(inputChars[i]) + key + alphabet.size());
                        } else {
                            resultChars[i] = (Character) alphabet.get(alphabet.indexOf(inputChars[i]) + key);
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
            }
            upperCaseFlag = false;
        }
        return resultChars;
    }

    private char[] decryptProcess(char[] inputChars, int key) {
        int decryptKey = key * (-1);
        boolean upperCaseFlag = false;
        char[] resultChars = new char[inputChars.length];
        for (int i = 0; i < inputChars.length; i++) {
            if (Character.isUpperCase(inputChars[i])) {                              //saving uppercase status
                upperCaseFlag = true;
                inputChars[i] = Character.toLowerCase(inputChars[i]);
            }
            if (alphabet.contains(inputChars[i])) {                                  // decrypt for alphabetic symbols
                switch (Validator.isKeyPositive()) {
                    case "Yes": { // decrypting when key > 0
                        if ((alphabet.indexOf(inputChars[i]) + decryptKey) < 0) {
                            resultChars[i] = (Character) alphabet.get(alphabet.indexOf(inputChars[i]) + decryptKey + alphabet.size());
                        } else {
                            resultChars[i] = (Character) alphabet.get(alphabet.indexOf(inputChars[i]) + decryptKey);
                        }
                        break;
                    }
                    case "No": {  // decrypting when key < 0
                        if (((alphabet.indexOf(inputChars[i])) + decryptKey) > alphabet.size() - 1) {
                            resultChars[i] = (Character) alphabet.get(alphabet.indexOf(inputChars[i]) + decryptKey - alphabet.size());
                        } else {
                            resultChars[i] = (Character) alphabet.get(alphabet.indexOf(inputChars[i]) + decryptKey);
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
        return resultChars;
    }

    public void printInfo() {
        System.out.println("Входящий путь:   " + getInputPath());
        System.out.println("Путь для записи: " + getOutputPath());
        if(!matchesMap.isEmpty()){
            System.out.println("Ключ шифрования: " + statisticTrueKey);
        }else System.out.println("Ключ шифрования: " + getKey());
        System.out.println("Ключ дешивровки: " + getDecryptKey());
        if (!getBrutForceDecryptingWords().isEmpty()) {
            System.out.print("Слова поиска brutForce: " + getBrutForceDecryptingWords());
        }
        if (!matchesMap.isEmpty()){

            System.out.println("Статистический анализ показал что ");
            System.out.println("Наилучший показатель: " + Collections.max(statiscticPercents)*100 + "% - " +
                    "(" + Collections.max(matchesMap.values()) + "шт.) совпадений при ключе: " + statisticTrueKey);
        }
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

    public int getDecryptKey() {
        return decryptKey;
    }
}
