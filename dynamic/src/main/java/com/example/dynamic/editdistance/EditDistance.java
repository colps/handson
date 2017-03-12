package com.example.dynamic.editdistance;


public class EditDistance {

    /**
     * I)  Edit distance of empty strings is 0
     * If one of the strings is empty, the edit distance will be equal to the length of the other
     *
     * If both strings are not empty then
     * Considering the last element a[m-1] and b[n-1] element alone,
     *      if a[m-1] = b[n-1] edit(a[1..m], b[1..n]) will be
     *          edit(a[1..m-1], b[1..n-1])
     *      else
     *          cost(insertion/deletion/modification of element at i) + edit(a[1..?], b[1..?])
     *
     *
     * We look at minimum distance, Adding the minimum function
     * if both strings are not empty
     *       if a[m-1] = b[n-1] edit(a[1..m], b[1..n]) will be
     *          edit(a[1..m-1], b[1..n-1])
     *      else if they are not equal edit(a[1..m], b[1..n]) will be minimum of
     *          cost 1 of insertion at 1 + edit(a[1..m], b[1..n-1]) OR
     *          cost 1 of deletion at 1 + edit(a[1..m-1], b[1..n]) OR
     *          cost 1 of modification at 1 + edit(a[1..m-1], b[1..n-1])
     *
     */
    public static int recursive (char[] source, char[]target, int i, int j) {
        if(i == 0) {
            return j;
        }
        if(j == 0){
            return i;
        }
        if(source[i] == target[j]){
            return recursive(source, target, i-1, j-1);
        } else {
            int min = Math.min(
                    1 + recursive(source, target, i, j-1) , // insert
                    1 + recursive(source, target, i - 1, j)   // delete
            );

            return Math.min(
                    min,
                    1 + recursive(source, target, i - 1, j-1) // update
            );
        }
    }


    /**
     * II) We look at prefixes of the source[i] and target[j] strings, which operation gives the minimum distance
     * For DP ,
     * # Number of subproblems = m*n
     * # Time/subproblem = O(1)
     * # Complexity = O(m*n)
     *
     * III) We calculate value for all operations and select the least value
     *
     * IV) A 2 dimensional array n *n for start and end index will help memoize
     *
     * V) Dependencies between sub problems. Except for the base cases, every
     *	  recursive subproblem depends on other subproblems which ones? Draw a picture of
     *	  your data structure, pick a generic element, and draw arrows from each of the other
     *	  elements it depends on, In this case this will be tree
     *
     * VI) Topological order. Here, to compute (i,j) we need to compute (i, j+1) (suffix) ,
     *      So order will be
     *     for i in range(0:m)
     *       for j in range(0:n)
     *
     * VII) Solution - We look at prefixes of the sequences
     * For each prefixes char of i source and j of target,
     *  edit(i,j) = {
     *                  i       if (j == 0)  // Here 0 indicates a string of length 0
     *                  j       if (i == 0)  // Here 0 indicates a string of length 0
     *                  min {
     *                      if a[i] == a[j]{
     *                          edit(i-1, j-1)
     *                      } else {
     *                          1 + edit(i, j-1)      // insert
     *                          1 + edit(i-1, j)      // delete
     *                          1 + edit(i-1, j-1)    // modify
     *                      }
     *                  }
     *              }
     *
     *
     */

    public static int dynamic (char[] source, char[] target) {
        int[][] edMemo = new int[source.length + 1][target.length + 1];
        for(int i = 0; i <= source.length; i ++) {
            for (int j = 0; j <= target.length; j++) {
                if(i == 0) {
                    edMemo[i][j] = j;
                    continue;
                }
                if(j == 0) {
                    edMemo[i][j] = i;
                    continue;
                }

                if(source[i-1] == target[j-1]) {
                   edMemo[i][j] = edMemo[i-1][j-1];
                } else {
                    int min = Math.min(1 + edMemo[i-1][j], 1 + edMemo[i][j-1]);
                    edMemo[i][j] = Math.min(min, 1 + edMemo[i-1][j-1]);
                }
            }
        }
        return edMemo[source.length][target.length];
    }

    public static void main(String[] args) {
        String source = "ALTRUISTIC";
        String target = "ALGORITHM";
        System.out.println("recursive: " + recursive(source.toCharArray(), target.toCharArray(), source.length()-1, target.length()-1));
        System.out.println("DP: " + dynamic(source.toCharArray(), target.toCharArray()));

    }
}
