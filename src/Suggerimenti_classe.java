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
    private static String absol1 = file1.getAbsolutePath();

    static Scanner sc = new Scanner(System.in);
    
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
        //dopo che vengono inseriti i dati il programma si chiude e torna al login home
        
        boolean stato = ricercaLib(titolo, autore);
        

        if(stato == true){
            try (FileWriter writer = new FileWriter(absol, true)) {
                do{
                    do{  
                        System.out.print("\033c");
                        
                        System.out.println("Inserire il suggerimento: ");
                        System.out.println("Titolo: ");
                        titoloSugg = sc.nextLine();
                        
                        System.out.println("Autore: ");
                        autoreSugg = sc.nextLine();
    
                        stato = RicercaLibro_classe.ricercaAutoTito(titolo, autore);
                    }while(stato != true);

                    //System.out.println("-----" +limiteConsigli(titolo, autore));
                    if(limiteConsigli(titolo, autore) >= 3){
                        System.out.println("Hai raggiunto il massimo di consigli");
                        break;
                   }
                   //se metto 4 di fila gli scrive tutti
                    
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
                    System.out.println("Vuoi inserire un altro libro? (y / n) ");
                    scelta = sc.nextLine();
                    
                }while(!scelta.equals("n"));

            }catch (IOException e) {
                System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
            }
        }else{
            System.out.println("Il libro non e' presente in nessuna libreria.");
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
                    String title = fields[2]; 
                    String authors = fields[3];
                    
                    if(user.equals(Libreria_classe.userId) && titolo.equals(title) && autore.contains(authors)) {
                        stato = true;
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        return stato;
    }

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
