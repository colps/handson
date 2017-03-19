package com.example.dynamic.coin;

public class CoinChangeTotal {

    /**
     * change(S, 1..n) where S is the amount to be changed and i is the current
     * denomination
     *
     * If the amount S is 0, the amount can be changed in 1 way. So return 1. If
     * S < 0 return 0
     *
     * change(S, 1..n) can only depend on change(S, 1..n-1) the coin is not selected
     * or change(S-a[n], 1..n) the coin is selected
     *
     */
    public static int recursive(int S, int n, int[]denomination) {
        if(S == 0){
            return 1;
        }
        if(S < 0){
            return 0;
        }
        if(n < 0 && S > 0) {
            return 0;
        }
        return recursive(S, n-1, denomination) + recursive(S - denomination[n], n, denomination);
    }

    /**
     * II) Define subproblems & analyse running time -
     * We look at suffixes of denominations, should we include the current denomination in the sum.
     * For DP,
     * # Number of subproblems = nS
     * # time/subproblem = O(1)
     * # running time = nS * O(1) -> O(nS) pseudo polynomial
     *
     * III) Decide the guess part
     * We guess for all situations, with current denomination included and not included
     *
     * IV) Define structure for memoization
     * A 2 dimensional array of i,S should help in memoizing the solutions
     *
     * V) Create the dependency graph
     * Identify dependencies between subproblems. Except for the base cases, every
     * recursive subproblem depends on other subproblems which ones? Draw a picture of
     * your data structure, pick a generic element, and draw arrows from each of the other
     * elements it depends on, In this case this will be tree
     *
     * VI) Find the Topological order to solve the problem
     * To calculate change(S, n) we need to know S-denomination[i] and i -1, So we look
     * at prefixes
     * for n in range (1..n)
     *      for s in range (1..S)
     *
     * VII) Solution
     * change(n, S) = {
     *                  1          if S = 0 or n == 0
     *                  change(n-1, S)          if S - value[n] < 0
     *                  change(n-1, S) + change(n, S - denomination[n])     // otherwise
     *              }
     */
    public static int dynamic(int amount, int[]denomination) {
        int [][] changeMemo = new int[denomination.length][amount+1];
        for(int n = 0; n <= denomination.length - 1; n++){
            for(int s = 0; s <= amount; s++ ){
                if(s == 0 || n == 0){
                    changeMemo[n][s] = 1;
                    continue;
                }
                if(s - denomination[n] < 0) {
                    changeMemo[n][s] = changeMemo[n-1][s];
                } else {
                    changeMemo[n][s] = changeMemo[n-1][s] + changeMemo[n][s - denomination[n]];
                }
            }
        }
        return changeMemo[denomination.length - 1][amount];
    }
    
    public static void main(String[] args) {
        int a[] = {1, 2, 3, 6};
        System.out.println("recursive: " + recursive(10, a.length -1, a));
        System.out.println("dynamic: " + dynamic(10, a));
    }

}
