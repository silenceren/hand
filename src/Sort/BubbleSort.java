package Sort;

import static Sort.SelectSort.swap;

/**
 * @author silenceren
 * @create 2019/4/12-11:15
 */

public class BubbleSort {

    static void bubbleSort(int[] arr){
        for (int end = arr.length - 1; end > 0; end--) {
            int border = 0;
            for (int i = 0; i < end; i++) {
                if(arr[i] > arr[i+1]){
                    swap(arr, i, i+1);
                    border = i + 1;
                }
            }
            end = border;
        }
    }

    public static void main(String[] args) {
        int[] arr ={1,3,6,4,2,7,5};
        bubbleSort(arr);
        for (int i = 0; i<arr.length;i++) {
            System.out.println(arr[i]);
        }
    }
}
