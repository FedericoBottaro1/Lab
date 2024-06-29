import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Suggerimenti_classe extends Libreria_classe{
    private static File file = new File("Resources/ConsigliLibri.csv");
    private static String absol = file.getAbsolutePath();  
    
    private static File file1 = new File("Resources/Librerie.csv");
    private static String absol1 = file.getAbsolutePath();

    static Scanner sc = new Scanner(System.in);
    
    public static void inserisciSuggerimentoLibro(){
       File csvFile = new File(absol);

        String scelta ="";

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

        System.out.println("Inserire i dati del libro a cui si vogliono inserire i suggerminti: ");
        System.out.println("Titolo: ");
        String titolo = sc.nextLine();
        System.out.println("Autore: ");
        String autore = sc.nextLine();
        //dopo che vengono inseriti i dati il programma si chiude e torna al login home
        boolean stato=ricercaLib(titolo, autore);

        System.out.println(stato);

        if(stato == true){
            System.out.println("Inserire il suggerimento: ");
            System.out.println("Titolo: ");
            String titoloSugg = sc.nextLine();
            System.out.println("Autore: ");
            String autoreSugg = sc.nextLine();

            try (FileWriter writer = new FileWriter(absol, true)) {
                writer.append(titolo); 
                writer.append(';');
                writer.append(titoloSugg); 
                writer.append(';');
                writer.append(autoreSugg);
                writer.append(';');
                writer.append('\n');
                System.out.println("Vuoi inserire un altro libro? (y / n) ");
                scelta = sc.nextLine();

                while(scelta.equals("y")){
                    System.out.println("Inserire il titolo del nuovo suggerimento; ");
                    titoloSugg = sc.nextLine();
                    System.out.println("Inserire l'autore del libro: "+ titoloSugg);
                    autoreSugg = sc.nextLine();
                    writer.append(titolo); 
                    writer.append(';');
                    writer.append(titoloSugg); 
                    writer.append(';');
                    writer.append(autoreSugg);
                    writer.append(';');
                    writer.append('\n');
                    System.out.println("Vuoi inserire un altro libro? (y / n) ");
                    scelta = sc.nextLine();
                }
                System.out.println("Dati salvati correttamente in " + absol);
            }catch (IOException e) {
                System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
            }
            sc.nextLine();
        }else{
            System.out.println("Il libro non è presente in nessuna libreria.");
        }
        sc.nextLine();
    }

    public static boolean ricercaLib(String titolo, String autore){
        String line;
        boolean stato = false;

        try (BufferedReader br = new BufferedReader(new FileReader(absol1))) {
            

            while ((line = br.readLine()) != null) {
                String[] fields = parseLine(line);
                if (fields.length == 5) {
                    String user = fields[0];
                    String nome = fields[1];
                    String title = fields[2]; 
                    String authors = fields[3];

                    if(user.equals(Libreria_classe.userId) && titolo.equals(title)) {
                        stato = true;
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        return stato;
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
