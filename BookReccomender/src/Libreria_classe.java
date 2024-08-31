import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Libreria_classe extends Login_classe{
    /**
     * File statico che rappresenta il percorso del file CSV contenente il database delle librerie.
     */
    private static File file = new File("data/Librerie.csv");

    /**
     * Stringa statica che contiene il percorso assoluto del file CSV rappresentato dal campo `file`.
     */
    private static String absol = file.getAbsolutePath();  
    
    /**
     * Istanza statica della classe `RicercaLibro_classe` utilizzata per gestire le operazioni 
     * di ricerca di libri nel sistema.
     */
    private static RicercaLibro_classe rc = new RicercaLibro_classe();
    
    /**
     * Scanner statico utilizzato per la lettura dell'input da console. 
     */
    static Scanner sc = new Scanner(System.in);

    /**
     * Questo metodo rappresenta il menù delle funzionalità che si possono effettuare sulle librerie
     * e i richiami ai metodi in base alla scelta effettuata.
     */
    public static void menu(){
        String scelta = "";

        System.out.print("\033c");

        System.out.println("Scegli cosa desideri fare");
        System.out.println("1) Crea una nuova libreria");
        System.out.println("2) Inserisci libro ad una libreria già esistente");
        System.out.println("3) Visualizza librerie");
        System.out.println("4) Esci dal menu");
        scelta = sc.nextLine();
        
        switch(scelta){
            case "1":{
                registraLibreria();
                break;
            }
            case "2":{
                inserisciLibro();
                break;
            }
            case "3":{
                leggiLibreria();
                break;
            }
            case "4":{
                break;
            }
        }

    }

    /**
     * Il metodo permette di creare una nuova libreria personale 
     * e chiede il titolo e l'autore del primo libro da inserire nella libreria
     */
    public static void registraLibreria(){
        File csvFile = new File(absol);

        String scelta ="";
        String nome, titolo, autore;
        boolean stato = false;

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


        try (FileWriter writer = new FileWriter(absol, true)) {
            System.out.println("Inserire il nome della nuova libreria: ");
            nome = sc.nextLine();

            do{
                do{  
                    //System.out.print("\033c");
                    System.out.println("Inserire il titolo da inserire nella libreria: " + nome);
                    titolo = sc.nextLine();
                    System.out.println("Inserire l'autore del libro: " + titolo);
                    autore = sc.nextLine();

                    stato = rc.ricercaAutoTito(titolo, autore);
                }while(stato != true);
                    
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
            }while(!scelta.equals("n"));
            
            
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
        }
    }

    /**
     * Il metodo permette di inserire un libro in una determinata libreira
     */
    public static void inserisciLibro(){
        boolean stato=false;
        String nomeLib = "";
        System.out.println("Inserisci il nome della libreria a cui vuoi aggiungere il libro: ");
        nomeLib = sc.nextLine();
        
        System.out.print("\033c");
        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] fields = parseLine(line);
                
                if(fields.length == 5) {
                    // Esempio di accesso ai dati
                    String user = fields[0];
                    String nome = fields[1];
                    String title = fields[2];
                    String authors = fields[3]; 

                    if(user.equals(Login_classe.userId) && nome.equals(nomeLib)){
                        stato = true;
                        //sc.nextLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(stato){
            try (FileWriter writer = new FileWriter(absol, true)) {
                String titolo ="";
                String autore ="";
                boolean stato1 = false;
                
                do{  
                    //System.out.print("\033c");
                    System.out.println("Inserire il titolo da inserire nella libreria: " + nomeLib);
                    titolo = sc.nextLine();
                    System.out.println("Inserire l'autore del libro: " + titolo);
                    autore = sc.nextLine();

                    stato1 = rc.ricercaAutoTito(titolo, autore);
                }while(stato1 != true);
                        
                // Scrittura dei dati nel file CSV
                writer.append(Login_classe.userId); //nome user
                writer.append(';');
                writer.append(nomeLib); //nome libreria
                writer.append(';');
                writer.append(titolo); //titolo libro
                writer.append(';');
                writer.append(autore); //autore
                writer.append(';');
                writer.append('\n');            
            } catch (IOException e) {
                System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
            }
        }else{
            System.out.println("Non e' stato possibie trovare la seguente libreria");
        }
    }

    /**
     * Il metodo permette di visualizzare tutti i libri presenti nella libreria
     */
    public static void leggiLibreria(){
        String nomeLib = "";
        System.out.println("Inserisci il nome della libreria che vuoi visualizzare: ");
        nomeLib = sc.nextLine();
        
        System.out.print("\033c");
        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] fields = parseLine(line);
                
                if(fields.length == 5) {
                    // Esempio di accesso ai dati
                    String user = fields[0];
                    String nome = fields[1];
                    String title = fields[2];
                    String authors = fields[3]; 

                    if(user.equals(Libreria_classe.userId) && nome.equals(nomeLib)){
                        stampa(nome, user, title, authors);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        sc.nextLine();
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
     * Il metodo permette di visualizzare a schermo le informazioni inerenti alla libreria
     * @param nome prende il nome della libreria
     * @param user prende lo user del proprietario della libreria
     * @param title prende i titoli dei libri presenti nella libreria
     * @param authors prende i nomi degl'autori dei libri in libreria 
     */
    public static void stampa(String nome, String user, String title, String authors){
        System.out.println("Libreria: " + nome);
        System.out.println("Proprietario: " + user);
        System.out.println("Titolo: " + title);
        System.out.println("Autore: " + authors);
        System.out.println("---------------------------");
    }
}