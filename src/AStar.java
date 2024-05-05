// gabungan dari UCS dan GBFS
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
    public int evalFuncG(int indexExpanded) {
        return ucs.evalFuncG(indexExpanded);
    }

    // fungsi H dari GBFS
    public int evalFuncH(String word) {
        return gbfs.evalFuncH(word);
    }
}
