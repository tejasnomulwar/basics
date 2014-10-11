import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;



public class Ship implements Serializable{ 
  static void replaceRowColumnWithZero(int [][]a) {
    boolean []r = new boolean[a.length];
    boolean []c = new boolean[a[0].length];
    
    for(int i=0 ; i < r.length; i++) {
      for(int j=0 ; j < c.length; j++) {
        if(a[i][j] == 0) {
          r[i] = true;
          c[j] = true;
        }
      }
    }
    
    for(int i=0 ; i < r.length; i++) {
      for(int j=0 ; j < c.length; j++) {
        if(r[i] || c[j]) {
          a[i][j] = 0;
        }
      }
    }
    System.out.println(Arrays.deepToString(a));
  }
  
  static void oddEvenOccurence(int []a) {
    HashSet s = new HashSet();
    for(int i : a) {
      if(s.contains(i)) {
        s.remove(i);
      } else {
        s.add(i);
      }
    }
    System.out.println(" Odd occurences: " + Arrays.toString(s.toArray()));
  }

  
  static ArrayList<String> getExpandedStrings(String s, ArrayList<String>  expandedStrings) {
    if(s == null) return null;
    String exp = "";
    if(s.indexOf('?') != -1) {
      exp = insertCharAt(s, 'j', s.indexOf('?'));
      expandedStrings = getExpandedStrings(exp, expandedStrings);
      exp = insertCharAt(s, 'k', s.indexOf('?'));
      expandedStrings = getExpandedStrings(exp, expandedStrings);
    } else {
      expandedStrings.add(s);
    }
    return expandedStrings;
   }

  static String insertCharAt(String s, char c, int i){
    return s.substring(0,i) + c + s.substring(i+1);
  }

  static void printline(List<String> words, int len) {
    StringBuffer line = new StringBuffer();
    for(String word : words) {
      if(line.length() + word.length()  <= len) {
        line.append(word).append(" ");
      } else {
        System.out.println(line.substring(0, line.length()-1));
        line = new StringBuffer();
        line.append(word).append(" ");
      }
    }
    System.out.println(words.get(words.size()-1));
  }
  
  static int[] selfExcludingProduct(int[] input) {
    int [] output = new int[input.length];
    for(int i=0 ; i < input.length; i++){
      int mult = 1;
      for(int j=0; j < input.length; j++){
        if(i != j) {
          mult = mult * input[j];
        }  
      }
      output[i] = mult;
    }
    return output;
}
  
  static ArrayList<ArrayList<Integer>> permutations(ArrayList<Integer> a){
    ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    if(a.size() == 1) {
      res.add(a);
      return res;
    }
    int x = a.get(0);
    ArrayList<Integer> rem = new ArrayList<Integer>();
    for(int i = 1; i < a.size(); i++) {
      rem.add(a.get(i));
    }
    ArrayList<ArrayList<Integer>> sub = permutations(rem);
    for(int i = 0; i < sub.size(); i++) {
      for(int j=0; j<= sub.get(i).size(); j++) {
        res.add(insertNumAt(sub.get(i), j, x));
      }
    }
   return res;
  }

  static ArrayList<Integer> insertNumAt(ArrayList<Integer> a, int index, int j){
    ArrayList<Integer> temp = new ArrayList<Integer>();
    int i = 0;
    while(i < index){
      temp.add(a.get(i));
      i++;
    }
    temp.add(j);
    i = index;
    while(i < a.size()){
      temp.add(a.get(i));
      i++;
    }
    return temp;
  }

  static void findperms(){
    ArrayList<Integer> a = new ArrayList<Integer>();
    a.add(1);
    a.add(2);
    a.add(3);
   
   System.out.println(permutations(a));
  }
  
  static int test(int []a){
    int p=1, np=1, mp =1;
    for (int i = 0; i < a.length; i++) {
      p *= a[i];
      if(p > mp && p > 0){
        mp = p;
      } else if(p == 0){
        p = 0;
      } else if(p < 0){
        np = p;
      }
    }
    return np > p ? mp : p;
  }
  
