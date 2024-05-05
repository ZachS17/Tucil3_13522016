public class AStar extends Algorithm {
    private UCS ucs;
    private GBFS gbfs;

    @Override
    public int evaluationFunction(int indexExpanded) {
        // Call the evaluation functions of UCS and GBFS and sum their results
        int ucsResult = ucs.evaluationFunction(indexExpanded);
        int gbfsResult = gbfs.evaluationFunction(indexExpanded);
        return ucsResult + gbfsResult;
    }

    public AStar(UCS ucsTemp, GBFS gbfsTemp) {
        super(ucsTemp.initialWord, ucsTemp.targetWord);
        this.ucs = ucsTemp;
        this.gbfs = gbfsTemp;
    }
}
