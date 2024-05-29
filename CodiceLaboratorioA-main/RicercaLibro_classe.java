import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class RicercaLibro_classe {
    //Attributi
    //Metodi
    private static int sceltaRicerca = 0;
    
    static Scanner scanner = new Scanner(System.in);
    public static void menu(){
        
        int scelta = 0;

        do{
            System.out.print("\033c");
            System.out.println("Scegli secondo che criterio effettuare la ricerca: ");
            
            System.out.println("1) Titolo");
            System.out.println("2) Autore");
            System.out.println("3) Autore e Anno");
            System.out.println("4) Ritorna alla home");
            
            System.out.print("Selezionare l'azione: ");
            scelta = scanner.nextInt();
            scanner.nextLine();
            
            scelta(scelta);

        } while (scelta != 1 && scelta != 2 && scelta != 3);
    }

    public static void scelta(int scelta){
        switch(scelta){
            case 1: //Ricerca per Titolo 1
                ricercaTitolo();
                break;
            case 2: //Ricerca per Autore 2
                ricercaAutore();
                break;
            case 3: //Ricerca per Autore,Anno 3
                ricercaAutoAnno();
                break;
            case 4: //Torna Home 4
                
                break;
        }
    }
    
    //1
    public static void ricercaTitolo(){
        sceltaRicerca = 1;
        System.out.println("Inserire il titolo del libro di cercare: ");
        String titolo=scanner.nextLine(); 
        String csvFile = "C://Users//utente//OneDrive//Desktop//UNI//Lab//CodiceLaboratorioA-main//DataBaseLibriVero.csv";
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
                    String titleMin=title.toLowerCase();
                    String titoloMin=titolo.toLowerCase();
                    if(titleMin.contains(titoloMin)){
                        stampa(sceltaRicerca, title, authors, description, category, publisher, price, publishMonth, publishYear);
                    }
                    } else {
                        System.out.println("Riga con numero di campi errato: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    //2
    public static void ricercaAutore(){
        sceltaRicerca=2;
        System.out.println("Inserire l'autore da cercare: ");
                String autore=scanner.nextLine(); 
                String csvFile1 = "C://Users//utente//OneDrive//Desktop//UNI//Lab//CodiceLaboratorioA-main//DataBaseLibriVero.csv";
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile1))) {
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
                            String authorsMin=authors.toLowerCase();
                            String autoreMin=autore.toLowerCase();
                            if(authorsMin.contains(autoreMin)){
                                stampa(sceltaRicerca, title, authors, description, category, publisher, price, publishMonth, publishYear);
                            }
                            
                        } else {
                            System.out.println("Riga con numero di campi errato: " + line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }

    //3
    public static void ricercaAutoAnno(){
        sceltaRicerca = 3;
        System.out.println("Inserire l'autore da cercare: ");
        String autore1=scanner.nextLine();
        System.out.println("Inserire l'anno di uscita del libro: ");
        String anno=scanner.nextLine(); 
        String csvFile2 = "C://Users//utente//OneDrive//Desktop//UNI//Lab//CodiceLaboratorioA-main//DataBaseLibriVero.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile2))) {
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
                    String authorsMin=authors.toLowerCase();
                    String autoreMin=autore1.toLowerCase();
                    if(authorsMin.contains(autoreMin) && publishYear.equals(anno)){
                        stampa(sceltaRicerca, title, authors, description, category, publisher, price, publishMonth, publishYear);
                    }        
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

    /*
    public static void letturaCSV(){
        String title = fields[0];
        String authors = fields[1];
        String description = fields[2]; // Può essere vuoto
        String category = fields[3]; // Può essere vuoto
        String publisher = fields[4];
        String price = fields[5];
        String publishMonth = fields[6];
        String publishYear = fields[7];
    }*/

    public static void stampa(int sceltaRicerca, String title, String authors, String description, String category, String publisher, String price, String publishMonth, String publishYear){
        if (sceltaRicerca == 1) {
            System.out.println("Title: " + title);
            System.out.println("Authors: " + authors);
            System.out.println("Description: " + description);
            System.out.println("Category: " + category);
            System.out.println("Publisher: " + publisher);
            System.out.println("Price: " + price);
            System.out.println("Publish Date: " + publishMonth + " " + publishYear);
            System.out.println("---------------------------");
        } else if (sceltaRicerca == 2){
            System.out.println("Title: " + title);
            System.out.println("Authors: " + authors);
            System.out.println("---------------------------");
        } else {
            System.out.println("Title: " + title);
            System.out.println("Authors: " + authors);
            System.out.println("Publish Date: " + publishMonth + " " + publishYear);
            System.out.println("---------------------------");
        }
    }
}