  static int couples(int a[]) {
    int top = 0, count = 0;
    // the character array ...

    for (int i = 1; i < a.length; i++) {
      if (a[top] == a[i]) {
        top = top - 1;
        count++;
      }

      else {
        top = top + 1;
      }
    }
    return count;
  }
  
  static boolean isPrEq(List a, List b){
    if(a.size() == 0 || b.size() == 0) return false;
    if(sum(a) == sum(b)) {
      return true;
    }
    a.add(b.get(0));
    b.remove(0);
    return isPrEq(a, b);
  }
  
  static int sum(List a) {
    int sum = 0;
    for (Object i : a) {
      sum += (Integer)i;
    }
    return sum;
  }
  
  static int maxConsecutiveSum (int [] a) {
    int maxsum = a[0], sum = 0;
    for(int i = 1; i < a.length; i++) {
      sum = sum + a[i];
      if(sum > maxsum) {
        maxsum = sum;
      } else if (sum < 0) {
        sum = a[i];
      }
    }
    return maxsum;
  }
  
  static int maxConsecutiveProduct (int [] a) {
    int maxprod = 1, minprod = 1, max = 1;
    for(int i : a) {
      if(i > 0) {
        maxprod = maxprod * i;
        minprod = Math.min(minprod, 1);
      } else if(i == 0) {
        maxprod = 1;
        minprod = 1;
      } else {
        int temp = maxprod;
        maxprod = Math.max(minprod * i, 1);
        minprod = temp * i;
      }
      if(maxprod > max) {
        max = maxprod;
      }
    }
    return max;
  }
  
  static void bestStock(int []a) {
    int buy = 0, sell = 0, min = 0, diff = 0, maxDiff = 0;
    for(int i=0 ; i<a.length; i++) {
      if(a[i] < a[min]) {
        min = i;
      }
      diff = a[i] - a[min];
      if(diff > maxDiff) {
        buy = min;
        sell = i;
        maxDiff = diff;
      }
    }
    System.out.println("buy: " + buy + " sell: " + sell);
  }
  
  static void sortedTriplet (int []a) {
    int min = 0, max = a.length -1 ;
    int[] smaller = new int[a.length], greater = new int[a.length];
    smaller[0] = -1;
    
    for(int i = 1 ; i < a.length; i++) {
      if(a[i] > a[min]) {
        smaller[i] = min;
      } else {
        min = i;
        smaller[i] = -1;
      }
    }
   
    greater[greater.length-1] =-1;
    for(int i = greater.length - 2 ; i >= 0 ; i--) {
      if(a[i] < a[max]){
        greater[i] = max;
      } else {
        greater[i] = -1;
        max = i;
      }
    }
    
    for(int i=0; i < a.length; i++) {
      if(smaller[i] != -1 && greater[i] != -1) {
        System.out.println(smaller[i] + " " + a[i] + " " + greater[i]);
      }
    }
  }
  
  static void findMaxInAslidingWindow(int[] a, int w) {
    int[] b = new int[a.length - w + 1];
    for (int i = 0; i < a.length - w; i++) {
      int max = a[i];
      for (int j = i; j < i + w && j < a.length; j++) {
        if (a[j] > max) {
          max = a[j];
        }
      }
      b[i] = max;
    }
    System.out.println(Arrays.toString(b));
  }
  
  static void frequentWords(String s) {
    String[] tokens = s.split("\\s+");
    System.out.println(Arrays.toString(tokens));
    Map<String, Integer> map = new HashMap<String, Integer>();

    for (String word : tokens) {
      Integer i = map.get(word);
      if (i == null) {
        map.put(word, 1);
      } else {
        map.put(word, ++i);
      }
    }

    List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(
        map.entrySet());

    // Sorting the list based on values
    Collections.sort(list, new Comparator<Entry<String, Integer>>() {
      public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
        return o2.getValue().compareTo(o1.getValue());
      }
    });

    Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
    for (Entry<String, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    System.out.println(sortedMap);
  }
  
  static List<List<Integer>> allPossibleSubsets(List<Integer> a, int index) {
    List<List<Integer>> subsets = new ArrayList<List<Integer>>();
    if(a.size() == index) {
      subsets.add(new ArrayList<Integer>());
    } else {
      subsets = allPossibleSubsets(a, index + 1);
      int i = a.get(index);
      List<List<Integer>> moreSets = new ArrayList<List<Integer>>();
      
      
    }
    return subsets;
  }
  
