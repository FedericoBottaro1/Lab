import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Valutazioni_classe extends RicercaLibro_classe{
    /**
     * File statico che rappresenta il percorso del file CSV contenente il database delle valutazioni.
     */
    private static File file = new File("data/ValutazioniLibri.csv");

    /**
     * Stringa statica che contiene il percorso assoluto del file CSV rappresentato dal campo `file`.
     */
    private static String absol = file.getAbsolutePath();  
    
    /**
     * Scanner statico utilizzato per la lettura dell'input da console. 
     */
    static Scanner sc = new Scanner(System.in);

    /**
     * Il metodo effettua la ricerca del libro attraverso il ittolo e l'autore
     * @param titolo prende il titolo del libro a cui si vuole inserire una valutazione
     * @param autore prende il nome dell'autore del libro a cui si vuole inserire una valutazione
     * @return ritorna il valore di contorollo, indicando se il libro è stato trovato oppure no
    */
    public static boolean lettura(String titolo, String autore){
        boolean controllo = ricercaAutoTito(titolo, autore);
        return controllo;
    }

    /**
     * Il metodo permette all'utente di inserire una valutazione per un determinato libro, inserendo:
     * stile, contenuto, gradevolezza, originarietà, edizione
     */
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
                System.out.println("Gradevolezza: ");
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

    /**
     * Il metodo permette di visualizzare a schermo le valutazioni singole per il libro ricercato più la media delle valutazioni
     */
    public static void visualizza(){
        String titoloIns = "";
        String autoreIns = "";
        String opzione="";
        boolean stato1, statoval;
        
        System.out.println("Inserisci il titolo del libro");
        titoloIns = sc.nextLine();
        System.out.println("Inserisci l'autore del libro");
        autoreIns = sc.nextLine();
        
        System.out.print("\033c");
        
        stato1 = ricercaValAutoTito(titoloIns, autoreIns);
        
        if(stato1==false){
            System.out.println("Il libro di cui vuoi visualizzare la valutazione non è ancora stato valutato");
        }

        System.out.println("Premi invio per tornare al menu principale...");
        sc.nextLine();
    }
    
    /**
     * Il metodo effettua la ricerca delle valutazioni di un determinato libro attraverso il titolo e l'autore
     * @param titoloIns prende il titolo del libro di cui si devono ricercare le valutazioni
     * @param autoreIns prende il nome dell'autore del libro di cui si devono prendere le valutazioni
     * @return ritorna il valore di stato, indicando se sono state trovate delle valutazioni per il libro ricercato
     */
    public static boolean ricercaValAutoTito(String titoloIns, String autoreIns){    
        boolean stato = false;
        int count = 0;
        int stileMedia = 0;
        int contMedia = 0;
        int gradMedia = 0;
        int origMedia = 0;
        int ediMedia = 0;
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
                            
                    // Stampa i dettagli del libro
                    String authorsMin=authors.toLowerCase();
                    String autoreMin=autoreIns.toLowerCase();
                    String titleMin1=title.toLowerCase();
                    String titoloMin1=titoloIns.toLowerCase();
                    
                    if(authorsMin.contains(autoreMin) && titleMin1.equals(titoloMin1)){
                        stileMedia += Integer.parseInt(stile);
                        contMedia += Integer.parseInt(contenuto);
                        gradMedia += Integer.parseInt(gradevolezza);
                        origMedia += Integer.parseInt(originarieta);
                        ediMedia += Integer.parseInt(edizione);
                        count++;

                        stampa(title, authors, stile, noteS, contenuto, noteC, gradevolezza, noteG, originarieta, noteO, edizione, noteE);
                        stato = true;
                    }    
                }
            }
            stampaMedie(stileMedia, contMedia, gradMedia, origMedia, ediMedia, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stato;
    }

    /**
     * Il metodo permette di visualizzare a schermo le singole valutazioni per il libro ricercato
     * @param title prende il nome del libro valutato
     * @param authors prende il nome dell'autore del libro valutato
     * @param stile prende la valutazione del campo stile
     * @param noteS prende l'eventuale nota inserita dall'utente inerente al campo stile
     * @param contenuto prende la valutazione del campo contenuto
     * @param noteC prende l'eventuale nota inserita dall'utente inerente al campo contenuto
     * @param gradevolezza prende la valutazione del campo gradevolezza
     * @param noteG prende l'eventuale nota inserita dall'utente inerente al campo gradevolezza
     * @param originarieta prende la valutazione del campo originarietà
     * @param noteO prende l'eventuale nota inserita dall'utente inerente al campo originarietà
     * @param edizione prende la valutazione del campo edizione
     * @param noteE prende l'eventuale nota inserita dall'utente inerente al campo edizione
     */
    public static void stampa(String title, String authors, String stile, String noteS, String contenuto, String noteC, String gradevolezza, String noteG, String originarieta, String noteO, String edizione, String noteE){
        System.out.println("Titolo: " + title);
        System.out.println("Autore: " + authors);
        System.out.println("Stile: " + stile + " \n" + noteS);
        System.out.println("Contenuto: " + contenuto + " \n" + noteC);
        System.out.println("Gradevolezza: " + gradevolezza + " \n" + noteG);
        System.out.println("Originarietà: " + originarieta + " \n" + noteO);
        System.out.println("Edizione: " + edizione + " \n" + noteE);
        System.out.println("---------------------------");
    }

    /**
     * Il metodo permette di visualizzare a schermo la media delle valutazioni dei campi
     * @param stile prende la valutazione del campo stile
     * @param contenuto prende la valutazione del campo contenuto
     * @param gradevolezza prende la valutazione del campo gradevolezza
     * @param originarieta prende la valutazione del campo originarietà
     * @param edizione prende la valutazione del campo edizione
     * @param contatore prende il numero di valutazioni presenti per il libro ricercato
     */
    public static void stampaMedie(int stile, int contenuto, int gradevolezza, int originarieta, int edizione, int contatore){
        System.out.println("Le medie delle recensioni del seguente libro sono:");
        System.out.println("Stile: " + stile/contatore);
        System.out.println("Contenuto: " + contenuto/contatore);
        System.out.println("Gradevolezza: " + gradevolezza/contatore);
        System.out.println("Originarietà: " + originarieta/contatore);
        System.out.println("Edizione: " + edizione/contatore);
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