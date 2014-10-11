import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConcurrentLRUCache {
  private ConcurrentSkipListMap<Object, Object> cache = new ConcurrentSkipListMap<Object, Object>();
  private ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<Object>();
  private final int capacity = 10;
  private volatile AtomicInteger cnt = new AtomicInteger();
  private Lock lock = new ReentrantLock();
  
  public Object get(Object key) {
    Object ret = cache.get(key);
    lock.lock();
    try {
      if (ret != null) {
        Object head = queue.remove();
        System.out.println(Thread.currentThread().getId() + " Removed: " + head);
        queue.add(head);
      }
    } finally {
      lock.unlock();
    }
    return ret;
  }

  public void put(Object key, Object value) {
    //System.out.println(Thread.currentThread().getId() + " incremented count: " + count);
    lock.lock();
    int count = cnt.incrementAndGet();
    try {
      if (count > capacity) {
        System.out.println(Thread.currentThread().getId() + " Removing: " + queue.peek());
        Object rem = queue.remove();
        cache.remove(rem);
        cnt.decrementAndGet();
      }
    } finally {
      lock.unlock();
    }
    // System.out.println(this.toString());
    // System.out.println(" cnt: " + cnt.get());
    Object prev = cache.put(key, value);
    System.out.println("Thread: " + Thread.currentThread().getId() + " added key: " + key);

    
    if (prev != null) {
      queue.remove(prev);
    }
    queue.add(key);
    
  }
  
  @Override
  public String toString(){
    StringBuffer s = new StringBuffer();
    int i = 0;
    for(Entry e : cache.entrySet()) {
      s.append(++i + ") " + e.getKey() + " -> "+ e.getValue()).append("\n");
    }
    return s.toString();
  }
  
  public int size() {
    return cnt.get();
  }
  
  public static void main(String[] args) throws InterruptedException {
    new ConcurrentLRUCache().execute(20);
  }
  
   private void execute(int threads) throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(threads);
    int start = 0 , end = 10;
    for (int i = 0; i < threads; i++) {
      Putter p = new Putter(start, end);
      executor.submit(p);
//      Getter g = new Getter(start, end);
//      executor.submit(g);
      start =  end;
      end = end + 10;
    }
    executor.shutdown();
    while (!executor.isTerminated()) { }
 
    System.out.println(cache.size());
    System.out.println(this);
    
//    executor = Executors.newFixedThreadPool(threads);
//     start = 0; end = 10;
//    for (int i = 0; i < threads; i++) {
//      Getter g = new Getter(start, end);
//      executor.submit(g);
//      start =  end;
//      end = end + 10;
//    }
//    executor.shutdown();
//    while (!executor.isTerminated()) { }
  }
   
  private class Putter implements Runnable {
    private int start;
    private int end;
    
    private Putter(int start, int end) {
      this.start = start;
      this.end = end;
    }
    
    @Override
    public void run() {
      for (int i = start; i < end; i++) {
        // System.out.println("Thread: " +  Thread.currentThread().getId() + "  putting " + i);
         put(i, "v-" + i);
       }
    }
  }
  
  private class Getter implements Runnable {
    private int start;
    private int end;
    
    private Getter(int start, int end) {
      this.start = start;
      this.end = end;
    }
    
    @Override
    public void run() {
      for (int i = start; i < end; i++) {
        // System.out.println("Thread: " +  Thread.currentThread().getId() + "  putting " + i);
        System.out.println("Thread getting: " +  Thread.currentThread().getId() + " : " + get(i));
       }
    }
  }
}
