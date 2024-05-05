package Solver;
// jarak dari awal ke saat ini

import Utilities.Tuple;

public class UCS extends Algorithm {
    public UCS(String initialWord, String targetWord) {
        super(initialWord, targetWord);
    }

    // banyaknya perubahan (perubahan seminimal mungkin)
    // lebih kecil -> semakin baik
    public int evalFuncG(int prevCost) {
        return prevCost + 1;
    }

    // tidak ada karena UCS
    public int evalFuncH(Tuple wordTuple) {
        return 0;
    }
}
