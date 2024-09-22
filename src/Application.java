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
                System.out.println(Information.OPTION_ENCRYPT_AND_INPUT_PATH_REQUEST);                                             //path
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println("Не вижу такого файла. Попробуйте еще раз:");
                }
                System.out.println(chipher.getInputPath());//toDELETE (проверка ввода)

                System.out.println("Файл получен. Введите ключ:");                                          //key
                while (!Validator.isKeyValid(console.nextLine())) {
                    System.out.println("Вы должны ввести число отличное от нуля:");
                }

                System.out.println("Ключ:" + chipher.getKey());//toDELETE (проверка ввода)
                System.out.println(Information.ABSOLUTE_INPUT_PATH_REQUEST);
                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.ABSOLUTE_INPUT_PATH_REQUEST_IF_WRONG);
                }

                System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP);
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP_IF_WRONG_INPUT);
                }

                chipher.encrypt(chipher.getInputPath(), chipher.getKey());
                break;
            }
            case 2: {   //decrypt
                System.out.println(Information.OPTION_DECRYPT_AND_INPUT_PATH_REQUEST);                                            //inputPath
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println("Не вижу такого файла. Попробуйте еще раз:");
                }

                System.out.println("Файл получен. Введите ключ:");                                          //key
                while (!Validator.isKeyValid(console.nextLine())) {
                    System.out.println("Вы должны ввести число отличное от нуля:");
                }

                System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST);
                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST_IF_WRONG);
                }

                System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP);
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP_IF_WRONG_INPUT);
                }

                chipher.decrypt(chipher.getInputPath(), chipher.getKey());
                break;
            }
            case 3: {  //brut-force decrypt
                System.out.println(Information.OPTION_BRUT_FORCE_AND_INPUT_PATH_REQUEST);
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println("Не вижу такого файла. Попробуйте еще раз:");
                }
                System.out.println(chipher.getInputPath());//toDELETE (проверка ввода)

                System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST);
                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST_IF_WRONG);
                }

                System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP);
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP_IF_WRONG_INPUT);
                }
                System.out.println(Information.BRUT_FORCE_INPUT_WORDS_OPTION);
                while (Validator.isBrutForceInputWordsValid(console.nextLine())){

                }
                System.out.println("Введено объкетов поиска: " + chipher.getBrutForceDecryptingWords().size() + ", ищем совпадения по: "); //check searching words
                for (String brutForceDecryptingWord : chipher.getBrutForceDecryptingWords()) {
                    System.out.print(brutForceDecryptingWord + ", ");
                }
                System.out.println("\n");
                chipher.brutForceDecrypt();
                chipher.printInfo();
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
