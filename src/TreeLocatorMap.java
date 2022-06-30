public class TreeLocatorMap<K extends Comparable<K>> implements LocatorMap<K> {

    private final TreeLocator<K> locator = new TreeLocator<>();
    private final BST<K, Location> map = new BST<>();

    @Override
    public Map<K, Location> getMap() {
        return map;
    }

    @Override
    public Locator<K> getLocator() {
        return locator;
    }

    @Override
    public Pair<Boolean, Integer> add(K k, Location loc) {
        Pair<Boolean, Integer> result = map.insert(k, loc);
        if (result.first)
            locator.add(k, loc);

        return result;
    }

    @Override
    public Pair<Boolean, Integer> move(K k, Location loc) {
        Pair<Boolean, Integer> result = map.find(k);

        if (!result.first)
            return result;

        locator.remove(k, map.retrieve());
        locator.add(k, loc);
        map.update(loc);

        return result;
    }

    @Override
    public Pair<Location, Integer> getLoc(K k) {
        Pair<Boolean, Integer> pair = map.find(k);

        if (pair.first)
            return new Pair<>(map.retrieve(), pair.second);

        return new Pair<>(null, pair.second);
    }

    @Override
    public Pair<Boolean, Integer> remove(K k) {
        Pair<Boolean, Integer> result = map.find(k);

        if (result.first) {
            Location loc = map.retrieve();
            map.remove(k);
            locator.remove(k, loc);
        }

        return result;
    }

    @Override
    public List<K> getAll() {
        return map.getAll();
    }

    @Override
    public Pair<List<K>, Integer> getInRange(Location lowerLeft, Location upperRight) {
        Pair<List<Pair<Location, List<K>>>, Integer> result = locator.inRange(lowerLeft, upperRight);
        List<K> keys = new LinkedList<>();
        Pair<List<K>, Integer> pair = new Pair<>(keys, result.second);

        List<Pair<Location, List<K>>> outer = result.first;
        if (outer.empty())
            return pair;

        outer.findFirst();
        while (!outer.last()) {
            List<K> inner = outer.retrieve().second;

            inner.findFirst();
            while (!inner.last()) {
                keys.insert(inner.retrieve());
                inner.findNext();
            }
            keys.insert(inner.retrieve());

            outer.findNext();
        }
        List<K> inner = outer.retrieve().second;
        inner.findFirst();
        while (!inner.last()) {
            keys.insert(inner.retrieve());
            inner.findNext();
        }
        keys.insert(inner.retrieve());

        return pair;
    }

}
