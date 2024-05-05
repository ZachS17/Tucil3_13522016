public class AStar extends Algorithm {
    private UCS ucs;
    private GBFS gbfs;

    public AStar() {
        this.ucs = new UCS();
        this.gbfs = new GBFS();
    }

    @Override
    public int evaluationFunction(int indexExpanded) {
        // Call the evaluation functions of UCS and GBFS and sum their results
        int ucsResult = ucs.evaluationFunction(indexExpanded);
        int gbfsResult = gbfs.evaluationFunction(indexExpanded);
        return ucsResult + gbfsResult;
    }
}
