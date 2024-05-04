public class GBFS extends Algorithm {
    public int evaluationFunctionGBFS(int indexExpanded) {
        int num = 0;
        for (int i = 0; i < targetWord.length(); i++) {
            if (lastStringAtIndex(indexExpanded).charAt(i) != targetWord.charAt(indexExpanded)) {
                num++;
            }
        }
        return num;
    }
}
