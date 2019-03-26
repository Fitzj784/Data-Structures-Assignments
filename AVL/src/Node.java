
public class Node<T> {
    T data;
    Node<T> leftchild;
    Node<T> rightchild;
    int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node(T data){
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeftchild() {
        return leftchild;
    }

    public void setLeftchild(Node<T> leftchild) {
        this.leftchild = leftchild;
    }

    public Node<T> getRightchild() {
        return rightchild;
    }

    public void setRightchild(Node<T> rightchild) {
        this.rightchild = rightchild;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
