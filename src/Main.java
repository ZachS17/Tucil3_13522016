
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
        if (kataAwal.length() != kataAkhir.length()) {
            System.out.println("Panjang kata tidak sama!");
            System.out.println("Nggak mungkin dong!");
            scanner.close();
            return;
        }
        System.out.print("Masukkan tipe algoritma: ");
        String algorithmType = scanner.next();
        scanner.close();
        if (algorithmType.equals("UCS")) {
            Algorithm tempAlgorithm = new UCS(kataAwal, kataAkhir);
            Instant startTime = Instant.now();
            tempAlgorithm.solve();
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            System.out.println("Waktu eksekusi: " + duration.toMillis() + " ms");
        } else if (algorithmType.equals("GBFS")) {
            Algorithm tempAlgorithm = new GBFS(kataAwal, kataAkhir);
            Instant startTime = Instant.now();
            tempAlgorithm.solve();
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            System.out.println("Waktu eksekusi: " + duration.toMillis() + " ms");
        } else if (algorithmType.equals("AStar")) {
            UCS tempUCS = new UCS(kataAwal, kataAkhir);
            GBFS tempGBFS = new GBFS(kataAwal, kataAkhir);
            Algorithm tempAlgorithm = new AStar(tempUCS, tempGBFS);
            Instant startTime = Instant.now();
            tempAlgorithm.solve();
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            System.out.println("Waktu eksekusi: " + duration.toMillis() + " ms");
        } else {
            System.out.println("Algoritma tidak valid!");
        }
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