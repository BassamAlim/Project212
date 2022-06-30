public class BST<K extends Comparable<K>, T> implements Map<K, T> {

    private BSTNode<K, T> root, current;

    @Override
    public boolean empty() {
        return root == null;
    }

    @Override
    public boolean full() {
        return false;
    }

    @Override
    public T retrieve() {
        return current.data;
    }

    @Override
    public void update(T e) {
        current.data = e;
    }

    @Override
    public Pair<Boolean, Integer> find(K key) {
        int counter = 0;
        if (empty())
            return new Pair<>(false, counter);

        BSTNode<K, T> q = root;
        while (q != null) {
            counter++;
            if (key.compareTo(q.key) == 0) {
                current = q;
                return new Pair<>(true, counter);
            }
            else if (key.compareTo(q.key) < 0)
                q = q.left;
            else
                q = q.right;
        }
        return new Pair<>(false, counter);
    }

    @Override
    public Pair<Boolean, Integer> insert(K key, T data) {
        int counter = 0;
        BSTNode<K, T> newNode = new BSTNode<>(key, data);

        if (empty()) {
            root = current = newNode;
            return new Pair<>(true, 0);
        }

        BSTNode<K, T> original = current;
        Pair<Boolean, Integer> lookup = find(key);
        if (lookup.first) {
            current = original;
            return new Pair<>(false, lookup.second);
        }
        counter += lookup.second;

        BSTNode<K, T> q = root;
        while (q != null) {
            current = q;
            counter++;
            if (key.compareTo(q.key) < 0)
                q = current.left;
            else if (key.compareTo(q.key) > 0)
                q = q.right;
        }

        if (key.compareTo(current.key) < 0)
            current.left = newNode;
        else
            current.right = newNode;

        current = newNode;
        return new Pair<>(true, counter);
    }

    @Override
    public Pair<Boolean, Integer> remove(K key) {
        removeCounter = 0;
        if (empty())
            return new Pair<>(false, removeCounter);

        removed = false;
        BSTNode<K, T> p = removeAux(key, root);
        current = root = p;
        return new Pair<>(removed, removeCounter);
    }

    private int removeCounter;
    private boolean removed;
    private BSTNode<K, T> removeAux(K key, BSTNode<K, T> p) {
        if (p == null)
            return null;

        removeCounter++;

        if (key.compareTo(p.key) < 0)
            p.left = removeAux(key, p.left);
        else if (key.compareTo(p.key) > 0)
            p.right = removeAux(key, p.right);
        else {
            removed = true;

            if (p.left != null && p.right != null) {
                BSTNode<K, T> q = p.right;
                while (q.left != null)
                    q = q.left;

                p.key = q.key;
                p.data = q.data;
                p.right = removeAux(q.key, p.right);
            }
            else {
                if (p.right == null)
                    return p.left;
                else
                    return p.right;
            }
        }
        return p;
    }

    @Override
    public List<K> getAll() {
        if (empty())
            return null;

        counter = 0;
        List<K> list = new LinkedList<>();
        recGetAll(list, root);
        return list;
    }

    int counter;
    private void recGetAll(List<K> list, BSTNode<K, T> node) {
        counter++;

        if (node.left != null)
            recGetAll(list, node.left);

        list.insert(node.key);

        if (node.right != null)
            recGetAll(list, node.right);
    }

}
