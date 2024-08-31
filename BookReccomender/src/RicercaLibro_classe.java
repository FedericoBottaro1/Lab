import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class RicercaLibro_classe {
    /**Campi*/
    /**
     * Salva in sceltaRicerca l'opzione selezionata
     */
    private static int sceltaRicerca = 0;
    /**
     * Scanner statico utilizzato per la lettura dell'input da console. 
     */
    static Scanner scanner = new Scanner(System.in);

    /**
     * File statico che rappresenta il percorso del file CSV contenente il database dei libri.
     */
    private static File file = new File("data/Libri.csv");

    /**
     * Stringa statica che contiene il percorso assoluto del file CSV rappresentato dal campo `file`.
     */
    private static String absol = file.getAbsolutePath();  
    
    /**
     * Questo metodo rappresenta il menù in cui viene scelta il tipo di ricerca che si vuole effettuare
     */
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

        } while (scelta != 1 && scelta != 2 && scelta != 3 && scelta != 4);
    }

    /**
     * Questo metodo rappresenta la scelta della ricerca con i richiami ai metodi corrispondenti
     * @param scelta prende il numero corrispondete al tipo di scelta
     */
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
    /**
     * Il metodo permette di effettuare la ricerca di un libro attraverso il titolo
     */
    public static void ricercaTitolo(){
        sceltaRicerca = 1;
        System.out.println("Inserire il titolo del libro di cercare: ");
        String titolo=scanner.nextLine(); 

        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
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
    /**
    * Il metodo permette di effettuare la ricerca di un libro attraverso il uso autore
    */
    public static void ricercaAutore(){
        sceltaRicerca=2;
        System.out.println("Inserire l'autore da cercare: ");
        String autore=scanner.nextLine(); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
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
                }else {
                    System.out.println("Riga con numero di campi errato: " + line);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //3
    /**
    * Il metodo permette di effettuare la ricerca di un libro attraverso il suo autore e l'anno di pubblicazione
    */
    public static void ricercaAutoAnno(){
        sceltaRicerca = 3;
        System.out.println("Inserire l'autore da cercare: ");
        String autore1=scanner.nextLine();
        System.out.println("Inserire l'anno di uscita del libro: ");
        String anno=scanner.nextLine(); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
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

    /**
    * Il metodo permette di effettuare la ricerca di un libro attraverso il uso autore e il titolo del libro
    * @param titoloIns è il titolo inserito dall'utente
    * @param autoreIns è l'autore inserito dall'utente 
    * @return lo stato della ricerca
    */
    public static boolean ricercaAutoTito(String titoloIns, String autoreIns){    
        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
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
                    String autoreMin=autoreIns.toLowerCase();
                    String titleMin1=title.toLowerCase();
                    String titoloMin1=titoloIns.toLowerCase();
                    if(authorsMin.contains(autoreMin) && titleMin1.equals(titoloMin1)){
                        return true;
                    }        
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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


    /**
    * Il metodo permette di visualizzare a schermo le informazioni dei libri 
    * @param sceltaRicerca è il numero corrispondete al tipo di ricerca scelta
    * @param title prende il titolo del libro 
    * @param authors prende l'autore del libro
    * @param description prende la descrizione del libro
    * @param category prende la categoria del libro
    * @param publisher prende l'editore del libro
    * @param price prende il prezzo del libro
    * @param publishMonth prende il mese di pubblicazione del libro
    * @param publishYear prende l'anno di pubblicazione del libro
    */
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