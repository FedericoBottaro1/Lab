import java.util.Scanner;

public class Login_classe extends Registrazione_classe{
    static Scanner sc = new Scanner(System.in);

    private static String userId;

    public static void inserimento(){
        String pass;
        String scelta = "";
        boolean controllo;
        
        System.out.print("\033c");
        String header = "**********************************\n"
                      + "*              LOG-IN            *\n"
                      + "**********************************";

        System.out.println(header);
    
        System.out.println("Sei già registrato? (y / n) ");
        scelta = sc.nextLine();

        if(scelta.equals("y")){
            do{
                System.out.println("\nInserisci userId: ");
                userId = sc.nextLine();
                controllo = controllo_dato(userId, 3);
                System.out.println(controllo);
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

    public static void menuLog(){
        int scelta = 0;

        do {
            System.out.print("\033c");
            String header = "**********************************\n"
                          + "*          BENVENUTO "+ userId +"*\n"
                          + "**********************************";

            System.out.println(header);
            System.out.println("In questo portale potrai scegliere se:");
        
            System.out.println("1) Eseguire una ricerca di un libro specifico");
            System.out.println("2) Visualizzare la valutazioni fornite dagli altri utenti registrati");
            System.out.println("3) Crea una libreria personale");
            System.out.println("4) Inserisci valutazione libro");
            System.out.println("5) Inserisci un suggerimento");
            System.out.println("7) log-out");
            scelta = sc.nextInt();

            switch (scelta) {
                case 1:{
                    RicercaLibro_classe rc = new RicercaLibro_classe();
                    rc.menu();
                    break;
                }
                case 2:{
                    Valutazioni_classe vc = new Valutazioni_classe();
                    vc.visualizza();
                }
                case 4:{
                    Valutazioni_classe vc = new Valutazioni_classe();
                    vc.inserimento();
                }
                //?
                case 7:{
                    break;
                }
                default:{
                    System.out.print("\033c");
                    System.out.println("Scelta non valida, per favore riprova.");
                }
            }

        }while (scelta != 7);
    }
}