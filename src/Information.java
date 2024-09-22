public final class Information {

    public static final String LINK = "https://en.wikipedia.org/wiki/Caesar_cipher";
    public static final String GREETING = "--------Добро Пожаловать---------\nВыберите одну из следующих опций (введите номер операции):";
    public static final String OPTIONS = "1. Шифрование\n2. Дешифровка\n3. Дешифровка brut-force (в стадии разработки)\n4. Статистический анализ текста\n5. Что тут вообще происходит?\n6. Выход";
    public static final String OPTIONS_NO_VALID_OPTION_PROVIDED = "Такой опции нет. Введите число из перечня:";
    public static final String OPTION_ENCRYPT_AND_INPUT_PATH_REQUEST = "Вы выбрали опцию \"Шифрование\". Приступаем.\nУкажите абсолютный путь к файлу:";
    public static final String OPTION_DECRYPT_AND_INPUT_PATH_REQUEST = "Вы выбрали опцию \"Дешифровка\". Приступаем.\nУкажите абсолютный путь к файлу:";
    public static final String OPTION_STATISTIC_ANALYSIS_AND_INPUT_PATH_REQUEST = "Вы выбрали опцию \"Статистический анализ текста\". Приступаем.\nУкажите абсолютный путь к файлу:";
    public static final String OPTION_INFORMATION = "Вы открыли программу для шифровки/дешифровки текстовых файлом шифром Цезаря.\n"
            + "Данная программа работает на данный момент только с русским алфавитом и рядом знаков препинаний.\n" +
            "У вас будет возможность записать остальные символы, либо пропустить их при шифровании.\n" +
            "Шифр Цезаря — это шифр подстановки: в нем каждый символ в открытом тексте заменяется на символ,\n" +
            "который находится на некотором постоянном числе позиций левее или правее него в алфавите.\n" +
            "Допустим, мы устанавливаем сдвиг на 3 (ключ = 3). В таком случае А заменится на Г, Б станет Д, и так далее.\n" +
            "Ссылка на более подробную информацию: " + LINK + "\n" +
            "Если вы хотите попробовать - запустите программу и выберите один из вышеперечисленных пунктов.";
    public static final String OPTION_BRUT_FORCE_AND_INPUT_PATH_REQUEST = "Дешифровка методом перебора ключа. Приступаем.\nУкажите путь к файлу:";
    public static final String ABSOLUTE_INPUT_PATH_REQUEST = "Введите абсолютный путь для результата. " +
            "Нажмите ENTER если такового нет (файл будет создан автоматически)\n" +
            "ВНИМАНИЕ! Если вы указали пусть к существующему файлу - он будет ПЕРЕЗАПИСАН!";
    public static final String ABSOLUTE_INPUT_PATH_REQUEST_IF_WRONG = "Введите, пожалуйста, АБСОЛЮТНЫЙ путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)";
    public static final String ABSOLUTE_OUTPUT_PATH_REQUEST = "Введите абсолютный путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)\n" +
            "ВНИМАНИЕ! Если вы указали пусть к существующему файлу - он будет ПЕРЕЗАПИСАН!";
    public static final String ABSOLUTE_OUTPUT_PATH_REQUEST_IF_WRONG = "Введите, пожалуйста, АБСОЛЮТНЫЙ путь для результата. Нажмите ENTER если такового нет (файл будет создан автоматически)";
    public static final String ABSOLUTE_INTPUT_PATH_REQUEST_IF_WRONG = "Не вижу такого файла. Попробуйте еще раз:";
    public static final String FILE_RECEIVED_KEY_REQUEST = "Файл получен. Введите ключ:";
    public static final String KEY_REQUEST_IF_WRONG = "Вы должны ввести число отличное от нуля:";

    public static final String UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP = "Хотите ли вы оставить неизвестные мне символы? Введите число:\n" +
            "1. Да (такие символы запишутся без изменения)\n" +
            "2. Нет (такие символы будут пропускаться)";
    public static final String UNKNOWN_SYMBOLS_OPTION_1_LEFT_2_SKIP_IF_WRONG_INPUT = "Вы можете ввести только число:\n1 - для записи символовб которых я не знаю\n2 - для их пропуска ";
    public static final String BRUT_FORCE_INPUT_WORDS_OPTION = "Какой текст будем искать? Введите слово/слова, строку/строки.\n" +
            "Помните, чем длиннее слова, тем точнее поиск!" +
            "Разделите их пробелами или нажатием клавиши ENTER.\n" +
            "Если хотите остановиться - введите пустую строку:";
}