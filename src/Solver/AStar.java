package Solver;
// gabungan dari UCS dan GBFS

import Utilities.Tuple;

public class AStar extends Algorithm {
    private UCS ucs;
    private GBFS gbfs;

    public AStar(UCS ucsTemp, GBFS gbfsTemp) {
        // memastikan inisialisasi masih benar
        super(ucsTemp.initialWord, ucsTemp.targetWord);
        this.ucs = ucsTemp;
        this.gbfs = gbfsTemp;
    }

    // fungsi G dari UCS
    public int evalFuncG(int prevCost) {
        return ucs.evalFuncG(prevCost);
    }

    // fungsi H dari GBFS
    public int evalFuncH(Tuple wordTuple) {
        return gbfs.evalFuncH(wordTuple);
    }
}
