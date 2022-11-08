import java.util.*;
import java.io.*;


public class Pass1 {
	
	public static boolean isNumeric(String str) {
		  return str.matches("[^A-Za-z=]*[0-9]+[^A-Za-z=]*");  //match a number with optional '-' and decimal.
		}
	
	public static boolean containsLetters(String str) {
		return str.matches("[A-Za-z]+[0-9]*");
	}
	
	public static boolean containsPlus(String str) {
		for(int i = 0;i<str.length();i++){
			if(str.charAt(i) == '+') {
				return true;
			}
		}
		return false;
		
	}
	public static boolean containsMinus(String str) {
		for(int i = 0;i<str.length();i++){
			if(str.charAt(i) == '-') {
				return true;
			}
		}
		return false;
		
	}
	public static boolean containsEQU(String str) {
		String temp = "";
		int i = 0;
		while(str.charAt(i) == ' ') {
			i++;
		}
		for(int j = 0;j<str.length();j++){
			if(str.charAt(j) == ' ') {
				if(temp.equals("EQU")) {
					return true;
				}
				temp = "";
			}
			else {
				temp+=str.charAt(j);
			}
		}
		return false;
		
	}


	public static void main(String[] args) {
		BufferedReader reader;
		Tables t = new Tables();
		int lc = 0;
		int snc = 1;
		int litC = 1;
		ArrayList<String> Literals = new ArrayList<String>();
		Map<String,Map.Entry<Integer, Integer>> SymTab=new HashMap<String,Map.Entry<Integer, Integer>>();  
		try {
			reader = new BufferedReader(new FileReader(
					"/home/pict/31233/2_phase_assembler/source2.txt"));
			FileWriter fw;
			FileWriter fs = new FileWriter("/home/pict/31233/2_phase_assembler/symbolTable.txt");
			FileWriter fl = new FileWriter("/home/pict/31233/2_phase_assembler/literalTable.txt");
			FileWriter fp = new FileWriter("/home/pict/31233/2_phase_assembler/pooltable.txt");
			String line = reader.readLine();
			int litCount = 1;
			while (line != null) {
				int flag = 0;
				String line1 = line + " ";
				String x = "";
				boolean labelPassedFlag = false;
				boolean addedSymTab = false;
				boolean stopTraversing = false;
				for(int i = 0;i<line1.length();i++) {
					if(line1.charAt(i) == ' ') {
							if(x!="" && t.getCode(x)!=-1) {
								labelPassedFlag = true;
								
								if(t.getType(x) == "AD"){
									flag = 1;
								}
								if(x.equals("START")) {
									int mul = 1; 
									for(int j = line.length()-1;line.charAt(j)!=' ';j--) {
										lc += (line.charAt(j)-48)*mul;
										mul = mul*10;
									}
									flag = 1;
								}
								if(x.equals("LTORG")) {
									flag = 1;
									for(int j = 0;j<Literals.size();j++) {
										fl.write(Literals.get(j)+" "+(lc+j)+"\n");
									}
									fp.write("#"+litCount+"\n");
									lc = lc+Literals.size();
									litCount+=Literals.size();
									Literals.clear();
								}
								
								
								
							}
							else {
								if(x!="" && x.charAt(0) == '=') {
									Literals.add(x);
								}
								else if(containsLetters(x)) {
									
									String l1 = line1;
								
									if(containsEQU(l1) && stopTraversing == false) {
										String temp = "";
										boolean passed = false;
										String initial = "";
										String fi = "";
										for(int j = 0;j<l1.length();j++) {
											if(l1.charAt(j) == ' ') {
												if(temp!="EQU" && passed == false) {
													initial = temp;
													passed = true;
												}
												else if(temp!="EQU" && passed == true) {
													fi = temp;
												}
												temp = "";
											}
											else {
												temp = temp + l1.charAt(j);
											}
										}
										System.out.println(initial);
										System.out.println(fi);
										int in = 0;
										temp = "";
									
										int val = 0;
										if(!containsMinus(fi) && !containsPlus(fi)) {
											SymTab.put(initial,Pair.of(SymTab.get(fi).getKey(),snc));
											fs.write(snc+" "+initial+" "+SymTab.get(fi).getKey()+"\n");
										}
										else {
											boolean minus = false;
											for(int q = 0;q<fi.length();q++) {
												if(fi.charAt(q) == '+' || fi.charAt(q) == '-') {
													if(fi.charAt(q) == '-') {
														minus = true;
													}
													in = q;
													if(SymTab.get(temp)!=null) {
														val = SymTab.get(temp).getKey();
														break;
													}
												}
												else {
													temp+=fi.charAt(q);
												}
											}
											if(SymTab.get(fi)!=null) {
												System.out.println(SymTab.get(fi).getKey());
											}
											
											System.out.println(val);
											String num = "";
											for(int s = in;s<fi.length();s++) {
												if(fi.charAt(s)>=48 && fi.charAt(s)<=57) {
													num+=fi.charAt(s);
												}
											}
											int num1 = 0;
											if(num!="") {
												num1 = Integer.parseInt(num);
											}
											if(minus == true) {
												val = val  - num1;
											}
											else {
												val = val+num1;
											}
											
											SymTab.put(initial, Pair.of(val, snc));
											fs.write(snc+" "+initial+" "+val+"\n");
										}
										snc++;
										addedSymTab = true;
										stopTraversing = true;
									}
									
									if(labelPassedFlag == false) {
										
										if(addedSymTab == false) {
											SymTab.put(x, Pair.of(lc, snc));
											fs.write(snc+" "+x+" "+lc+"\n");
											snc++;
										}
										
									}
									
										
								}
								else if(containsPlus(x) && stopTraversing == false) {
									String temp = "";
									int in = 0;
									for(int q = 0;q<x.length();q++) {
										if(x.charAt(q) == '+') {
											in = q;
											if(SymTab.get(temp)!=null) {
												lc = SymTab.get(temp).getKey();
												flag = 1;
												break;
											}
										}
										temp+=x.charAt(q);
									}
									String num = "";
									for(int s = in;s<x.length();s++) {
										if(x.charAt(s)>=48 && x.charAt(s)<=57) {
											num+=x.charAt(s);
										}
									}
									int num1 = Integer.parseInt(num);  
									lc+=num1;
								}
								else if(containsMinus(x)&& stopTraversing == false) {
									
									String temp = "";
									int in = 0;
									for(int q = 0;q<x.length();q++) {
										if(x.charAt(q) == '-') {
											in = q;
											if(SymTab.get(temp)!=null) {
												lc = SymTab.get(temp).getKey();
												flag = 1;
												break;
											}
										}
										temp+=x.charAt(q);
									}
									String num = "";
									for(int s = in;s<x.length();s++) {
										if(x.charAt(s)>=48 && x.charAt(s)<=57) {
											num+=x.charAt(s);
										}
									}
									int num1 = Integer.parseInt(num);  
									lc-=num1;
								}
							}
							x = "";
					}
					if(line1.charAt(i)!=' ') {
						x = x+line1.charAt(i);
					}
							
				}
				if(flag == 0) {
					lc+=2;
				}
				
				line = reader.readLine();
			}
			reader.close();
			fs.close();
			for(int i = 0;i<Literals.size();i++) {
				fl.write(Literals.get(i)+" "+(lc)+"\n");
			}
			fp.write("#"+litCount+"\n");
			fp.close();
			fl.close();
			
			fw = new FileWriter("/home/pict/31233/2_phase_assembler/icf.txt");
			reader = new BufferedReader(new FileReader(
					"/home/pict/31233/2_phase_assembler/source2.txt"));
			line = reader.readLine();
			lc = 0;
			while (line != null) {
				int flag = 0;
				int count = 0;
				String line1 = line + " ";
				String x = "";
				boolean labelPassedFlag = false;
				boolean lcWritten = false;
				boolean stopTraversing = false;
				for(int i = 0;i<line1.length();i++) {
					if(line1.charAt(i) == ' ') {
							
							if(x!="" && t.getCode(x)!=-1) {
								if(t.getType(x) != "AD" && lcWritten == false && labelPassedFlag == false) {
									fw.write(lc+" ");
									lcWritten = true;
								}
								else if(t.getType(x) == "AD"){
									fw.write("");
									flag = 1;
								}
								labelPassedFlag = true;
								if(t.getType(x) == "") {
								}
								else {
									fw.write("("+t.getType(x)+","+t.getCode(x)+") ");
								}
								
								if(x.equals("START")) {
									int mul = 1; 
									for(int j = line.length()-1;line.charAt(j)!=' ';j--) {
										lc += (line.charAt(j)-48)*mul;
										mul = mul*10;
									}
									flag = 1;
								}
										
							}
							else {
								if(x!="" && x.charAt(0) == '=') {
									fw.write("("+"L"+litC+","+x+") ");
									litC++;
								}
								else if(isNumeric(x)) {
									fw.write("("+"C"+","+x+") ");
								}
								else if(containsLetters(x)) {
									if(SymTab.get(x)!=null && labelPassedFlag == true) {
										fw.write("("+"S"+","+SymTab.get(x).getValue()+") ");
									}
									if(containsEQU(line1)) {
										stopTraversing = true;
									}
								}
								else if(containsPlus(x) && stopTraversing == false) {
									String temp = "";
									int in = 0;
									for(int q = 0;q<x.length();q++) {
										if(x.charAt(q) == '+') {
											in = q;
											if(SymTab.get(temp)!=null) {
												fw.write("("+"S"+","+SymTab.get(temp).getValue()+") ");
												lc = SymTab.get(temp).getKey();
												flag = 1;
												break;
											}
										}
										temp+=x.charAt(q);
									}
									String num = "";
									for(int s = in;s<x.length();s++) {
										fw.write(x.charAt(s));
										if(x.charAt(s)>=48 && x.charAt(s)<=57) {
											num+=x.charAt(s);
										}
									}
									int num1 = Integer.parseInt(num);  
									lc+=num1;
								}
								else if(containsMinus(x) && stopTraversing == false) {
									String temp = "";
									int in = 0;
									for(int q = 0;q<x.length();q++) {
										if(x.charAt(q) == '-') {
											in = q;
											if(SymTab.get(temp)!=null) {
												fw.write("("+"S"+","+SymTab.get(temp).getValue()+") ");
												lc = SymTab.get(temp).getKey();
												flag = 1;
												break;
											}
										}
										temp+=x.charAt(q);
									}
									String num = "";
									for(int s = in;s<x.length();s++) {
										fw.write(x.charAt(s));
										if(x.charAt(s)>=48 && x.charAt(s)<=57) {
											num+=x.charAt(s);
										}
									}
									int num1 = Integer.parseInt(num);  
									lc-=num1;
									
								}
								else {
									fw.write("");
								}
									
							}
							
							x = "";
					}
					if(line1.charAt(i)!=' ') {
						x = x+line1.charAt(i);
					}
							
				}
				fw.write("\n");
				if(flag == 0) {
					lc+=2;
				}
				
				line = reader.readLine();
			}
			reader.close();
			fw.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}