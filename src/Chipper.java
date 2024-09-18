import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//Singleton class
public class Chipper {

    private int key; //добавить параметр на остаток от деления
    private Path inputPath;
    private Path outputPath;

    private static final Chipper chipper = new Chipper();

    private Chipper(){}

    public static Chipper getInstance(){
        return chipper;
    }

    public File encrypt(Path encryptingPath){
        try {
            if (Files.exists(getOutputPath())){
                Files.delete(getOutputPath());      //checking if file already exists
            }
            Files.createFile(getOutputPath());
        } catch (IOException e){
            System.out.println("Файл не создался");
        }

        File encryptingFile = new File(String.valueOf(encryptingPath));
        File resultFile = new File(String.valueOf(getOutputPath()));

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(encryptingFile));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFile))){
            while (bufferedReader.ready()){
                char[] inputChars = bufferedReader.readLine().toCharArray();
                char[] resultChars = new char[inputChars.length];
                for (int i = 0; i < inputChars.length; i++) {
                    if (Alphabet.ALPHABET.contains(inputChars[i])){
                        if (Alphabet.ALPHABET.size() > Alphabet.ALPHABET.indexOf(inputChars[i] + key)){
                            int j = Alphabet.ALPHABET.indexOf(inputChars[i]) + key - Alphabet.ALPHABET.size();
                            resultChars[i] = (Character) Alphabet.ALPHABET.get(j);
                        }
                    }
                    bufferedWriter.write(resultChars);
                }
            }
        } catch (Exception e){
            System.out.println("Что-то пошло не так с файлом");
        }
        return resultFile;
    }

    public File decrypt(File file){
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
