import java.util.Arrays;
import java.util.List;
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
                System.out.println(Information.OPTION_ENCRYPT);                                             //path
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println("Не вижу такого файла. Попробуйте еще раз:");
                }
                System.out.println(chipher.getInputPath());//toDELETE (проверка ввода)

                System.out.println("Файл получен. Введите ключ:");                                          //key
                while (!Validator.isKeyValid(console.nextLine())) {
                    System.out.println("Вы должны ввести число отличное от нуля:");
                }

                System.out.println("Ключ:" + chipher.getKey());//toDELETE (проверка ввода)

                System.out.println("Введите абсолютный путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)");
                System.out.println("ВНИМАНИЕ! Если вы указали пусть к существующему файлу - он будет ПЕРЕЗАПИСАН!");

                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println("Введите, пожалуйста, АБСОЛЮТНЫЙ путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)");
                }

                System.out.println("Хотите ли вы оставить неизвестные мне символы? Введите число:");
                System.out.println("1. Да (такие символы запишутся без изменения)");
                System.out.println("2. Нет (такие символы будут пропускаться)");
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {                                  //outputPath
                    System.out.println("Вы можете ввести только число:\n1 - для записи символовб которых я не знаю\n2 - для их пропуска ");
                }

                chipher.encrypt(chipher.getInputPath());
                break;
            }
            case 2: {   //decrypt
                System.out.println(Information.OPTION_DECRYPT);                                            //inputPath
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println("Не вижу такого файла. Попробуйте еще раз:");
                }
                System.out.println(chipher.getInputPath());//toDELETE (проверка ввода)

                System.out.println("Файл получен. Введите ключ:");                                          //key
                while (!Validator.isKeyValid(console.nextLine())) {
                    System.out.println("Вы должны ввести число отличное от нуля:");
                }

                System.out.println("Ключ:" + chipher.getKey());//toDELETE (проверка ввода)

                System.out.println("Введите абсолютный путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)");
                System.out.println("ВНИМАНИЕ! Если вы указали пусть к существующему файлу - он будет ПЕРЕЗАПИСАН!");

                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println("Введите, пожалуйста, АБСОЛЮТНЫЙ путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)");
                }

                System.out.println("Хотите ли вы оставить неизвестные мне символы? Введите число:");
                System.out.println("1. Да (такие символы запишутся без изменения)");
                System.out.println("2. Нет (такие символы будут пропускаться)");
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {                                  //outputPath
                    System.out.println("Вы можете ввести только число:\n1 - для записи символов которых я не знаю\n2 - для их пропуска ");
                }

                chipher.decrypt(chipher.getInputPath());
                break;
            }
            case 3: {  //brut-force decrypt
                System.out.println(Information.OPTION_BRUT_FORCE);
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println("Не вижу такого файла. Попробуйте еще раз:");
                }
                System.out.println(chipher.getInputPath());//toDELETE (проверка ввода)
                System.out.println("Введите абсолютный путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)");
                System.out.println("ВНИМАНИЕ! Если вы указали пусть к существующему файлу - он будет ПЕРЕЗАПИСАН!");

                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println("Введите, пожалуйста, АБСОЛЮТНЫЙ путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)");
                }

                System.out.println("Хотите ли вы оставить неизвестные мне символы? Введите число:");
                System.out.println("1. Да (такие символы запишутся без изменения)");
                System.out.println("2. Нет (такие символы будут пропускаться)");
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {                                  //outputPath
                    System.out.println("Вы можете ввести только число:\n1 - для записи символовб которых я не знаю\n2 - для их пропуска ");
                }
                System.out.println("Какой текст будем искать? Введите слово/слова, строку/строки.\n" +
                        "Разделите их пробелами или нажатием клавиши ENTER.\n" +
                        "Если хотите остановиться - введите пустую строку:");
                while (Validator.isBrutForceInputWordsValid(console.nextLine())){
                    System.out.println("Введено объкетов поиска: " + chipher.getBrutForceDecryptingWords().size() + ", ищем совпадения по: "); //check searching words
                    for (String brutForceDecryptingWord : chipher.getBrutForceDecryptingWords()) {
                        System.out.print(brutForceDecryptingWord.toLowerCase() + " ");
                    }
                }
                System.out.println(chipher.getBrutForceDecryptingWords());
                chipher.brutForceDecrypt();
                break;
            }
            case 4: {  //info
                System.out.println(Information.OPTION_INFORMATION);
                break;
            }
            case 5: {  //exit
                break;
            }
        }
        console.close();                                                               //scanner closed
    }
}
