package Sort;

import java.util.Arrays;

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
        int[] arr ={5,3,6,4,2,7,8};
        //bubbleSort(arr);
        quick(arr, 0 , arr.length - 1 );
//        QuickSort.quickSort(arr, 0, arr.length -1 );
        System.out.println(Arrays.toString(arr));
    }

    static void quick(int[] arr, int left, int right) {

        if (left >= right) {
            return ;
        }

        int pivot = arr[left];
        int l = left, r = right;
        while (l < r) {

            while (l < r && arr[r] >= pivot) {
                r--;
            }
            if (l < r) {
                arr[l] = arr[r];
            }
            while (l < r && arr[l] <= pivot) {
                l++;
            }
            if (l < r) {
                arr[r] = arr[l];
            }
            if ( l >= r) {
                arr[l] = pivot;
            }
        }
        quick(arr,r+1, right);
        quick(arr, left, r-1 );
    }

    static void quickk(int[] arr, int left, int right) {
        if (left >= right) {
            return ;
        }
        int L = left, R = right;
        int p = arr[L];

        while (L < R) {
            while (L < R && arr[R] >= p) {
                R--;
            }
            if (L < R) {
                arr[L] = arr[R];
            }
            while (L < R && arr[L] <= p) {
                L++;
            }
            if (L < R) {
                arr[R] = arr[L];
            }
            if (L >= R) {
                arr[R] = p;
            }
        }

        quickk(arr, left, R - 1);
        quickk(arr, R + 1, right);
    }
























}
