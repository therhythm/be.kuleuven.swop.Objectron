package be.kuleuven.swop.objectron.domain.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 15:22
 */
public class GridFileReader {

    final static Charset ENCODING = StandardCharsets.UTF_8;

    public char[][] readGridFile(String fileName) throws IOException {
        //todo change , doesnt work with other partitions (ask Thomas)
        Path path = Paths.get(fileName);

        BufferedReader reader = Files.newBufferedReader(path, ENCODING);
        String line;
        int lineLength = 0;
        ArrayList<String> temp = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            temp.add(line);
            if (lineLength != 0 && line.length() != 0 && line.length() != lineLength) {
                //todo exception
            } else {
                lineLength = line.length();
            }

        }

        char[][] inputArray = new char[temp.size()][lineLength];
        for (int i = 0; i < temp.size(); i++) {
            char[] chars = temp.get(i).toCharArray();
            inputArray[i] = chars;
        }
        return inputArray;
    }
}
