package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAuthService implements AuthService{
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatementInsert;
    private static PreparedStatement preparedStatementUpdate;

    private static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:dbUsers.db");
        statement = connection.createStatement();
    }

    private static void disconnect() {
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private static void prepareAllStatement() throws SQLException {
        preparedStatementInsert =connection.prepareStatement("INSERT INTO User( login, password, nickname) VALUES (?, ?, ?)");
    }

    private static void prepareAllStatementUpdate() throws SQLException {
        preparedStatementUpdate =connection.prepareStatement("UPDATE User SET nickname = ? WHERE login = ? AND password = ? AND nickname = ?");
    }

    private class UserData {
        String login;
        String password;
        String nickname;

        public UserData(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    private List<UserData> users;

    public DBAuthService() {
        users = new ArrayList<>();

        users.add(new UserData("qwe", "qwe", "qwe"));
        users.add(new UserData("asd", "asd", "asd"));
        users.add(new UserData("zxc", "zxc", "zxc"));

        for (int i = 0; i < 10; i++) {
            users.add(new UserData("login" + i, "pass" + i, "nick" + i));
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {
            connect();

            ResultSet resultSet = statement.executeQuery("SELECT nickname FROM User WHERE login = '" + login + "' AND password = '" + password + "'");
            if (resultSet.next()) {
                return resultSet.getString("nickname");
            }
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {

        try {
            connect();

            ResultSet resultSet = statement.executeQuery("SELECT nickname FROM User WHERE login = '" + login + "' AND password = '" + password + "'");
            if (resultSet.next()) {
                return false;
            }
            else {
                prepareAllStatement();

                preparedStatementInsert.setString(1, login);
                preparedStatementInsert.setString(2, password);
                preparedStatementInsert.setString(3, nickname);
                preparedStatementInsert.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }

        return false;
    }

    @Override
    public boolean changeNick(String login, String password, String nickname, String newNickname) {
        System.out.println(newNickname + "!555555");

        try {
            connect();
            System.out.println("SELECT nickname FROM User WHERE login = '" + login + "' AND password = '" + password + "' AND nickname = '" + newNickname + "'");
            ResultSet resultSet = statement.executeQuery("SELECT nickname FROM User WHERE login = '" + login + "' AND password = '" + password + "' AND nickname = '" + newNickname + "'");
            if (resultSet.next()) {
                return false;
            }
            else {
                prepareAllStatementUpdate();

                preparedStatementUpdate.setString(2, login);
                preparedStatementUpdate.setString(3, password);
                preparedStatementUpdate.setString(4, nickname);
                preparedStatementUpdate.setString(1, newNickname);
                preparedStatementUpdate.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }

        return false;
    }
}
