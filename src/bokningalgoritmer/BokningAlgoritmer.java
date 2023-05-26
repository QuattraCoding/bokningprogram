package bokningalgoritmer;

import java.util.Scanner;
import java.time.LocalDate;

public class BokningAlgoritmer {

    static Scanner Scan = new Scanner(System.in);
    private static boolean on = true;
    private static boolean menu = true;
    private static boolean book = false;
    private static boolean cancel = false;
    private static boolean info = false;

    static boolean[] verarray = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    static String[] personnumber = new String[15];
    static String[] nameArray = new String[15];
    static int[] genderArray = new int[15];

    static String infoinput;
    static String stryear;
    static String strmonth;
    static String strday;

    static int day;
    static int month;
    static int year;

    static int price;

    static LocalDate locald = LocalDate.now();
    static int monthvalue = locald.getMonthValue();
    static int yearvalue = locald.getYear();
    static int dayvalue = locald.getDayOfMonth();

    static int choice;
    static boolean validP;

    static int tempnumber;

    public static void main(String[] args) {

        while (on) {

            if (menu) {
                System.out.println("välkommen till menyn, 1: Boka 2: Info 3: avboka 4: stäng av.");

                int val = Scan.nextInt();
                switch (val) {
                    case 1 ->
                        bswitch();
                    case 2 ->
                        iswitch();
                    case 3 ->
                        avswitch();
                    default ->
                        on = false;
                }

            } else if (book) {

                System.out.println("vill Du boka en plats? 1 för ja, 2 för nej");
                int valb = Scan.nextInt();
                switch (valb) {
                    case 1 ->
                        book();
                    default ->
                        mswitch();
                }
            } else if (info) {
                info();
            } else if (cancel) {
                cancel();
            }
        }

    }

    static void bswitch() {
        menu = false;
        book = true;
        cancel = false;
        info = false;
    }

    static void mswitch() {
        menu = true;
        book = false;
        cancel = false;
        info = false;
    }

    static void iswitch() {
        menu = false;
        book = false;
        cancel = false;
        info = true;
    }

    static void avswitch() {
        menu = false;
        book = false;
        cancel = true;
        info = false;

    }

    static public void book() {

        int tempN = verification();
        validP = true;
        String personn;
        System.out.println("Personummer utan dina personliga siffror: (yyymmdd) ");
        personn = Scan.next();
        personnumber[tempN] = personn;
        while (validP) {
            if (personn.length() != 8) {
                System.out.println("Personummer utan dina personliga siffror: (yyyymmdd) ");
                personn = Scan.next();
            } else {
                validP = false;
            }
        }
        Scan.nextLine();
        System.out.println("Namn (Både för och efternamn) : ");
        nameArray[tempN] = Scan.nextLine();
        System.out.println(" 1. Man, 2. Kvinna, 3. Annat");
        genderArray[tempN] = Scan.nextInt();
        System.out.println(tempN);
        stryear = personn.substring(0, 4);
        strmonth = personn.substring(4, 6);
        strday = personn.substring(6, 8);
        pay();
        mswitch();
    }

    static public void info() {
        validP = true;
        while (validP) {
            System.out.println("Skriv in bokat namn eller personnummer, back för att gå tillbaka ");
            Scan.nextLine();
            infoinput = Scan.nextLine();

            if (infoinput.matches("\\D+")) {
                for (int i = 0; i < nameArray.length; i++) {
                    if (infoinput.equals(nameArray[i])) {

                        System.out.println(personnumber[i]);
                        System.out.println(nameArray[i]);
                        switch (genderArray[i]) {
                            case 1 ->
                                System.out.println("Kön: Man");
                            case 2 ->
                                System.out.println("Kön: Kvinna");
                            case 3 ->
                                System.out.println("Kön: Annat");
                            default ->
                                System.out.println("inget kön angivet");
                                
                        }
                        
                }
                    else {
                        System.out.println("personnumret eller namnet hittades inte, försök igen.");
                    }
                } 

            } else if (infoinput.matches("\\D+") && infoinput.equalsIgnoreCase("back")) {

                validP = false;

            } else if (infoinput.matches("\\d+")) {
                // parse till int ifrån string här. Kollar int i den andra arrayn.
                for (int i = 0; i < nameArray.length; i++) {

                    if (infoinput.equals(personnumber[i])) {

                        System.out.println(personnumber[i]);
                        System.out.println(nameArray[i]);
                        switch (genderArray[i]) {
                            case 1 ->
                                System.out.println("Kön: Man");
                            case 2 ->
                                System.out.println("Kön: Kvinna");
                            case 3 ->
                                System.out.println("Kön: Annat");
                            default ->
                                System.out.println("inget kön angivet");
                        }
                        validP = false;

                    } else {
                        System.out.println("personnumret eller namnet hittades inte, försök igen.");
                    }

                }

            } else if (infoinput.matches("\\d+\\D+")) {
                System.out.println("ogiltig input, skriv bara nummer eller bokstäver");

            }
        }
        mswitch();
    }

