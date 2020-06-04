import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String pageRoot = "http://globus.aquaviva.ru/places/?t=2097&view=catalog&PAGEN_1=";
    static List templates = new ArrayList<Template>();
    public static void main(String[] args) throws IOException {
        for (int i=1; i<=114; i++){
            read(i);
            System.out.printf("Page %d read already!\n", i);
        }

        var text = templates.stream().reduce((a, b) -> a.toString()+'\n'+b.toString());

        try (PrintWriter out = new PrintWriter("temples.csv")) {
            out.println(text);
        }
    }

    private static void read(int page) throws IOException {
        Document doc = Jsoup.connect(pageRoot+page).get();
        var details = doc.select(".detail");
        for (var detail : details) {
            templates.add(new Template(
                    detail.getElementsByAttributeValue("itemprop","name").text(),
                    detail.getElementsByClass("descr").text()
            ));
        }
    }
}
