package ru.aserdyuchenko.testQuestionsTwo;
/**
 * @author Anton Serdyuchenko (anton415@gmail.com   +7 (916) 154-92-00)
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Scanner;
/**
 * Тестовое задание по модулю "ввод-вывод".
 * Программа переворачивает слова наизнанку, например:
 * Исходное предложение: Привет мир
 * Результат: тевирП рим
 */
class InvertString {
  /**
   * Метод переворачивает одно слово, например:
   * Исходное слово: Hello
   * Результат: olleH
   * Сначала слово заносится в массив символов, затем данный массив переносится в новый,
   * но задом на перед. В залючении новый массив конвертируется в строку(слово).
   * @param wordForInvert - слово которуе будет перевернуто.
   * @return newWord - перевернутое слово.
   */
  public String invertFirstMethod(String wordForInvert) {
    char[] charArrayNewWord = new char[wordForInvert.length()];
    char[] charArrayOldWord = wordForInvert.toCharArray();
    int j = 0;
    for (int index = charArrayOldWord.length - 1; index >= 0; index--) {
      if (j < charArrayNewWord.length) {
        charArrayNewWord[j++] = charArrayOldWord[index];
      }
    }
    String newWord = String.valueOf(charArrayNewWord);
    return newWord;
  }

  public String invertSecondMethod(String wordForInvert) {
    String newWord = "";
      for (int index = 0; index < wordForInvert.length(); index++) {
        newWord = wordForInvert.charAt(index) + newWord;
      }
    return newWord;
  }

  public String invertThirdMethod(String wordForInvert) {
    StringBuilder builder = new StringBuilder();
    for (int index = wordForInvert.length() - 1; index >= 0; index--) {
      builder.append(wordForInvert.charAt(index));
    }
    return builder.toString();
  }

  /**
   * Метод принимает поток слов для конвертации.
   * Из потока выбирается слово и передает в метод invertOneWord(String wordForInvert),
   * для его переворота. Затем полученной слово записывается в поток вывода.
   */
  public void invertAllString(InputStream in, OutputStream out) throws IOException {
    try (Scanner reader = new Scanner(new BufferedReader(new InputStreamReader(in)))) {
      while (reader.hasNext()) {
        String word = reader.next();
        String invertWord = invertFirstMethod(word) + " ";
        out.write(invertWord.getBytes());
      }
    }
  }
}
