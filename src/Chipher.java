import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

//Singleton class
public class Chipher {

    private int key;
    private Path inputPath;
    private Path outputPath;

    private static final Chipher CHIPHER = new Chipher();

    private Chipher() {
    }

    public static Chipher getInstance() {
        return CHIPHER;
    }

    public File encrypt(Path encryptingPath) {
        try {
            if (Files.exists(getOutputPath())) {
                Files.delete(getOutputPath());      //checking if file already exists
            }
            Files.createFile(getOutputPath());
        } catch (IOException e) {
            System.out.println("Файл не создался");
        }

        File encryptingFile = new File(String.valueOf(encryptingPath));
        File resultFile = new File(String.valueOf(getOutputPath()));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(encryptingFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFile))) {
            while (bufferedReader.ready()) {
                char[] inputChars = bufferedReader.readLine().toCharArray();
                char[] resultChars = new char[inputChars.length];
                boolean upperCaseFlag = false;
                for (int i = 0; i < inputChars.length; i++) {
                    if (Character.isUpperCase(inputChars[i])){
                        upperCaseFlag = true;
                        inputChars[i] = Character.toLowerCase(inputChars[i]);
                    }
                    if (Alphabet.ALPHABET.contains(inputChars[i])) {
                        switch (Validator.isKeyPositive()) {
                            case "Yes": {
                                if ((Alphabet.ALPHABET.indexOf(inputChars[i]) + key) > (Alphabet.ALPHABET.size() - 1)) {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + key - Alphabet.ALPHABET.size());
                                } else {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + key);
                                }
                                break;
                            }
                            case "No": {
                                if ((Alphabet.ALPHABET.indexOf(inputChars[i])) < Math.abs(getInstance().getKey())) {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + key + Alphabet.ALPHABET.size());
                                } else {
                                    resultChars[i] = (Character) Alphabet.ALPHABET.get(Alphabet.ALPHABET.indexOf(inputChars[i]) + key);
                                }
                                break;
                            }
                        }
                    } else resultChars[i] = inputChars[i]; //non-alphabet symbols will be not changed.
                    if (upperCaseFlag){
                        resultChars[i] = Character.toUpperCase(resultChars[i]);
                        inputChars[i] = Character.toUpperCase(inputChars[i]); //  - just to check is that feature working
                    }
                    upperCaseFlag = false;
                }
                bufferedWriter.write(resultChars);
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

    public File decrypt(File file) {
        return null;
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
}
