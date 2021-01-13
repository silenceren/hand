package Sort;

import static Sort.SelectSort.swap;

public class QuickSort {
    static void quickSort(int arr[]){
        if (arr == null || arr.length <= 1)
            return ;
        quickProcess(arr,0,arr.length - 1);
    }

    static void quickProcess(int[] arr, int L,int R){
        if (L > R)
            return ;
        int p = partition(arr,L,R);
        quickProcess(arr,L,p - 1);
        quickProcess(arr,p + 1,R);
    }



    /**
     * 对arr[l...r]部分进行partition操作
     * 返回p, 使得arr[L...p-1] < arr[p] ; arr[p+1...R] > arr[p]
     */

    static int partition(int[] arr,int L,int R){
        //选arr【L】为pivot（中心点）
        int key = arr[L];
        int pivot = L;
        for(int i = L + 1;i <= R;i++){
            if(arr[i] < key)
                swap(arr,i,++pivot);
        }
        swap(arr,pivot,L);
        return pivot;
    }

    public static void quickSort(int arr[], int L, int R){
        if (L >= R) {
            return ;
        }
        int left = L, right = R;
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            if (left < right) {
                arr[left] = arr[right];
            }
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            if (left < right) {
                arr[right] = arr[left];
            }
            if (left >= right) {
                arr[left] = pivot;
            }
        }
        
        quickSort(arr, L, right - 1);
        quickSort(arr, right + 1, R);

    }
}
