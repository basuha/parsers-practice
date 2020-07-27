package telegram;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static void main(String[] args) throws IOException {
        InputStream reader = new FileInputStream("andr.json");
        OutputStream outputStream = new FileOutputStream("andr.txt");

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        BufferedReader buf = new BufferedReader(new InputStreamReader(reader));

        List<Message> messages = new ArrayList<>();
        while (true) {
            Message message = new Message();
            while (true) {
                String line = buf.readLine();
                if (line == null) {
                    break;
                }
                Pattern fromPat = Pattern.compile("\"from\": \"(.+)\"");
                Matcher fromMatch = fromPat.matcher(line);
                if (fromMatch.find()) {
                    message.setFrom(fromMatch.group(1));
                    break;
                }
            }
            while (true) {
                String line = buf.readLine();
                if (line == null) {
                    break;
                }
                Pattern textPat = Pattern.compile("\"text\": \"(.+|\n)\"");
                Matcher textMatch = textPat.matcher(line);
                if (textMatch.find()) {
                    message.setText(textMatch.group(1).replaceAll("\\\\n", " "));
                    break;
                }
            }
            if(message.getFrom() == null && message.getText() == null) {
                break;
            }
            messages.add(message);
        }

        for (var s : messages) {
            writer.write(s.getFrom() + ": " + s.getText());
            writer.write("\n");
            writer.flush();
        }
        writer.close();
    }
}