package backend2;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DBWorker dbWorker = new DBWorker();
        Scanner sc = new Scanner(System.in);

        //Блок работы регистрации
        System.out.println("Введите логин:  *");
        String login = sc.nextLine();
        System.out.println("Введите пароль:  *");
        int pass = sc.nextInt();
        sc.nextLine();
        System.out.println("Введите свою почту:  *");
        String email = sc.nextLine();
        dbWorker.register(login, email, pass);

        //Блок авторизации
        int i = 0;
        while(i == 0) {
            System.out.println("Авторизоваться\nвведите логин:");
            String loginForCheck = sc.nextLine();
            System.out.println("Введите пароль:");
            int passForCheck = sc.nextInt();
            dbWorker.authorize(loginForCheck, passForCheck);
            System.out.println("Чтобы повторить попытку введите 0, чтобы закончить введите 1");
            int j = sc.nextInt();
            sc.nextLine();
            if (j > 0){
                break;
            }
        }
    }
}
