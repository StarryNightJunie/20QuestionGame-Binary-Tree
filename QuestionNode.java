public class QuestionNode {
    public String data;
    public QuestionNode yesNode;
    public QuestionNode noNode;

    // leaf
    public QuestionNode(String data) {
        this(data, null, null);
    }

    // branch
    public QuestionNode(String data, QuestionNode yesNode, QuestionNode noNode) {
        this.data = data;
        this.yesNode = yesNode;
        this.noNode = noNode;
    }
}