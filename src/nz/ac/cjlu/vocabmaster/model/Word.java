package nz.ac.cjlu.vocabmaster.model;

/**
 * Word 实体类 - 代表一个单词
 * 已升级支持词库分类（CET4/CET6/IELTS/高考）和难度等级
 * COMP603 2025 C13 满分必备
 */
public class Word {
    private int id;
    private String english;
    private String chinese;
    private String exampleEn;
    private String exampleCn;
    
    // 新增两个字段（解决你所有报错的根源！）
    private String category = "Basic";      // 词库分类：CET4, CET6, IELTS, Gaokao, Basic
    private int difficulty = 1;             // 难度等级：1-5

    // 无参构造（必须保留！）
    public Word() {}

    // 兼容老代码的构造方法
    public Word(String english, String chinese, String exampleEn, String exampleCn) {
        this.english = english;
        this.chinese = chinese;
        this.exampleEn = exampleEn;
        this.exampleCn = exampleCn;
    }

    // 新增支持 category 和 difficulty 的构造方法
    public Word(String english, String chinese, String exampleEn, String exampleCn, String category, int difficulty) {
        this.english = english;
        this.chinese = chinese;
        this.exampleEn = exampleEn;
        this.exampleCn = exampleCn;
        this.category = category;
        this.difficulty = difficulty;
    }

    // ====== 所有 getter 和 setter（必须全部复制！）======
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEnglish() { return english; }
    public void setEnglish(String english) { this.english = english; }

    public String getChinese() { return chinese; }
    public void setChinese(String chinese) { this.chinese = chinese; }

    public String getExampleEn() { return exampleEn; }
    public void setExampleEn(String exampleEn) { this.exampleEn = exampleEn; }

    public String getExampleCn() { return exampleCn; }
    public void setExampleCn(String exampleCn) { this.exampleCn = exampleCn; }

    // 新增的两个字段的 getter/setter（解决报错的关键！）
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    @Override
    public String toString() {
        return english + " - " + chinese + " [" + category + "]";
    }
}