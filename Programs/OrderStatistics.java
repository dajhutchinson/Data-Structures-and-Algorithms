import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Map;

class OrderStatistics {

  // right=array length
  public static int partition(int[] arr, int left, int right) {
    Random r=new Random();
    int pivot=r.nextInt(right)+left;
    int x=arr[pivot];

    swap(arr, pivot, right-1, false);

    int i=left;
    for (int j=left; j<right; j++) {
      if (arr[j]<=x) {
        swap(arr, i, j, false);
        i++;
    }}

    return i-1;
  }

  public static int partition(List<Integer> list, int left, int right, Boolean explain) {
    Random r=new Random();
    int pivot=r.nextInt(right-left+1)+left;
    int x=list.get(pivot);
    if (explain) {System.out.printf("Pivot=%d\n", x);}

    swap(list, pivot, right, explain);

    int i=left;
    for (int j=left; j<right+1; j++) {
      if (list.get(j)<=x) {
        swap(list, i, j, explain);
        i++;
    }}
    return i-1;
  }

  public static int partition(List<Integer> list, int left, int right, int x) {
    int pivot=list.indexOf(x);
    swap(list, pivot, right, false);

    int i=left;
    for (int j=left; j<right+1; j++) {
      if (list.get(j)<=x) {
        swap(list, i, j, false);
        i++;
    }}
    return i-1;
  }

  public static int partitionExplained(int[] arr, int left, int right) {
    Random r=new Random();
    int pivot=r.nextInt(right)+left;
    int x=arr[pivot];
    System.out.printf("PARTITION\nBefore - ");
    Printer.print(arr);
    System.out.printf("\nPivot  - %d\nValue  - %d\n", pivot, x);

    swap(arr, pivot, right-1, true);

    int i=left;
    for (int j=left; j<right; j++) {
      if (arr[j]<=x) {
        swap(arr, i, j, false);
        i++;
    }}
    System.out.printf("After  - ");
    Printer.print(arr);
    System.out.printf("\nReturned {i-%d}", i-1);

    return i-1;
  }

  // swap elements at index x & y in array arr
  private static void swap(int[] arr, int x, int y, Boolean explain) {
    if (explain) {System.out.printf("SWAP {INDICES %d->%d VALUES %d->%d}\n     ", x, y, arr[x], arr[y]);}
    int z=arr[y]; // temp
    arr[y]=arr[x];
    arr[x]=z;
    if (explain) {
      Printer.print(arr);
      System.out.println();
    }
  }

  private static void swap(List<Integer> list, int x, int y, Boolean explain) {
    if (explain) {System.out.printf("SWAP {INDICES %d->%d VALUES %d->%d}\n", x, y, list.get(x), list.get(y));}
    Integer z=list.get(x);
    list.set(x, list.get(y));
    list.set(y,z);
  }

  // right = last index, i = zero indexed
  public static Integer randomSelect(List<Integer> list, int left, int right, int i) {
    if (left==right) {
      return list.get(right);
    }

    int q=partition(list, left, right, false);

    // target is pivot
    if (i==q) {return list.get(i);}

    // target in left partition
    if (q>i) {return randomSelect(list, left, q-1, i);}

    // target in right partition
    return randomSelect(list, q+1, right, i);
  }

  // right = last index, i = zero indexed
  public static Integer randomSelectExplained(List<Integer> list, int left, int right, int i) {
    System.out.print("\nRANDOM-SELECT(");
    Printer.print(list);
    System.out.printf(", %d, %d, %d)\n",left,right,i);

    if (left==right) {
      System.out.printf("%d==%d\nDONE\n", left, right);
      return list.get(right);
    }
    System.out.printf("%d!=%d\nPARTION(", left, right);
    Printer.print(list);
    System.out.printf(",%d,%d)\n", left, right);

    int q=partition(list, left, right, true);
    System.out.printf("Pivot Index=%d\nPartioned List-", q);
    Printer.print(list);
    System.out.println();

    if (i==q) {
      System.out.println("Pivot is Target.\nDONE");
      return list.get(i);
    }

    // target to left of partition
    if (q>i) {
      System.out.print("Target in left partition -");
      Printer.print(list, left, q-1);
      System.out.println();
      return randomSelectExplained(list, left, q-1, i);
    }

    // target to right of partition
    System.out.print("Target in right partition - ");
    Printer.print(list, q+1, right);
    System.out.println();
    return randomSelectExplained(list, q+1, right, i);
  }

  public static Integer bfprtSelect(List<Integer> list, int left, int right, int i) {

    if (left==right) {return list.get(left);}

    List<List<Integer>> groups=split(list, left, right, 5, false);

    List<Integer> medians=groupMedians(groups);
    Sort.mergeSort(medians);
    int median=medians.get(medians.size()/2);

    int k=partition(list, left, right, median);
    if (i==k) {
      return median;
    }
    if (i<k)  {
      return bfprtSelect(list, left, k-1, i);
    }
    return bfprtSelect(list, k+1, right, i);
  }

  public static Integer bfprtSelectExplained(List<Integer> list, int left, int right, int i) {
    System.out.print("\nNEW PASS (");
    Printer.print(list);
    System.out.printf(", %d, %d, %d)\n", left, right, i);

    if (left==right) {return list.get(left);}

    List<List<Integer>> groups=split(list, left, right, 5, true);

    List<Integer> medians=groupMedians(groups);
    Sort.mergeSort(medians);
    int median=medians.get(medians.size()/2);

    System.out.print("medians=");
    Printer.print(medians);
    System.out.printf("\nmedian=%d\n", median);

    int k=partition(list, left, right, median);
    System.out.printf("partition around median");
    Printer.print(list);
    System.out.printf("\nPivot index=%d\n", k);
    if (i==k) {
      System.out.println("Pivot is target.\n");
      return median;
    }
    if (i<k)  {
      System.out.println("Target in Left Partition.\n");
      return bfprtSelectExplained(list, left, k-1, i);
    }
    System.out.println("Target in Right Partition.\n");
    return bfprtSelectExplained(list, k+1, right, i);
  }

  private static List<List<Integer>> split(List<Integer> list, int left, int right, int n, Boolean explain) {
    if (explain) {
      System.out.print("split(");
      Printer.print(list);
      System.out.printf(",%d)\n", n);
    }

    List<List<Integer>> groups=new ArrayList<>();

    int i=left;
    while (i+n<=right) {
      List<Integer> group=new ArrayList<>();
      for (int j=0; j<n; j++) {
        group.add(list.get(i));
        i++;
      }
      groups.add(group);
      if (explain) {Printer.print(group);System.out.println();}
    }

    int d=right-i;
    if (d!=0) {
      List<Integer> last=new ArrayList<>();
      for (int j=0; j<d; j++) {
        last.add(list.get(i));
        i++;
      }
      groups.add(last);
      if (explain) {Printer.print(last);System.out.println();}
    }

    return groups;
  }

  private static List<Integer> groupMedians(List<List<Integer>> groups) {
    List<Integer> medians=new ArrayList<>();
    for (List<Integer> group:groups) {
      Sort.mergeSort(group);
      Integer median=group.get(group.size()/2);
      medians.add(median);
    }
    return medians;
  }

}
