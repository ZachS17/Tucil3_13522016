// jarak dari awal ke saat ini
// pakai jumlah perubahan kata
public class UCS extends Algorithm {
    public UCS(String initialWord, String targetWord) {
        super(initialWord, targetWord);
    }

    // banyaknya perubahan (perubahan seminimal mungkin)
    // lebih kecil -> semakin baik
    public int evalFuncG(int indexExpanded) {
        return evalValues.get(indexExpanded) + 1;
    }

    // tidak ada karena UCS
    public int evalFuncH(String word) {
        return 0;
    }
}
