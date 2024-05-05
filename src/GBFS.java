// jarak dari saat ini ke akhir
// pakai jumlah huruf yang masih berbeda
public class GBFS extends Algorithm {
    public GBFS(String initialWord, String targetWord) {
        super(initialWord, targetWord);
    }

    // jumlah huruf saat ini yang berbeda dengan target
    // lebih kecil -> semakin baik
    public int evaluationFunction(int indexExpanded) {
        int num = 0;
        for (int i = 0; i < targetWord.length(); i++) {
            if (lastStringAtIndex(indexExpanded).charAt(i) != targetWord.charAt(i)) {
                num++;
            }
        }
        return num;
    }
}
