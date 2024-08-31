import java.util.Scanner;

public class Login_classe extends Registrazione_classe{
    /**Campi*/
    /**
     * Scanner statico utilizzato per la lettura dell'input da console. 
     */
    static Scanner sc = new Scanner(System.in);

    /**
     * Il nome dell'utente
     */
    protected static String userId;

    /**
     * Restituisce lo userId
     * @return ritorna lo userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId prende il campo userId
     */
    public static void setUserId(String userId) {
        Login_classe.userId = userId;
    }

    /**
     * Metodo che gestisce il processo di login per l'utente. Se l'utente è già registrato, 
     * gli viene richiesto di inserire il proprio userId e la password, che vengono verificati 
     * tramite il metodo `controllo_dato`. Se l'utente non è registrato, viene avviato il 
     * processo di registrazione richiamando il metodo `inserimento` della classe 
     * `Registrazione_classe`, dopo di che viene nuovamente eseguito il processo di login.
     */
    public static void inserimento(){
        String pass;
        String scelta = "";
        boolean controllo;
        
        System.out.print("\033c");
        String header = "**********************************\n"
                      + "*              LOG-IN            *\n"
                      + "**********************************";

        System.out.println(header);
        sc.nextLine();
        System.out.println("Sei già registrato? (y / n) ");
        scelta = sc.nextLine();

        if(scelta.equals("y")){
            do{
                System.out.println("\nInserisci userId: ");
                userId = sc.nextLine();
                controllo = controllo_dato(userId, 3);
            }while(controllo != true);
           
            System.out.println("\nInserisci password: ");
            controllo = false;
            do{
                pass = sc.nextLine();
                controllo = controllo_dato(pass, 4);
            }while(controllo != true);
        }else{
            Registrazione_classe rg = new Registrazione_classe();
            rg.inserimento();
            inserimento();
        }
    }

    /**
     * Metodo che rappresenta il menu principale per l'utente autenticato nel sistema. 
     * Una volta che l'utente ha effettuato il login con successo, viene visualizzato 
     * questo menu che permette di accedere a varie funzionalità, tra cui la ricerca 
     * di libri, la visualizzazione delle valutazioni, l'interazione con le librerie, 
     * l'inserimento di nuove valutazioni e suggerimenti, o il log-out.
     */
    public static void menuLog(){
        int scelta = 0;
        boolean loggedIn=true;

        do {
            System.out.print("\033c");
            String header = "**********************************\n"
                          + "*          BENVENUTO "+ userId +"*\n"
                          + "**********************************";

            System.out.println(header);
            System.out.println("In questo portale potrai scegliere se:");
        
            System.out.println("1) Eseguire una ricerca di un libro specifico");
            System.out.println("2) Visualizzare la valutazioni fornite dagli altri utenti registrati");
            System.out.println("3) Interagisci con librerie");
            System.out.println("4) Inserisci valutazione libro");
            System.out.println("5) Inserisci un suggerimento");
            System.out.println("6) log-out");
            scelta = sc.nextInt();

            switch (scelta) {
                case 1:{
                    RicercaLibro_classe rc = new RicercaLibro_classe();
                    rc.menu();
                    break;
                }
                case 2:{
                    Valutazioni_classe vc1 = new Valutazioni_classe();
                    vc1.visualizza();
                    break;
                }
                case 3:{
                    Libreria_classe lc = new Libreria_classe();
                    lc.menu();
                    break;
                }
                
                case 4:{
                    Valutazioni_classe vc = new Valutazioni_classe();
                    vc.inserimento();
                    break;
                }
                case 5:{
                    Suggerimenti_classe sc = new Suggerimenti_classe();
                    sc.inserisciSuggerimentoLibro();
                    break;
                }
                case 6:{
                    loggedIn= false;
                    System.out.println("Log-out effettuato con successo.");
                    sc.nextLine();
                    break;
                }
                default:{
                    System.out.print("\033c");
                    System.out.println("Scelta non valida, per favore riprova.");
                }
            }

        }while (scelta != 6);
    }
}