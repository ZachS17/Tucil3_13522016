
/**
 * main
 */
import java.util.*;

public class Main {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan kata awal: ");
        String kataAwal = scanner.next();
        System.out.print("Masukkan kata tujuan: ");
        String kataAkhir = scanner.next();
        scanner.close();
        UCS tempAlgorithm = new UCS(kataAwal, kataAkhir);
        tempAlgorithm.solve();
    }

    public void end() {
        System.out.println("Permainan selesai!");
        System.out.println("Kata telah ditemukan!");
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
        game.end();
    }
}