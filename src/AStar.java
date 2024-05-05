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

    @Override
    public int evaluationFunction(int indexExpanded) {
        // panggil dari kelas masing-masing
        // semakin kecil -> semakin baik
        int ucsResult = ucs.evaluationFunction(indexExpanded);
        int gbfsResult = gbfs.evaluationFunction(indexExpanded);
        return ucsResult + gbfsResult;
    }
}
