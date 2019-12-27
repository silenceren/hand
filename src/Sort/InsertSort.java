package Sort;

import static Sort.SelectSort.swap;

/**
 * @author silenceren
 * @create 2019/4/12-12:31
 */
public class InsertSort {
    static void insertSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 && arr[j] < arr[j-1]; j--) {
                swap(arr,j,j-1);
            }
        }
    }
    static void binaryInsertSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int L = 0, R = i - 1;
            while( L <= R){
                int mid = L + (R - L)/2;
                if(arr[mid] > key)
                    R = mid -1;
                else
                    L = mid + 1;
            } //二分结束之后　L = 刚好大于key(不是等于)的那个位置
            for (int j = i - 1; j >= L; j--)
                arr[j+1] = arr[j];
            arr[L] = key;
        }
    }
    public static void main(String[] args) {
        int[] arr = {4,2,6,9,8,1,3,10,5,7};
        binaryInsertSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
