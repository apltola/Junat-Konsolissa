package fi.academy;

        import java.util.Scanner;

public class Main {
    private static final Scanner lukija = new Scanner(System.in);

    public static void main(String[] args) {
        // write your code here
        Kayttoliittyma kl = new Kayttoliittyma(lukija);
        kl.kayta();

    }
}
