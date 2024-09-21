package org.example;

import java.util.Random;

public class ZeroHashCodeGenerator {

    public static void main(String[] args) {
        // 生成并打印长度在10位以内且hashCode为0的字符串
        generateAndPrintZeroHashCodeStrings(8);
    }

    private static void generateAndPrintZeroHashCodeStrings(int maxLength) {
        Random random = new Random();
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789"; // 包含字母和数字

        while (true) {

            int length = random.nextInt(maxLength) + 1; // 确保字符串长度在1到maxLength之间
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(chars.length());
                sb.append(chars.charAt(index)); // 生成随机字符
            }

            String generatedString = sb.toString();
            if (generatedString.hashCode() == 0) {
                System.out.println("找到hashCode为0的字符串: " + generatedString);
            }
            // 为了演示，这里不设置退出条件；实际应用中可以根据需要设置退出条件
        }
    }
}