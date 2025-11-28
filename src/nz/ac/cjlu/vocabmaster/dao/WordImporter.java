package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.Word;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

/**
 * 单词批量导入工具类（加分神器！）
 * 功能：一键导入四六级、雅思、高考等 TXT 词库
 * 使用方法：把 TXT 文件放项目根目录 → 运行这个类的 main 方法
 */
public class WordImporter {

    /**
     * 从 TXT 文件导入单词
     * @param filePath TXT文件路径（放在项目根目录）
     * @param category 词库类别（如"四级词汇"、"雅思核心词"）
     */
    public static void importFromFile(String filePath, String category) {
        WordDAO wordDAO = new WordDAO();
        int count = 0;

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("文件不存在：" + filePath + " 请检查路径！");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // 跳过空行和注释

                // 假设你的TXT格式是：单词 翻译 英文例句 中文例句
                String[] parts = line.split("\t");  // 用制表符分隔（推荐）
                if (parts.length < 2) parts = line.split("\\s{2,}"); // 或者用多个空格

                if (parts.length >= 4) {
                    String english = parts[0].trim();
                    String chinese = parts[1].trim();
                    String exampleEn = parts[2].trim();
                    String exampleCn = parts[3].trim();

                    Word word = new Word(english, chinese, exampleEn, exampleCn);
                    wordDAO.insert(word);
                    count++;
                    System.out.println("导入成功：" + english + " → " + category);
                }
            }
            System.out.println("【完成】" + category + " 共导入 " + count + " 个单词！");
        } catch (Exception e) {
            System.out.println("导入失败！请检查TXT格式");
            e.printStackTrace();
        }
    }

    // 测试用的 main 方法，右键 Run File 就能导入
    public static void main(String[] args) {
        // 先确保数据库已创建
        DatabaseInitializer.initialize();

        // 把你的 TXT 文件放在项目根目录（G:\aut\Foreign-Language-Vocabulary-Learning-Software\）
        // 文件名自己改
        importFromFile("CET4.txt", "四级词汇");
        importFromFile("CET6.txt", "六级词汇");
        importFromFile("IELTS.txt", "雅思核心词");
        importFromFile("高考词汇.txt", "高考必备词");

        System.out.println("所有词库导入完成！现在可以运行主程序学习啦！");
    }
}