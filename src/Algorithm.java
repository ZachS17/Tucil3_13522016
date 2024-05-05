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

    public boolean isWordValid(String kata) {
        // try {
        // // Initialize ExtJWNL dictionary
        // Dictionary dictionary = Dictionary.getDefaultResourceInstance();

        // // Look up a word in WordNet
        // String word = "dog";
        // IndexWord indexWord = dictionary.lookupIndexWord(POS.NOUN, word);

        // // If the word is found, print its synsets (sets of synonyms)
        // if (indexWord != null) {
        // System.out.println("Synonyms for \"" + word + "\":");
        // for (Synset synset : indexWord.getSenses()) {
        // System.out.println("- " + synset.getGloss());
        // }
        // } else {
        // System.out.println("Word \"" + word + "\" not found in WordNet.");
        // }
        // } catch (JWNLException e) {
        // e.printStackTrace();
        // }
        String filePath = "../lib/words.txt"; // Path to the text file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the target word
                if (line.contains(kata)) {
                    found = true;
                    break; // Exit loop if the word is found
                }
            }

            // Print result
            if (found) {
                System.out.println("Word '" + kata + "' found in the text file.");
                return true;
            } else {
                System.out.println("Word '" + kata + "' not found in the text file.");
                return false;
            }
        } catch (IOException e) {
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
            // kalau nilainya sama maka secara alfabet kecuali sudah sama persis dengan
            // string target
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
                        if (lastStringAtIndex(i).charAt(j) < minString.charAt(j)) {
                            minValue = evalValues.get(i);
                            minString = lastStringAtIndex(i);
                            break;
                        } else if (lastStringAtIndex(i).charAt(j) > minString.charAt(j)) {
                            break;
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
        // berbeda semua
        return 0;
    }

    public boolean isExpanded(String word) {
        int i = 0;
        while (i < expandedList.size()) {
            if (word == expandedList.get(i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public String lastStringAtIndex(int index) {
        return possibleSolutions.get(index).get(possibleSolutions.get(index).size() - 1);
    }

    // abstract public int evaluationFunction(String kata);

    // yang sama hurufnya jadi patokan
    public void solve() {
        while (true) { // dilakukan sampai dibreak ketika sama dengan kata yang diekspansi
            if (possibleSolutions.size() == 0) // belum ada -> inisialisasi
            {
                ArrayList<String> initialArray = new ArrayList<>();
                initialArray.add(initialWord);
                possibleSolutions.add(initialArray);
                evalValues.add(0);
                lastIndexChange.add(-1);
            }

            System.out.println("masuk sistem");
            // index smallest index saat ini sesuai aturan
            int indexExpanded = smallestEvalIndex();
            // path dengan kata yang diexpand
            List<String> listExpanded = new ArrayList<>(possibleSolutions.get(indexExpanded));
            // System.out.println(listExpanded);
            // kata diexpand
            String wordExpanded = new String(listExpanded.get(listExpanded.size() - 1));
            // System.out.println(wordExpanded);
            // prosedur ekspansi
            // jika elemen terakhir pada path sudah sesuai -> ketemu
            if (lastStringAtIndex(smallestEvalIndex()).equals(targetWord)) {
                System.out.println("Path:" + possibleSolutions.get(indexExpanded));
                System.out.println("yay dapet");
                break;
            } else // ekspansi string terakhir pada indeks 0
            {
                // katanya belum diekspansi
                if (!isExpanded(lastStringAtIndex(smallestEvalIndex()))) {
                    // iterasi huruf dari yang terakhir diexpand (langsung mulai dari berikutnya)
                    int i = lastIndexChange.get(indexExpanded) + 1;
                    // System.out.println(i);
                    // mencatat pengulangan dan ada kata yang sudah diekspansi atau tidak
                    boolean hasExpanded = false;
                    boolean cycle = false;
                    System.out.println("Indeks diubah: " + i);
                    while (!hasExpanded && i <= wordExpanded.length()) // selama huruf yang diubah belum valid dan belum
                                                                       // diexpand
                    // semua kemungkinan huruf dari situ
                    {
                        // reset kalau melebihi
                        if (i == wordExpanded.length()) {
                            i = 0;
                            cycle = true;
                        }
                        // // belum ada yang diubah (awal)
                        // if (i == -1) {
                        // i = 0;
                        // }
                        // lanjut proses ekspansi

                        // sudah melewati akhir dan sama dengan awal mula (tinggal itu yang perlu
                        // diubah)
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
                                    System.out.println("Copy Word:" + copywordExpanded);
                                    // dicopy juga path yang dengan kata yang diexpand
                                    List<String> copyListExpanded = new ArrayList<>(listExpanded);
                                    System.out.println("Copy List:" + copyListExpanded);
                                    // buat string baru
                                    String modifiedwordExpanded = new String(copywordExpanded.substring(0, i) + c
                                            + copywordExpanded.substring(i + 1));
                                    System.out.println("Modified word:" + modifiedwordExpanded);
                                    if (isWordValid(modifiedwordExpanded)
                                            && modifiedwordExpanded.charAt(i) != wordExpanded.charAt(i)
                                            && !expandedList.contains(modifiedwordExpanded)) // yang sudah pernah
                                    // ekspansi
                                    // tidak
                                    // usah lagi
                                    {
                                        // tambah ke array string sebagai kandidat
                                        copyListExpanded.add(modifiedwordExpanded);
                                        System.out.println("Copy List (loop):" + copyListExpanded);
                                        // tambah ke possible solution
                                        System.out.println("PSolution (before addition):" + possibleSolutions);
                                        possibleSolutions.add(copyListExpanded);
                                        System.out.println("Psolution (after addition):" + possibleSolutions);
                                        // tambah ke array value untuk nilai evaluasinya
                                        evalValues.add(evaluationFunction(indexExpanded));
                                        // tambah ke lastIndex yaitu indeks saat ini
                                        lastIndexChange.add(i);
                                        // ubah terminator ketika berhasil mengubah huruf
                                        hasExpanded = true;
                                        // // reset
                                        // System.out.println("Copy List(before removal:" + copyListExpanded);
                                        // copyListExpanded.remove(copyListExpanded.size() - 1);
                                        // System.out.println("Copy list after removal: " + copyListExpanded);
                                    }
                                }
                            }
                        }
                        // tambah indeks untuk iterasi huruf berikutnya
                        i++;
                    }
                }
                // tambah sebagai kata yang sudah diekspansi
                expandedList.add(wordExpanded);
                // hapus element pertama setiap array
                listExpanded.removeAll(listExpanded);
                possibleSolutions.remove(indexExpanded);
                evalValues.remove(indexExpanded);
                lastIndexChange.remove(indexExpanded);
            }
            System.out.println(possibleSolutions);
            System.out.println(evalValues);
            System.out.println(expandedList);
            System.out.println(lastIndexChange);
            System.out.println(evalValues.get(smallestEvalIndex()));
            System.out.println(lastIndexChange.get(smallestEvalIndex()));
        }
    }

    public Algorithm(String initialWord, String targetWord) {
        this.initialWord = initialWord;
        this.targetWord = targetWord;
    }

    public static void main(String[] args) {
    }
}