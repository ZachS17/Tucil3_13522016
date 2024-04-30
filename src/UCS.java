import java.util.*;

// jarak dari awal ke saat ini
// pakai jumlah perubahan kata
// yang mau diekspansi adalah string terakhir pada elemen pertama possible solutions
public class UCS extends Algorithm {
    // yang sama hurufnya jadi patokan
    public void solve(String kataAwal, String kataAkhir) {
        while (true) { // dilakukan sampai dibreak ketika ditambahkan dan sama dengan kata yang
                       // diekspansi
            // prosedur ekspansi
            // jika elemen terakhir pada path sudah sesuai -> ketemu
            if (lastElementStringList(0).equals(kataAkhir)) {
                break;
            } else // ekspansi string terakhir pada indeks 0
            {
                // kata diexpand
                String expandedWord = lastElementStringList(0);
                // path dengan kata yang diexpand
                ArrayList<String> expandedList = possibleSolutions.get(0);
                // iterasi huruf
                int i = 0;
                // kata valid atau tidak
                boolean isValid = false;
                while (!isValid && i < expandedWord.length()) // selama huruf yang diubah belum valid dan belum diexpand
                                                              // semua kemungkinan huruf dari situ
                {
                    if (expandedWord.charAt(i) != kataAkhir.charAt(i)) // belum sama huruf di lokasinya
                    {
                        // dicopy kata yang mau diexpand karena mau diubah huruf-hurufnya
                        String copyExpandedWord = expandedWord;
                        // dicopy juga path yang dengan kata yang diexpand
                        ArrayList<String> copyExpandedList = expandedList;
                        for (char c = (char) (kataAwal.charAt(i) + 1); c <= 'z'; c++) { // dari huruf saat ini sampai
                                                                                        // akhir saja biar aman
                            // buat string baru
                            String modifiedExpandedWord = copyExpandedWord.substring(0, i) + c
                                    + copyExpandedWord.substring(i + 1);
                            if (isWordValid(modifiedExpandedWord)) // kata valid
                            {
                                if (!expandedList.contains(modifiedExpandedWord)) // yang sudah pernah ekspansi tidak
                                                                                  // usah
                                // lagi
                                {
                                    // tambah ke array string sebagai kandidat
                                    copyExpandedList.add(modifiedExpandedWord);
                                    // tambah ke possible solution
                                    possibleSolutions.add(copyExpandedList);
                                    // tambah ke array value untuk nilai evaluasinya
                                    evalValues.add(evaluationFunction(copyExpandedWord));
                                    // ubah terminator ketika berhasil mengubah huruf
                                    isValid = true;
                                }
                            }
                        }
                    }
                    // tambah indeks untuk iterasi berikutnya
                    i++;
                }
            }
        }
    }

    // menerima string kata awal yang mau diubah karakternya
    // langsung update nilai pada array evalValues
    // sudah pasti ada
    public int evaluationFunction(String kata) {
        for (int i = 0; i < possibleSolutions.size(); i++) {
            if (lastElementStringList(i) == kata) {
                // tambah 1 karena mau diubah
                return evalValues.get(i) + 1;
            }
        }
        return 0;
    }
}
