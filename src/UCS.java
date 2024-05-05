// jarak dari awal ke saat ini
// pakai jumlah perubahan kata
// yang mau diekspansi adalah string terakhir pada elemen pertama possible solutions
// ekspansi straightforward dengan perubahan pada indeks yang ditujukan 
// tidak handle mengubah indeks kemudian tanpa mengubah indeks saat ini
// ubah huruf berurutan (0 jika lewat akhir) dengan ekspansi semua kemungkinan
public class UCS extends Algorithm {
    // menerima string kata awal yang mau diubah karakternya
    // langsung update nilai pada array evalValues
    // sudah pasti ada
    // banyaknya perubahan (perubahan seminimal mungkin)
    public int evaluationFunction(int indexExpanded) {
        return evalValues.get(indexExpanded) + 1;
    }

    public UCS(String initialWord, String targetWord) {
        super(initialWord, targetWord);
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