  static int binarySearch(int []a, int s) {
    int low = 0, high = a.length - 1;
    while(low <= high) {
      int mid = low + (high - low)/2;
 
      if(s > a[mid]) {
        low = mid + 1;
      } else if (s < a[mid]) {
        high = mid -1;
      } else {
        return mid;
      }
    }
    return -1;
  }
  
  static int binarySearchFirstOccurence(int []a, int s) {
    int low = 0, high = a.length - 1;
    while(low <= high) {
      int mid = low + (high - low)/2;
 
      if(s > a[mid]) {
        low = mid + 1;
      } else if (s < a[mid]) {
        high = mid -1;
      } else if (low != mid){
        high = mid;
      } else {
        return mid;
      }
    }
    return -1;
  }
  
  static int binarySearchRotatedArray(int []a, int s) {
    int low = 0, high = a.length - 1;
    while(low <= high) {
      int mid = low + (high - low)/2;
      if( mid == s) return mid;
      //lower half sorted
      if(a[low] <= a[mid]) {
        if( s >= a[low] && s < a[mid]){
          high = mid -1;
        } else{
          low = mid + 1;
        }
        
      } else  { // upper half sorted
        if (s > a[mid] && s <= a[high]) {
          low = mid + 1;
        } else {
          high = mid -1;
        }
      }
            
    }
    return -1;
  }
  
  static int searchPivotInRotatedArray(int []a) {
    int low = 0, high = a.length -1;
    while(a[low] > a[high]) {
      int mid = low + (high - low)/2;
      if(a[mid] > a[high]) {
        low = mid + 1;
      } else {
        high = mid;
      }
    }
    return low;
  }
  

static int getNumberOfGames(int players){
    if(players == 1) return 0;
    if(players == 0) return 0;
    
    int games = players / 2;
    
    if(players % 2 == 0) {
      games += getNumberOfGames(games);
    } else {
      games += getNumberOfGames(games+1);
    }
    return games;
}

 static int distinctNumbersInArray(int []a) {
  int [] r = new int[a.length]; 
  int k = 0, x = 0;
  
    for (int i = 0; i < a.length; x ^= a[i]);

     
    
  return x;
 }

 
  static int[] alternatePositiveNegativeArray(int []a) {
    int [] res = new int[a.length*2];
    boolean positive = a[0] > 0;
    res[0] = a[0];
    int j = 2;
    for(int i = 1; i< a.length; i++) {
      if(positive) {
        if(a[i] > 0) {
          res[j] = a[i];
        }
      } else {
        if(a[i] < 0) {
          res[j] = a[i];
        }
      }
      j+=2;
    }
    j = 1;
    for(int i = 1; i < a.length; i++) {
      if(positive) {
        if(a[i] < 0) {
          res[j] = a[i];
          j+=2;
        }
      } else {
        if(a[i] > 0) {
          res[j] = a[i];
          j+=2;
        }
      }
    }
    int[] res2 = new int[a.length];
    j  = 0;
    for(int i = 0 ; i < res.length; i++) {
      if(res[i] != 0) {
        res2[j++] = res[i];
      }
    }
    return res2;
  }
  
  static int[] relativeSort(int []a, int []b) {
    boolean agreater = a.length >= b.length;
    int[] temp = new int[agreater?a.length:b.length];
    int[] o = new int[agreater?a.length:b.length];
    int k=0;
    if(agreater) {
      for(int i=0 ; i < b.length; i++) {
        for(int j=0 ; j < a.length; j++) {
          if(b[i] == a[j]) {
            o[k++] = b[i];
          }
        }
      }
      int n=0;
      
      for(int i=0 ; i < a.length; i++) {
        boolean found = false;
        for(int j=0 ; j < b.length; j++) {
          if(a[i] == b[j]) {
            found = true;
            break;
          } 
        }
        if(!found) {
          temp[n++] = a[i];
        }
      }
      int j = 0;
      temp = Arrays.copyOf(temp, n);
      Arrays.sort(temp);
      for(int i = k; i < k + n; i++) {
        o[i] = temp[j++];
      }
    } else {
      
    }
    return o;
  }
  
