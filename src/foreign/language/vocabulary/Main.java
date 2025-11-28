// 包: foreign.language.vocabulary
// 这是应用程序的入口点。它初始化数据库、设置主GUI框架，并配置初始面板和控制器。
// 演示OO封装，通过在集中的main方法中初始化组件。

package foreign.language.vocabulary;

import nz.ac.cjlu.vocabmaster.dao.DatabaseInitializer;
import nz.ac.cjlu.vocabmaster.view.MainFrame;
import nz.ac.cjlu.vocabmaster.view.LoginPanel;
import nz.ac.cjlu.vocabmaster.view.RegisterPanel;
import nz.ac.cjlu.vocabmaster.controller.AuthController;

public class Main {
    // main方法: 程序执行的入口点。
    // 初始化DB、创建GUI组件，并以登录屏幕启动应用程序。
    // 遵循SOLID的单一责任原则: 只处理启动逻辑。
    public static void main(String[] args) {
        // 自动初始化嵌入式Derby数据库（无需手动配置）。
        DatabaseInitializer.initialize();  // 自动设置DB

        // 使用MVC的View层创建主应用程序框架。
        MainFrame frame = new MainFrame();
        
        // 创建登录和注册面板（View组件）。
        LoginPanel login = new LoginPanel();
        RegisterPanel register = new RegisterPanel();

        // 将面板添加到框架的CardLayout中，用于动态切换。
        frame.addPanel("login", login);
        frame.addPanel("register", register);
        
        // 实例化AuthController来处理登录/注册事件（MVC Controller）。
        new AuthController(login, register, frame);

        // 显示初始登录面板并使框架可见。
        frame.showPanel("login");  // 以登录开始
        frame.setVisible(true);
    }
}