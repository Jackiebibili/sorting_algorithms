public class ShellSort {
   public static <E extends Comparable<E>> void sort(E[] array) {
      if (array == null || array.length <= 0) {
         return;
      }
      int gap = getFirstGapNum(array.length);
      while (gap != 0) {
         for (int i = 0; i < gap; i++) {
            insertionSortVariated(array, gap, i);
         }
         gap = getNextGapNum(gap);
      }
   }

   public static <E extends Comparable<E>> void insertionSortVariated(E[] array, int gap, int start) {
      for (int i = start; i < array.length; i = i + gap) {
         // for each item in the interleaved list
         E cur = array[i];
         int j;
         for (j = i; j - gap >= start && cur.compareTo(array[j - gap]) < 0; j = j - gap) {
            // swap back 1 item
            array[j] = array[j - gap];
         }
         array[j] = cur;
      }
   }

   private static int getFirstGapNum(int size) {
      // get the nearest 2^p - 1 to the array size
      return (int) (Math.pow(2, (int) (Math.log10(size) / Math.log10(2)))) - 1;
   }

   private static int getNextGapNum(int current) {
      // get the next number of 2^p - 1
      return ((current + 1) / 2) - 1;
   }
}
