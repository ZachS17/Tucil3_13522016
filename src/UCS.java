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
        this.possibleSolutions = new ArrayList<ArrayList<String>>();
        this.expandedList = new ArrayList<>();
        this.lastIndexChange = new ArrayList<>();
    }

    // yang sama hurufnya jadi patokan
    public void solve() {
        while (true) { // dilakukan sampai dibreak ketika sama dengan kata yang diekspansi
            // prosedur ekspansi
            // jika elemen terakhir pada path sudah sesuai -> ketemu
            if (lastStringAtIndex(smallestEvalIndex()).equals(targetWord)) {
                System.out.println("yay dapet");
                break;
            } else // ekspansi string terakhir pada indeks 0
            {
                // index 0 karena yang diekspansi pasti pertama

                // path dengan kata yang diexpand
                ArrayList<String> expandedList = possibleSolutions.get(0);
                // kata diexpand
                String expandedWord = expandedList.get(0);
                // iterasi huruf dari yang terakhir diexpand
                int i = lastIndexChange.get(0);
                // kata sudah diekspansi atau tidak
                boolean hasExpanded = false;
                while (!hasExpanded && i < expandedWord.length()) // selama huruf yang diubah belum valid dan belum
                                                                  // diexpand
                // semua kemungkinan huruf dari situ
                {
                    // reset kalau melebihi
                    if (i == expandedWord.length()) {
                        i = 0;
                    }
                    // lanjut proses ekspansi
                    else {
                        if (expandedWord.charAt(i) != targetWord.charAt(i) && i != lastIndexChange.get(0)) // belum sama
                                                                                                           // huruf di
                                                                                                           // lokasinya,
                                                                                                           // sama
                                                                                                           // -> skip
                        {
                            for (char c = 'a'; c <= 'z'; c++) { // dari huruf pertama sampai akhir (semua kemungkinan)
                                // dicopy kata yang mau diexpand karena mau diubah huruf-hurufnya
                                String copyExpandedWord = expandedWord;
                                // dicopy juga path yang dengan kata yang diexpand
                                ArrayList<String> copyExpandedList = expandedList;
                                // buat string baru
                                String modifiedExpandedWord = copyExpandedWord.substring(0, i) + c
                                        + copyExpandedWord.substring(i + 1);
                                if (isWordValid(modifiedExpandedWord)) // kata valid
                                {
                                    if (!expandedList.contains(modifiedExpandedWord)) // yang sudah pernah ekspansi
                                                                                      // tidak
                                                                                      // usah lagi
                                    {
                                        // tambah ke array string sebagai kandidat
                                        copyExpandedList.add(modifiedExpandedWord);
                                        // tambah ke possible solution
                                        possibleSolutions.add(copyExpandedList);
                                        // tambah ke array value untuk nilai evaluasinya
                                        evalValues.add(evaluationFunction(copyExpandedWord));
                                        // tambah ke lastIndex yaitu indeks saat ini
                                        lastIndexChange.add(lastIndexChange.get(0) + 1);
                                        // ubah terminator ketika berhasil mengubah huruf
                                        hasExpanded = true;
                                    }
                                }
                            }
                        }
                        // tambah indeks untuk iterasi huruf berikutnya
                        i++;
                    }
                }
                // tambah sebagai kata yang sudah diekspansi
                expandedList.add(expandedWord);
                // hapus element pertama setiap array
                possibleSolutions.remove(smallestEvalIndex());
                evalValues.remove(smallestEvalIndex());
                lastIndexChange.remove(smallestEvalIndex());
            }
            System.out.println(possibleSolutions.get(smallestEvalIndex()));
            System.out.println(evalValues.get(smallestEvalIndex()));
            System.out.println(lastIndexChange.get(smallestEvalIndex()));
        }
    }

    // menerima string kata awal yang mau diubah karakternya
    // langsung update nilai pada array evalValues
    // sudah pasti ada
    // banyaknya perubahan (perubahan seminimal mungkin)
    public int evaluationFunction(String currentWord) {
        int evalValue = 0;
        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) != initialWord.charAt(i)) {
                // tambah 1 karena mau diubah
                evalValue++;
            }
        }
        return evalValue;
    }
}
