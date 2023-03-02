package tasks.second.coders;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class VerticalEncoder {
    public final static int permutationOrder = 5;

    private ArrayList<LinkedList<Character>> codingTable;

    private File originalFile;
    private File encodedFile;
    private File keyFile;

    private LinkedHashSet<Integer> key;

    public VerticalEncoder(String nameOfOriginalFile, String nameOfEncodedFile,
                           String nameOfKeyFile) {
        codingTable = new ArrayList<>();
        for (int i = 0; i != permutationOrder; i++)
            codingTable.add(new LinkedList<>());

        originalFile = new File(nameOfOriginalFile);
        encodedFile = new File(nameOfEncodedFile);

        key = new LinkedHashSet<>();
        keyFile = new File(nameOfKeyFile);
    }

    public void encode() {
        try {
            readDataToTable();
            readKey();
            var writer = new BufferedWriter(new FileWriter(encodedFile));
            key.forEach(columnNumber ->
                    codingTable.get(columnNumber - 1).forEach(character -> {
                        try {
                            writer.write(character);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }));
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void readKey() {
        try {
            var reader = new BufferedReader(new FileReader(keyFile));
            String stringKey = reader.readLine();
            for (String columnNumber : stringKey.split("-"))
                key.add(Integer.valueOf(columnNumber));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readDataToTable() {
        int tmp;
        int columnNumber = 0; //Номер столбца, в который будет записан данный символ (в порядке очереди)
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(originalFile), StandardCharsets.UTF_8);

            while ((tmp = inputStreamReader.read()) != -1) {
                codingTable.get(columnNumber).add((char) tmp);
                columnNumber = columnNumber < 4 ? ++columnNumber : 0;
            }

            inputStreamReader.close();

            //Считали файл в таблицу, теперь заполняем пустоты
            int charCount = 0;
            for (int i = 0; i != codingTable.size(); i++)
                charCount += codingTable.get(i).size();

            if (charCount % permutationOrder != 0)
                for (int i = permutationOrder - 1; i != permutationOrder - 1 - ((charCount / 5 + 1) * 5 - charCount); i--)
                    codingTable.get(i).add('z');

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Encode {
    public static void main(String[] args) {
        var encoder = new VerticalEncoder("example.txt", "encrypted.txt", "key.txt");
        encoder.encode();
    }
}