  static List<String> stringPermutations(String s) {
    List<String> perms = new ArrayList<String>();
    if(s.length() == 1) {
      perms.add(s);
      return perms;
    }
    char c = s.charAt(0);
    String s2 = s.substring(1);
    List<String> iperms = stringPermutations(s2);
    for(String ip : iperms) {
      for(int j = 0 ; j <= ip.length(); j++) {
        perms.add(appendCharAt(ip, c, j));
      }
    }
    return perms;
  }
  
  static String appendCharAt(String s, char c, int j) {
    if(j == 0) return c + s;
    if(j == s.length()) return s + c;
    return s.substring(0, j) + c + s.substring(j, s.length());
  }
  
  static int handshake(int n) {
   if(n == 0 || n == 1) return 0;
   if(n == 2) return 1;
   return handshake(n-1) + n-1;
  }
  
  static String removeAdjacentDuplicates(String s) {
    char[]c = s.toCharArray();
    String s2 = "";
    if(s.length() == 1) return s;
    for(int i = 1 ; i < s.length(); i++) {
      if(c[i] != c[i-1]) {
        s2 += c[i-1];   
      } else {
        i++;
      }
      if( i == s.length() -1) {
        s2 += c[i];
      }
    }
    if(s.equals(s2)) return s;
    return removeAdjacentDuplicates(s2);
  }
  
  static String removeBandAC(String s) {
    String s2 = "";
    for(int i=0; i < s.length(); i++) {
      if(s.charAt(i) == 'b') {
        
      } else if(i != s.length()-1 && s.charAt(i) == 'a' && s.charAt(i+1) == 'c') {
        i++;
      } else {
        s2 += s.charAt(i);
      }
    }
    return s2;
  }
  
  static ArrayList<String> generateParens(int n) {
    ArrayList<String> parens = new ArrayList<String>();
    if(n == 0){
      parens.add(""); 
    } else {
      ArrayList<String> prev = generateParens(n - 1);
      for (String s : prev) {
        for(int i=0 ; i < s.length(); i++) {
          if(s.charAt(i) == '(') {
            parens.add(insertParamsAt(s, i));
          }
        }
        parens.add("()"+s);
      }
     
    }
    
    return parens;
  }
 
  static String insertParamsAt(String s, int pos) {
    return s.substring(0, pos) + "()" + s.substring(pos); 
  }
  
  private static class QueueStack {
    private int[] arr = new int[10];
    private int head, tail, top = -1;

    private void enqueue(int o) {
      arr[head++] = o;
      if (head == arr.length)
        head = 0;
    }

    private int dequeue() {
      int i = arr[tail++];
      if (tail == arr.length)
        tail = 0;
      return i;
    }

    private void push(int o) {
      arr[++top] = o;
    }

    private int pop() {
      return arr[top--];
    }
    
    private boolean isEmpty() {
      return top == -1;
    }
  }
  
  HashMap map = new HashMap();
  public volatile Object curr;
  
  private void put(Object key, Object value) {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    map.put(key, value);
  }
  
  private void put(Object key, Object val, Object c) throws InterruptedException {
    if(curr == null) {
      curr = c;
     // System.out.println("Thread: " +  Thread.currentThread().getId() + " adding: key: " + key + " val: " + val + " c: " + c);
      put(key, val);
    } else if (curr == c) {
      System.out.println("Thread: " +  Thread.currentThread().getId() + " adding: key: " + key + " val: " + val + " c: " + c);
      put(key, val);
      synchronized(this){
        System.out.println("Thread: " +  Thread.currentThread().getId() + "  notifying ");
        this.notify();
      }
    } else {
      synchronized(this) {
        System.out.println("Thread: " +  Thread.currentThread().getId() + "  waiting ");
        this.wait();
        System.out.println("Thread: " +  Thread.currentThread().getId() + " adding: key: " + key + " val: " + val + " c: " + c);
        put(key, val);
      }
    }
    
  }
  
