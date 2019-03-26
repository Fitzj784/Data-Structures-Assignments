
public class App {
    public static void main(String[] args) {
        Tree<Integer> avl = new AVL<Integer>();

        avl.insert(10);
        avl.insert(20);
        avl.insert(30);
        avl.insert(40);
        avl.insert(50);
        avl.insert(55);
        avl.delete(40);

        avl.traversal();
        System.out.println();
        System.out.println("The largest value is: " + avl.getMaxValue());
        System.out.println("The smallest value is: " + avl.getMinValue());
    }
}
