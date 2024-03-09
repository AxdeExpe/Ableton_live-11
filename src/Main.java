import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main{

    public static void main(String[] var0) {
        int threads = Runtime.getRuntime().availableProcessors() - 1;
        Writer[] writerArr = new Writer[threads];

        while(true) {

            for(int i = 0; i < threads; ++i) {
                writerArr[i] = new Writer();
                writerArr[i].start();
            }

            for(int i = 0; i < threads; ++i) {
                try {
                    writerArr[i].join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            String mergeFile = "infinite_merge.txt";
            String[] fileList = new String[threads];

            for(int i = 0; i < threads; ++i) {
                fileList[i] = "infinite_" + writerArr[i].getId() + ".txt";
            }

            System.out.println("Merging...");
            mergeFiles(fileList, mergeFile);
        }
    }

    private static void mergeFiles(String[] fileNames, String mergedFileName) {
        try (FileWriter fw = new FileWriter(mergedFileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (String fileName : fileNames) {
                try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    // copy each line into the new file
                    while ((line = br.readLine()) != null) {
                        bw.write(line);
                        bw.newLine(); // Neue Zeile einfügen
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // deleting the deprecated files
        for (String fileName : fileNames) {
            File file = new File(fileName);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File deleted: " + fileName);
                } else {
                    System.out.println("Error occurred while deleting file: " + fileName);
                }
            }
        }

    }
}