  private static void findSubArrayToSortArray(int [] a) {
    int i = 1;
    while( i < a.length - 1){
      if(a[i-1] > a[i]) break;
      i++;
    }
    i--;
    
    int j = a.length - 2;    
    while( j > 0){
      if(a[j+1] < a[j]) break;
      j--;
    }
    j++;
    int min = a[i], max = a[i];
    for(int k = i+1 ; k <= j; k++){
      if(a[k] < min) {
        min = a[k];
      } 
      if(a[k] > max){
        max = a[k];
      }
    }
    int start = i;
    for(int l = 0; l < i; l++){
      if(a[l] > min){ 
        start = l;
        break;
      }
    }
    
    int end = j;
    for(int m = a.length-1; m >= j+1; m--) {
      if(a[m] < max){
        end = m; 
        break;
      }
      
    }
    
    System.out.println("min array indexes: " + start + " and " + end);
  }
  
  private static int findCrossover(int []a, int x, int l, int h) {
    int mid = l + (h-l)/2;
    if(x < a[mid]) {
      return findCrossover(a, x, l, mid-1);
    } else if(x > a[mid]) {
      return findCrossover(a, x, mid+1, h);
    } else {
      return mid;
    }
  }
  
  private static int[] kClosest(int []a, int k, int x) {
    int[] c = new int[a.length];
    int pos = findCrossover(a, x, 0, a.length-1);
    int i = pos - 1, j = pos + 1, count = 0, p = 0;
    while(i >=0 && j < a.length && count < k) {
      if((x - a[i]) < (a[j] - x)) {
        c[p++] = a[i];
      } else {
        c[p++] = a[j];
      }
      i--; j++;count++;
    }
    return c;
  }
  
  private static int reverseNumber (int i) {
    int div = 1, r = i;
    r = r / 10;
    while(r > 0) {
      div = div * 10;
      r = r / 10;
    }
    int s = 0;
    while(i > 0){
      int t = i % 10;
      i = i / 10;
      s = s + t * div;
      div = div / 10;
    }
    return s;
  }
  
  private static void divideArrayEqualSum(int []a) {
    int b[] = new int[a.length];
    int c[] = new int[a.length];
    int bi = 0, ci = 0;
    Arrays.sort(a);
    for(int i = a.length -1 ; i >= 0 ; i--) {
      if(sum(b) <= sum(c)) {
        b[bi++] = a[i];
      } else {
        c[ci++] = a[i];
      }
    }
    System.out.println(Arrays.toString(b));
    System.out.println(Arrays.toString(c));
  }
  
  private static int sum(int []a){
    int sum = 0;
    for(int i : a) {
      sum += i;
    }
    return sum;
  }
  
  // 100, 80, 60, 70, 60, 75, 85
  public static int[]  stockSpanProblem(int a[]){
    Stack<Integer> st = new Stack<Integer>();
    st.push(0);
    int []s = new int[a.length];
    s[0] = 1;
    
    for(int i = 1 ; i < a.length; i++) {
      s[i] = 1;
      while(!st.empty() && a[i] > a[st.peek()]) {
        st.pop();
      }
      
      if(st.empty()) {
        s[i] = i + 1;  
      } else {
        s[i] = i - st.peek();
      }
      st.push(i);
    }
    return s;
  }
  
  public static void printMaxOfKElements(int [] a, int k) {
    ArrayDeque<Integer> q = new ArrayDeque<Integer>();  
     
    for(int i = 0 ; i < k; i++) {
      while(!q.isEmpty() && a[q.getLast()] <= a[i]) {
        q.pollLast();
      }
      q.addLast(i);
    }
    
    for(int i = k; i < a.length; i++) {
      System.out.println(a[q.getFirst()]);
      
      while(!q.isEmpty() && q.getFirst() <= i-k) {
        q.pollFirst();
      }
      
      while(!q.isEmpty() && a[q.getLast()] <= a[i]){
        q.pollLast();
      }
      q.addLast(i);
    }
    System.out.println(a[q.getFirst()]);
  }
  
