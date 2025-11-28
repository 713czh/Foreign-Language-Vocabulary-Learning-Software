package nz.ac.cjlu.vocabmaster.model;

// Word.java - Model for word entity
// Encapsulates word data including examples
public class Word {
    private int id;
    private String english;
    private String chinese;
    private String exampleEn;
    private String exampleCn;

    public Word() {}

    public Word(String english, String chinese, String exampleEn, String exampleCn) {
        this.english = english;
        this.chinese = chinese;
        this.exampleEn = exampleEn;
        this.exampleCn = exampleCn;
    }

    // Getters and Setters
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