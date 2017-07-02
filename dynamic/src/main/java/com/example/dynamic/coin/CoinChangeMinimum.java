package com.example.dynamic.coin;

public class CoinChangeMinimum {

    /**
     *  For change(1..n,S), if s = 0 minimum value is Integer.MAX_VALUE. You need 0 coins to
     *  as change for value s. ALso, only one coin is needed if S = coin[n]
     *
     *  change(1..n, S) will depend on 2 cases coin n is selected for the change OR
     *  coin n is not selected. So change(1..n, S) will depend on change(1..n, S-a[n])
     *  or change(1..n-1, S)
     *
     *  We want the minimum number of coins so we add the minimum condition
     *  change(1..n, S) = min {
     *                          1 + change(1..n, S-a[n]),
     *                          change(1..n-1, S)
     *                      }
     *
     */
    public static int recursive(int n, int[] coins, int s) {
        if(s <= 0 || n < 0) {
            return Integer.MAX_VALUE;
        }
        if(s == coins[n]) {
            return 1;
        }

        int res = recursive(n, coins, s-coins[n]);
        // Add one for the selected coin only if result is not equal to Integer.MAX_VALUE
        if(res != Integer.MAX_VALUE){
            res++;
        }
        res = Math.min(
                recursive(n-1, coins, s),
                res
        );
        return res;
    }


    /**
     * II) Sub problems -
     * We look at prefixes of coins. For each prefix,we decide if the coin will be
     * included in the change or not
     * # Number of subproblems = nS
     * # time/subproblem = O(1)
     * # running time = nS * O(1) -> O(nS) pseudo polynomial
     *
     * III) Decide the guess part
     * We need to find the minimum number of coins. So we guess for all coins and select the
     * minimum value
     *
     * IV) Define structure for memoization
     * A 2 dimensional array of n,S should help in memoizing the solutions
     *
     * V) Create the dependency graph
     * Identify dependencies between subproblems. Except for the base cases, every
     * recursive subproblem depends on other subproblems which ones? Draw a picture of
     * your data structure, pick a generic element, and draw arrows from each of the other
     * elements it depends on, In this case this will be tree
     *
     * VI)Find the Topological order to solve the problem
     * We look at  prefixes of n, S -> n-1, s-a[n].
     * for s in range 0..s
     *    for n in range 1..n
     *
     * VII) Solution
     * change(n,s) = {
     *                  Integer.MAX_VALUE               if s = 0 or n = 0 or s < a[n]. Here s = 0 indicates sum is 0
     *                                                  and n = 0 indicates coin value as 0
     *                  1                               if s = a[n]
     *                  min {                           otherwise
     *                      change(n-1, s)              // not selected
     *                      1 + change(n, s-a[n])       // selected
     *                  }
     *              }
     *
     */
    public static int dynamic(int[] coins, int amount) {
        int [][] changeMemo = new int[coins.length + 1][amount+1];
        for(int n = 0; n <= coins.length; n++) {
            for (int s = 0; s <= amount; s++) {
                if(s == 0 || n == 0 || s < coins[n-1]) {
                    changeMemo[n][s] = Integer.MAX_VALUE;
                    continue;
                }
                if(coins[n - 1] == s){
                    changeMemo[n][s] = 1;
                    continue;
                }
                int res = changeMemo[n][s - coins[n-1]];
                if(res != Integer.MAX_VALUE){
                    res ++;
                }
                changeMemo[n][s] =  Math.min(res, changeMemo[n-1][s]);
            }
        }
        return changeMemo[coins.length][amount];
    }
    
    public static void main(String[] args) {
        int coins[] =  {9, 6, 5, 1};
        System.out.println("recursive :" + recursive(coins.length - 1, coins, 11));
        System.out.println("dynamic :" + dynamic(coins, 11));
    }

}
