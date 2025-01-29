package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import animatefx.animation.*;
import org.jasypt.util.text.BasicTextEncryptor;
import service.custom.impl.LoginSignupServiceImpl;

import java.io.IOException;

public class LoginSignupController {
    public JFXPasswordField txtPasswordlogin;
    @FXML
    private JFXTextField txtUserNameSignup;

    @FXML
    private JFXTextField txtEmailSignup;

    @FXML
    private JFXPasswordField txtPasswordSignup;

    @FXML
    private JFXPasswordField txtPasswordConfirmSignup;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private AnchorPane signupPane;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPasswordConfirm;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        if (txtUserNameSignup.getText().isEmpty() || txtPasswordSignup.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Empty Fields");
            alert.setHeaderText("Fields cannot be empty");
        }else{
            if (txtPasswordSignup.getText().equals(txtPasswordConfirmSignup.getText())) {
                if (!LoginSignupServiceImpl.getInstance().checkUser(txtEmailSignup.getText())) {
                    BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                    String key = "@bCd3f";
                    basicTextEncryptor.setPassword(key);

                    if (LoginSignupServiceImpl.getInstance().signup(new User(txtUserNameSignup.getText(), txtEmailSignup.getText(), basicTextEncryptor.encrypt(txtPasswordSignup.getText())))){
                        loadDashBoard();
                        Stage currentStage = (Stage) txtEmailSignup.getScene().getWindow();
                        if (currentStage != null) {
                            currentStage.close();
                        }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error - Incorrect Password");
                        alert.setHeaderText("User not added");
                        alert.show();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error - User already exists");
                    alert.setHeaderText("User already exists");
                    alert.show();
                }

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error - Incorrect Password");
                alert.setHeaderText("Username and password do not match");
                alert.show();
            }
        }
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        if (txtEmail.getText().isEmpty() || txtPasswordlogin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Empty Fields");
            alert.setHeaderText("Fields cannot be empty");
            alert.show();
        } else {
            if (LoginSignupServiceImpl.getInstance().login(txtEmail.getText(), txtPasswordlogin.getText())) {
                loadDashBoard();
                Stage currentStage = (Stage) txtEmail.getScene().getWindow();
                if (currentStage != null) {
                    currentStage.close();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Email or Password");
                alert.show();
            }
        }
    }

    private void loadDashBoard() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    void loginOnMousePressed(MouseEvent event) {
        new SlideInRight(loginPane).play();
        loginPane.toFront();
    }

    @FXML
    void signUpOnMousePressed(MouseEvent event) {
        new  SlideInRight(signupPane).play();
        signupPane.toFront();
    }

}
