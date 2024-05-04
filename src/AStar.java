public class AStar extends Algorithm {
    public int evaluationFunctionAStar(int indexExpanded) {
        return evaluationFunctionGBFS(indexExpanded) + evaluationFunctionUCS(indexExpanded);
    }

}
