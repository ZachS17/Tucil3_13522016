// import net.sf.extjwnl.data.IndexWord;
// import net.sf.extjwnl.data.POS;
// import net.sf.extjwnl.data.Synset;
// import net.sf.extjwnl.dictionary.Dictionary;
// import net.sf.extjwnl.JWNLException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

abstract class Algorithm {
    // nilai evaluasi
    protected static List<Integer> evalValues = new ArrayList<>();
    // path yang sudah dijalani (cek string terakhir setiap elemen)
    protected static List<List<String>> possibleSolutions = new ArrayList<List<String>>();
    // list dari kata yang sudah diexpand
    protected static List<String> expandedList = new ArrayList<>();
    // list dari kata yang sudah pernah dikunjungi agar tidak usah lagi
    protected static List<String> visitedList = new ArrayList<>();
    // list dari index yang terakhir diubah untuk keep track karena jika terakhir
    // diubah tidak akan langsung diubah
    protected static List<Integer> lastIndexChange = new ArrayList<>();

    // kata awal
    protected String initialWord;
    // kata akhir
    protected String targetWord;

    // tipe algoritma
    protected String algorithmType;

    abstract int evaluationFunction(int index);

    public Algorithm(String initialWord, String targetWord) {
        this.initialWord = initialWord;
        this.targetWord = targetWord;
    }

