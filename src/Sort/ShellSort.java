package Sort;

/**
 * @author silenceren
 * @create 2019/4/12-14:58
 */
public class ShellSort {
    static void shellSort(int[] arr){
        for (int gap = arr.length; gap > 0; gap/=2) {  //增量序列gap
            for (int i = gap; i < arr.length; i++) {   //从数组gap个元素开始
                                                       //每个元素与自己组内元素做直接插入排序
                int key = arr[i], j;
                for (j = i - gap; j >= 0 && key < arr[j] ; j -= gap) {
                    arr[j + gap] = arr[j];
                }
                arr[j+gap] = key;
            }
        }
    }
    public static void main(String[] args) {
        int[] arr = {4,2,6,9,8,1,3,10,5,7};
        shellSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
