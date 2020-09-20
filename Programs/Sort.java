import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sort {

  // NOTE in place algorithm
  public static <T extends Comparable<T>> void insertionSort (List<T> unsorted) {
    for (T e:unsorted) { // sort every element, working left to right
      int j=unsorted.indexOf(e)-1; // element immediately before
      while (j>-1 && e.compareTo(unsorted.get(j))<0) { // compare to elements before until one is less than e
        unsorted.set(j+1, unsorted.get(j)); // shift element to right
        j--;
      }
      unsorted.set(j+1, e); // place e
    }
  }

  public static void insertionSortExplained (List<Integer> list) {
    System.out.printf("INSERTION SORT\nBefore - ");
    Printer.print(list);
    System.out.print("\n");

    for (Integer e:list) {
      System.out.printf("Sorting - %s - ", e);
      int j=list.indexOf(e)-1;
      while (j>-1 && e.compareTo(list.get(j))<0) {
        list.set(j+1, list.get(j));
        j--;
      }
      list.set(j+1, e);
      Printer.print(list);
      System.out.print("\n");
    }

    System.out.printf("After - ");
    Printer.print(list);
    System.out.print("\n");
  }

  public static void mergeSort(int arr[]) {
    sort(arr, 0, arr.length-1, false);
  }

  public static void mergeSort(List<Integer> list) {
    int[] array=list.stream().mapToInt(i->i).toArray();
    sort(array, 0, array.length-1, false);

    list.clear();
    for (int i=0; i<array.length; i++) {
      list.add(array[i]);
    }
  }

  // NOTE in place algorithm
  public static void mergeSortExplained (List<Integer> list) {
    System.out.printf("MERGE SORT\nBefore - ");
    Printer.print(list);
    System.out.printf("\n");

    int[] array=list.stream().mapToInt(i->i).toArray();
    sort(array, 0, array.length-1, true);

    // update list
    list.clear();
    for (int i=0; i<array.length; i++) {
      list.add(array[i]);
    }

    System.out.printf("\nAfter - ");
    Printer.print(list);
    System.out.printf("\n");
  }

  public static void sort(int arr[], int l, int r, Boolean explain) {
    if (l < r) {
        // Find the middle point
        int m = (l+r)/2;

        // Sort first and second halves
        if (explain) {
          System.out.printf("\nSPLIT ");
          Printer.print(arr,l,r+1);
          System.out.printf(" -> ");
          Printer.print(arr,l,m+1);
          System.out.printf(",");
          Printer.print(arr,m+1,r+1);
          System.out.println();
        }

        sort(arr, l, m, explain);
        sort(arr, m+1, r, explain);

        // Merge the sorted halves
        if (explain) {
          System.out.printf("\nMERGE ");
          Printer.print(arr,l,m+1);
          System.out.printf(" & ");
          Printer.print(arr,m+1,r+1);
          System.out.println();
        }
        merge(arr, l, m, r, explain);
        if (explain) {
          System.out.printf("-> ");
          Printer.print(arr,l,r+1);
          System.out.println();
        }
    }
  }

  public static void merge(int arr[], int l, int m, int r, Boolean explain) {
      // Find sizes of two subarrays to be merged
      int n1 = m - l + 1;
      int n2 = r - m;
      /* Create temp arrays */
      int L[] = new int [n1];
      int R[] = new int [n2];
      /*Copy data to temp arrays*/
      for (int i=0; i<n1; ++i)
          L[i] = arr[l + i];
      for (int j=0; j<n2; ++j)
          R[j] = arr[m + 1+ j];
      /* Merge the temp arrays */
      // Initial indexes of first and second subarrays
      int i = 0, j = 0;
      // Initial index of merged subarry array
      int k = l;
      while (i < n1 && j < n2) {
          if (explain) {System.out.printf("   ADD %d || %d\n", L[i], R[j]);}
          if (L[i] <= R[j]) {
              arr[k] = L[i];
              i++;
          } else {
              arr[k] = R[j];
              j++;
          }
          k++;
      }
      /* Copy remaining elements of L[] if any */
      while (i < n1) {
        if (explain) {System.out.printf("   ADD %d\n", L[i]);}
        arr[k] = L[i];
        i++;
        k++;
      }
      /* Copy remaining elements of R[] if any */
      while (j < n2) {
        if (explain) {System.out.printf("   ADD %d\n", R[j]);}
        arr[k] = R[j];
        j++;
        k++;
      }
  }

}
