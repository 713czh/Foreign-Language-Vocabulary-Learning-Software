// 包: nz.ac.cjlu.vocabmaster.model
// Word.java - 词实体的模型。
// 封装词数据包括例句。

package nz.ac.cjlu.vocabmaster.model;

// Word.java - 词实体的模型
// 封装词数据包括例句
public class Word {
    // ID。
    private int id;
    // 英语。
    private String english;
    // 中文。
    private String chinese;
    // 英语例句。
    private String exampleEn;
    // 中文例句。
    private String exampleCn;

    // 默认构造函数。
    public Word() {}

    // 带参数构造函数。
    public Word(String english, String chinese, String exampleEn, String exampleCn) {
        this.english = english;
        this.chinese = chinese;
        this.exampleEn = exampleEn;
        this.exampleCn = exampleCn;
    }

    // Getter和Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getExampleEn() {
        return exampleEn;
    }

    public void setExampleEn(String exampleEn) {
        this.exampleEn = exampleEn;
    }

    public String getExampleCn() {
        return exampleCn;
    }

    public void setExampleCn(String exampleCn) {
        this.exampleCn = exampleCn;
    }
}