import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 class RunProgram
{
	 	 public void backwardChaining()
	{
		int i;
		boolean getDecision;
		for(i=0;i<pl.queryList.size();i++)
		{
			Variable c=new Variable(pl.queryList.get(i).sentence.charAt(0));	//T
			pl.flagPrintOnlyOnce=0;
			getDecision=callOR_PopulateArrayList(c);	// to fill the possibilities and will only give NO
			/* cycle detection case still left */
			if(getDecision==false)
			{
				
				try
				{
					pl.output.write("NO");
					pl.output.newLine();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					pl.output.write("YES");
					pl.output.newLine();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			for(Clause clr: pl.sentenceList)
			{
				clr.parsed=false;	// after doing the operation set all the parsed variables to false
				
			} 
			try
			{
				//System.out.println("-------------------------------------------------------------");
				pl.logs.newLine();
				pl.logs.write("-------------------------------------------------------------");
				pl.logs.newLine();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		} 
		try
		{
			pl.output.close();
			pl.logs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
			
	}
	 
	 
	 
	public boolean callAND_getPremises(String str)
 	{
 		String[] strArray;
 		String str1;
 		int i=0,index;
 		
 		List<Clause>premiseList=new ArrayList<Clause>();
 		index=str.indexOf('-');
 		str1=str.substring(index+2);			//facts condition here
 		strArray=str1.split(",");
 		boolean resultFromOR;
 		printLogsRelevantRules(str);
 		printLogsNewGoalIntroduced(str1);
 		for(String string:strArray)
 		{
 			Character c=string.charAt(0);
 			
 			Clause clr=new Clause(c.toString());
 			//clr.setParsed();
 			premiseList.add(clr);
 			if( isFact(c))
 			{
 				printLogsQueueOfGoals(c);
 				printLogsRelevantRules(c.toString());
 				printLogsNewGoalIntroduced("N/A");
 				 	//as  NA; its a fact but continue the loop
 				premiseList.get(i).setParsed();
 			}
 			else if(isCycle(c))		//iscycle(str)
 			{
 				try
 				{
 					//System.out.println(c+" # CYCLE DETECTED # N/A");
 					pl.logs.write(c+" # CYCLE DETECTED # N/A");
 					pl.logs.newLine();
 				}catch(Exception e)
 				{
 					e.printStackTrace();
 				}
 				} 
 			else
 			{
 				printLogsQueueOfGoals(c);
 				//printLogsRelevantRules()
 				setClauseForCycle(str);
 				Variable var=new Variable(c);
 				resultFromOR=callOR_PopulateArrayList(var);
 				if(resultFromOR==true)
 				{
 					premiseList.get(i).setParsed();
 					//Clause of a premise satisfied
 				}
 				else
 				{
 					/*if(pl.flagPrintOnlyOnce==0)
 					{
 						System.out.println("N/A # N/A");
 						try
 						{//pl.logs.write("N/A # N/A ");
 						//pl.logs.newLine();
 						}catch(Exception e)
 						{
 							e.printStackTrace();
 						}
 						pl.flagPrintOnlyOnce=1;
 					} */
 					//printLogsRelevantRules("N/A");
 					//printLogsNewGoalIntroduced("N/A");
 					break;
 				}
 			}
 			i++;
 			
 		}	
 		for(Clause clause: premiseList)
		{
			if(clause.getParsed()==false)
			return false;
		}
			return true;
 			
 	}
	
	public boolean callOR_PopulateArrayList(Variable c) 
	{
		boolean noExtraPossibilities=false;
		boolean atLeastOnePossibility=false;
		for(Clause clause: pl.sentenceList)
		{
			//clause.setParsed();
			Character c1=clause.getString().charAt(0);
			
			if(c1.equals(c.var))
			{
				printLogsQueueOfGoals(c1);
				noExtraPossibilities=callAND_getPremises(clause.getString());
				if(noExtraPossibilities==true)
				{
					//break;		
				}
				
			}
			
		}
		/* none of the possibilites found */
		 if(atLeastOnePossibility==false)
		 {
			//printLogsRelevantRules("N/A");
			//printLogsNewGoalIntroduced("N/A");
		 } 
		 
		return noExtraPossibilities;
	}
	
	public boolean isFact(Character c)
	{
		for(Clause clause: pl.kb)
		{
			if(c.equals(clause.getString().charAt(0)))
			{
				return true;
			}
		}
		return false;
		
	}
	
	public boolean isCycle(Character c)
	{
		for(Clause clause: pl.sentenceList)
		{
			if(c.equals(clause.getString().charAt(0)))
			{
				if(clause.getParsed()==true)
					return true;
			}
		}
		return false;
	} 
	
	public void setClauseForCycle(String str)
	{
		for(Clause clause: pl.sentenceList)
		{
			if(str.equalsIgnoreCase(clause.getString()))
			{
				clause.setParsed();
			}
		}
	}
	
	public void printLogsQueueOfGoals(Character c)
	{
		try
		{
			//System.out.print(c+" # ");
			pl.logs.write(c+" # ");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void printLogsRelevantRules(String str)
	{
		try
		{
			//System.out.print(str+" # ");
			pl.logs.write(str+" # ");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void printLogsNewGoalIntroduced(String str)
	{
		try
		{
			//System.out.println(str);
			pl.logs.write(str);
			pl.logs.newLine();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void startPlResolutionLogic(String kbFile,String qFile)
	{
		
		String queryLine;
		boolean bool=false;
		BufferedReader reader1;
		boolean result;
		try
		{
			reader1=new BufferedReader(new FileReader(qFile));
			pl.logs.write("Resolving clause 1#Resolving clause 2#Added clause");
			pl.logs.newLine();
			
			
			for(queryLine=reader1.readLine();queryLine!=null;queryLine=reader1.readLine())
			{
				
				
				result=plResolutionLogic(queryLine,kbFile);
				if(result==true)
					pl.output.write("YES");
				else
					pl.output.write("NO");
				pl.output.newLine();
				pl.logs.write("-----------------------------------------------------------------------");
				pl.logs.newLine();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean plResolutionLogic(String alpha,String kbFile)
	 {
		String line;
		
		boolean bool=false;
		List<String> clauses=new ArrayList<String> ();
		List<String> new1=new ArrayList<String>();
		List<String> resolvents=new ArrayList<String>();
		String[] pair=new String[2];
		
		int i=0,j=i+1;
		int iteration=1;
		
		
		
		
		try
		{
			pl.logs.write("ITERATION "+ iteration);
			pl.logs.newLine();
			
				pl.reader=new BufferedReader( new FileReader(kbFile));
				for(line=pl.reader.readLine();line!=null;line=pl.reader.readLine())
				{
					String line1=hornToCNF(line);
					clauses.add(line1);
				}
				// Add alpha which is in query list
				//alpha="-"+alpha;	//Negate alpha function
				alpha=negateAlpha(alpha);
				clauses.add(alpha);
				while(true)
				{
					while((pair=getPair(clauses,i,j))!=null)
					{
						plResolve(resolvents,pair[0],pair[1]);	// add to resolvents there only
						
						//iterate over all resolvents to check if any one is empty
						 if(resolventsContainsEmptyClause(resolvents))
						 {
							 return true;
						 }
						//doUnion(new1,resolvents.get(resolvents.size()-1)); 		// do not add duplicates
						  doUnion(new1,resolvents);
						if(j==clauses.size()-1)
						{
							i++;
							j=i+1;
						}
						else
						{
							j++;
						}
						resolvents.clear();
						
					}
					if(new1SubsetOfClauses(new1,clauses))
					{
						return false;
					}
					doUnion(clauses,new1);
					i=0;
					j=i+1;  
					iteration++;
				
					pl.logs.write("ITERATION "+ iteration);
					pl.logs.newLine();
					resolvents.clear();
					//new1.clear();
				}
				
				
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
			
		return bool;
	}
	
	public String[] getPair(List<String> clauses,Integer i,Integer j)
	{
			String[] str=new String[2];
			if(i>=clauses.size()-1 && j>clauses.size()-1)
			{
				str=null;
			}
			else
			{
				str[0]=new String(clauses.get(i));
				str[1]=new String(clauses.get(j));
			}
				return str;
			
	}
	
	
	public void plResolve(List<String> resolvents,String pair0,String pair1)
	{
		int pair0Size=0;
		int pair1Size=0;
		int lastIndex=0;
		boolean flag=false;
		
		String[] pair0Array=pair0.split("#");
		String[] pair1Array=pair1.split("#");
		String resolvedString="";
		for(int i=0;i<pair0Array.length;i++)
		{
			pair0Size=pair0Array[i].length();
			for(int j=0;j<pair1Array.length;j++)
			{
				pair1Size=pair1Array[j].length();
				if(pair0Size!=pair1Size)
				{
					Character x=pair0Array[i].charAt(pair0Size-1);
					Character y=pair1Array[j].charAt(pair1Size-1);
					if(x.equals(y))
					{
						//pair0Array[i]="";
						//pair1Array[j]="";
						resolvedString=resolve_String(pair0Array,i,pair1Array,j);
						resolvents.add(resolvedString);	
							try
							{
								//pl.logs.write(resolvedString);
								if(resolvedString.equalsIgnoreCase("one"));
								else
								{
									printClause(pair0);
									printClause(pair1);
									printAddedClause(resolvedString);
								}
								
								//pl.logs.newLine();
							}catch(Exception e)
							{
								e.printStackTrace();
							}
						
					}
				}
			}
			
		}
	}
		/*if(flag==true)
		{
		for(int i=0;i<pair0Array.length;i++)
		{
			if(pair0Array[i].equalsIgnoreCase(""))
			{
				// do not add
			}
			else
			{
				resolvedString=resolvedString+pair0Array[i]+"#";
			}
			
		}
		for(int i=0;i<pair1Array.length;i++)
		{
			if(pair1Array[i].equalsIgnoreCase(""))
			{
				// do not add
			}
			else if(duplicateLiteralFound(pair1Array[i],resolvedString))
			{
				//do not add
			}
			else
			{
				if(i+1==pair1Array.length)
				{
					resolvedString=resolvedString + pair1Array[i];
				}
				else
				{
					resolvedString=resolvedString + pair1Array[i]+"#";
				}
			}
		}
		lastIndex=resolvedString.length()-1;
		if(lastIndex==-1)
		{
			// resolvedString will be ""
			printClause(pair0);
			printClause(pair1);
			printAddedClause("Empty");
			resolvents.add(resolvedString);
		}
		else if(containsNeutrals(resolvedString))
		{
			//do not add the clause coz we have literal and its negation
			//System.out.println(resolvedString);
		} 
		else
		{
			Character lastChar=resolvedString.charAt(lastIndex);
			if(lastChar.equals('#'))
			{
				resolvedString=resolvedString.substring(0, lastIndex);
			}
			resolvents.add(resolvedString);
			try
			{
				//pl.logs.write(resolvedString);
				printClause(pair0);
				printClause(pair1);
				printAddedClause(resolvedString);
				//pl.logs.newLine();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
			//resolvents.add(resolvedString);
		
	}
	}*/
	
	public void doUnion(List<String> new1,List<String> resolvents)
	{
		int i,j;
		int flagDuplicate=0;
		boolean duplicateFound=false;
		for(String str: resolvents)
		{
			String[] resStrArray=str.split("#");
			for(String new1String : new1)
			{
				String[] new1StrArray=new1String.split("#");
				//compare using size of array
				if(resStrArray.length==new1StrArray.length)
				{
					// compare each literal if the size is the same
					for(i=0;i<resStrArray.length;i++)
					{
						for(j=0;j<new1StrArray.length;j++)
						{
							if(resStrArray[i].equalsIgnoreCase(new1StrArray[j]))
								flagDuplicate++;
						}
					} 
						
				}
				if(flagDuplicate==resStrArray.length)
				{
					// they are duplicate do not add to new1 clause
					duplicateFound=true;
					flagDuplicate=0;
					break;
				}		
				flagDuplicate=0; 
			}
			if(duplicateFound==true)
				duplicateFound=false;
			
			else
				new1.add(str);
			
		}
		}
	
	
	public boolean new1SubsetOfClauses(List<String> new1,List<String> clauses)
	{
		int i,j;
		int count=0;
		int flagDuplicate=0;
		boolean duplicateFound=false;
		if(new1.size() < clauses.size())
		{
			for(String str: clauses)
			{
				String[] clausesStrArray=str.split("#");
				for(String new1String : new1)
				{
					String[] new1StrArray=new1String.split("#");
					//compare using size of array
					if(clausesStrArray.length==new1StrArray.length)
					{
						// compare each literal if the size is the same
						for(i=0;i<clausesStrArray.length;i++)
						{
							for(j=0;j<new1StrArray.length;j++)
							{
								if(clausesStrArray[i].equalsIgnoreCase(new1StrArray[j]))
									flagDuplicate++;
							}	
						}
						
					}
					if(flagDuplicate==clausesStrArray.length)
					{
						// they are duplicate do not add to new1 clause
						duplicateFound=true;
						flagDuplicate=0;
						break;
					}		
					flagDuplicate=0;
				}
				if(duplicateFound==true)
				{
					duplicateFound=false;
					count++;
				}
				
			}
		}
		if(count==new1.size())	// if new1.size() > clauses.size() then count will be zero 
			return true;
		else
			return false;
		
	}
	
	public boolean duplicateLiteralFound(String literal, String resolvedString)
	{
		boolean duplicateFound=false;
		String[] resArray=resolvedString.split("#");
		for(int i=0;i<resArray.length;i++)
		{
			if(literal.equalsIgnoreCase(resArray[i]))
				duplicateFound=true;
		}
		
		return duplicateFound;
	}
	
	public String resolve_String(String[] pair0Array,int i,String[] pair1Array,int j)
	{
		String resolvedString="";
		int x,y;
		int lastIndex;
		Character lastChar;
		for(x=0;x<pair0Array.length;x++)
		{
			if(x==i)
			{
				//do not add
				continue;
			}
			resolvedString=resolvedString+pair0Array[x]+"#";
		}
		
		for(y=0;y<pair1Array.length;y++)
		{
			if(duplicateLiteralFound(pair1Array[y],resolvedString))
			{
				// do not add the literal
			}
			else if(y==j)
			{
				//do not add
				continue;
			}
			else
			{
				resolvedString=resolvedString+pair1Array[y]+"#";
				
			}
		}
		lastIndex=resolvedString.length()-1;
		if(lastIndex==-1)
		{
			// resolvedString will be ""
			resolvedString=resolvedString+"Empty";
			//resolvents.add(resolvedString);
			return resolvedString;
		}
		else if(containsNeutrals(resolvedString))
		{
			// do not add to resolvents
			return "one";
		}
		else
		{
			lastChar=resolvedString.charAt(lastIndex);
			if(lastChar.equals('#'))
			{
				resolvedString=resolvedString.substring(0,lastIndex);
			}
			return resolvedString;
		}
		
	}
	
	public boolean resolventsContainsEmptyClause(List<String> resolvents)
	{
		boolean bool=false;
		for(String str:resolvents)
		{
			if(str.equalsIgnoreCase("Empty"))
				bool=true;
		}
		return bool;
	}
	
	public boolean containsNeutrals(String resolvedString)
	{
		boolean returnCode=false;
		int neg1=0,neg2=0;
		Character c1,c2;	// for negation
		Character c3,c4;	// for literals
		String[] arr=resolvedString.split("#");
		for(int i=0;i<arr.length;i++)
		{
			neg1=0;
			String str1=arr[i];
			c1=str1.charAt(0);
			if(c1.equals('-'))
				neg1=1;
			c3=str1.charAt(str1.length()-1);	// literal
			for(int j=i+1;j<arr.length;j++)
			{
				neg2=0;
				String str2=arr[j];
				c2=str2.charAt(0);
				if(c2.equals('-'))
					neg2=1;
				c4=str2.charAt(str2.length()-1);
				if(c3.equals(c4))
				{
					if((neg1==0 & neg2==1) | (neg1==1 & neg2==0))
					{
						returnCode=true;
						return returnCode;
					}
				}
			}
		}
		return returnCode;
	}
		
		
		public void printClause(String str)
		{
			try
			{
				for(int i=0;i<str.length();i++)
				{
					if(str.charAt(i)=='#')
						pl.logs.write(" OR ");
					
					else
						pl.logs.write(str.charAt(i)+ "");
				}
				pl.logs.write(" # ");
			}catch(Exception e)
			{
				System.out.println("Error in line 644: "+e.toString());
			}
			
		}
		
		public void printAddedClause(String str)
		{
			try
			{
				for(int i=0;i<str.length();i++)
				{
					if(str.charAt(i)=='#')
						pl.logs.write(" OR ");
					
					else
						pl.logs.write(str.charAt(i)+ "");
				}
				pl.logs.newLine();
			}catch(Exception e)
			{
				System.out.println("Error in line 664: "+e.toString());
			}
		}
	 
	
	public String hornToCNF(String line)
	{
		String[] str;
		String returnLine=new String("");
		String line1=null;
		int index=0;
		index=line.indexOf('-');
		line1=line.substring(index+2);
		str=line1.split(",");
		if(line.length()!=1)
		{
			for(int i=0;i<str.length;i++)
			{
				returnLine=returnLine+"-"+str[i]+"#";
			}
		}
		Character c=line.charAt(0);
		returnLine=returnLine + c.toString();
		return returnLine;
	}
	
	public String negateAlpha(String alpha)
	{
		Character first;
		
		first=alpha.charAt(0);
		if(first=='-')
			alpha=alpha.substring(1);
		else
		{
			alpha="-"+alpha;
		}
		return alpha;
	}
	
	public void startForwardChaining(String kbFile,String qFile)
 	{
		String queryLine;
		pl.kb.clear();
		pl.sentenceList.clear();
		BufferedReader reader1;
		boolean result;
		String line;
		
		try
		{
			reader1=new BufferedReader(new FileReader(qFile));
			pl.logs.newLine();
			for(queryLine=reader1.readLine();queryLine!=null;queryLine=reader1.readLine())
			{
				
				
					BufferedReader reader=new BufferedReader(new FileReader(kbFile));
					for(line=reader.readLine();line!=null;line=reader.readLine())
					{
						/* lIST OF RULES */
						Clause c=new Clause(line);
						if(c.getString().length()!=1)
							pl.sentenceList.add(c);
						else
							pl.kb.add(c);
		             }
						
				result=forwardChaining(queryLine);
				if(result==true)
					pl.output.write("YES");
				else
					pl.output.write("NO");
				pl.output.newLine();
				pl.logs.write("-----------------------------------------------------------------------");
				pl.logs.newLine();
				/*for(Clause clr:pl.sentenceList)
					clr.parsed=false; */
				pl.kb.clear();
				pl.sentenceList.clear();
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
 	}
	
	public boolean forwardChaining(String alpha)
	{
		//pl.sentenceList,pl.kb
		int index,loopCount=0;
		String str2,line;
		String conclusion="";
		
		
			
		while(loopCount!=pl.sentenceList.size())
		{
			loopCount=0;
			for(Clause clause:pl.sentenceList)
			{
				loopCount++;
				String str=clause.getString();
				index=str.indexOf('-');
				str2=str.substring(index+2);
				if(notInKnowledgeBase(str2)==true)
				{
					//move forward
				}
				else
				{
					if(clause.parsed==true)
					{
						// do not add again
						conclusion="";
					}
					else
					{
						conclusion=addToKnowledgeBase(clause);
						loopCount=0;	
						break;
					}
					
				}
			
			}
			if(conclusion.equalsIgnoreCase(alpha))
				return true;
		}
		return false;
	}
	
	public boolean notInKnowledgeBase(String str)
	{
		boolean flag=true;
		String[] strArray=str.split(",");
		int count=strArray.length;
		int actualCount=0;
		for(Clause c:pl.kb)
		{
			for(int i=0;i<strArray.length;i++)
			{
				if(c.getString().equalsIgnoreCase(strArray[i]))
				{
					actualCount++;
				}
			}
		}
			if(actualCount==count)
			{
				// In KB
				flag=false;
				return false;
			}
		
		return flag;
	}
	
	public String addToKnowledgeBase(Clause clause)
	{
		String str=clause.getString();
		Character c=str.charAt(0);
		printForwardDeducedFacts();
		printForwardRulesFired(clause);
		printForwardNewFacts(c.toString());
		pl.kb.add(new Clause(c.toString()));
		clause.setParsed();
		//printForwardLogs(clause,c.toString());
		return c.toString();
	}
	
	public void printForwardDeducedFacts()
	 {
		try
		{
			for(int j=0;j<pl.kb.size();j++)
			{
				pl.logs.write(pl.kb.get(j).getString());
				pl.logs.write(", ");
			}
		pl.logs.write("# ");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	 }
	 
	public void printForwardRulesFired(Clause c)
	 {
		try
		{	pl.logs.write(c.getString());
			pl.logs.write("# ");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	 
	 public void printForwardNewFacts(String str)
	 {
		try
		{
			pl.logs.write(str);
			pl.logs.newLine();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	 }
	
}
 
 	
 	

public class pl {

	      public static BufferedReader reader;
	      public static BufferedWriter output, logs;
	      public static List<Clause> sentenceList=new ArrayList<Clause>();
	      public static List<Clause> queryList=new ArrayList<Clause>();
	      public static List<Clause> kb=new ArrayList<Clause>();
	      public static List<Clause> backward=new ArrayList<Clause>();
	      public static boolean backwardChainingGotYESFlag=false;
	      public static int flagPrintOnlyOnce=0;
	      public static void main(String args[])
	      {
	          String line=null;
	          Integer option;
	          Scanner in;

	          try
	          {
	              reader=new BufferedReader(new FileReader(args[3]));	// "kb2.txt"
	              output=new BufferedWriter(new FileWriter(args[7]));	// "output.txt"
	              logs=new BufferedWriter(new FileWriter(args[9]));	//"logs.txt"
	              for(line=reader.readLine();line!=null;line=reader.readLine())
	              {
	                     /* lIST OF RULES */
	                  Clause c=new Clause(line);
	                  if(c.getString().length()!=1)
	                  {
	                      sentenceList.add(c);
	                       //System.out.println("rules: "+c.getString());
	                  }
	                  else
	                  {
	                      kb.add(c);
	                       //System.out.println("Facts: "+c.getString());
	                  }
	              }
	              reader=new BufferedReader(new FileReader(args[5]));	//"q2.txt"
	              for(line=reader.readLine();line!=null;line=reader.readLine())
	              {             /* list of query */
	                  Clause c=new Clause(line);
	                  queryList.add(c);
	                 // System.out.println(c.getString());
	              }
	          }catch(Exception e)
	          {
	              System.out.println("Error(743) in FileRead:"+e.toString());
	          }

	          //System.out.println("Enter option:");
	          //in=new Scanner(System.in);
	          option=Integer.parseInt(args[1]);		//in.nextInt();
	          RunProgram run=new RunProgram();
	          			
	          if(option==1)
	          {
	              System.out.println("Forward Chaining");
	              try
	              {
	            	  logs.write("<Known/Deducted facts>#Rules Fires#NewlyEntailedFacts");
	            	  logs.newLine();
	              }catch(Exception e)
	              {
	            	  System.out.println("Error(760): "+e.toString());
	              }
	              run.startForwardChaining(args[3],args[5]);
	          }
	          else if(option==2)
	          {
	              System.out.println("Backward Chaining");
	              try
        			{
        				logs.write("<Queue of Goals>#Relevant Rules/Fact#New Goal Introduced");
        				logs.newLine();
        			}catch(Exception e)
        			{
        				e.printStackTrace();
        			}
	              run.backwardChaining();
	          }
	          else
	          {
	              System.out.println("Resolution");
	              run.startPlResolutionLogic(args[3],args[5]);
	          }
	          try
	          {output.close();
	          logs.close();
	          }catch(Exception e)
	          {
	        	  e.printStackTrace();
	          }
	          
	      }

	}


