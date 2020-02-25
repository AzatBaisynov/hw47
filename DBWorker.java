package backend2;

import java.sql.*;
import java.util.Arrays;

public class DBWorker {
    private final String url = "jdbc:postgresql://localhost:5432/";
    private final String user = "postgres";
    private final String password = "z123456789Zazat";
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("------------------------------");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void insertValues(String name, String mail, int password) {
        String SQL = "insert into users(login, email, password, date_of_registration) values (?,?,?,current_date);";
        try(Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, mail);
            preparedStatement.setInt(3, password);
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkValueByName(String login) {
        String SQL = "select users.login from users where login = '" + login + "'";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
            while(rs.next()){
                if (rs.getString("login").equals(login)){
                    System.out.println("Логин создан");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка добавления");
            System.out.println(ex.getMessage());
        }
    }

    public void register(String login, String mail, int password){
        insertValues(login, mail, coder(password));
        checkValueByName(login);
    }

    private boolean checkByPassword(String login, int pass) {
        String SQL = "select users.login from users where password = '" + coder(pass) + "'";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
            while(rs.next()){
                if (rs.getString("login").equals(login)){
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    private boolean checkByLogin(String login){
        String SQL = "select users.login from users where login = '" + login + "'";
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
            while(rs.next()){
                if (rs.getString("login").equals(login)){
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    private void insertLogs(String login, boolean isTruePassword) {
        String SQL = "insert into userlogs (user_login, lastlogin, successibility) values (?, current_timestamp, ?);";
        try(Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {
            preparedStatement.setString(1, login);
            preparedStatement.setBoolean(2, isTruePassword);
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void authorize(String login, int password){
        if (checkByLogin(login)){
            if (checkByPassword(login, password)){
                insertLogs(login, true);
                System.out.println("Вы успешно вошли в аккаунт");
            } else {
                insertLogs(login, false);
                System.out.println("неправильный логин или пароль");
            }
        } else {
            System.out.println("неправильный логин или пароль");
        }
    }

    private int[] passToArray(int password){
        String s = Integer.toString(password);
        int[] numArr = Arrays.stream(s.split("")).mapToInt(Integer::parseInt).toArray();
        return numArr;
    }
    private int[] passCoder(int[] arr){
        int[] arr_new = new int[arr.length + 1];
        int j = 0;
        for (int i = arr.length; i > 0; i--){
            arr_new[j] = arr[i-1];
            j++;
        }
        arr_new[arr.length] = arr[0];
        return arr_new;
    }

    private int[] passDecoder(int[] arr1){
        int[] arr = new int[arr1.length - 1];
        int j = 0;
        int length = arr.length-1;
        for (int i = length; i > 0; i--){
            arr[j] = arr1[i];
            j++;
        }
        arr[length] = arr1[0];
        return arr;
    }

    private int arrayToPass(int[] arr){
        int result = 0;
        for (int i =arr.length -1 , n = 0; i >= 0; --i, n++) {
            int pos = (int)Math.pow(10, i);
            result += arr[n] * pos;
        }
        return result;
    }

    public int coder(int i){
        return arrayToPass(passCoder(passToArray(i)));
    }

    public int decoder(int i){
        return arrayToPass(passDecoder(passToArray(i)));
    }
}
