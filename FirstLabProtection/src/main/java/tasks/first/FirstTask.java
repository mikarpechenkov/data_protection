package tasks.first;

import tasks.first.readers.ReaderFileByBytes;

import java.io.File;
import java.io.IOException;

public class FirstTask {
    private static final File firstFile = new File("first.doc");
    private static final File secondFile = new File("second.txt");

    public static void main(String[] args) {
        try {
            System.out.println("For the first exercise\n Size of file is "
                    +firstFile.length()+" bytes");

            var reader = new ReaderFileByBytes(secondFile.getName());
            reader.readData();

            System.out.println("For the second exercise\n"+
                    reader.getDataOfFile());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
