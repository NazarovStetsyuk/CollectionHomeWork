package fileReader;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class FileReader {
    private Map<String, Integer> map = new HashMap<>();

    File enterFilePath() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите путь до файла: ");
        try {
            while (true) {
                String filePath = reader.readLine();
                Path path = Paths.get(filePath);
                File file = new File(path.toUri());
                if (!file.isFile()) System.out.println("Введен не правильный путь. Попробуйте снова:");
                else return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<String> readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            List<String> wordsList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> buffer = Arrays.asList(line.split("\\W+"));
                buffer.removeIf(String::isEmpty);
                wordsList.addAll(buffer);
            }
            wordsList.sort(Comparator.naturalOrder());
            System.out.println("Список слов из файла в алфавитном порядке:\n" + wordsList);
            return wordsList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void printCountWords(List<String> wordsList) {
        System.out.println("\nКоличество одинаковых слов в файле:");
        wordsList.forEach(s -> map.merge(s.toLowerCase(), 1, (a, b) -> a + b));
        map.forEach((String k, Integer v) -> System.out.println(k + " : " + v));
    }

    void printMostPopularWord() {
        if (!map.isEmpty()) {
            int max = Collections.max(map.values());
            System.out.println(String.format("\nСледующие слова встретились '%s' раз:", max));
            map.forEach((String k, Integer v) -> {
                if (v == max) System.out.println(k);
            });
        }
    }
}
