package AstonHomeWork;

import AstonHomeWork.DAO.UserDAOImpl;
import AstonHomeWork.models.User;
import AstonHomeWork.services.UserService;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUser();
                case 2 -> getUser();
                case 3 -> listUsers();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> running = false;
                default -> System.out.println("Неверный выбор!");
            }
        }
        userService.close();
    }

    private static void printMenu() {
        System.out.println("\n--- User Service ---");
        System.out.println("1. Создать пользователя");
        System.out.println("2. Найти пользователя по ID");
        System.out.println("3. Список всех пользователей");
        System.out.println("4. Обновить пользователя");
        System.out.println("5. Удалить пользователя");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void createUser() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        userService.createUser(name, email, age);
        System.out.println("Пользователь создан!");
    }

    private static void getUser() {
        System.out.print("Введите ID пользователя: ");
        Long id = scanner.nextLong();
        var user = userService.getUserById(id);
        System.out.println(user != null ?"Пользователь: " + user : "Пользователь не найден!");
    }

    private static void listUsers() {
        var users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст!");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void updateUser() {
        System.out.print("Введите ID пользователя для обновления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Новое имя: ");
        String name = scanner.nextLine();
        System.out.print("Новый email: ");
        String email = scanner.nextLine();
        System.out.print("Новый возраст: ");
        int age = scanner.nextInt();
        userService.updateUser(id, name, email, age);
    }

    private static void deleteUser() {
            System.out.print("Введите ID пользователя для удаления: ");
            Long id = scanner.nextLong();
            userService.deleteUser(id);


    }
}
