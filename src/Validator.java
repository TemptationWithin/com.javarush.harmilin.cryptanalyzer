import java.nio.file.Files;
import java.nio.file.Path;

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
            case 1, 2, 3, 4: {
                userOptionChoice = i;
                return true;
            }
            default:
                return false;
        }
    }

    public static boolean isKeyValid(String number){
        int i = 0;
        try {
            i = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("А я ведь просил число..");
        }
        if (i == 0) {
            System.out.println("Ну с таким ключом мы далеко не уйдем. Наврядли вам нужна обычная копия файла..");
            return false;
        } else if (Math.abs(i) > Alphabet.ALPHABET.size()-1) {
            System.out.println("Давайте отбросим полные обороты ключа. Ваш ключ эквивалентен: " + i%Alphabet.ALPHABET.size());
            Chipper.getInstance().setKey(i%Alphabet.ALPHABET.size());
            return true;
        } else {
            Chipper.getInstance().setKey(i);
            return true;
        }
    }

    public static boolean isInputFileExists(String s) {
        Path path = Path.of(s);
        path = path.normalize();
        if (Files.isRegularFile(path)) {
            Chipper.getInstance().setInputPath(path);
            return true;
        } else return false;
    }

    public static boolean isOutputPathValid(String s){
        Path path = Path.of(s);
        // if entered same path or pressed enter - we create new file based on input absolute path
        if (s.isEmpty()|| path.startsWith(Chipper.getInstance().getInputPath())){
            StringBuilder string = new StringBuilder(String.valueOf(Chipper.getInstance().getInputPath()));
            string.replace(string.length() - 4, string.length(), "Encrypted.txt");
            Chipper.getInstance().setOutputPath(Path.of(String.valueOf(string)));
            System.out.println(Chipper.getInstance().getOutputPath()); //console path
            return true;
        } else {
            path = path.normalize();
            if (path.isAbsolute()){
                Chipper.getInstance().setOutputPath(path);
                System.out.println(Chipper.getInstance().getOutputPath()); //checking path
                return true;
            }
        }
        return false;
    }
}
