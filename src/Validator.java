import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator {

    private static int userOptionChoice;

    public static int getUserOptionChoice() {
        return userOptionChoice;
    }

    public static boolean isChoiceValid(String number) {
        int i = 0;
        try {
            i = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("А я ведь просил число..");
        }
        switch (i) {
            case 1, 2, 3, 4, 5: {
                userOptionChoice = i;
                return true;
            }
            default:
                return false;
        }
    }

    public static boolean isOptionForNonAlphabeticSymbolsValid(String number) {
        int i = 0;
        try {
            i = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("А я ведь просил число..");
        }
        switch (i) {
            case 1: {
                Chipher.getInstance().setSkippingForeignSymbols(true);
                return true;
            }
            case 2: {
                Chipher.getInstance().setSkippingForeignSymbols(false);
                return true;
            }
            default:
                return false;
        }
    }

    public static boolean isKeyValid(String number) {
        int i = 0;
        try {
            i = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("А я ведь просил число..");
        }
        if (i == 0) {
            System.out.println("Ну с таким ключом мы далеко не уйдем. Наврядли вам нужна обычная копия файла..");
            return false;
        } else if (Math.abs(i) > Alphabet.ALPHABET.size() - 1) {
            System.out.println("Давайте отбросим полные обороты ключа. Ваш ключ эквивалентен: " + i % Alphabet.ALPHABET.size());
            Chipher.getInstance().setKey(i % Alphabet.ALPHABET.size());
            return true;
        } else {
            Chipher.getInstance().setKey(i);
            return true;
        }
    }

    public static String isKeyPositive() {
        if (Chipher.getInstance().getKey() > 0) {
            return "Yes";
        } else return "No";
    }

    public static boolean isInputFileExists(String s) {
        Path path = Path.of(s);
        path = path.normalize();
        if (Files.isRegularFile(path)) {
            Chipher.getInstance().setInputPath(path);
            return true;
        } else return false;
    }
    public static void isOutputFileExistsAndCreateIfNot(Path path){
        try {
            path = path.normalize();
            if (Files.exists(path)) {
                Files.delete(path);      //checking if file already exists
            }
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Файл не создался");
        }
    }

    public static boolean isOutputPathValid(String s) {
        Path path = Path.of(s);
        // if entered same path or pressed enter - we create new file based on input absolute path
        if (s.isEmpty() || path.startsWith(Chipher.getInstance().getInputPath())) {
            StringBuilder string = new StringBuilder(String.valueOf(Chipher.getInstance().getInputPath()));
            if (userOptionChoice == 1){
                string.replace(string.length() - 4, string.length(), "Encrypted.txt");
            } else if (userOptionChoice == 2){
                string.replace(string.length() - 4, string.length(), "Decrypted.txt");
            }
            Chipher.getInstance().setOutputPath(Path.of(String.valueOf(string)));
            System.out.println(Chipher.getInstance().getOutputPath()); //console path
            return true;
        } else {
            path = path.normalize();
            if (path.isAbsolute()) {
                Chipher.getInstance().setOutputPath(path);
                System.out.println(Chipher.getInstance().getOutputPath()); //checking path
                return true;
            }
        }
        return false;
    }

    public static boolean isBrutForceInputWordsValid(String string){
        if (!string.isBlank()){
            string = string.trim();
            if ((string.contains(" ")) || (string.length() == 1)){
                String[] strings = string.split(" ");
                for (int i = 0; i < strings.length; i++) {
                    strings[i] = strings[i].replaceAll(" ", "");
                    if (strings[i].length() == 1 && i>0){                   // to look all 1-char articles as: I ->  _i_ (3chars)
                        strings[i] = " " + strings[i] + " ";
                    }
                    //else if (strings[i].length() == 1 && i == 0) {        // to look all 1-char articles
                    //    strings[i] = strings[i] + " ";                    //at the beginning of the row as: I ->  I_   (2chars)
                    //}
                    Chipher.getInstance().getBrutForceDecryptingWords().add(strings[i]);
                }
            } else Chipher.getInstance().getBrutForceDecryptingWords().add(string);
            return true;
        } else return false;
    }
}
