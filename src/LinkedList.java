public class LinkedList<T> implements List<T> {

    private Node<T> head, current;

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public boolean full() {
        return false;
    }

    @Override
    public void findFirst() {
        current = head;
    }

    @Override
    public void findNext() {
        current = current.next;
    }

    @Override
    public boolean last() {
        if (current != null)
            return current.next == null;
        return true;
    }

    @Override
    public T retrieve() {
        if (current != null)
            return current.data;
        return null;
    }

    @Override
    public void update(T e) {
        current.data = e;
    }

    @Override
    public void insert(T e) {
        Node<T> newNode = new Node<>(e);
        if (empty())
            head = current = newNode;
        else {
            Node<T> tmp = current.next;
            current.next = newNode;
            current = current.next;
            current.next = tmp;
        }
    }

    @Override
    public void remove() {
        if (current == head)
            head = head.next;
        else {
            Node<T> tmp = head;
            while (tmp.next != current)
                tmp = tmp.next;
            tmp.next = current.next;
        }

        if (last())
            findFirst();
        else
            findNext();
    }

}
