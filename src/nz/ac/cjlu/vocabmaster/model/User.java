// 包: nz.ac.cjlu.vocabmaster.model
// User.java - 用户实体的模型。
// 使用getter/setter封装用户数据（OO封装）。

package nz.ac.cjlu.vocabmaster.model;

import java.sql.Timestamp;

// User.java - 用户实体的模型
// 使用getter/setter封装用户数据（OO封装）
public class User {
    // ID。
    private int id;
    // 用户名。
    private String username;
    // 密码。
    private String password;
    // 创建日期。
    private Timestamp createdDate;

    // 默认构造函数。
    public User() {}

    // 带参数构造函数。
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter和Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}