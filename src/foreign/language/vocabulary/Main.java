package foreign.language.vocabulary;

import nz.ac.cjlu.vocabmaster.dao.DatabaseInitializer;
import nz.ac.cjlu.vocabmaster.view.MainFrame;
import nz.ac.cjlu.vocabmaster.view.LoginPanel;
import nz.ac.cjlu.vocabmaster.view.RegisterPanel;
import nz.ac.cjlu.vocabmaster.controller.AuthController;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();  // Auto setup DB

        MainFrame frame = new MainFrame();
        LoginPanel login = new LoginPanel();
        RegisterPanel register = new RegisterPanel();

        frame.addPanel("login", login);
        frame.addPanel("register", register);
        new AuthController(login, register, frame);

        frame.showPanel("login");  // Start with login
        frame.setVisible(true);
    }
}