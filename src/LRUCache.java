import java.util.HashMap;
import java.util.Map;


public class LRUCache<K, V> {
  private Map<K, Node<K,V>> map = new HashMap<K, LRUCache.Node<K,V>>();
  private Node<K,V> head, tail;
  
  private static final int CAPACITY = 5;
  private int size;
  
  public void put(K key, V value) {
    Node<K,V> val = map.get(key);
    if(val != null) {
      removeNode(val);
      val.value = value;
      addNodeToTail(val);
    } else {
      if(size >= CAPACITY) {
        map.remove(head.key);
        removeNode(head);
      }
      val = new Node<K, V>(key, value);
      addNodeToTail(val);
      ++size;
    }
    map.put(key, val);
  }
  
  public V get(K key) {
    Node<K, V> val = map.get(key);
    if(val != null) {
      removeNode(val);
      addNodeToTail(val);
    } else {
      return null;
    }
    return val.value;
  }
  
  private void addNodeToTail(Node<K,V> node) {
    if(head == null) {
      head = tail = node;
    } else {
      tail.next = node;
      node.prev = tail;
      tail = tail.next;
    }
  }
  
  private void removeNode(Node<K,V> node) {
    Node<K,V> prev = node.prev;
    Node<K,V> next = node.next;
    
    if(prev != null) {
      prev.next = next;
    } else {
      head = next;
    }
    if(next != null) {
      next.prev = prev;
    } else {
      tail = prev;
    }
  }
  
  private static class Node<K,V> {
    public K key;
    public V value;
    public Node<K,V> prev, next;
    
    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }
  
  public static void main(String[] args) {
    int size = 10;
    LRUCache<String, String> c = new LRUCache<String, String>();
    for(int i = 0 ; i < size; i++) {
      c.put("key-"+i, "value-"+i);
    }
    for(int i = 0 ; i < size; i++) {
      System.out.println(c.get("key-"+i));
    }
  }
}
