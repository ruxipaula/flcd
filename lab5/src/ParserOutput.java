import java.util.List;
import java.util.Stack;

public class ParserOutput {
    TreeNode root;

    public TreeNode addSibling(TreeNode node, String value, Integer level, Integer index) {
        if (node == null) {
            return null;
        }
        while (node.getRightSibling() != null) {
            node = node.getRightSibling();
        }

        TreeNode sibling = new TreeNode(value);
        sibling.setParent(node.getParent());
        sibling.setLevel(level);
        sibling.setIndex(index);
        node.setRightSibling(sibling);

        return node.getRightSibling();
    }

    public TreeNode addChild(TreeNode node, String value, Integer level, Integer index) {
        if (node == null) {
            return null;
        }

        if (node.getLeftChild() != null) {
            return addSibling(node.getLeftChild(), value, level, index);
        } else {
            TreeNode child = new TreeNode(value);
            child.setParent(node);
            child.setLevel(level);
            child.setIndex(index);
            node.setLeftChild(child);
            return node.getLeftChild();
        }
    }

    public void addParsedSequence(Stack<Integer> parsedSequence, Grammar grammar) {
        TreeNode lastParent = null;
        Integer level = 0;
        Integer index = 0;

        while (!parsedSequence.empty()) {
            int productionIndex = parsedSequence.pop();
            Pair<String, List<String>> production = grammar.getOrderedProductions().get(productionIndex - 1);

            if (productionIndex == 1) {
                root = new TreeNode(production.getKey());
                root.setLevel(level);
                root.setIndex(index);
                level++;
                index++;
                lastParent = root;
            }

            for (String symbol : production.getValue()) {
                TreeNode child = addChild(lastParent, symbol, level, index);
                if (!parsedSequence.empty()) {
                    int nextProductionIndex = parsedSequence.peek();
                    Pair<String, List<String>> nextProduction = grammar.getOrderedProductions().get(nextProductionIndex - 1);
                    if (symbol.equals(nextProduction.getKey())) {
                        lastParent = child;
                    }
                }
                index++;
            }
            level++;
        }
    }

    public void traverseTree(TreeNode root) {
        if (root == null) {
            return;
        }

        while (root != null) {
            System.out.println(root);
            if (root.getLeftChild() != null) {
                traverseTree(root.getLeftChild());
            }
            root = root.getRightSibling();
        }
    }

    public TreeNode getRoot() {
        return root;
    }
}