    public static int verification() {
        //For-loop som kollar om friplatsArray[nummer] == false

        /* Om Det stämmer så break; ut ur loopen och sparar variabeln från nummret.
    denna variabeln skrivs in i massor av Fält["nummret"] = scanner.nextLine; var för sig.
    Sparar ditt personnummer i en temporär variabel. som sen används i Betala();
    Efter allt är färdigt så skickas du tillbaka till betalningRutan. */
        for (int i = 0; i < verarray.length; i++) {
            if (!verarray[i]) {
                tempnumber = i;
                verarray[i] = true;
                break;
            }

        }
        return tempnumber;
    }

    static void pay() {

        /* Använder de fyra första siffrorna i deras personnummer.
      Subtraherar det nuvarande året med deras. Sedan tar jag de två nummrena efter och subtraherar det nuvarande månaderna med deras. 
      om differensen blir negativ så drar jag -1 från året och då har vi hur många år denna personen är. DEtta är allt vi behöver för att räkna ut kostnaden
        
        barn > 18
        student - 18 år till 23
        vuxen - 24 till 69
        pensionär 69 <
        
        massor med if satser på hur gamla de är
        detta räknar ut en variabel som lägger ett pris på biljetten
        
         */
        year = Integer.parseInt(stryear);
        month = Integer.parseInt(strmonth);
        day = Integer.parseInt(strday);
        if ((monthvalue - month) < 0) {

            year = yearvalue - year;

        } else if (monthvalue == 0) {
            if (dayvalue - day <= 0) {
                year = yearvalue - year;

            } else if (dayvalue - day > 0) {
                year = yearvalue - (year + 1);
            }
        } else if (monthvalue - month > 0) {
            year = yearvalue - (monthvalue + 1);
        }
        if (year < 18) {
            price = 149;
        } else if (18 <= year && year < 69) {
            price = 299;
        } else {
            price = 150;
        }
        System.out.println("du ska nu betala, ditt pris är " + price + " kr. 1, betala 2, betala inte");
        choice = Scan.nextInt();
        switch (choice) {
            case 1 ->
                System.out.println("du har betalat.");

            case 2 ->
                mswitch();
        }
        mswitch();
    }

    static void cancel() {
        System.out.println("Skriv in bokat namn eller personnummer ");
        Scan.nextLine();
        infoinput = Scan.nextLine();

        if (infoinput.matches("\\D")) {
            for (int i = 0; i < nameArray.length; i++) {
                if (infoinput.equals(nameArray[i])) {

                    System.out.println("är du säker på att du vill avboka din plats? 1, ja 2, nej");
                    choice = Scan.nextInt();
                    if (choice == 1) {
                        personnumber[i] = "";
                        nameArray[i] = "";
                        genderArray[i] = 4;
                        verarray[i] = false;
                        System.out.println("Du har nu avbokat din plats");
                        mswitch();
                    } else {
                        mswitch();
                    }
                }
            }

        } else if (infoinput.matches("\\d+")) {
            for (int i = 0; i < nameArray.length; i++) {
                if (infoinput.equals(personnumber[i])) {

                    System.out.println("är du säker på att du vill avboka din plats? 1, ja 2, nej");
                    choice = Scan.nextInt();
                    if (choice == 1) {
                        personnumber[i] = "";
                        nameArray[i] = "";
                        genderArray[i] = 4;
                        verarray[i] = false;
                        System.out.println("Du har nu avbokat din plats");
                        mswitch();
                    } else {
                        mswitch();
                    }

                }

            }

        } else {
            System.out.println("ogiltig input");
            mswitch();
        }

    }

}
