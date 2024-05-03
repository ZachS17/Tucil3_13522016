import java.util.*;

// jarak dari awal ke saat ini
// pakai jumlah perubahan kata
// yang mau diekspansi adalah string terakhir pada elemen pertama possible solutions
// ekspansi straightforward dengan perubahan pada indeks yang ditujukan 
// tidak handle mengubah indeks kemudian tanpa mengubah indeks saat ini
// ubah huruf berurutan (0 jika lewat akhir) dengan ekspansi semua kemungkinan
public class UCS extends Algorithm {
    public UCS(String initialWord, String targetWord) {
        this.initialWord = initialWord;
        this.targetWord = targetWord;
        this.evalValues = new ArrayList<>();
        this.possibleSolutions = new ArrayList<List<String>>();
        this.expandedList = new ArrayList<>();
        this.lastIndexChange = new ArrayList<>();
    }

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
            String wordExpanded = listExpanded.get(listExpanded.size() - 1);
            // System.out.println(wordExpanded);
            // prosedur ekspansi
            // jika elemen terakhir pada path sudah sesuai -> ketemu
            if (lastStringAtIndex(smallestEvalIndex()).equals(targetWord)) {
                System.out.println("Path:" + possibleSolutions.get(indexExpanded));
                System.out.println("yay dapet");
                break;
            } else // ekspansi string terakhir pada indeks 0
            {
                // belum diekspansi
                if (!isExpanded(lastStringAtIndex(smallestEvalIndex()))) {
                    // iterasi huruf dari yang terakhir diexpand
                    int i = lastIndexChange.get(indexExpanded);
                    // System.out.println(i);
                    // kata sudah diekspansi atau tidak
                    boolean hasExpanded = false;
                    boolean cycle = false;
                    while (!hasExpanded && i < wordExpanded.length()) // selama huruf yang diubah belum valid dan belum
                                                                      // diexpand
                    // semua kemungkinan huruf dari situ
                    {
                        // reset kalau melebihi
                        if (i == wordExpanded.length()) {
                            i = 0;
                            cycle = true;
                        }
                        // belum ada yang diubah (awal)
                        if (i == -1) {
                            i = 0;
                        }
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
                                    String copywordExpanded = wordExpanded;
                                    System.out.println("Copy Word:" + copywordExpanded);
                                    // dicopy juga path yang dengan kata yang diexpand
                                    List<String> copyListExpanded = new ArrayList<>(listExpanded);
                                    System.out.println("Copy List:" + copyListExpanded);
                                    // buat string baru
                                    String modifiedwordExpanded = copywordExpanded.substring(0, i) + c
                                            + copywordExpanded.substring(i + 1);
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
                                        evalValues.add(evaluationFunctionUCS(indexExpanded));
                                        // tambah ke lastIndex yaitu indeks saat ini
                                        lastIndexChange.add(lastIndexChange.get(indexExpanded) + 1);
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

    // menerima string kata awal yang mau diubah karakternya
    // langsung update nilai pada array evalValues
    // sudah pasti ada
    // banyaknya perubahan (perubahan seminimal mungkin)
    public int evaluationFunctionUCS(int indexExpanded) {
        return evalValues.get(indexExpanded) + 1;
    }

    // // gbfs
    // public int evaluationFunction(String currentWord) {
    // int evalValue = 0;
    // for (int i = 0; i < currentWord.length(); i++) {
    // if (currentWord.charAt(i) != initialWord.charAt(i)) {
    // // tambah 1 karena mau diubah
    // evalValue++;
    // }
    // }
    // return evalValue;
    // }
}
