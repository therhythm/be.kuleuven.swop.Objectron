package be.kuleuven.swop.objectron.domain.util;

import java.io.BufferedReader;
import java.io.IOException;
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
        Path path = Paths.get(fileName);
        try {
            BufferedReader reader = Files.newBufferedReader(path, ENCODING);
            String line;
            ArrayList<String> temp = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                temp.add(line);
            }
            //TODO: is a grid always square?
            char[][] inputArray = new char[temp.size()][temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                char[] chars = temp.get(i).toCharArray();
                inputArray[i] = chars;
            }
            return inputArray;
        } catch (IOException e) {
            throw e;
        }
    }
}
