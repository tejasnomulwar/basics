import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class BinaryTree {
  private Node root;
  
  public BinaryTree(Node root) {
    this.root = root;
  }
  
  private static class Node {
    private int value;
    private Node left;
    private Node right;
    private Node next;
    private Node parent;
    
    public Node(int value, Node left, Node right) {
      super();
      this.value = value;
      this.left = left;
      this.right = right;
    }        
    
    public String toString(){
      return value+"";
    }
  }
  
  private void preOrderRecursive(Node root) {
    if(root == null) return;
    System.out.print(root + " ");
    preOrderRecursive(root.left);
    preOrderRecursive(root.right);
  }
  
  private void inOrderRecursive(Node root) {
    if(root == null) return;
    inOrderRecursive(root.left);
    System.out.print(root + " ");
    inOrderRecursive(root.right);
  }
  
  private void postOrderRecursive(Node root) {
    if(root == null) return;
    postOrderRecursive(root.left);
    postOrderRecursive(root.right);
    System.out.print(root + " ");
  }
  
  private int size(Node n) {
    if(n == null) return 0;
    return size(n.left) + 1 + size(n.right) ;
  }
  
  private int height(Node n) {
    if(n == null) return 0;
    return (Math.max(height(n.left), height(n.right)) + 1) ;
  }
  
  private void printAllNodeToLeafPaths(Node root, ArrayList<Integer> path, int level){
    if(root == null) return;
    path.add(level++, root.value);
    if(root.left == null && root.right == null) {
      System.out.println(Arrays.toString(path.subList(0, level).toArray()));
    } else {
      printAllNodeToLeafPaths(root.left, path, level);
      printAllNodeToLeafPaths(root.right, path, level);
    }
  }
  
  private void printLevelOrder(Node node) {
    if(root == null) return;
    Queue<Node> q = new LinkedList<Node>();
    q.offer(root);
    while(!q.isEmpty()){
      Node n = q.poll();
      if(n != null) {
        System.out.print(n.value + " ");
        q.offer(n.left);
        q.offer(n.right);
      }
    }   
  }
  
  private void setNextCompleteBinaryTree(Node node) {
    if(root == null) return;
    if(root.left != null) {
      root.left.next = root.right;
    }
    if(root.right != null) {
      if(root.next != null) {
        root.right.next = root.next.left;
      } 
    }
    setNextCompleteBinaryTree(root.left);
    setNextCompleteBinaryTree(root.right);
  }
  
  
  private void setNext(Node root) {
    if(root == null) return;
    Node curr = root;
    
    while (root != null) {
      while (curr != null) {  // same level
        if (curr.left != null) {
          if (curr.right != null) {
            curr.left.next = curr.right;
          } else {
            curr.left.next = getRight(curr.next);
          }
        }
        curr = curr.next;
      }
      if(root.left != null) root = root.left;
      else if(root.right != null) root = root.right;
      else root = getRight(root); // no left and right; go to next on same level
    }
    
  }
  
  private Node getRight(Node n) {
    while(n != null) {
      if(n.left != null) return n.left;
      if(n.right != null) return n.right;
      n = n.next;
    }
    return null;
  }
  
  private int countLeaves(Node n) {
    if(n == null) return 0;
    if(n.left == null && n.right == null) return 1;
    return countLeaves(n.left) + countLeaves(n.right);
  }
  
  private void levelOrder(Node n) {
    if (n == null) return;
    for(int i = 1 ; i <= height(n); i++) {
      printLevel(n, i);
    }
  }
  
  private void printLevel(Node n, int level) {
    if(n == null) return;
    
    if(level == 1) {
      System.out.print(n.value + " ");
    }
    
    if(level > 1) {
      printLevel(n.left, level - 1);
      printLevel(n.right, level - 1);
    }
  }
  
  private void levelOrderSpiral(Node n) {
    if (n == null) return;
    boolean direction = true;
    for(int i = 1 ; i <= height(n); i++) {
      printLevelSpiral(n, i, direction);
      direction = !direction;
    }
  }
  
  
  private void printLevelSpiral(Node n, int level, boolean direction) {
    if(n == null) return;
    
    if(level == 1) {
      System.out.print(n.value + " ");
    }
    
    if(level > 1) {
      if(direction) {
        printLevel(n.left, level - 1);
        printLevel(n.right, level - 1);
      } else {
        printLevel(n.right, level - 1);
        printLevel(n.left, level - 1);
      }
    }
  }
  
  public static void join(Node a, Node b) {
    a.right = b;
    b.left = a;
}
  
  public static Node append(Node a, Node b) {
    // if either is null, return the other
    if (a==null) return(b);
    if (b==null) return(a);
    
    // find the last node in each using the .previous pointer
    Node aLast = a.left;
    Node bLast = b.left;
    
    // join the two together to make it connected and circular
    join(aLast, b);
    join(bLast, a);
    
    return(a);
}


/*
 --Recursion--
 Given an ordered binary tree, recursively change it into
 a circular doubly linked list which is returned.
*/
public static Node treeToList(Node root) {
    // base case: empty tree -> empty list
    if (root==null) return(null);
    
    // Recursively do the subtrees (leap of faith!)
    Node aList = treeToList(root.left);
    Node bList = treeToList(root.right);
    
    // Make the single root node into a list length-1
    // in preparation for the appending
    root.left = root;
    root.right = root;
    
    // At this point we have three lists, and it's
    // just a matter of appending them together
    // in the right order (aList, root, bList)
    aList = append(aList, root);
    aList = append(aList, bList);
    
    return(aList);
}

  private void printList(Node n) {
    Node temp = n;
    while(temp != null){
      System.out.print(temp.value + " ");
      temp = temp.right;
      if(temp == n) break;
    }
  }
  
  private boolean childrenSumProperty(Node n) {
    if(n.left == null && n.right == null) return true;
    
    if(n.left == null && n.right != null) {
      return (n.value == n.right.value);
    }
    
    if(n.left != null && n.right == null) {
      return (n.value == n.left.value);
    }
    
    if(n.left != null && n.right != null && n.value != n.left.value + n.right.value) {
      return false;
    }
    
    return childrenSumProperty(n.left) && childrenSumProperty(n.right);
  }
  
  private void convertChildrenSumProperty(Node n) {
    if(n == null) return;
    convertChildrenSumProperty(n.left);
    convertChildrenSumProperty(n.right);

    if(n.left != null && n.right != null){
      n.value = n.left.value + n.right.value;
    }
    if(n.left == null && n.right != null) {
      n.value = n.right.value;
    }
    if(n.left != null && n.right == null) {
      n.value = n.left.value;
    }    
  }
  
  private int diameter(Node n) {
    if(n == null) return 0;
    int hl = height(n.left);
    int hr = height(n.right);

    return Math.max(hl + hr + 1, Math.max(diameter(n.left), diameter(n.right)));
  }
  
  private static class Height {
    public int height;
  }
  
  private int diameterOptimized(Node n, Height h) {
    int hl = 0, hr = 0;
    if(n == null) return 0;
    Height lh = new Height();
    Height rh = new Height();
    int ld = diameterOptimized(n.left, lh);
    int rd = diameterOptimized(n.right, rh);
    h.height = Math.max(hl, hr) + 1;
    return Math.max(hl + hr + 1, Math.max(diameter(n.left), diameter(n.right)));
  }
  
  private Node LCA(Node root, Node l, Node r) {
    if(l.value < r.value && r.value < root.value) {
      return LCA(root.left, l , r);
    } else if (l.value > r.value && r.value > root.value) {
      return LCA(root.right, l , r);
    } else {
      return root;
    }
  }
  
  private Node LCABinaryTree (Node n, Node l, Node r) {
    if(n == null) return null;
    if(n.value == l.value || n.value == r.value) return n;
    Node llca = LCABinaryTree(n.left, l, r);
    Node rlca = LCABinaryTree(n.right, l, r);
    if(llca != null && rlca != null) return n;
    return (llca != null) ? llca : rlca;
  }
  
  private void printAncestors(Node root, ArrayList<Node> path, int level, int n) {
    if(root == null) return;
    path.add(level++, root);
    if(root.value == n) {
      System.out.println(Arrays.toString(path.subList(0, level).toArray()));
    } else {
      printAncestors(root.left, path, level, n);
      printAncestors(root.right, path, level, n);
    }
  }
  
  private boolean isIdentical(Node r1, Node r2) {
    if((r1 != null && r2 == null) || (r2 != null && r1 == null)) return false;
    return (r1.value == r2.value && isIdentical(r1.left, r2.left) && isIdentical(r1.right, r2.right));
  }
  
  private boolean isSubTree(Node n, Node s) {
    if(n == null || s == null) return true;
    if(isIdentical(n, s)) return true;
    return isSubTree(n.left, s) || isSubTree(n.right, s);
  }
  
  private boolean isCousin(Node n, Node a, Node b) {
    if(n == null) return true;
    return (getLevel(n, a, 1) == getLevel(n, b, 1)) && !areSiblings(n, a, b);
    
  }
  
  private boolean areSiblings(Node root, Node a, Node b) {
    if(root == null) return false;
    if(root.left != null && root.right != null && root.left.value == a.value && root.right.value == b.value){
      return true;
    }
    return areSiblings(root.left, a, b) || areSiblings(root.right, a, b);
    
  }
  
  private int getLevel(Node root, Node n, int level) {
    if(root == null) return 0;
    if(root.value == n.value) return level;
    int ll = getLevel(root.left, n, level+1);
    if(ll > 0) return ll;
    return getLevel(root.right, n, level+1);
  }
  
  private Node createBST(int []arr, int l, int h) {
    int mid = l + (h-l)/2;
    Node n = new Node(arr[mid], null, null);
    n.left = createBST(arr, 0, mid-1);
    n.right = createBST(arr, mid+1, h);
    return n;
  }
  
  private void createLeveledLinkdedList(ArrayList<ArrayList<Node>> lists, Node root, int level){
    if(root == null) return;
    ArrayList<Node> list = null;
    if(lists.size() == level){
      list = new ArrayList<BinaryTree.Node>();
      lists.add(list);
    } else {
      list = lists.get(level);
    }
    list.add(root);
    createLeveledLinkdedList(lists, root.left, level+1);
    createLeveledLinkdedList(lists, root.right, level+1);
  }
  private static int index;
  private void isBST(int[] arr, Node root) {
    if(root == null) return;
    isBST(arr, root.left);
    arr[index++] = root.value;
    isBST(arr, root.right);  
  }
  
  private boolean isBST(Node root, Integer min, Integer max) {
    if(root == null) return true;
    if(min != null && root.value < min || max != null && root.value > max) return false;
    return isBST(root.left, min, root.value) && isBST(root.right, root.value, max);
  }
  
  private Node inOrderSuccessor(Node n) {
    if(n == null) return null;
    if(n.right != null) {
      Node temp = n.right;
      while(temp.left != null) {
        temp = temp.left;
      }
      return temp;
    } else {
      Node temp = n.parent;
      while(temp != null && temp.left != n){
        temp = temp.parent;
        n = temp;
      }
      return temp;
    }
  }
  
  private void findPathsForSum(Node root, int[] path, int sum, int level) {
    if(root == null) return;
    path[level] = root.value;
    int s = 0;
    for(int i : path) s += i;
    if(s == sum){
      for(int j=0; j <= level; j++){
        System.out.print(path[j] + " ");
      }
    } else {
      findPathsForSum(root.left, path, sum, level+1);
      findPathsForSum(root.right, path, sum, level+1);
    }
  }
  
  
  private int setValueSumOfLeftTree(Node n) {
    if(n == null) return 0;
    n.value += setValueSumOfLeftTree(n.left);
    return n.value + setValueSumOfLeftTree(n.right);
  }
  
  private static class IntWrapper {
    public int sum;
  }
  
  private int maxPathSum(Node n, int sum) {
    if(n == null) return 0;
    int ls = maxPathSum(n.left, sum);
    int rs = maxPathSum(n.right, sum);
    
    int currSum = Math.max(ls + rs + n.value, Math.max(ls, rs));
    if(currSum > sum) {
      sum = currSum;
    }
    
    return Math.max(ls, rs) + n.value;
  }
  
  
  public Node getNextForGivenNode(Node root, Node n) {
    if(root == null) return null;
    Queue<Node> q = new LinkedList<BinaryTree.Node>();
    q.add(root);
    while(!q.isEmpty()) {
      Node t = q.poll();
      if(t.value == n.value) {
        if(q.isEmpty()) {
          return null;
        } else {
          return q.poll();
        }
      }
      if(t.left != null) {
        q.add(t.left);
      }
      if(t.right != null){
        q.add(t.right);
      }
    }
    return null;
  }
  
  
  public int sumOfAllNodes(Node root, int val) {
    if(root == null) return 0;
    val = val * 10 + root.value;
    if(root.left == null && root.right == null) {
      return val;
    }
    return sumOfAllNodes(root.left, val) + sumOfAllNodes(root.right, val);
  }
  
  public void removeNodeIfSumIsGreater(Node root, int sum, int val, boolean isleft) {
    if(root == null) return;
    val = val + root.value;
    if(isleft && root.left != null && (val + root.left.value) < sum) {
      root.left = null;
    }
    if(!isleft && root.right != null && val + root.right.value < sum) {
      root.right = null;
    }
    removeNodeIfSumIsGreater(root.left, sum, val, true);
    removeNodeIfSumIsGreater(root.right, sum, val, false);
  }
  
  public static void main(String[] args) {
    Node left1 = new Node(1, null, null);
    Node right1 = new Node(2, null, null);
    Node rootLeft = new Node(3, left1, right1);
    
    Node left2 = new Node(5, null, null);
    Node right2 = new Node(7, null, null);
    Node rootRight = new Node(6, left2, right2);
    
    Node root = new Node(4, rootLeft, rootRight);
    
    BinaryTree tree = new BinaryTree(root);
//    tree.preOrderRecursive(root);
//    System.out.println("\n");
//    tree.inOrderRecursive(root);
//    System.out.println("\n");
//    tree.postOrderRecursive(root);
//    System.out.println("\n");
//    
//    System.out.println(tree.size(root));
//    System.out.println(tree.height(root));
//    ArrayList<Integer> path = new ArrayList<Integer>();
//    tree.printAllNodeToLeafPaths(root, path, 0);
//  tree.printLevelOrder(root);
//    System.out.println(tree.countLeaves(root));
//    tree.levelOrderSpiral(root);
//    tree.printList(tree.treeToList(root));
//    System.out.println(tree.childrenSumProperty(root));
 //     tree.convertChildrenSumProperty(root);
  //    tree.printLevelOrder(root);
  //  System.out.println(tree.diameter(root));
 //   System.out.println(tree.LCA(root, new Node(1, null, null), new Node(3, null, null)));
 //   System.out.println(tree.LCABinaryTree(root, new Node(1, null, null), new Node(7, null, null)));
 //   tree.printAncestors(root, new ArrayList<Node>(), 0, 3);
  //  System.out.println(tree.getLevel(root, new Node(6, null, null), 1));
 // System.out.println(tree.isCousin(root, new Node(2, null, null), new Node(3, null, null)));
//    ArrayList<ArrayList<Node>> lists = new ArrayList<ArrayList<Node>>();
//    tree.createLeveledLinkdedList(lists, root, 0);
//    for (ArrayList<Node> arrayList : lists) {
//      System.out.println(Arrays.toString(arrayList.toArray()));
//    }
//    int[] arr = new int[7];
//    tree.isBST(arr, root);
//    System.out.println(Arrays.toString(arr));
 //   System.out.println(tree.isBST(root, null, null));
  //  tree.findPathsForSum(root, new int[tree.height(root)], 15, 0);
  //  System.out.println(tree.maxPathSum(root, 0));
  //  System.out.println(tree.getNextForGivenNode(root, rootLeft).value);
    tree.removeNodeIfSumIsGreater(root, 9, 0, false);
  }
}
