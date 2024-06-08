import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static void main(String[] args) {
        String csvFile = "DataBaseLibriVero.csv"; // Sostituisci con il percorso del tuo file CSV

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            // Salta la prima riga (intestazione)
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = parseLine(line);

                if (fields.length == 8) {
                    // Esempio di accesso ai dati
                    String title = fields[0];
                    String authors = fields[1];
                    String description = fields[2]; // Può essere vuoto
                    String category = fields[3]; // Può essere vuoto
                    String publisher = fields[4];
                    String price = fields[5];
                    String publishMonth = fields[6];
                    String publishYear = fields[7];

                    // Stampa i dettagli del libro
                    System.out.println("Title: " + title);
                    System.out.println("Authors: " + authors);
                    System.out.println("Description: " + description);
                    System.out.println("Category: " + category);
                    System.out.println("Publisher: " + publisher);
                    System.out.println("Price: " + price);
                    System.out.println("Publish Date: " + publishMonth + " " + publishYear);
                    System.out.println("---------------------------");
                } else {
                    System.out.println("Riga con numero di campi errato: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] parseLine(String line) {
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        List<String> fields = new ArrayList<>();

        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes; // Toggle the inQuotes flag
            } else if (c == ';' && !inQuotes) {
                fields.add(sb.toString());
                sb.setLength(0); // Clear the StringBuilder
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString()); // Add the last field

        return fields.toArray(new String[0]);
    }
}
