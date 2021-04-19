package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RegController {
    private Controller controller;

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField nickField;
    @FXML
    public TextField newNickField;
    @FXML
    private TextArea textArea;

    @FXML
    public void tryToReg(ActionEvent actionEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        String nickname = nickField.getText().trim();

        controller.registration(login, password, nickname);
    }

    public void showResult(String result) {
        if (result.equals("/reg_ok")) {
            textArea.appendText("Регистрация прошла успешно\n");
        } else {
            textArea.appendText("Регистрация не удалась. \nВозможно логин или никнейм заняты\n");
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    public void tryToChange(ActionEvent actionEvent) {
        System.out.println(111);
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        String nickname = nickField.getText().trim();
        String newNickname = newNickField.getText().trim();

        System.out.println(nickname);
        System.out.println(newNickname);

        controller.changeNickname(login, password, nickname, newNickname);
    }
}
