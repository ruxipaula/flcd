public class TreeNode {
    private String value;
    private TreeNode parent;
    private TreeNode leftChild;
    private TreeNode rightSibling;
    private Integer level;
    private Integer index;

    public TreeNode(String value, TreeNode parent, TreeNode leftChild, TreeNode rightSibling) {
        this.value = value;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightSibling = rightSibling;
    }

    public TreeNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public TreeNode getParent() {
        return parent;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightSibling() {
        return rightSibling;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getIndex() {
        return index;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightSibling(TreeNode rightSibling) {
        this.rightSibling = rightSibling;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value='" + value + '\'' +
                ", parent=" + (parent != null ? parent.getIndex() : -1) +
                ", leftChild=" + (leftChild != null ? leftChild.getIndex() : -1) +
                ", rightSibling=" + (rightSibling != null ? rightSibling.getIndex() : -1) +
                ", level=" + level +
                ", index=" + index +
                '}';
    }
}
