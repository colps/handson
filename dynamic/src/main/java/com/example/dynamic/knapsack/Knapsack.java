package com.example.dynamic.knapsack;


public class Knapsack {

    /**
     * if all items have been considered or no capacity left S = 0 return 0
     *
     * sack(1..n, S) where 1..n is the items list and S is the remaining capacity
     * sack(1, S) can only be sack(2..n, S) meaning item 1 is not selected OR
     * value[1] + sack(2..n, S - weight[1]) meaning item 1 is selected
     *
     * We are looking for the maximum value, So we add the condition
     * sack(n,S) - sack with remaining capacity S and current item n
     *
     * if no items are left n == items.length or there is no capacity left S == 0 return 0
     * if remaining capacity is less than weight[n] sack(n,S) = sack(n+1, S)
     * else sack(n, S) will be equal to sack(n+1, S) OR value[n] + sack(n, S - weight[n])
     * whichever is maximum
     *
     */
    public static int recursive (int[] value, int[] weight, int n, int S) {
        if(n == value.length || S == 0){
            return 0;
        }
        if(S < weight[n]) {
            return recursive(value, weight, n+1, S);
        } else {
            return Math.max(
                    recursive(value, weight, n+1, S),  // item i is not selected
                    value[n] + recursive(value, weight, n+1, S - weight[n])  // item is selected
            );
        }
    }

    /**
     * II) Define subproblems & analyse running time -
     * We look at suffixes of items. For item n and capacity S we have to find,  if we should select item n
     * so as to maximize value
     * # Number of subproblems = n * S
     * # Time/subproblem = O(1)
     * # Complexity = O(nS) (pseudo polynomial time)
     *
     * III) Define what will be guess part
     * We calculate the max value for all conditions
     *
     * IV) Define structure for memoization
     * A 2 dimensional array n*S for start and end index will help memoize
     *
     * V) Create the dependency graph
     *    Dependencies between sub problems. Except for the base cases, every
     *	  recursive subproblem depends on other subproblems which ones? Draw a picture of
     *	  your data structure, pick a generic element, and draw arrows from each of the other
     *	  elements it depends on, In this case this will be tree
     *
     * VI)  Find the Topological order to solve the problem
     * Topological order. From the recurrence above sack(n,S) depends on sack(n+1, S) or sack(n+1, S-w[n])
     * So topological order will be
     * for n in range(n..1)
     *      for S in range(S..0)
     *
     * VII) We consider suffixes of items so For each suffix i in items list[1..n]
     * sack(n,S) = {
     *                  0               if  S = 0
     *                  sack(n+1, S)    if  S < weight[n]
     *                  max {
     *                      sack(n+1, S),       // item n is not selected
     *                      value[n] + sack(n+1, S-weight[n]) // item n is selected
     *                  }
     *          }
     *
     */
    public static int dynamic (int[] value, int[] weight, int capacity) {
        int sackMemo[][] = new int[value.length+1][capacity+1];
        for(int n = value.length-1; n >= 0; n-- ){
            for(int s = 0; s <= capacity; s++){
                if(s == 0){
                    sackMemo[n][s] = 0;
                    continue;
                }
                if(s < weight[n]){
                    sackMemo[n][s] = sackMemo[n+1][s];
                } else {
                    sackMemo[n][s] = Math.max(
                            sackMemo[n+1][s],
                            value[n] + sackMemo[n+1][s-weight[n]]
                    );
                }
            }
        }
        return sackMemo[0][50];
    }

    public static void main(String[] args) {
        int value[] = {60, 100, 120};
        int weight[] = {10, 20, 30};
        System.out.println("recursive: " + recursive(value, weight, 0, 50));
        System.out.println("dynamic: " + dynamic(value, weight,50));
    }
}