    public static boolean isWordValid(String kata) {
        String filePath = "../lib/words.txt"; // rute file txt

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;

            // baca setiap line dari file
            while ((line = reader.readLine()) != null) {
                // pengecekan kata
                if (line.contains(kata)) {
                    found = true;
                    break;
                }
            }

            // hasil
            if (found) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            // misal error baca
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        }
    }

    // fungsi mendapatkan yang paling kecil nilai hasil evaluation functionnya
    public int smallestEvalIndex() {
        int minValue = evalValues.get(0);
        String minString = lastStringAtIndex(0);
        int i;
        for (i = 0; i < evalValues.size(); i++) {
            // kalau nilai lebih kecil otomatis menjadi min
            if (evalValues.get(i) < minValue) {
                minValue = evalValues.get(i);
                minString = lastStringAtIndex(i);
            }
            // kalau nilainya sama maka secara alfabet kecuali
            // sama persis dengan string target
            else if (evalValues.get(i) == minValue) {
                // sama
                if (lastStringAtIndex(i).equals(targetWord)) {
                    return i;
                }
                // perbedaan huruf yang diteliti sama -> dari kecil
                else {
                    int j = 0;
                    while (true) {
                        // ketemu lebih kecil (alfabetis)
                        if (j == minString.length()) {
                            break;
                        }
                        // ketemu huruf pertama yang berbeda lebih kecil dari current
                        if (lastStringAtIndex(i).charAt(j) < minString.charAt(j)) {
                            minValue = evalValues.get(i);
                            minString = lastStringAtIndex(i);
                            break;
                            // ketemu huruf pertama yang berbeda lebih besar dari current
                        } else if (lastStringAtIndex(i).charAt(j) > minString.charAt(j)) {
                            break;
                            // ketemu huruf pertama yang berbeda dan ternyata sama dengan target
                            // urutan pembenaran huruf sebisa mungkin in order
                        } else if (lastStringAtIndex(i).charAt(j) == targetWord.charAt(j)
                                && minString.charAt(j) != targetWord.charAt(j)) {
                            minValue = evalValues.get(i);
                            minString = lastStringAtIndex(i);
                            break;
                        }
                        j++;
                    }
                }
            }
        }
        // seharusnya tidak sampai karena kehandle semua
        return 0;
    }

    public String lastStringAtIndex(int index) {
        return possibleSolutions.get(index).get(possibleSolutions.get(index).size() - 1);
    }

    // yang sama hurufnya jadi patokan
    public void solve() {
        while (true) { // dilakukan sampai dibreak ketika sama dengan kata yang diekspansi
            // kosong -> tidak bisa expand lagi -> tidak ketemu solusi
            boolean isFirst = false;
            if (possibleSolutions.isEmpty() && isFirst) {
                System.out.println("Tidak ada solusi!");
                break;
            } else {
                if (possibleSolutions.isEmpty()) // belum ada -> inisialisasi
                {
                    List<String> initialArray = new ArrayList<>();
                    initialArray.add(initialWord);
                    possibleSolutions.add(initialArray);
                    evalValues.add(0);
                    lastIndexChange.add(-1);
                    isFirst = true;
                }
                // index smallest index saat ini sesuai aturan
                int indexExpanded = smallestEvalIndex();
                // path dengan kata yang diexpand
                List<String> listExpanded = new ArrayList<>(possibleSolutions.get(indexExpanded));
                // kata diexpand
                String wordExpanded = new String(listExpanded.get(listExpanded.size() - 1));
                System.out.println("Kata yang diexpand:" + wordExpanded);
                // prosedur ekspansi
                // jika elemen terakhir pada path sudah sesuai -> ketemu
                if (lastStringAtIndex(smallestEvalIndex()).equals(targetWord)) {
                    System.out.println("Path:" + possibleSolutions.get(indexExpanded));
                    break;
                } else // ekspansi string terakhir pada indeks 0
                {
                    // katanya belum diekspansi
                    if (!expandedList.contains(lastStringAtIndex(indexExpanded))) {
                        // iterasi huruf dari yang terakhir diexpand (langsung mulai dari berikutnya)
                        int i = lastIndexChange.get(indexExpanded) + 1;
                        // mencatat pengulangan dan huruf yang dapat diekspansi
                        boolean hasExpanded = false;
                        boolean cycle = false;
                        while (!hasExpanded && i <= wordExpanded.length()) // selama huruf yang diubah belum valid
                        // semua kemungkinan huruf dari situ
                        {
                            // reset kalau melebihi ketika penelusuran
                            if (i == wordExpanded.length()) {
                                i = 0;
                                cycle = true;
                            }
                            // sudah berputar dan kembali ke terakhir -> skip (tidak ada yang diexpand)
                            if (cycle && i == lastIndexChange.get(indexExpanded)) {
                                hasExpanded = true;
                            } else {
                                // belum sama huruf di posisi
                                if (wordExpanded.charAt(i) != targetWord.charAt(i)
                                        && i != lastIndexChange.get(indexExpanded)) {
                                    for (char c = 'a'; c <= 'z'; c++) { // dari huruf pertama sampai akhir (semua
                                                                        // kemungkinan)
                                        // dicopy kata yang mau diexpand karena mau diubah huruf-hurufnya
                                        String copywordExpanded = new String(wordExpanded);
                                        // dicopy juga path yang dengan kata yang diexpand
                                        List<String> copyListExpanded = new ArrayList<>(listExpanded);
                                        // buat string baru
                                        String modifiedwordExpanded = new String(copywordExpanded.substring(0, i) + c
                                                + copywordExpanded.substring(i + 1));
                                        // kata valid, tidak sama dengan yang diexpand, dan belum pernah diexpand
                                        if (isWordValid(modifiedwordExpanded)
                                                && modifiedwordExpanded.charAt(i) != wordExpanded.charAt(i)
                                                && !expandedList.contains(modifiedwordExpanded)) // sudah expand -> skip
                                        {
                                            // tambah ke array string sebagai kandidat
                                            copyListExpanded.add(modifiedwordExpanded);
                                            // tambah ke possible solution
                                            possibleSolutions.add(copyListExpanded);
                                            // tambah ke array value untuk nilai evaluasinya
                                            evalValues.add(evaluationFunction(indexExpanded));
                                            // tambah ke lastIndex yaitu indeks saat ini
                                            lastIndexChange.add(i);
                                            // ubah terminator ketika berhasil mengubah huruf
                                            hasExpanded = true;
                                        }
                                    }
                                }
                            }
                            // tambah indeks untuk iterasi huruf berikutnya
                            i++;
                        }
                    } else // sudah pernah diexpand -> hapus dan lanjut ke berikutnya
                    {
                        possibleSolutions.remove(indexExpanded);
                        evalValues.remove(indexExpanded);
                        lastIndexChange.remove(indexExpanded);
                    }
                    // tambah sebagai kata yang sudah diekspansi
                    expandedList.add(wordExpanded);
                    // hapus element dari setiap array
                    listExpanded.removeAll(listExpanded);
                    possibleSolutions.remove(indexExpanded);
                    evalValues.remove(indexExpanded);
                    lastIndexChange.remove(indexExpanded);
                }
            }
        }
    }
}