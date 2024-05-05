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

    // kata awal
    protected String initialWord;
    // kata akhir
    protected String targetWord;

    // fungsi g
    abstract public int evalFuncG(int indexExpanded);

    // fungsi h
    abstract public int evalFuncH(String word);

    public Algorithm(String initialWord, String targetWord) {
        this.initialWord = initialWord;
        this.targetWord = targetWord;
    }

    public static boolean isWordValid(String kata) {
        String filePath = "dictionary.txt"; // rute file txt

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

    public List<String> getOneDiffString(String word) {
        List<String> wordArray = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (word.charAt(i) == targetWord.charAt(i)) {
                    continue;
                }
                String tempWord = new String(word.substring(0, i) + c + word.substring(i + 1));
                if (!expandedList.contains(tempWord) && isWordValid(tempWord)) {
                    wordArray.add(tempWord);
                }
            }
        }
        return wordArray;
    }

    // fungsi mendapatkan yang paling kecil nilai hasil evaluation functionnya
    public int smallestEvalIndex() {
        int minValue = evalValues.get(0);
        int minIndex = 0;
        for (int i = 0; i < evalValues.size(); i++) {
            // kalau nilai lebih kecil otomatis menjadi min
            if (lastStringAtIndex(i).equals(targetWord)) {
                return i;
            }
            // nilai lebih kecil
            if (evalValues.get(i) < minValue) {
                minValue = evalValues.get(i);
                minIndex = i;
            }
        }
        return minIndex;
    }

    public String lastStringAtIndex(int index) {
        return possibleSolutions.get(index).get(possibleSolutions.get(index).size() - 1);
    }

    // yang sama hurufnya jadi patokan
    public void solve() {
        while (true) { // dilakukan sampai dibreak ketika sama dengan kata yang diekspansi
            // kosong -> tidak bisa expand lagi -> tidak ketemu solusi
            boolean isNotFirst = false;
            if (possibleSolutions.isEmpty() && isNotFirst) {
                System.out.println("Tidak ada solusi!");
                break;
            } else {
                if (possibleSolutions.isEmpty()) // belum ada -> inisialisasi
                {
                    List<String> initialArray = new ArrayList<>();
                    initialArray.add(initialWord);
                    possibleSolutions.add(initialArray);
                    evalValues.add(0);
                    isNotFirst = true;
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
                        List<String> nextWordsList = getOneDiffString(wordExpanded);
                        // masukan semua dalam list
                        for (String word : nextWordsList) {
                            List<String> copyListExpanded = new ArrayList<>(listExpanded);
                            copyListExpanded.add(word);
                            possibleSolutions.add(copyListExpanded);
                            // kasusnya sudah dihandle setiap kelas algoritma
                            evalValues.add(evalFuncG(indexExpanded) + evalFuncH(word));
                        }
                    } else // sudah pernah diexpand -> hapus dan lanjut ke berikutnya
                    {
                        possibleSolutions.remove(indexExpanded);
                        evalValues.remove(indexExpanded);
                    }
                    // tambah sebagai kata yang sudah diekspansi
                    expandedList.add(wordExpanded);
                    // hapus element dari setiap array
                    possibleSolutions.remove(indexExpanded);
                    evalValues.remove(indexExpanded);
                }
            }
        }
    }
}