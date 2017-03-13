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

    public static int recursive (int []a, int s, int e, int prev) {
        if(e-s == -1){
            return 0;
        } else {
            int max = 0;
            if(a[s] < prev){
                // a[1] is < x
                // LIS of a[2..n]
                max = recursive(a, s+1, e, a[s]);
            } else {
                // max(LIS of a[2..n], a[1] followed by LIS of a[2..n])
                int l1 = recursive(a, s+1, e, a[s]);
                int l2 = 1 + recursive(a, s+1, e, a[s]);
                int l = Math.max(l1, l2);
                if(l > max){
                    max = l;
                }
            }
            return max;
        }

    }

    /**
     *
     * Improved implementation
     *
     */
    public static int improvedRecursive (int a[], int s, int e, int prev) {
        if(e-s == -1){
          return 0;
        } else {
            // the line below is present in both situations in the previous implementation.
            // `So we move it outside
            int max = improvedRecursive(a, s+1, e, a[s]);
            if(a[s] > prev){
                int l = 1 + improvedRecursive(a, s+1, e, a[s]);
                if(l > max) {
                    max = l;
                }
            }
            return max;
        }

    }

    /**
     * II) Define subproblems & analyse running time  -
     * For each suffix of Is a[i], is a[i+1] part of longest subsequence
     * For DP ,
     * # Number of subproblems = n*n
     * # Time/subproblem = O(1)
     * # Complexity = O(n^2)
     *
     * III)  Define what will be guess part
     * Guess for all situations, if i+1 is part of LIS or not part of it
     *
     * IV)  Define structure for memoization
     * A 2 dimensional array n *n for start and end index will help memoize
     *
     * V) Create the dependency graph
     *    Dependencies between sub problems. Except for the base cases, every
     *	  recursive subproblem depends on other subproblems which ones? Draw a picture of
     *	  your data structure, pick a generic element, and draw arrows from each of the other
     *	  elements it depends on, In this case this will be tree
     *
     * VI)  Find the Topological order to solve the problem
     * Topological order. Here, to compute (i,j) we need to compute (i, j+1) (suffix) ,
     *      So order will be
     *     for i in range(n-1:0)
     *       for j in range(n-1:0)
     *
     * VII) Solution - We look at suffixes of the sequence
     *  If a[i] > a[j], a[j] will not be part of the LIS, LIS(i, j) = LIS(i, j+1)
     *  else
     *  a[j] may still may or may not be part of the LIS, (e.g. 2,8,3,4,5 here for i=0 and j=1
     *  a[i] < a[j], but still a[j] is not part of the LIS(2,3,4,5). So we look at LIS with and
     *  without j and select the greater value.
     *      LIS with j selected = 1 + LIS[j][j+1]  (j, j+1 because, j is selected now, So we
     *                                              need to find next element in LIS greater
     *                                              than a[j] so i (start of LIS) will be j)
     *      LIS with j rejected = LIS[i][j+1]
     *
     * LIS(i,j) ->  {
     *                  0                   if j > n
     *                  LIS(i, j+1)         if a[i] > a[j]
     *                  max(LIS(i, j+1), 1 + LIS(j, j+1))
     *              }
     *
     */
    public static int dynamicProgramming (int a[]) {
        int[][] lisMemo = new int[a.length][a.length+1];
        for(int i = a.length-1; i >= 0; i --){
            for(int j = a.length-1; j >= 0; j--){
                if(i > j){
                    continue;
                }
                if(i == j) {
                    lisMemo[i][j] = 1;
                    continue;
                }
                if(a[i] > a[j]) {
                    lisMemo[i][j] = lisMemo[i][j+1];
                } else {
                    lisMemo[i][j] = Math.max(lisMemo[i][j+1], 1 + lisMemo[j][j+1]);
                }

            }
        }
        return lisMemo[0][a.length-1];
    }

    public static void main(String[] args) {
        // {3, 1, 2, 5, 1, 6}
        // { 10, 22, 9, 33, 21, 50, 41, 60 }
        int []a = {3, 1, 2, 5, 1, 6};
                //{12,2,10,4,5,25,20,30,50,19};

        int x = dynamicProgramming(a);
        int y =recursive(a, 0,a.length -1, Integer.MIN_VALUE);
        int z =improvedRecursive(a, 0,a.length -1, Integer.MIN_VALUE);
        System.out.println("DP: " + z);
        System.out.println("rec: " + y);
        System.out.println("improved rec: " + z);
    }




}
