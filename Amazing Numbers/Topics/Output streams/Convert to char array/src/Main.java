import java.io.CharArrayWriter;
import java.io.IOException;

class Converter {
    public static char[] convert(String[] words) throws IOException {
        CharArrayWriter writer = new CharArrayWriter();
        char[] chars = new char[0];

        for (String word : words) {
            writer.write(word);
            chars = writer.toCharArray();
        }

        writer.close();

        return chars;
    }
}