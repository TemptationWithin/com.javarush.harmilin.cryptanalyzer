import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

//Singleton class
public class Chipper {

    private int key; //добавить параметр на остаток от деления
    private Path inputPath;
    private Path outputPath;

    public static final Chipper chipper = new Chipper();

    private Chipper(){}

    public static Chipper getInstance(){
        return chipper;
    }

    public File encrypt(File encryptingFile){
        try {
            if (Files.exists(getOutputPath())){
                Files.delete(getOutputPath());      //checking if file already exists
            }
            Files.createFile(getOutputPath());
        } catch (IOException e){
            System.out.println("Файл не создался");
        }


        File resultFile = new File(String.valueOf(getOutputPath()));
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(encryptingFile));){

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
