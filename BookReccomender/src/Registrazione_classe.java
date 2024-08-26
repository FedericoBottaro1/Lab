import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Registrazione_classe{
    private static File file = new File("Resources/UtentiRegistrati.csv");
    private static String absol = file.getAbsolutePath();  
    
    public static boolean controllo_dato(String dato, int controllo){
        boolean stato = false;

        try (BufferedReader br = new BufferedReader(new FileReader(absol))) {
            String line;
            
            // Salta la prima riga (intestazione)
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = parseLine(line);
                if (fields.length == 6) {
                    // Esempio di accesso ai dati
                    String codFisc = fields[2]; 
                    String mail = fields[3]; 
                    String userid = fields[4];
                    String pass = fields[5];

                    if(controllo == 1 && dato.equals(codFisc)){
                        stato = true;
                        break;
                    }else if(controllo == 2 && dato.equals(mail)){
                        stato = true;
                        break;
                        
                    }else if(controllo == 3 && dato.equals(userid)){
                        stato = true;
                        break;
                    }
                    else if(controllo == 4 && dato.equals(pass)){
                        stato = true;
                        break;
                    } 
                }
            }
        }catch (IOException e) {
                e.printStackTrace();
        }
        return stato;
    }
 
    public static void inserimento(){
        File csvFile = new File(absol);

        int controllo = 0;
        String info = "";
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

        Scanner scanner = new Scanner(System.in);
        List<String[]> dataLines = new ArrayList<>();
        
        // Lettura dei dati dall'utente
        System.out.println("Inserisci i seguenti dati personale per la registrazione.");
        System.out.println("Nome: ");
        String nomeU = scanner.nextLine(); 
        System.out.println("Cognome: ");
        String cognomeU = scanner.nextLine(); 
        String codFiscU, mailU, useridU;

        do{
            System.out.println("Codice fiscale: ");
            codFiscU = scanner.nextLine(); 
            info = codFiscU;
            stato = controllo_dato(info, 1);
        }while(stato == true);
        
        do{
            System.out.println("Email: ");
            mailU=scanner.nextLine(); 
            info = mailU;
            stato = controllo_dato(info, 2);
        }while(stato == true);
       
        do{
            System.out.println("Userid: ");
            useridU = scanner.nextLine(); 
            info = useridU;
            stato = controllo_dato(info, 3);
        }while(stato == true);
       
        System.out.println("Password: ");
        String psswU = scanner.nextLine();

        try (FileWriter writer = new FileWriter(absol, true)) {
            // Scrittura dei dati nel file CSV
            writer.append(nomeU);
            writer.append(';');
            writer.append(cognomeU);
            writer.append(';');
            writer.append(codFiscU);
            writer.append(';');
            writer.append(mailU);
            writer.append(';');
            writer.append(useridU);
            writer.append(';');
            writer.append(psswU);
            writer.append('\n');

            System.out.println("Dati salvati correttamente in " + absol);

        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
        }   
        
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
