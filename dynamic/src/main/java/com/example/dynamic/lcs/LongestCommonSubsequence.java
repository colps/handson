package com.example.dynamic.lcs;

public class LongestCommonSubsequence {

    /**
     * lcs(1..m, 1..n) will be 0 if n = 0 or m = 0.
     * lcs(1..n) can only depend on lcs(1..m-1, 1..n) OR lcs(1..m, 1..n-1) OR
     * lcs(1..m-1, 1..n-1)
     *
     * We are looking for a common subsequence. Adding the condition for common
     * lcs(1..m, 1..n) is given by
     * 1 + lcs(1..m-1, 1..n-1) if a[m] equals b[n]
     * or else
     * if the values are not equal lcs(1..m, 1..n) then
     * lcs will be given by lcs(1..m-1, 1..n) OR lcs(1..m, 1..n-1)
     *
     * Since we need the longest subsequence, we add the max condition
     * lcs(1..m, 1..n) is given by
     * 1 + lcs(1..m-1, 1..n-1) if a[m] equals b[n]
     * or else
     * if the values are not equal lcs(1..m, 1..n) then
     * lcs will be given by max(lcs(1..m-1, 1..n) OR lcs(1..m, 1..n-1))
     *
     */
    public static int recursive(char[] a, char[] b, int m , int n) {
        if(n == 0 || m == 0){
            return 0;
        }

        if(a[m-1] == b[n-1]) {
            return 1 + recursive(a, b, m-1, n-1);
        } else {
            return Math.max(
                    recursive(a, b, m-1 , n),
                    recursive(a, b, m, n-1)
            );
        }
    }


    /**
     * II) Define subproblems & analyse running time -
     * We look at prefixes of the sequences. For each pair of prefixes, should we
     * include the pair as part of lcs
     * For DP ,
     * # Number of subproblems = m*n
     * # Time/subproblem = O(1)
     * # Complexity = O(m*n)
     *
     * III) Define what will be guess part
     * We try for all combination of pairs and calculate the longest value
     *
     * IV) Define structure for memoization
     * A 2D array m * n will be able to
     *
     * V) Create the dependency graph
     *  Dependencies between sub problems. Except for the base cases, every
     *	recursive subproblem depends on other subproblems which ones? Draw a picture of
     *	your data structure, pick a generic element, and draw arrows from each of the other
     *	elements it depends on, In this case this will be tree
     *
     * VI) Find the Topological order to solve the problem
     * Since lcs(1..m, 1..n) depends on lcs(1..m-1, 1..n) OR lcs(1..m, 1..n-1) OR
     * lcs(1..m-1, 1..n-1), the topological order will be
     *
     * for m in range 0..M          // 0 represents empty sequence
     *  for n in range 0..N
     *
     * VI) Solution - Looking at prefixes of the input
     * lcs(0..m, 0..n) = {
     *              0                   if m = 0 or n = 0
     *              1 + lcs(m-1, n-1)   if(a[m] = b[n])
     *              max {
     *                  lcs(m-1, n),    otherwise
     *                  lcs(m, n-1)
     *              }
     *          }
     *
     */
    public static int dynamic(char[] a, char[] b) {
        int[][] lcsMemo = new int[a.length+1][b.length+1];
        for(int m = 0; m <= a.length; m++) {
            for(int n = 0; n <= b.length; n++) {
                if(m == 0 || n == 0){
                    lcsMemo[m][n] = 0;
                    continue;
                }
                if(a[m-1] == b[n-1]){
                    lcsMemo[m][n] = 1 + lcsMemo[m-1][n-1];
                } else {
                    lcsMemo[m][n] = Math.max(
                            lcsMemo[m-1][n], lcsMemo[m][n-1]
                    );
                }

            }
        }
        return lcsMemo[a.length][b.length];
    }

    public static void main(String[] args) {
        String a = "AGGTAB";
        String b = "GXTXAYB";
        System.out.println("recursive: " + recursive(a.toCharArray(), b.toCharArray(), a.length(), b.length()));
        System.out.println("DP: " + dynamic(a.toCharArray(), b.toCharArray()));
    }

}
