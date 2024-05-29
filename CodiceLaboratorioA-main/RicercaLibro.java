import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RicercaLibro{
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        int scelta = 0;

        System.out.print("\033c");
        System.out.println("Scegli secondo che criterio effettuare la ricerca: ");
        
        System.out.println("1) Titolo");
        System.out.println("2) Autore");
        System.out.println("3) Autore e Anno");
        System.out.println("4) Ritorna alla home");
    do {
        System.out.print("Selezionare l'azione: ");
        scelta = scanner.nextInt();
        scanner.nextLine();
        switch (scelta) {
            case 1:
                System.out.println("Inserire il titolo del libro di cercare: ");
                String titolo=scanner.nextLine(); 
                String csvFile = "C://Users//utente//OneDrive//Desktop//UNI//Lab//CodiceLaboratorioA-main//DataBaseLibriVero.csv";
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
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
                                System.out.println("Title: " + title);
                                System.out.println("Authors: " + authors);
                                System.out.println("Description: " + description);
                                System.out.println("Category: " + category);
                                System.out.println("Publisher: " + publisher);
                                System.out.println("Price: " + price);
                                System.out.println("Publish Date: " + publishMonth + " " + publishYear);
                                System.out.println("---------------------------");
                            }
                            
                        } else {
                            System.out.println("Riga con numero di campi errato: " + line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Inserire l'autore da cercare: ");
                String autore=scanner.nextLine(); 
                String csvFile1 = "C://Users//utente//OneDrive//Desktop//UNI//Lab//CodiceLaboratorioA-main//DataBaseLibriVero.csv";
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile1))) {
                    String line;
                    // Salta la prima riga (intestazione)
                    br.readLine();

                    while ((line = br.readLine()) != null) {
                        String[] fields = parseLine(line);

                        if (fields.length == 8) {
                            // Esempio di accesso ai dati
                            String title = fields[0];
                            String authors = fields[1];
                           /*String description = fields[2]; // Può essere vuoto
                            String category = fields[3]; // Può essere vuoto
                            String publisher = fields[4];
                            String price = fields[5];
                            String publishMonth = fields[6];
                            String publishYear = fields[7];*/

                            // Stampa i dettagli del libro
                            String authorsMin=authors.toLowerCase();
                            String autoreMin=autore.toLowerCase();
                            if(authorsMin.contains(autoreMin)){
                                System.out.println("Authors: " + authors);
                                System.out.println("Title: " + title);
                                /*System.out.println("Description: " + description);
                                System.out.println("Category: " + category);
                                System.out.println("Publisher: " + publisher);
                                System.out.println("Price: " + price);
                                System.out.println("Publish Date: " + publishMonth + " " + publishYear);*/
                                System.out.println("---------------------------");
                            }
                            
                        } else {
                            System.out.println("Riga con numero di campi errato: " + line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                System.out.println("Inserire l'autore da cercare: ");
                String autore1=scanner.nextLine();
                System.out.println("Inserire l'anno di uscita del libro: ");
                String anno=scanner.nextLine(); 
                String csvFile2 = "C://Users//utente//OneDrive//Desktop//UNI//Lab//CodiceLaboratorioA-main//DataBaseLibriVero.csv";
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile2))) {
                    String line;
                    // Salta la prima riga (intestazione)
                    br.readLine();

                    while ((line = br.readLine()) != null) {
                        String[] fields = parseLine(line);

                        if (fields.length == 8) {
                            // Esempio di accesso ai dati
                            String title = fields[0];
                            String authors = fields[1];
                            String publishYear = fields[7];
                        /*String description = fields[2]; // Può essere vuoto
                            String category = fields[3]; // Può essere vuoto
                            String publisher = fields[4];
                            String price = fields[5];
                            String publishMonth = fields[6];*/

                            // Stampa i dettagli del libro
                            String authorsMin=authors.toLowerCase();
                            String autoreMin=autore1.toLowerCase();
                            if(authorsMin.contains(autoreMin) && publishYear.equals(anno)){
                                System.out.println("Authors: " + authors);
                                System.out.println("Title: " + title);
                                System.out.println("Publish Date: " + publishYear);
                                /*System.out.println("Description: " + description);
                                System.out.println("Category: " + category);
                                System.out.println("Publisher: " + publisher);
                                System.out.println("Price: " + price);
                                System.out.println("Publish Date: " + publishMonth + " " + publishYear);*/
                                System.out.println("---------------------------");
                            }
                            
                        } else {
                            System.out.println("Riga con numero di campi errato: " + line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
                default:
                    System.out.print("\033c");
                    System.out.println("Scelta non valida, per favore riprova.");
            }
        } while (scelta != 1 && scelta != 2 && scelta != 3);
        scanner.close();
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