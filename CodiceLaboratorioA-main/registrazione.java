import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class registrazione{
    public static void main(String[] args) {
        String csvFileName = "UtentiRegistrati.csv";
        File csvFile = new File(csvFileName);
        try {
            if (csvFile.createNewFile()) {
                System.out.println("File creato: " + csvFileName);
            } else {
                System.out.println("Il file " + csvFileName + " esiste già e verrà sovrascritto.");
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore durante la creazione del file.");
            e.printStackTrace();
        }
        System.out.print("\033c");

        Scanner scanner = new Scanner(System.in);
        List<String[]> dataLines = new ArrayList<>();
        
        // Lettura dei dati dall'utente
        System.out.println("Inserisci i seguenti datipersonale per la registrazione.Scrivi 'fine' per concludere");

        
        System.out.println("Nome: ");
        String nome=scanner.nextLine(); 
        System.out.println("Cognome: ");
        String cognome=scanner.nextLine(); 
        System.out.println("Codice fiscale: ");
        String codFisc=scanner.nextLine(); 
        System.out.println("Email: ");
        String mail=scanner.nextLine(); 
        System.out.println("Userid: ");
        String userid=scanner.nextLine(); 
        System.out.println("Password: ");
        String pssw=scanner.nextLine(); 

        try (FileWriter writer = new FileWriter(csvFileName, true)) {
            // Scrittura dei dati nel file CSV
            writer.append(nome);
            writer.append(';');
            writer.append(cognome);
            writer.append(';');
            writer.append(codFisc);
            writer.append(';');
            writer.append(mail);
            writer.append(';');
            writer.append(userid);
            writer.append(';');
            writer.append(pssw);
            writer.append('\n');

            System.out.println("Dati salvati correttamente in " + csvFileName);

        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
        }   
    }
}
