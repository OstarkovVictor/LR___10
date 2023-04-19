package EX7;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class NewsParser {
    private static final String URL = "http://fat.urfu.ru/index.html";
    private static final String FILE_NAME = "src/lab10/task3/news.txt";
    private static final int MAX_RETRIES = 3;

    public static void main(String[] args) {
        Document doc = null;
        int retries = 0;
        boolean success = false;

        // Try to connect to the website and get the HTML code
        while (retries < MAX_RETRIES && !success) {
            try {
                doc = Jsoup.connect(URL).get();
                success = true;
            } catch (IOException e) {
                System.err.println("Failed to connect to " + URL + ": " + e.getMessage());
                retries++;
            }
        }

        // If we couldn't get the HTML code after max retries, exit the program
        if (!success) {
            System.err.println("Failed to get HTML code after " + MAX_RETRIES + " retries. Exiting.");
            System.exit(1);
        }

        // Extract the list of news items
        Elements newsParent = doc
                .select("body > table > tbody > tr > td > div > table > " +
                        "tbody > tr:nth-child(5) > td:nth-child(3) > table > tbody > " +
                        "tr > td:nth-child(1)");

        // Write the news items to a file
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (int i = 3; i < 20; i++) {
                if (!(i % 2 == 0)) {
                    List<Node> nodes = newsParent.get(0).childNodes();
                    String topic = ((Element) nodes.get(i))
                            .getElementsByClass("blocktitle")
                            .get(0).childNodes().get(0).toString();
                    String date = ((Element) nodes.get(i))
                            .getElementsByClass("blockdate")
                            .get(0).childNodes().get(0).toString();
                    String newsItem = "Topic: " + topic + "\n" +
                            "Date: " + date + "\n\n";
                    writer.write(newsItem);
                    System.out.println(newsItem);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to write to file " + FILE_NAME + ": " + e.getMessage());
        }
    }
}
