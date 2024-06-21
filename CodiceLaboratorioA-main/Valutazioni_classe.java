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

    public static boolean letturaval(String titolo, String autore){
        boolean controllo = ricercaAutoTitoVal(titolo, autore);
        return controllo;
    }

    public static void inserimento(){
        String csvFileName = "C://Users//utente//OneDrive//Desktop//UNI//Lab//Lab//ValutazioniClienti.csv";
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
        String opzione="";
        boolean stato, statoval;
        
        System.out.println("Inserisci il titolo del libro");
        titoloIns = sc.nextLine();
        System.out.println("Inserisci l'autore del libro");
        autoreIns = sc.nextLine();

        stato = lettura(titoloIns, autoreIns);
        statoval = letturaval(titoloIns, autoreIns);
        
        if(stato==true && statoval == false){
            System.out.println("Il libro di cui vuoi visualizzare la valutazione non è ancora stato valutato, vuoi essere il primo a valutarlo?");
            opzione = sc.nextLine();

            if(opzione.equals("y")){
                inserimento();
            }
        }
        else if(stato==true && statoval==true){
            String csvFile = "C://Users//utente//OneDrive//Desktop//UNI//Lab//Lab//ValutazioniClienti.csv";
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                    String line;
                    // Salta la prima riga (intestazione)
                    br.readLine();
                    
                    while ((line = br.readLine()) != null) {
                        String[] fields = parseLine(line);
                        if (fields.length == 13) {
                            // Esempio di accesso ai dati
                            String title = fields[0];
                            String authors = fields[1];
                            String stile = fields[2]; 
                            String noteS = fields[3]; // Può essere vuoto
                            String contenuto = fields[4];
                            String noteC = fields[5]; // Può essere vuoto
                            String gradevolezza = fields[6];
                            String noteG = fields[7]; // Può essere vuoto
                            String originarieta = fields[8];
                            String noteO = fields[9]; // Può essere vuoto
                            String edizione = fields[10];
                            String noteE = fields[11]; // Può essere vuoto
                            String med = fields[12];
                            
                            stampa(title, authors, stile, noteS, contenuto, noteC, gradevolezza, noteG, originarieta, noteO, edizione, noteE, med);
                        }
                    }
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }

    

    public static void stampa(String title, String authors, String stile, String noteS, String contenuto, String noteC, String gradevolezza, String noteG, String originarieta, String noteO, String edizione, String noteE, String med){
        System.out.println("Titolo: " + title);
        System.out.println("Autore: " + authors);
        System.out.println("Stile: " + stile + " " + noteS);
        System.out.println("Contenuto: " + contenuto + " " + noteC);
        System.out.println("Gradevolezza: " + gradevolezza + " " + noteG);
        System.out.println("Originarietà: " + originarieta + " " + noteO);
        System.out.println("Edizione: " + edizione + " " + noteE);
        System.out.println("---------------------------");
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
