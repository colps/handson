package com.example.dynamic.lis;


public class LongestIncreasingSubsequence {

    /**
     *
     * LIS of a 0 length sequence is 0. The LIS of a[1..n] can only be either the
     * LIS of a[2..n] OR a[1] followed by LIS[2..n]
     *
     * Since we are looking at the longest subsequence
     * This LIS of a[1..n] is
     *      either LIS of a[2..n] OR
     *          a[1] followed by LIS[2..n] whichever is greater
     *
     * Now we need to add the condition for increasing subsequence
     *
     * if a[1] is < x, the LIS of a[1..n] with elements larger than x
     *      is LIS of a[2..n] with elements larger than x
     * Otherwise, the LIS of a[1..n] with elements larger than x
     *      is LIS of a[2..n] with elements larger than x OR
     *      the a[1] followed by LIS of a[2..n] with elements larger than A[1]
     *      whichever is longer
     *
     *
     * The longest increasing subsequence without restrictions can now be redefined
     * as the longest increasing subsequence with elements larger than −1.
     *
     *
     * The running time of this algorithm satisfies the recurrence
     * T(n) <= 2T(n − 1) + O(1), which as usual implies that T(n) = O(2n).
     *
     */

    public static int rec (int []a, int s, int e, int prev) {
        if(e-s == -1){
            return 0;
        } else {
            int max = 0;
            if(a[s] < prev){
                max = rec(a, s+1, e, a[s]);
            } else {
                int l = 1 + rec(a, s+1, e, a[s]);
                if(l > max) {
                    max = l;
                }
            }
            return max;
        }

    }

    /**
     *
     * Another implementation
     *
     */
    public static int recursive (int a[], int s, int e, int prev) {
        if(e-s == -1){
          return 0;
        } else {
            int max = recursive(a, s+1, e, prev);
            if(a[s] > prev){
                int l = 1 + recursive(a, s+1, e, a[s]);
                if(l > max) {
                    max = l;
                }
            }
            return max;
        }

    }

    public static void main(String[] args) {
        // {3, 1, 2, 5, 1, 6}
        // { 10, 22, 9, 33, 21, 50, 41, 60 }
        int []a = {12,2,10,4,5,25,20,30,50,19};
        int y =rec(a, 0,a.length -1, Integer.MIN_VALUE);
        int z =recursive(a, 0,a.length -1, Integer.MIN_VALUE);
        System.out.println(y);
        System.out.println(z);
    }
}
