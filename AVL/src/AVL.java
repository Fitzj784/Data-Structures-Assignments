/**
 * AVL tree construction and testing program...
 * 
 * @author Daryl
 *
 * @param <T>
 */

public class AVL<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    @Override
    public void insert(T data) {
        root = put(root, data);
    }

    private Node<T> put(Node<T> node, T data) {
        if (node == null) {
            return new Node<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftchild(put(node.getLeftchild(), data));
        } else {
            node.setRightchild(put(node.getRightchild(), data));
        }
        node.setHeight(Math.max(Height(node.getLeftchild()), Height(node.getRightchild())) + 1);
        node = violationCheck(node, data);

        return node;
    }

    /**
     * This rotation preserves the O(log N) time by checking the height of the
     * subtrees and rotating if their height difference is > 1
     * 
     * @param node
     *            - root node
     * @param data
     *            - general user input
     * @return
     */
    
    private Node<T> violationCheck(Node<T> node, T data) {
        int balance = getBalance(node);
        // double left heavy
        if (balance > 1 && data.compareTo(node.getLeftchild().getData()) < 0) {
            return rightRotate(node);
        }

        // double right heavy
        if (balance < -1 && data.compareTo(node.getRightchild().getData()) > 0) {
            return leftRotate(node);
        }
        // left-right rotation
        if (balance > 1 && data.compareTo(node.getLeftchild().getData()) > 0) {
            node.setLeftchild(leftRotate(node.getLeftchild()));
            return rightRotate(node);
        }
        // right-left rotation
        if (balance < -1 && data.compareTo(node.getRightchild().getData()) < 0) {
            node.setRightchild(rightRotate(node.getRightchild()));
            return leftRotate(node);
        }
        // if none of the criteria is met(usually the root node is simply
        // returned)...
        return node;
    }

    private Node<T> remove(Node<T> node, T data) {

        if (node == null)
            return node;

        if (data.compareTo(node.getData()) < 0) {
            node.setLeftchild(remove(node.getLeftchild(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightchild(remove(node.getRightchild(), data));
        } else {

            // we have found the node we want to remove !!!
            if (node.getLeftchild() == null && node.getRightchild() == null) {
                System.out.println("Removing a leaf node...");
                return null;
            }

            if (node.getLeftchild() == null) {
                System.out.println("Removing the right child...");
                Node<T> tempNode = node.getRightchild();
                node = null;
                return tempNode;
            } else if (node.getRightchild() == null) {
                System.out.println("Removing the left child...");
                Node<T> tempNode = node.getLeftchild();
                node = null;
                return tempNode;
            }
            // this is the node with two children case !!!
            System.out.println("Removing item with two children...");
            Node<T> tempNode = getPredecessor(node.getLeftchild());
            node.setData(tempNode.getData());
            node.setLeftchild(remove(node.getLeftchild(), tempNode.getData()));
        }
        return node;
    }

    private Node<T> getPredecessor(Node<T> node) {

        if (node.getRightchild() != null)
            return getPredecessor(node.getRightchild());

        System.out.println("Predecessor is: " + node);
        return node;
    }
    
    @Override
    public void delete(T data) {
        if (root == null)
            return;
        remove(root, data);
    }

    @Override
    public T getMaxValue() {
        if (root == null)
            return null;
        else
            return getMax(root);
    }

    private T getMax(Node<T> node) {
        if (node.getRightchild() != null)
            return getMax(node.getRightchild());
        return node.getData();
    }

    @Override
    public T getMinValue() {
        if (root == null)
            return null;
        return getMin(root);
    }

    private T getMin(Node<T> node) {
        if (node.getLeftchild() != null)
            return getMin(node.getLeftchild());
        return node.getData();
    }

    /**
     * Method used to traverse the AVL tree
     */
    @Override
    public void traversal() {
        if (root == null)
            return;
        else {
            System.out.println();
            System.out.println("In-Order:");
            inOrder(root);
            System.out.print("null");
            System.out.println();
            System.out.println("Pre-Order:");
            preOrder(root);
            System.out.print("null");
            System.out.println();
            System.out.println("Post-Order:");
            postOrder(root);
            System.out.print("null");
            System.out.println();
        }

    }

    // takes in a node and returns the height of the node.
    private int Height(Node<T> node) {
        if (node == null) {
            return -1;
        } else
            return node.getHeight();
    }

    // used to see if the difference of the sub trees is greater > 1 or < -1 for
    // rotation purposes.
    private int getBalance(Node<T> node) {
        if (node == null) {
            return 0;
        } else
            return Height(node.leftchild) - Height(node.getRightchild());
    }

    /**
     * This method does a right rotation of the root node or any node of choice.
     * 
     * @param node
     * @return
     */
    
    private Node<T> rightRotate(Node<T> node) {
        System.out.println("Rotating right..." + node);
        Node<T> templeft = node.getLeftchild(); // A new temporary node that
        // references the left child of
        // the node.
        Node<T> t = templeft.getRightchild(); // takes reference of the right
        // child to the new temporary left
        // node.

        templeft.setRightchild(node); // the root node in this case is made to
        // be the right child of the temp node(new
        // root).
        node.setLeftchild(t);

        node.setHeight(Math.max(Height(node.getLeftchild()), Height(node.getRightchild())) + 1);
        templeft.setHeight(Math.max(Height(templeft.getLeftchild()), Height(templeft.getRightchild())) + 1);

        return templeft;
    }

    /**
     * this method does a left rotation of the root node or any node of choice.
     * 
     * @param node
     * @return
     */
    
    private Node<T> leftRotate(Node<T> node) {
        System.out.println("Rotating left..." + node);
        Node<T> tempright = node.getRightchild();
        Node<T> t = tempright.getLeftchild();

        tempright.setLeftchild(node);
        node.setRightchild(t);

        node.setHeight(Math.max(Height(node.getLeftchild()), Height(node.getRightchild())) + 1);
        tempright.setHeight(Math.max(Height(tempright.getLeftchild()), Height(tempright.getRightchild())) + 1);

        return tempright;
    }

    /*********************************************************************************************************************
     * TRAVERSALS FOR INORDER,PREORDER AND POSTORDER BELOW USING RECURSION.... 
     * 
     *********************************************************************************************************************/
    private void inOrder(Node<T> node) {
        // (LEFT SUB TREE-->ROOT--->RIGHT SUB TREE)
        if (node.getLeftchild() != null)
            inOrder(node.getLeftchild());

        System.out.print(node.getData() + "-->");

        if (node.getRightchild() != null)
            inOrder(node.getRightchild());

    }

    private void preOrder(Node<T> node) {
        // (ROOT-->LEFT SUB TREE-->RIGHT SUB TREE)
        if (node != null) {
            System.out.print(node.getData() + "-->");
            preOrder(node.getLeftchild());
            preOrder(node.getRightchild());
        }
    }

    private void postOrder(Node<T> node) {
        // (LEFT SUB TREE-->RIGHT SUB TREE-->ROOT)
        if (node != null) {
            postOrder(node.getLeftchild());
            postOrder(node.getRightchild());
            System.out.print(node.getData() + "-->");
        }

    }
}
