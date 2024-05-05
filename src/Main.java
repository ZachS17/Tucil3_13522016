import java.util.*;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan kata awal: ");
        String kataAwal = scanner.next();
        while (true) {
            if (Algorithm.isWordValid(kataAwal)) {
                break;
            } else {
                System.out.println("Kata tidak ada!");
                System.out.print("Masukkan kata awal: ");
                kataAwal = scanner.next();
            }
        }
        System.out.print("Masukkan kata tujuan: ");
        String kataAkhir = scanner.next();
        while (true) {
            if (Algorithm.isWordValid(kataAkhir) && kataAwal.length() == kataAkhir.length()) {
                break;
            } else if (!Algorithm.isWordValid(kataAkhir)) {
                System.out.println("Kata tidak ada!");
                System.out.print("Masukkan kata tujuan: ");
                kataAkhir = scanner.next();
            } else {
                System.out.println("Panjang kata tidak sama!");
                System.out.println("Nggak mungkin dong!");
                System.out.print("Masukkan kata tujuan: ");
                kataAkhir = scanner.next();
            }
        }
        System.out.println("Tipe Algoritma:");
        System.out.println("1. Uniform Cost Search");
        System.out.println("2. Greedy Best First Search");
        System.out.println("3. A*");
        System.out.print("Masukkan tipe algoritma (1-3): ");
        int algorithmType = 0;
        algorithmType = scanner.nextInt();
        while (true) {
            if (algorithmType > 0 && algorithmType < 4) {
                break;
            } else {
                System.out.println("Algoritma tidak valid!");
                System.out.print("Masukkan tipe algoritma: ");
                algorithmType = scanner.nextInt();
            }
        }
        scanner.close();
        if (algorithmType == 1) {
            Algorithm tempAlgorithm = new UCS(kataAwal, kataAkhir);
            Instant startTime = Instant.now();
            tempAlgorithm.solve();
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            System.out.println("Waktu eksekusi: " + duration.toMillis() + " ms");
        } else if (algorithmType == 2) {
            Algorithm tempAlgorithm = new GBFS(kataAwal, kataAkhir);
            Instant startTime = Instant.now();
            tempAlgorithm.solve();
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            System.out.println("Waktu eksekusi: " + duration.toMillis() + " ms");
        } else if (algorithmType == 3) {
            UCS tempUCS = new UCS(kataAwal, kataAkhir);
            GBFS tempGBFS = new GBFS(kataAwal, kataAkhir);
            Algorithm tempAlgorithm = new AStar(tempUCS, tempGBFS);
            Instant startTime = Instant.now();
            tempAlgorithm.solve();
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            System.out.println("Waktu eksekusi: " + duration.toMillis() + " ms");
        }
    }

    public void end() {
        System.out.println("Permainan selesai!");
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
        game.end();
    }
}