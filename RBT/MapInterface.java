package Learning4AiSD.DSA.RBT;

public interface MapInterface<K extends Comparable<K>, V> {

    public void setValue(K key, V value);

    public V getValue(K key);

}
