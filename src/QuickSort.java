import java.util.concurrent.*;

class QuickSort<E extends Comparable<E>> {
   final class QuickSortAction extends RecursiveAction {
      E[] arr;
      int i;
      int k;

      public QuickSortAction() {
         this(null, -1, 0);
      }

      public QuickSortAction(E[] array, int start, int end) {
         arr = array;
         i = start;
         k = end;
      }

      public void compute() {
         int divide = -1;
         if (i >= k) {
            return;
         }

         divide = partition(arr, i, k);
         RecursiveAction action1 = new QuickSortAction(arr, i, divide);
         RecursiveAction action2 = new QuickSortAction(arr, divide + 1, k);
         invokeAll(action1, action2);
      }
   }

   public void sort(E[] array) {
      if (array == null) {
         return;
      }
      RecursiveAction action = new QuickSortAction(array, 0, array.length - 1);
      ForkJoinPool pool = new ForkJoinPool();
      pool.invoke(action);
   }

   public int partition(E[] array, int start, int end) {
      int low = start;
      int high = end;
      int mid = start + (end - start) / 2;
      E pivot = array[mid];
      boolean done = false;
      while (!done) {
         // find the index that violates the properties
         // low elements < pivot
         // cannot have equal sign because unnecessary swaps may cause infinite loop
         while (array[low].compareTo(pivot) < 0) {
            low++;
         }
         // high element > pivot
         while (array[high].compareTo(pivot) > 0) {
            high--;
         }

         if (low >= high) {
            done = true;
         } else {
            // swap
            E temp = array[low];
            array[low] = array[high];
            array[high] = temp;
            low++;
            high--;
         }
      }
      return high;
   }

   /*
    * Quicksort in non-parallel programming
    */
   public static <E extends Comparable<E>> void quickSort_basic(E[] list) {
      quickSort_basic(list, 0, list.length - 1);
   }

   private static <E extends Comparable<E>> void quickSort_basic(E[] list, int first, int last) {
      if (last > first) {
         int pivotIndex = partition_basic(list, first, last);
         quickSort_basic(list, first, pivotIndex);
         quickSort_basic(list, pivotIndex + 1, last);
      }
   }

   private static <E extends Comparable<E>> int partition_basic(E[] list, int first, int last) {

      int low = first;
      int high = last;
      int mid = first + (last - first) / 2;
      E pivot = list[mid];
      boolean done = false;
      while (!done) {
         while (list[low].compareTo(pivot) < 0) {
            low++;
         }

         while (list[high].compareTo(pivot) > 0) {
            high--;
         }

         if (high > low) {
            // swap
            E temp = list[high];
            list[high] = list[low];
            list[low] = temp;
            low++;
            high--;
         } else {
            done = true;
         }
      }
      return high;
   }

}