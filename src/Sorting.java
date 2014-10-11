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
  
  public static void main(String[] args) {
    int []a = new int[]{2,3,4,1,5 };
    quicksort(a, 0, a.length-1);
    System.out.println(Arrays.toString(a));
  }
}
