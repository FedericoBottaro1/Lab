import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Libreria_classe extends Login_classe{
    private static File file = new File("Resources/Librerie.csv");
    private static String absol = file.getAbsolutePath();  
    
    private static RicercaLibro_classe rc = new RicercaLibro_classe();
    
    static Scanner sc = new Scanner(System.in);

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

    public static void leggiLibreria(){
        //deve leggere e stampare solamente i libri presenti nella libreria (titolo autore)
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
                        //sc.nextLine();
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
