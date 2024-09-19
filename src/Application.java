import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        System.out.println(Information.GREETING);
        System.out.println(Information.OPTIONS);

        Scanner console = new Scanner(System.in);                                    // scanner opened
        while (!Validator.isChoiceValid(console.nextLine())) {
            System.out.println("Такой опции нет. Введите число из перечня:");
            System.out.println(Information.OPTIONS);
        }

        Chipher chipher = Chipher.getInstance();

        switch (Validator.getUserOptionChoice()) {
            case 1: {  //encrypt
                System.out.println(Information.OPTION_ENCRYPT);   //path
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println("Не вижу такого файла. Попробуйте еще раз:");
                }
                System.out.println(chipher.getInputPath());//toDELETE (проверка ввода)

                System.out.println("Файл получен. Введите ключ:");                                          //key
                while (!Validator.isKeyValid(console.nextLine())) {
                    System.out.println("Вы должны ввести число отличное от нуля:");
                }

                System.out.println("Ключ:" + chipher.getKey());//toDELETE (проверка ввода)

                System.out.println("Введите абсолютный путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически");
                System.out.println("ВНИМАНИЕ! Если вы указали пусть к существующему файлу - он будет ПЕРЕЗАПИСАН!");

                while (!Validator.isOutputPathValid(console.nextLine())) {
                    System.out.println("Введите путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически");
                }
                Chipher.getInstance().encrypt(Chipher.getInstance().getInputPath());
                break;
            }
            case 2: {   //decrypt
                System.out.println(Information.OPTION_DECRYPT);
                String inputFilePath = console.nextLine();
                break;
            }
            case 3: {  //info
                System.out.println(Information.OPTION_INFORMATION);
                break;
            }
            case 4: {  //exit
                break;
            }
        }
        console.close();                                                               //scanner closed
    }
}
