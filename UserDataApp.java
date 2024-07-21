/*
 * Урок 6. Семинар: Продвинутая работа с исключениями в Java
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол
Форматы данных:

фамилия, имя, отчество - строки
дата_рождения - строка формата dd.mm.yyyy
номер_телефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида

<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

Не забудьте закрыть соединение с файлом.

При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.

Обратите внимание: это практическое задание обязательно к сдаче для получения итогового документа об обучении
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserDataApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(
                    "Введите данные (Фамилия Имя Отчество датарождения номертелефона пол) в произвольном порядке, разделённые пробелом (или введите 'exit' для выхода):");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из программы...");
                break;
            }
            String[] userData = input.trim().split("[\\s\\xA0]+");
            try {
                if (checkQuantityOfValues(userData.length) == -1) {
                    throw new IllegalArgumentException(
                            "Неверное количество данных. Ожидалось 6, введено " + userData.length);
                }

                String lastName = null;
                String firstName = null;
                String middleName = null;
                String birthDate = null;
                String phoneNumber = null;
                String gender = null;

                for (String data : userData) {
                    if (Pattern.matches("\\d{2}\\.\\d{2}\\.\\d{4}", data)) {
                        birthDate = data;
                    } else if (Pattern.matches("\\d+", data)) {
                        phoneNumber = data;
                    } else if (Pattern.matches("[fm]", data)) {
                        gender = data;
                    } else if (lastName == null) {
                        lastName = capitalize(data);
                    } else if (firstName == null) {
                        firstName = capitalize(data);
                    } else {
                        middleName = capitalize(data);
                    }
                }

                if (lastName == null) {
                    throw new IllegalArgumentException("Фамилия отсутствует или имеет неверный формат.");
                }
                if (firstName == null) {
                    throw new IllegalArgumentException("Имя отсутствует или имеет неверный формат.");
                }
                if (middleName == null) {
                    throw new IllegalArgumentException("Отчество отсутствует или имеет неверный формат.");
                }
                if (birthDate == null) {
                    throw new IllegalArgumentException(
                            "Дата рождения отсутствует или имеет неверный формат. Ожидался формат dd.mm.yyyy.");
                }
                if (phoneNumber == null) {
                    throw new IllegalArgumentException(
                            "Номер телефона отсутствует или имеет неверный формат. Ожидалось целое число без форматирования.");
                }
                if (gender == null) {
                    throw new IllegalArgumentException(
                            "Пол отсутствует или имеет неверный формат. Ожидался символ f или m.");
                }

                writeToFile(lastName, firstName, middleName, birthDate, phoneNumber, gender);
                System.out.println("Данные успешно записаны.");
                break;

            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка валидации данных: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте снова.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка при чтении-записи файла: " + e.getMessage());
                break;
            }
        }
        scanner.close();
    }

    public static int checkQuantityOfValues(int amountOfData) {
        if (amountOfData != 6) {
            return -1;
        }
        return 0;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private static void writeToFile(String lastName, String firstName, String middleName, String birthDate,
            String phoneNumber, String gender) throws IOException {
        try (FileWriter writer = new FileWriter(lastName + ".txt", true)) {
            writer.write(String.format("%s %s %s %s %s %s%n", lastName, firstName, middleName, birthDate, phoneNumber,
                    gender));
        }
    }
}
