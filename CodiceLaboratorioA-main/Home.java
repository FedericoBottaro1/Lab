import java.util.Scanner;

public class Home {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scelta;
            
        do {
            scelta=0;
            System.out.print("\033c");
            String header = "**********************************\n"
                        + "*            BENVENUTO           *\n"
                        + "**********************************";

            System.out.println(header);

            System.out.println("In questo portale potrai scegliere se:");
        
            System.out.println("1) Eseguire una ricerca di un libro specifico");
            System.out.println("2) Visualizzare la valutazioni fornite dagli altri utenti registrati");
            System.out.println("3) Registrati alla piattaforma");
            System.out.println("4) Esegui il Log-in per poter accedere alle librerie personali");
            System.out.println("5) Esci");
            
            System.out.print("Selezionare l'azione: ");
            scelta = scanner.nextInt();
            scanner.nextLine(); 
            switch (scelta) {
                case 1:
                    RicercaLibro_classe rc = new RicercaLibro_classe();
                    rc.menu();
                    break;
                case 2:
                    Valutazioni_classe vc = new Valutazioni_classe();
                    vc.visualizza();
                    break;
                case 3:
                    Registrazione_classe regc = new Registrazione_classe();
                    regc.inserimento();
                    break;
                case 4:
                    Login_classe log = new Login_classe();
                    log.inserimento();
                    log.menuLog();
                    break;
                case 5:
                    System.out.println("Uscita dal programma...");
                    break;
                default:
                    System.out.print("\033c");
                    System.out.println("Scelta non valida, per favore riprova.");
            }
        } while (scelta != 5);

        scanner.close();
    }    
}

