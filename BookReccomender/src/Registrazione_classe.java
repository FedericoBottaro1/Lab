import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Registrazione_classe{
    /**
     * File statico che rappresenta il percorso del file CSV contenente il database degli utenti registrati.
     */
    private static File file = new File("data/UtentiRegistrati.csv");

    /**
     * Stringa statica che contiene il percorso assoluto del file CSV rappresentato dal campo `file`.
     */
    private static String absol = file.getAbsolutePath();  
    
    /**
     * Questo medoto rappresenta il controllo che viene effettuato sui campi (codFisc, mail, userid, pass) 
     * per verificare che non siano già presenti nel file csv 
     * @param dato prende il dato dal file csv
     * @param controllo prende il numero da controllo da effettuare
     * @return ritorna il valore di stato, indicando se il dato è già presente nel file csv
     */
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
 
    /**
     * Il metodo permette all'utente di effettuare la registrazione all'applicazione inserendo:
     * nome, cognome, codice fiscale, email, userid, password
     */
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