import java.util.Arrays;


public class Sorting {
  
  public static int partition(int []a, int l, int h) {
    int pivot = (l + (h-l))/2;  
    int i = l, j = h;
    while(i <= j) {
      while(a[i] < a[pivot]) {
        i++;
      }
      while(a[j] > a[pivot]) {
        j--;
      }
      if (i <= j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        i++;
        j--;
      }
    }
    return i;
  }
  
  public static void quicksort(int[]a, int l, int h) {
    if(h <= l) return;
    int partition = partition(a, l, h);
    quicksort(a, l, partition-1);
    quicksort(a, partition+1, h);
  }
  
  //nth smallest element
  public static int quickSelect(int[]a, int l, int h, int n) {
    int partition = partition(a, l, h);
    if(partition == n) {
      return a[n-1];
    }
    if(n < partition) {
      return quickSelect(a, l, partition-1, n);
    } else {
      return quickSelect(a, partition+1, h, n);
    }
  }
  
  public static void main(String[] args) {
    int []a = new int[]{22,33,44,11,55 };
    quicksort(a, 0, a.length-1);
    System.out.println(Arrays.toString(a));
    System.out.println(quickSelect(a, 0, a.length-1, 4));
  }
}
