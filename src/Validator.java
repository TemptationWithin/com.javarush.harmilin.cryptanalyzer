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
        }
        else {
            Chipper.chipper.setKey(i);
            return true;
        }
    }

    public static boolean isInputFileExists(String s) {
        Path path = Path.of(s);
        if (Files.isRegularFile(path)) {
            Chipper.chipper.setInputPath(path);
            return true;
        } else return false;
    }

    public static boolean isOutputPathValid(String s){
        Path path = Path.of(s);
        // if entered same path or pressed enter - we create new file based on input absolute path
        if (s.isEmpty()|| path.startsWith(Chipper.getInstance().getInputPath())){
            StringBuilder string = new StringBuilder(String.valueOf(Chipper.getInstance().getInputPath()));
            string.replace(string.length() - 5, string.length() - 1, "");
            string = string.append("Encrypted.txt");
            Chipper.getInstance().setOutputPath(Path.of(String.valueOf(string)));
            System.out.println(Chipper.getInstance().getOutputPath()); //idk why "t" before encrypted
            return true;
        } else {
            path = path.normalize();
            if (path.isAbsolute()){
                Chipper.getInstance().setOutputPath(path);
                return true;
            }
        }
        return false;
    }
}
