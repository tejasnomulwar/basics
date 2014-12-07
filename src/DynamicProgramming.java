import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DynamicProgramming {
 
  public static boolean NQueens(boolean[][] chess, int c) {
    // reached the end
    if(c >= chess[0].length) {
      return true;
    }
    
    // for all rows
    for(int r = 0 ; r < chess.length; r++) {
      // check if safe
      if (isSafe(chess, r, c)) {
        
        // place the queen
        chess[r][c] = true;
        
        // rest of the queens
        if (NQueens(chess, c + 1)) {
          return true;
        }

        // remove the queen if placing does not lead to solution
        chess[r][c] = false;
      }
    }
    
    return false;
  }
  
  private static boolean isSafe(boolean [][]chess, int r, int c) {
    // rows on left
    for(int i = 0; i < c; i++) {
      if(chess[r][i]) {
        return false;
      }
    }
    // diagonally left above
    for(int i = r, j = c; i >= 0 && j >= 0; i--, j--) {
      if(chess[i][j]) {
        return false;
      }
    }
    // diagonally left below
    for(int i = r, j = c; i < chess.length && j >= 0; i++, j--) {
      if(chess[i][j]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Give a cost matrix, return minimum cost path to reach x,y from 0,0
   * traversing only right, down and diagonally
   */
  public static int minCostRecursive(int cost[][], int x, int y) {
    if(x < 0 || y < 0) {
      return Integer.MAX_VALUE;
    }
    if(x == 0 && y == 0) {
      return cost[x][y];
    }
    int diagonal = minCostRecursive(cost, x-1, y-1);
    int right = minCostRecursive(cost, x-1, y);
    int down = minCostRecursive(cost, x, y-1);
    
    return cost[x][y] + Math.min(diagonal, Math.min(right, down));
  }
  
  public static int minCostDP(int cost[][], int x, int y) {
    int[][] path = new int[cost.length][];
    
    for(int r = 1 ; r < cost.length; r++) {
      path[r][0] = path[r-1][0] + cost[r][0];
    }
    
    for(int c = 1 ; c < cost[0].length; c++) {
      path[0][c] = path[0][c-1] + cost[0][c];
    }
    
    for(int r = 1; r < cost.length; r++) {
      for(int c = 1; c < cost[0].length; c++) {
        path[r][c] = Math.min(path[r-1][c-1], Math.min(path[r][c-1], path[r-1][c]));
      }
    }
    
    return path[x][y];
  }
  
  //GEEKSFORGEEKS => EKSFORG
  public static int longestUniqueSubstring(String s) {
    boolean []visited = new boolean[256];
    int  maxlen = 0, i = 0 , j = 0;
    StringBuilder sb = new StringBuilder();
    
    while(j < s.length()) {
      char c = s.charAt(j);
      if(!visited[c]) {
        visited[c] = true;
        j++;
        sb.append(c);
      } else {
        if(j-i > maxlen) {
          maxlen = j-i;
        }
        while(s.charAt(i) != c) {
          visited[s.charAt(i)] = false;
          i++;
        }
        i++; j++;
      }
    }
    
    System.out.println(sb);
    return maxlen;
  }
  
  public static int minJumps(int []a, int s, int e) {
    if(s == e) return 0;
    int min = Integer.MAX_VALUE;
    if(a[s] == 0) return min;
    for(int i = s+1 ; i <= e && i < s + a[s]; i++) {
      int jump = minJumps(a, i, e);
      if(jump < min) min = jump;
    }
    return min;
  }
  
  public static int minJumpsDP(int []a) {
    int []jmp = new int[a.length];
    
    for(int i = 1; i < a.length; i++) {
      jmp[i] = Integer.MAX_VALUE;
      for(int j = 0 ; j < i ; j++) {
        if(i <= j + a[j] && jmp[j] != Integer.MAX_VALUE) {
          if(jmp[i] > jmp[j] + 1) {
            jmp[i] = jmp[j] + 1;
          }
        }
      }
    }
    return jmp[a.length - 1];
  }
  
  
  public static int coinChange(int []coins, int len, int amt) {
    if(amt == 0) return 1;
    if(amt < 0) return 0;
    if(len <= 0 && amt > 0) return 0;
    return coinChange(coins, len - 1, amt) + coinChange(coins, len, amt - coins[len-1]);
  }
  
  public static int coinChangeDP(int[] coins, int amt) {
    int change[] = new int[amt+1];
    for (int i = 0; i < amt + 1; i++) {
      change[i] = Integer.MAX_VALUE;
    }
    change[0] = 0;
    for(int i = 1 ; i <= amt; i++) {
      for(int j = 0 ; j < coins.length ; j++) {
        int val = change[i-coins[j]] + 1; // add a coin
        if(i >= coins[j] && change[i] > val){ //amt > coin value && change req for the amt > change obtained by adding each coin
          change[i] = val;
        }
      }
    }
    return change[amt];
  }
  
  public static int buildBridges(int []n, int []s) {
    int[] x = new int[s.length];
    HashMap<Integer, Integer> nmap = new HashMap<Integer, Integer>();
    for(int i = 0 ; i < n.length; i++) {
      nmap.put(n[i], i);
    }
    
    for(int i = 0 ; i < s.length; i++) {
      x[i] = nmap.get(s[i]);
    }
    int [] lis = new int[x.length];
    for(int i = 0 ; i < lis.length; i++) {
      lis[i]  = 1;
    }
    for(int i = 1; i < x.length; i++) {
      for(int j = 0 ; j < i ; j++) {
        if(x[i] > x[j] && lis[i] < lis[j] + 1) {
          lis[i] = lis[j] + 1;
        }
      }
    }
    int max = 0;
    for(int i = 0 ; i < lis.length; i++) {
      if(lis[i] > max) max = lis[i];
    }
    return max;
  }
  
  public static int editDistance(char[] s, char[] t, int l1, int l2) {
    if(l1 == 0) return l2;
    if(l2 == 0) return l1;
    
    int cost = 0;
    
    if(s[l1-1] != t[l2-1]) {
      cost = 1;
    } 
    int replace = editDistance(s,t,l1-1,l2-1) + cost;
    int delete = editDistance(s,t,l1-1,l2)+1;
    int insert = editDistance(s,t,l1,l2-1)+1;
    
    return Math.min(Math.min(insert, delete), replace);
  }
  
  public static int editDistanceDP(char[] s, char[] t) {
    int[][] d = new int[s.length+1][t.length+1];
    
    for(int i = 0 ; i <= s.length; i++) {
      d[i][0] = i;
    }
    
    for(int i = 0 ; i <= t.length; i++) {
      d[0][i] = i;
    }
    
    for(int i = 1; i <= s.length; i++) {
      for(int j = 1 ; j <= t.length; j++) {
        int diff = 0;
        if(s[i-1] != t[j-1]){ 
          diff = 1;
        }
       int replace = d[i-1][j-1] + diff;
       int insert = d[i][j-1]+1;
       int delete = d[i-1][j]+1;
       int min = Math.min(replace, Math.min(insert, delete));
       d[i][j] = min;
      }
    }
    
    return d[s.length][t.length];
  }
  
  public static HashSet<String> dictionary = new HashSet<String>();
  
  public static boolean wordBreak(String s) {
    if(s.length() == 0) return false;
    if(dictionary.contains(s)) return true;
    for(int i = 0 ; i < s.length(); i++) {
      if(dictionary.contains(s.substring(0, i)) && wordBreak(s.substring(i))) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean wordBreakDP(String s) {
    boolean []w = new boolean[s.length()+1];
    w[0] = true;
    
    for(int i = 1; i <= s.length(); i++) {
      for(int j = 0 ; j < i ; j++) {
        if(w[j] && dictionary.contains(s.substring(j, i))) {
          w[i] = true;
          break;
        }
      }
    }
    return w[s.length()];
  }
  
  private static boolean isSafeForRobot(boolean [][] maze, int x, int y) {
    return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y];
  }
  
  public static boolean robot(boolean [][]maze, boolean[][] path, int x, int y) {
    path[x][y] = true;
    if(x == maze.length-1 && y == maze[0].length-1) {
      return true;
    }
    boolean success = false;
    if(x < maze.length -1 && isSafeForRobot(maze, x+1, y)) {
      success = robot(maze, path, x+1, y);
    }
    
    if(!success && y < maze[0].length && isSafeForRobot(maze, x, y+1)) {
      success = robot(maze, path, x, y+1);
    }    
    return success;
  }
  
  public static void findAllSubsets(ArrayList<Integer> set) {
    ArrayList<ArrayList<Integer>> allSubSets = new ArrayList<ArrayList<Integer>>();
    int max = 1 << set.size();
    for(int i = 0 ; i < max ; i++) {
      allSubSets.add(findSet(set, i));
    }
    System.out.println(Arrays.deepToString(allSubSets.toArray()));
  }
  
  private static ArrayList<Integer> findSet(ArrayList<Integer> set, int max) {
    ArrayList<Integer> newSet = new ArrayList<Integer>();
    int ind = 0;
    for(int i = max ; i > 0; i>>=1) {
      if((i & 1) == 1) {
        newSet.add(set.get(ind));
      }
      ind++;
    }
    return newSet;
  }
  
  public static boolean subsetSumProblem(int[] a, int len, int sum) {
    if(len == 0) return true;
    
    if (len == 0 && sum != 0)
      return false;
    
    if(a[len-1] > sum) {
      subsetSumProblem(a, len - 1, sum);
    }
    
    return subsetSumProblem(a, len - 1, sum) || subsetSumProblem(a, len - 1, sum - a[len-1]);
    
  }
  
  public static void main(String[] args) {
//    boolean [][] chess = new boolean[][] {
//        {false,false,false,false},
//        {false,false,false,false},
//        {false,false,false,false},
//        {false,false,false,false}};
//    NQueens(chess, 0);
//    System.out.println(Arrays.deepToString(chess));
//    System.out.println(longestUniqueSubstring("GEEKSFORGEEKS"));
 //   System.out.println(coinChangeDP(new int[]{1,2,3,4}, 5));
 //   System.out.println(buildBridges(new int[]{1,2,3,4}, new int[]{1,2,3,4}));
 //   System.out.println(editDistanceDP(new char[]{'a','b','c'}, new char[]{'a','x','c'}));
//    dictionary.add("this");
//    dictionary.add("active");
//    dictionary.add("act");
//    System.out.println(wordBreakDP("thisactive"));
//    ArrayList<Integer> list = new ArrayList<Integer>();
//    list.add(1);
//    list.add(2);
//    list.add(3);
//    
//    findAllSubsets(list);
    System.out.println(subsetSumProblem(new int[]{1,2,3}, 3, 5));
  }

}
