
/**
 * main
 */
import java.util.*;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan kata awal: ");
        String kataAwal = scanner.next();
        System.out.print("Masukkan kata tujuan: ");
        String kataAkhir = scanner.next();
        scanner.close();
        if (kataAwal.length() != kataAkhir.length()) {
            System.out.println("Panjang kata tidak sama!");
            System.out.println("Nggak mungkin dong!");
            return;
        }
        UCS tempAlgorithm = new UCS(kataAwal, kataAkhir);
        Instant startTime = Instant.now();
        tempAlgorithm.solve();
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        System.out.println("Waktu eksekusi: " + duration.toMillis() + " ms");
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