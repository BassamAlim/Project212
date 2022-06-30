public class LocatorNode<T> {

    public Location key;
    public List<T> data;
    public LocatorNode<T> child1, child2, child3, child4;

    public LocatorNode(Location k, List<T> val) {
        key = k;
        data = val;
    }

}
