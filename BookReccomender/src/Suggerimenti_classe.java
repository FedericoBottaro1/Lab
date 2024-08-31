import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Suggerimenti_classe extends Libreria_classe{
    /**
     * File statico che rappresenta il percorso del file CSV contenente il database dei consigli dei libri.
     */
    private static File file = new File("data/ConsigliLibri.csv");

    /**
     * Stringa statica che contiene il percorso assoluto del file CSV rappresentato dal campo `file`.
     */
    private static String absol = file.getAbsolutePath();  
    
    /**
     * File statico che rappresenta il percorso del file CSV contenente il database delle librerie.
     */
    private static File file1 = new File("data/Librerie.csv");

    /**
     * Stringa statica che contiene il percorso assoluto del file CSV rappresentato dal campo `file1`.
     */
    private static String absol1 = file1.getAbsolutePath();

    /**
     * Scanner statico utilizzato per la lettura dell'input da console. 
     */
    static Scanner sc = new Scanner(System.in);
    
    /**
     * Il metodo permette di inserire dei suggerimenti (max. 3) per determinato libro; 
     * per poter inserire un determinato libro come suggerimento quest'ultimo 
     * deve essere presente in almeno una libreria dell'utente.
    */
    public static void inserisciSuggerimentoLibro(){
       File csvFile = new File(absol);

        String scelta = "";
        String titoloSugg, autoreSugg;
        boolean stato1 = false;

        try {
            if (csvFile.createNewFile()) {
                System.out.println("File creato: " + absol);
            } else {
                System.out.println("Il file " + absol + " esiste gia' e verra' sovrascritto.");
            }
        } catch (IOException e) {
            System.out.println("Si e' verificato un errore durante la creazione del file.");
            e.printStackTrace();
        }
        System.out.print("\033c");

        Scanner scanner = new Scanner(System.in);
        List<String[]> dataLines = new ArrayList<>();

        System.out.println("Inserire i dati del libro a cui si vogliono inserire i suggerminti: ");
        System.out.println("Titolo: ");
        String titolo = sc.nextLine();
        
        System.out.println("Autore: ");
        String autore = sc.nextLine();
        
        boolean stato = ricercaLib(titolo, autore);

        int limite = limiteConsigli(titolo, autore);

        if(stato == true && limite < 3){
            try (FileWriter writer = new FileWriter(absol, true)) {
                do{  
                    System.out.print("\033c");
                    
                    System.out.println("Inserire il suggerimento: ");
                    System.out.println("Titolo: ");
                    titoloSugg = sc.nextLine();
                        
                    System.out.println("Autore: ");
                    autoreSugg = sc.nextLine();
    
                    stato = RicercaLibro_classe.ricercaAutoTito(titolo, autore);
                }while(stato != true);
                    
                // Scrittura dei dati nel file CSV
                writer.append(Login_classe.userId); //nome user
                writer.append(';');
                writer.append(titolo); 
                writer.append(';');
                writer.append(autore); 
                writer.append(';');
                writer.append(titoloSugg); 
                writer.append(';');
                writer.append(autoreSugg);
                writer.append(';');
                writer.append('\n');
            }catch (IOException e) {
                System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
            }
        }else if(limite == 3){
            System.out.println("E' stato raggiunto il numero massimo di suggerimenti.");
        }else{
            System.out.println("Il libro non e' presente in nessuna libreria");
        }
        sc.nextLine();
    }

    /**
     * Il metodo effettua la ricerca del libro da inserire come suggerimento nelle librerie dell'utente
     * @param titolo prende il nome del libro da inserire come suggermineto
     * @param autore prende il nome dell'autore del libro da inserire come suggerimento
     * @return ritorna il valore della variabile stato, indicando se il libro è stato trovato in una libreria dell'utente ooppure no
    */
    public static boolean ricercaLib(String titolo, String autore){
        String line;
        boolean stato = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader(absol1))) {
            while ((line = br.readLine()) != null) {
                String[] fields = parseLine(line);
                if (fields.length == 5) {
                    String user = fields[0];
                    String title = fields[2]; 
                    String authors = fields[3];
                    
                    if(user.equals(Login_classe.userId) && titolo.equals(title) && autore.contains(authors)) {
                        stato = true;
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        return stato;
    }

    /**
     * Il metodo permette di capire quando viene raggiunto il limite massimo di consigli per un determinato libro
     * @param titolo prende il nome  del libro a cui si vuole inserire un suggerimento
     * @param autore prende il nome dell'autore del libro a cui si vuole inserire un suggerimento
     * @return restituisce il valore di count che rappresenta il numero di consigli aggiunti per un determinato libro
     */
    public static int limiteConsigli(String titolo, String autore){
        int count = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
            while ((line = br.readLine()) != null) {
                String[] fields = parseLine(line);
                if (fields.length == 6) {
                    
                    String user = fields[0];
                    String title = fields[1];
                    String authors = fields[2]; 
                    String titleSugg = fields[3]; 
                    String authorsSugg = fields[4];

                    if(user.equals(Libreria_classe.userId) && titolo.equals(title)) {
                        count++;
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
    * Il metodo permette di leggere le righe del file csv ignorando i caratteri separatori
    * @param line è la riga del file csv
    * @return ritorna una array di stringhe con i campi separati
    */
    private static String[] parseLine(String line) {
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        List<String> fields = new ArrayList<>();

        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes; 
            } else if (c == ';' && !inQuotes) {
                fields.add(sb.toString());
                sb.setLength(0); 
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString()); 

        return fields.toArray(new String[0]);
    }
}