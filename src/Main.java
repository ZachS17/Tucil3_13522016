
/**
 * main
 */
import java.util.*;

public class Main {
    private String kataAwal;
    private String kataAkhir;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan kata awal: ");
        this.kataAwal = scanner.next();
        System.out.print("Masukkan kata tujuan: ");
        this.kataAkhir = scanner.next();
        scanner.close();
    }

    public void end() {
        System.out.println("Permainan selesai!");
        System.out.println("Kata telah ditemukan!");
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }
}