import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Libreria_classe extends Login_classe{
    private static RicercaLibro_classe rc = new RicercaLibro_classe();
    
    static Scanner sc = new Scanner(System.in);

    public static void registraLibreria(){
        String csvFileName = "C://Users//utente//OneDrive//Desktop//UNI//Lab//Lab//Librerie.csv";
        File csvFile = new File(csvFileName);

        String scelta ="";

        try {
            if (csvFile.createNewFile()) {
                System.out.println("File creato: " + csvFileName);
            } else {
                System.out.println("Il file " + csvFileName + " esiste già e verrà sovrascritto.");
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore durante la creazione del file.");
            e.printStackTrace();
        }
        System.out.print("\033c");

        Scanner scanner = new Scanner(System.in);
        List<String[]> dataLines = new ArrayList<>();

        System.out.println("Inserire il nome della nuova libreria: ");
        String nome = sc.nextLine();
        System.out.println("Inserire il titolo da inserire nella libreria: " + nome);
        String titolo = sc.nextLine();
        System.out.println("Inserire l'autore del libro: " + titolo);
        String autore = sc.nextLine();

        if(rc.ricercaAutoTito(titolo, autore) == true){
            try (FileWriter writer = new FileWriter(csvFileName, true)) {
                // Scrittura dei dati nel file CSV
                writer.append(Login_classe.userId); //nome user
                writer.append(';');
                writer.append(nome); //nome libreria
                writer.append(';');
                writer.append(titolo); //titolo libro
                writer.append(';');
                writer.append(autore); //autore
                writer.append(';');
                writer.append('\n');
                System.out.println("Vuoi inserire un altro libro? (y / n) ");
                scelta = sc.nextLine();
    
                //mettere a posto perchè se si preme 'n' torna al menu
                if(scelta.equals("y")){
                    System.out.println("Inserire il titolo da inserire nella libreria " + nome);
                    titolo = sc.nextLine();
                    System.out.println("Inserire l'autore del libro: " + titolo);
                    autore = sc.nextLine();
                    writer.append(Login_classe.userId); //nome user
                    writer.append(';');
                    writer.append(nome); //nome libreria
                    writer.append(';');
                    writer.append(titolo);
                    writer.append(';');
                    writer.append(autore); //autore
                    writer.append(';');
                }else{ 
                    writer.append('\n');
                   /* System.out.println("Vuoi inserire dei suggerimenti per questo libro? (max 3) (y / n) ");
                    scelta = sc.nextLine();

                    if(scelta.equals("y")){
                        Suggerimenti_classe.inserisciSuggerimentoLibro();
                    }else{
                        writer.append('\n');
                    } */
                    System.out.println("Dati salvati correttamente in " + csvFileName);
                    sc.nextLine();
                }
                // il file non viene creato, capire il perchè...
            } catch (IOException e) {
                System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
            }
        }else{
            System.out.println("Il libro non è presente nel database.");
            sc.nextLine();
        }
    }

    public static void leggiLibreria(){
        //deve leggere e stampare solamente i libri presenti nella libreira (titolo autore)
        String csvFile = "C://Users//utente//OneDrive//Desktop//UNI//Lab//Lab//Librerie.csv";

        System.out.println("oaic");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
                    
            while ((line = br.readLine()) != null) {
                String[] fields = parseLine(line);
                if (fields.length == 4) {
                    // Esempio di accesso ai dati
                    String nome = fields[0];
                    String user = fields[1];
                    String title = fields[2];
                    String authors = fields[3]; 
                    
                    while(user.equals(Login_classe.userId)){
                        stampa(nome, user, title, authors);
                        sc.nextLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sc.nextLine();
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
    
    public static void stampa(String nome, String user, String title, String authors){
        System.out.println("Libreria: " + nome);
        System.out.println("Proprietario: " + user);
        System.out.println("Titolo: " + title);
        System.out.println("Autore: " + authors);
        System.out.println("---------------------------");
    }
}
