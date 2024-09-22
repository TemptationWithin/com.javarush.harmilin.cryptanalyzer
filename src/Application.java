import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        System.out.println(Information.GREETING);
        System.out.println(Information.OPTIONS);

        Scanner console = new Scanner(System.in);                                    // scanner opened
        while (!Validator.isChoiceValid(console.nextLine())) {
            System.out.println(Information.OPTIONS_NO_VALID_OPTION_PROVIDED);
            System.out.println(Information.OPTIONS);
        }

        Chipher chipher = Chipher.getInstance();

        switch (Validator.getUserOptionChoice()) {
            case 1: {  // 1. - encrypt option
                System.out.println(Information.OPTION_ENCRYPT_AND_INPUT_PATH_REQUEST);                      //inputPath
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println(Information.ABSOLUTE_INTPUT_PATH_REQUEST_IF_WRONG);
                }
                System.out.println(chipher.getInputPath());//toDELETE (проверка ввода)

                System.out.println(Information.FILE_RECEIVED_KEY_REQUEST);                                          //key
                while (!Validator.isKeyValid(console.nextLine())) {
                    System.out.println(Information.KEY_REQUEST_IF_WRONG);
                }

                System.out.println("Ключ:" + chipher.getKey());//toDELETE (проверка ввода)
                System.out.println(Information.ABSOLUTE_INPUT_PATH_REQUEST);
                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.ABSOLUTE_INPUT_PATH_REQUEST_IF_WRONG);
                }

                System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP);
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {              //non-alphabetic symbols option
                    System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP_IF_WRONG_INPUT);
                }

                chipher.encrypt(chipher.getInputPath(), chipher.getOutputPath(), chipher.getKey());
                break;
            }
            case 2: {   // 2. - decrypt option
                System.out.println(Information.OPTION_DECRYPT_AND_INPUT_PATH_REQUEST);                     //inputPath
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println(Information.ABSOLUTE_INTPUT_PATH_REQUEST_IF_WRONG);
                }

                System.out.println(Information.FILE_RECEIVED_KEY_REQUEST);       //key
                while (!Validator.isKeyValid(console.nextLine())) {
                    System.out.println(Information.KEY_REQUEST_IF_WRONG);
                }

                System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST);
                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST_IF_WRONG);
                }

                System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP);
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {               //non-alphabetic symbols option
                    System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP_IF_WRONG_INPUT);
                }

                chipher.decrypt(chipher.getInputPath(), chipher.getOutputPath(), chipher.getKey());
                break;
            }
            case 3: {  // 3. - brut-force decrypt option
                System.out.println(Information.OPTION_BRUT_FORCE_AND_INPUT_PATH_REQUEST);                   //inputPath
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println(Information.ABSOLUTE_INTPUT_PATH_REQUEST_IF_WRONG);
                }
                System.out.println(chipher.getInputPath());//toDELETE (проверка ввода)

                System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST);
                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST_IF_WRONG);
                }

                System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP);
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {               //outputPath
                    System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP_IF_WRONG_INPUT);
                }

                System.out.println(Information.BRUT_FORCE_INPUT_WORDS_OPTION);                              // brut force words input
                while (Validator.isBrutForceInputWordsValid(console.nextLine())){

                }

                System.out.println("Введено объкетов поиска: " + chipher.getBrutForceDecryptingWords().size() + ", ищем совпадения по: "); //check searching words
                for (String brutForceDecryptingWord : chipher.getBrutForceDecryptingWords()) {
                    System.out.print(brutForceDecryptingWord + ", ");
                }
                System.out.println("\n");

                chipher.brutForceDecrypt(chipher.getInputPath(), chipher.getOutputPath());            //brutForce method
                chipher.printInfo();
                break;
            }
            case 4: {  // 4. - statistic analysis option
                System.out.println(Information.OPTION_STATISTIC_ANALYSIS_AND_INPUT_PATH_REQUEST);           // inputPath
                while (!Validator.isInputFileExists(console.nextLine())) {
                    System.out.println(Information.ABSOLUTE_INTPUT_PATH_REQUEST_IF_WRONG);
                }
                System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST);
                while (!Validator.isOutputPathValid(console.nextLine())) {                                  //outputPath
                    System.out.println(Information.ABSOLUTE_OUTPUT_PATH_REQUEST_IF_WRONG);
                }
                System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP);
                while (!Validator.isOptionForNonAlphabeticSymbolsValid(console.nextLine())) {               //non-alphabetic symbols option
                    System.out.println(Information.UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP_IF_WRONG_INPUT);
                }

                chipher.statisticalAnalysisDecrypt(chipher.getInputPath(), chipher.getOutputPath());
                chipher.printInfo();
                break;
            }
            case 5: {  // 5 - info option
                System.out.println(Information.OPTION_INFORMATION);
                break;
            }
            case 6: {  // 6. - exit
                break;
            }
        }
        console.close();                                                               //scanner closed
    }
}
