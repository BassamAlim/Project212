public class LinkedStack<T> {

    private Node<T> top;

    public void push(T e) {
        Node<T> tmp = new Node<>(e);
        tmp.next = top;
        top = tmp;
    }

    public T pop() {
        if (top == null)
            return null;

        T e = top.data;
        top = top.next;
        return e;
    }

}