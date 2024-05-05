// jarak dari saat ini ke akhir
// pakai jumlah huruf yang masih berbeda
public class GBFS extends Algorithm {
    public GBFS(String initialWord, String targetWord) {
        super(initialWord, targetWord);
    }

    // tidak ada karena GBFS
    public int evalFuncG(int indexExpanded) {
        return 0;
    }

    // jumlah huruf saat ini yang berbeda dengan target
    // lebih kecil -> semakin baik
    public int evalFuncH(String word) {
        int num = 0;
        for (int i = 0; i < targetWord.length(); i++) {
            if (word.charAt(i) != targetWord.charAt(i)) {
                num++;
            }
        }
        return num;
    }
}