  public static void main(String[] args) throws InterruptedException {
//  List l = getExpandedStrings("j?kj?", new ArrayList<String>());
//  System.out.println(l.size());
//  System.out.println(l);
//  ArrayList<String> s = new ArrayList<String>();
//  s.add("aaa");
//  s.add("bb");
//  s.add("cc");
//  s.add("ddddd");
//  s.add("ee");
//  s.add("ee");
//  s.add("e");
// printline(s, 5);
// 
// System.out.println(Arrays.toString(selfExcludingProduct(new int[]{3,1,4,2})));
//   findperms();
  //System.out.println(test(new int[]{1,5,3,-1, 2}));
//  System.out.println(couples(new int[]{'R', 'G', 'B', 'B', 'G', 'R', 'Y'}));
//    ArrayList<Integer> a = new ArrayList<Integer>();
//    a.add(5);
//    a.add(1);
//    a.add(5);
//    a.add(1);
//    
//    
//    System.out.println(isPrEq(a.subList(0, 1), a.subList(1, a.size())));
//    oddEvenOccurence(new int[]{1,2,2,1,1,3,3,3,3,1,2,1,0});
//    replaceRowColumnWithZero(new int[][]{{1,2,3},{4,0,6},{7,8,9}});
//    System.out.println(maxConsecutiveProduct(new int[] {-4,-2,-3}));
//    findMaxInAslidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3);
 //   frequentWords("one two two three three three");
    System.out.println(binarySearchFirstOccurence(new int[]{1,2,3,4,5,6}, 6));
   // System.out.println(getNumberOfGames(9));
  //  System.out.println(distinctNumbersInArray(new int[]{1,1,2,3,2}));
  //  System.out.println(Arrays.toString(alternatePositiveNegativeArray(new int[]{1,2,3,-4,-5,-6,-7})));
 //   System.out.println(Arrays.toString(relativeSort(new int[]{2,1,2,5,7,1,9,3,6,8,8}, new int[]{2,1,8,3})));
   // System.out.println(Arrays.toString(stringPermutations("abc").toArray()));
   // System.out.println(handshake(3));
   // System.out.println(removeAdjacentDuplicates("ABCCBCBA"));
   // System.out.println(removeBandAC("acbac"));
//    QueueStack c = new QueueStack();
//    c.push(1);
//    c.push(2);
//    c.push(3);
//    c.push(4);
//    c.push(5);
//    
//    while(!c.isEmpty()) {
//      System.out.println(c.pop());
//    }
//    
//    c.push(1);
//    c.push(2);
//    c.push(3);
//    
//    while(!c.isEmpty()) {
//      System.out.println(c.pop());
//    }
//    final String s = "c1";
//    final Ship ship = new Ship();
//    
//    Thread t1 = new Thread(new Runnable() {      
//      @Override
//      public void run() {
//        try {
//          ship.put("key-1","val-1", s);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }        
//      }
//    });
//    
//    Thread t2 = new Thread(new Runnable() {      
//      @Override
//      public void run() {
//        try {
//          ship.put("key-2","val-2", s);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }        
//      }
//    });
//    
//    Thread t3 = new Thread(new Runnable() {      
//      @Override
//      public void run() {
//        try {
//          ship.put("key-3","val-3", "c2");
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }        
//      }
//    });
//    
//    Thread t4 = new Thread(new Runnable() {      
//      @Override
//      public void run() {
//        try {
//          ship.put("key-3","val-3", s);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }        
//      }
//    });
//    
//    t1.start();
//    t2.start();
//    t3.start();
//   // Thread.sleep(10);
//    t4.start();
//    
//    t1.join();
//    t2.join();
//    t3.join();
//    t4.join();
   // findSubArrayToSortArray(new int[]{10, 12, 20, 30, 25, 40, 32, 31, 35, 50, 60});
  //  System.out.println(Arrays.toString(kClosest(new int[]{12, 16, 22, 30, 35, 39, 42, 45, 48, 50, 53, 55, 56}, 4, 35)));
   // System.out.println(reverseNumber(123));
   // divideArrayEqualSum(new int[]{1,2,3,6});
   // System.out.println(Arrays.toString(stockSpanProblem(new int[] {100, 80, 60, 70, 60, 75, 85})));
  //  printMaxOfKElements(new int[]{1, 2, 3, 1, 4, 5, 2, 3, 6}, 3);
}

}
