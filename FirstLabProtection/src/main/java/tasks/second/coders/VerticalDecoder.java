package tasks.second.coders;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class VerticalDecoder {
    public final static int permutationOrder = 5;

    private ArrayList<ArrayList<Character>> codingTable;
    private File encodedFile;
    private File decodedFile;
    private final File keyFile;

    private LinkedHashSet<Integer> key;

    public VerticalDecoder(String nameOfEncodedFile, String nameOfDecodedFile,
                           String nameOfKeyFile) {
        codingTable = new ArrayList<>();
        for (int i = 0; i != permutationOrder; i++)
            codingTable.add(new ArrayList<>());

        encodedFile = new File(nameOfEncodedFile);
        decodedFile = new File(nameOfDecodedFile);

        key = new LinkedHashSet<>();
        keyFile = new File(nameOfKeyFile);
    }

    public void decode() {
        try {
            readDataToTable();

            int rowsCount = codingTable.get(0).size();

            var writer = new BufferedWriter(new FileWriter(decodedFile));

            for (int rowNumber = 0; rowNumber != rowsCount; rowNumber++)
                for (int columnNumber = 0; columnNumber != codingTable.size(); columnNumber++)
                    writer.write(codingTable.get(columnNumber).get(rowNumber));

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
        readKey();

        var decodedText = new StringBuilder();

        try {
            var reader = new BufferedReader(new FileReader(encodedFile));
            while (reader.ready())
                decodedText.append(reader.readLine());
            reader.close();

            //Определяем, сколько строк будет в каждом столбце
            int rowsCount = decodedText.length() / 5;
            int indexOfCurrentSymbol = 0;

            for (int columnNumber : key)
                for (int rowNumber = 0; rowNumber != rowsCount; rowNumber++) {
                    codingTable.get(columnNumber - 1)
                            .add(decodedText.charAt(indexOfCurrentSymbol));
                    indexOfCurrentSymbol++;
                }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Decode {
    public static void main(String[] args) {
        var decoder = new VerticalDecoder("encrypted.txt", "decoded.txt", "key.txt");
        decoder.decode();
    }
}
