package be.kuleuven.swop.objectron.domain.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * A class of GridFileReaders.
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 15:22
 */
public class GridFileReader {

    final static Charset ENCODING = StandardCharsets.UTF_8;

    /**
     * Read a file specified in a filename.
     * @param fileName
     *        The filename of the file to read.
     * @return A two-dimensional array of characters.
     * @throws IOException
     *         The system encountered an IOError.
     */
    public char[][] readGridFile(String fileName) throws IOException {
        System.out.println(fileName);
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
