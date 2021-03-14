				PL RESOLUTION, BACKWARD CHAINING AND FORWARD CHAINING

There are three tasks: Forward Chaining, Backward Chaining, and Propositional Logic. The details of each of these are as follows:-

1) Forward Chaining: In this we are given a knowledge base which includes the rules and the facts. The basic idea behind forward chaining is that
	from facts we go through the rules and see which of them can be fired. The rule which is fired,its consequence is added to the list of facts. Then the collective list of facts are then used to fire further rules.
	
2)Backward Chaining: In this we have the conclusion and we have to prove that it can be derived by going backwards i.e from rules to facts. So, on each iteration, we obtain a list of goals(which is the list of premises ) and using these premises we try to reach to the facts. If we can then, it means the conclusion can be derived out of those facts. If not, then conclusion cannot be proved out of the given facts.
	
3) Propositional Logic: Input is given to us in the HORN clause. So, for implementing propositional logic to work, we have to convert the horn form of the clause into Conjunctive Normal Form(CNF). The rule applied to form a resolved clause from two clauses is as follows:-
						
						(A v B)	(-B v C) will give (A v C).
When following this rule, two things needs to be considered. First is that we have to remove duplicate literal which may arise when we resolve the
	clause. Second, and the most important, if a clause have literals such that a positive and negative type of that literal exists(Eg. say A v B v C v -A),then we do not include this clause. This is because, its is going to be calculated to 1.
	
	Compilation and Running Instruction
	1) Compile the program: javac pl.java
	2) Run the program: java pl -t 1 -kb kb2.txt -q q2.txt -oe output.txt -ol logs.txt

	Time taken to run resolution for case I: 35-40 seconds on aludra machine
	Time taken to run resolution for case II: 2:30 to 3 minutes on aludra machine.
	
	Similarities/Difference between Task 1,2 and 3
				
From the logs of the program, it is evident that forward chaining is the best as it takes shorter time compared to other approaches. In backward chaining cycles are detected which causes the delays in the output. Forward and backward chaining work only for horn clauses.But resolution can work for any type of clause but the main problem is that resolution takes a lot of time compared to other apporaches. The logs of resolution are a lot bigger compared to forward and backward chaining techniques. The subuset of clauses becomes bigger as more number of clauses get resolved.
		

