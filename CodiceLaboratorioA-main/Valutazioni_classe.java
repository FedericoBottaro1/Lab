import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Valutazioni_classe extends RicercaLibro_classe{
    static Scanner sc = new Scanner(System.in);

    public static boolean lettura(String titolo, String autore){
        boolean controllo = ricercaAutoTito(titolo, autore);
        return controllo;
    }

    public static void inserimento(){
        String csvFileName = "ValutazioniClienti.csv";
        File csvFile = new File(csvFileName);

        int controllo = 0;
        int media = 0;
        String info = "";
        String titoloIns = "";
        String autoreIns = "";
        boolean stato;

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

        // Lettura dei dati dall'utente
        do{
            System.out.println("Inserisci il titolo del libro");
            titoloIns = sc.nextLine();
            System.out.println("Inserisci l'autore del libro");
            autoreIns = sc.nextLine();

            stato = lettura(titoloIns, autoreIns);

            if(stato == false){
                System.out.println("Non è stato possibile trovare il libro");
            }else{
                // Lettura dei dati dall'utente
                System.out.println("Inserisci le valutazioni e se vuoi anche un apiccola recensione per ogni criterio.");
                System.out.println("Stile: ");
                String stile = sc.nextLine(); 
                media += Integer.parseInt(stile);
                System.out.println("Note (opzionale): ");
                String noteS = sc.nextLine(); 
                System.out.println("Contenuto: ");
                String contenuto = sc.nextLine(); 
                media += Integer.parseInt(contenuto);
                System.out.println("Note (opzionale): ");
                String noteC = sc.nextLine(); 
                System.out.println("Gredevolezza: ");
                String gradevolezza = sc.nextLine(); 
                media += Integer.parseInt(gradevolezza);
                System.out.println("Note (opzionale): ");
                String noteG = sc.nextLine(); 
                System.out.println("Originarietà: ");
                String originarieta = sc.nextLine(); 
                media += Integer.parseInt(originarieta);
                System.out.println("Note (opzionale): ");
                String noteO = sc.nextLine();
                System.out.println("Edizione: ");
                String edizione = sc.nextLine(); 
                media += Integer.parseInt(edizione);
                System.out.println("Note (opzionale): ");
                String noteE = sc.nextLine(); 
                String med = media/5 + "";
                

                try (FileWriter writer = new FileWriter(csvFileName, true)) {
                    // Scrittura dei dati nel file CSV
                    writer.append(titoloIns);
                    writer.append(';');
                    writer.append(autoreIns);
                    writer.append(';');
                    writer.append(stile);
                    writer.append(';');
                    writer.append(noteS);
                    writer.append(';');
                    writer.append(contenuto);
                    writer.append(';');
                    writer.append(noteC);
                    writer.append(';');
                    writer.append(gradevolezza);
                    writer.append(';');
                    writer.append(noteG);
                    writer.append(';');
                    writer.append(originarieta);
                    writer.append(';');
                    writer.append(noteO);
                    writer.append(';');
                    writer.append(edizione);
                    writer.append(';');
                    writer.append(noteE);
                    writer.append(';');
                    writer.append(med);
                    writer.append('\n');
                    System.out.println("Dati salvati correttamente in " + csvFileName);

                } catch (IOException e) {
                    System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
                }
            }
        }while(stato != true);
    }
    public static void visualizza(){
        String titoloIns = "";
        String autoreIns = "";
        boolean stato;
        do{
            System.out.println("Inserisci il titolo del libro");
            titoloIns = sc.nextLine();
            System.out.println("Inserisci l'autore del libro");
            autoreIns = sc.nextLine();

            stato = lettura(titoloIns, autoreIns);
        }while(stato != false); 
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
