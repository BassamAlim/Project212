public class TreeLocator<T> implements Locator<T> {

    private LocatorNode<T> root, current;

    @Override
    public int add(T e, Location loc) {
        Pair<Boolean, Integer> lookup = find(loc);
        if (lookup.first) {
            current.data.insert(e);
            return lookup.second;
        }

        List<T> list = new LinkedList<>();
        list.insert(e);
        LocatorNode<T> newNode = new LocatorNode<>(loc, list);

        if (empty())
            root = current = newNode;
        else {
            if (loc.x < current.key.x && loc.y <= current.key.y)
                current.child1 = newNode;
            else if (loc.x <= current.key.x && loc.y > current.key.y)
                current.child2 = newNode;
            else if (loc.x > current.key.x && loc.y >= current.key.y)
                current.child3 = newNode;
            else if (loc.y < current.key.y)
                current.child4 = newNode;

            current = newNode;
        }
        return lookup.second;
    }

    @Override
    public Pair<List<T>, Integer> get(Location loc) {
        Pair<Boolean, Integer> lookup = find(loc);

        List<T> list = new LinkedList<>();
        if (current.data.empty() || !lookup.first)
            return new Pair<>(list, lookup.second);

        return new Pair<>(current.data, lookup.second);
    }

    @Override
    public Pair<Boolean, Integer> remove(T e, Location loc) {
        Pair<Boolean, Integer> lookup = find(loc);
        int count = lookup.second;

        if (!lookup.first || current.data.empty())
            return new Pair<>(false, count);

        boolean removed = false;
        List<T> list = current.data;
        list.findFirst();
        while (!list.last()) {
            //count++;
            if (list.retrieve().equals(e)) {
                list.remove();
                removed = true;
            }
            else
                list.findNext();
        }
        //count++;
        if (list.retrieve() != null && list.retrieve().equals(e)) {
            list.remove();
            removed = true;
        }

        return new Pair<>(removed, count);
    }

    @Override
    public List<Pair<Location, List<T>>> getAll() {
        List<Pair<Location, List<T>>> list = new LinkedList<>();

        if (empty())
            return list;

        LocatorNode<T> q = root;
        LinkedStack<LocatorNode<T>> stack = new LinkedStack<>();

        while (q != null) {
            if (!q.data.empty()) {
                Pair<List<T>, Integer> retrieved = get(q.key);
                list.insert(new Pair<>(q.key, retrieved.first));
            }

            if (q.child4 != null)
                stack.push(q.child4);
            if (q.child3 != null)
                stack.push(q.child3);
            if (q.child2 != null)
                stack.push(q.child2);

            if (q.child1 != null)
                q = q.child1;
            else
                q = stack.pop();
        }
        return list;
    }

    @Override
    public Pair<List<Pair<Location, List<T>>>, Integer> inRange(Location lowerLeft, Location upperRight) {
        List<Pair<Location, List<T>>> list = new LinkedList<>();
        recCounter = 0;

        if (!empty())
            recInRange(list, root, lowerLeft, upperRight);

        return new Pair<>(list, recCounter);
    }

    private int recCounter;
    private void recInRange(List<Pair<Location, List<T>>> list, LocatorNode<T> node,
                           Location lowerLeft, Location upperRight) {
        if (node != null) {
            recCounter++;
            if (!node.data.empty() && node.key.x >= lowerLeft.x && node.key.y >= lowerLeft.y &&
                    node.key.x <= upperRight.x && node.key.y <= upperRight.y)
                list.insert(new Pair<>(node.key, node.data));

            if (node.key.x > lowerLeft.x && node.key.y >= lowerLeft.y)
                recInRange(list, node.child1, lowerLeft, upperRight);
            if (node.key.x >= lowerLeft.x && node.key.y < upperRight.y)
                recInRange(list, node.child2, lowerLeft, upperRight);
            if (node.key.x < upperRight.x && node.key.y <= upperRight.y)
                recInRange(list, node.child3, lowerLeft, upperRight);
            if (node.key.x <= upperRight.x && node.key.y > lowerLeft.y)
                recInRange(list, node.child4, lowerLeft, upperRight);
        }
    }

    private Pair<Boolean, Integer> find(Location loc) {
        LocatorNode<T> q = root;
        int counter = 0;

        while (q != null) {
            current = q;
            counter++;

            if (loc.x == q.key.x && loc.y == q.key.y)
                return new Pair<>(true, counter);
            else if (loc.x < q.key.x && loc.y <= q.key.y)
                q = q.child1;
            else if (loc.x <= q.key.x && loc.y > q.key.y)
                q = q.child2;
            else if (loc.x > q.key.x && loc.y >= q.key.y)
                q = q.child3;
            else
                q = q.child4;
        }
        return new Pair<>(false, counter);
    }

    private boolean empty() {
        return root == null;
    }

}
