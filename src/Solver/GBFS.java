package Solver;
// jarak dari saat ini ke akhir

import Utilities.Tuple;

public class GBFS extends Algorithm {
    public GBFS(String initialWord, String targetWord) {
        super(initialWord, targetWord);
    }

    // tidak ada karena GBFS
    public int evalFuncG(int prevCost) {
        return 0;
    }

    // jumlah huruf saat ini yang berbeda dengan target
    // lebih kecil -> semakin baik
    public int evalFuncH(Tuple wordTuple) {
        int num = 0;
        for (int i = 0; i < targetWord.length(); i++) {
            if (wordTuple.getWord().charAt(i) != targetWord.charAt(i)) {
                num++;
            }
        }
        return num;
    }
}
