import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Valutazioni_classe extends RicercaLibro_classe{
    private static File file = new File("Resources/ValutazioniClienti.csv");
    private static String absol = file.getAbsolutePath();  
    
    static Scanner sc = new Scanner(System.in);

    public static boolean lettura(String titolo, String autore){
        boolean controllo = ricercaAutoTito(titolo, autore);
        return controllo;
    }

    public static void inserimento(){
        File csvFile = new File(absol);

        int controllo = 0;
        int media = 0;
        String info = "";
        String titoloIns = "";
        String autoreIns = "";
        boolean stato;

        try {
            if (csvFile.createNewFile()) {
                System.out.println("File creato: " + absol);
            } else {
                System.out.println("Il file " + absol + " esiste già e verrà sovrascritto.");
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
                

                try (FileWriter writer = new FileWriter(absol, true)) {
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
                    System.out.println("Dati salvati correttamente in " + absol);

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
        boolean stato1, statoval;
        
        System.out.println("Inserisci il titolo del libro");
        titoloIns = sc.nextLine();
        System.out.println("Inserisci l'autore del libro");
        autoreIns = sc.nextLine();

        stato1 = ricercaAutoTitoVal(titoloIns, autoreIns);
        
        if(stato1==false){
            System.out.println("Il libro di cui vuoi visualizzare la valutazione non è ancora stato valutato");
        }else{
            try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
                String line;
                        
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
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Premi invio per tornare al menu principale...");
        sc.nextLine();
    }
    
    public static boolean ricercaAutoTitoVal(String titoloIns, String autoreIns){    
        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
            String line;
            // Salta la prima riga (intestazione)
            //br.readLine();
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
                    
                    // Stampa i dettagli del libro
                    String authorsMin=authors.toLowerCase();
                    String autoreMin=autoreIns.toLowerCase();
                    String titleMin1=title.toLowerCase();
                    String titoloMin1=titoloIns.toLowerCase();
                    if(authorsMin.contains(autoreMin) && titleMin1.equals(titoloMin1)){
                        System.out.print("\033c");
                        stampa(title, authors, stile, noteS, contenuto, noteC, gradevolezza, noteG, originarieta, noteO, edizione, noteE, med);
                        return true;
                    }        
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
