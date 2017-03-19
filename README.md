# handson
The repository has 2 modules dynamic, java8

### java8 
This module contains examples on new concepts in java8

### dynamic 
This module contains popular examples on Dynamic programming. I am using the principles suggested by Jeff Erickson for solving these
problems. I am providing his explanation below. The module is an effort to try and solve DP problems by applying these principles

### misc
This module contains some misc problems. LRU cache

1. In a nutshell, dynamic programming is recursion without repetition. Dynamic programming 
algorithms store the solutions of intermediate subproblems, often but not always in some kind of
array or table. Many algorithms students make the mistake of focusing on the table (because
tables are easy and familiar) instead of the much more important (and difficult) task of finding a
correct recurrence. As long as we memoize the correct recurrence, an explicit table isn’t really
necessary, but if the recursion is incorrect, nothing works
> Dynamic programming is not about filling in tables. It’s about smart recursion


2. Steps for DP

	1. Formulate the problem recursively. Write down a recursive formula or algorithm for the
	whole problem in terms of the answers to smaller subproblems. This is the hard part. It
	generally helps to think in terms of a recursive definition of the object you’re trying to
	construct. A complete recursive formulation has two parts:
		- Describe the precise function you want to evaluate, in coherent English. Without this
		specification, it is impossible, even in principle, to determine whether your solution is
		correct.
		- Give a formal recursive definition of that function.
	2. Build solutions to your recurrence from the bottom up. Write an algorithm that starts
	with the base cases of your recurrence and works its way up to the final solution, by
	considering intermediate subproblems in the correct order. This stage can be broken down
	into several smaller, relatively mechanical steps:
		- Identify the subproblems. What are all the different ways can your recursive
		algorithm call itself, starting with some initial input? For example, the argument to
		RecFibo is always an integer between 0 and n.
		- Analyze space and running time. The number of possible distinct subproblems
		determines the space complexity of your memoized algorithm. The running time of DP
		number of sub problems * time/sub problem (treat recurssion as constant time op)
		- Choose a data structure to memoize intermediate results. For most problems,
		each recursive subproblem can be identified by a few integers, so you can use a
		multidimensional array. For some problems, however, a more complicated data
		structure is required.
		- Identify dependencies between subproblems. Except for the base cases, every
		recursive subproblem depends on other subproblems—which ones? Draw a picture of
		your data structure, pick a generic element, and draw arrows from each of the other
		elements it depends on. Then formalize your picture.
		- Find a good evaluation order. Order the subproblems so that each subproblem
		comes after the subproblems it depends on. Typically, this means you should consider
		the base cases first, then the subproblems that depends only on base cases, and so on.
		More formally, the dependencies you identified in the previous step define a partial
		order over the subproblems; in this step, you need to find a linear extension of that
		partial order. Be careful!
		- Write down the algorithm. You know what order to consider the subproblems, and
		you know how to solve each subproblem. So do that! If your data structure is an array,
		this usually means writing a few nested for-loops around
