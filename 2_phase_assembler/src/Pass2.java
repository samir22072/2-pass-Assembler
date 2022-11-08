import java.util.*;
import java.io.*;


public class Pass2{
	
	public static boolean isNumeric(String str) {
		  return str.matches("[0-9]+");  
		}
	
	public static boolean containsAD(String str) {
		String temp = "";
		String s = "";
		for(int i = 1;i<=2;i++) {
			s+=str.charAt(i);
		}
		if(s.equals("AD")) return true;
		return false;
		
	}
	public static boolean containsIS(String str) {
		String temp = "";
		String s = "";
		for(int i = 1;i<=2;i++) {
			s+=str.charAt(i);
		}
		if(s.equals("IS")) return true;
		return false;
		
	}
	public static boolean containsDL(String str) {
		String temp = "";
		String s = "";
		for(int i = 1;i<=2;i++) {
			s+=str.charAt(i);
		}
		if(s.equals("DL")) return true;
		return false;
		
	}
	public static boolean containsS(String str) {
		
		String temp = "";
		String s = "";
		for(int i = 1;i<2;i++) {
			s+=str.charAt(i);
		}
		if(s.equals("S")) return true;
		return false;
		
	}
	
	public static boolean containsL(String str) {
		
		String temp = "";
		String s = "";
		for(int i = 1;i<2;i++) {
			s+=str.charAt(i);
		}
		if(s.equals("L")) return true;
		return false;
		
	}
	
	public static boolean containsC(String str) {
		String temp = "";
		String s = "";
		for(int i = 1;i<2;i++) {
			s+=str.charAt(i);
		}
		if(s.equals("C")) return true;
		return false;
	}
	
	public static boolean containsRG(String str) {
		String temp = "";
		String s = "";
		for(int i = 1;i<=2;i++) {
			s+=str.charAt(i);
		}
		if(s.equals("RG")) return true;
		return false;
	}
	public static boolean containsCC(String str) {
		String temp = "";
		String s = "";
		for(int i = 1;i<=2;i++) {
			s+=str.charAt(i);
		}
		if(s.equals("CC")) return true;
		return false;
	}
	public static void main(String[] args) {
		
		BufferedReader readerICF,readerSym,readerLit;
		
		
		try {
			readerICF = new BufferedReader(new FileReader(
					"/home/pict/31233/2_phase_assembler/icf.txt"));
			
			readerSym = new BufferedReader(new FileReader(
					"/home/pict/31233/2_phase_assembler/symbolTable.txt"));
			readerLit = new BufferedReader(new FileReader(
					"/home/pict/31233/2_phase_assembler/literalTable.txt"));
			FileWriter fr = new FileWriter("/home/pict/31233/2_phase_assembler/finalResult1.txt");
			Map<Integer,Map.Entry<Integer, String>> Literals = new HashMap<Integer,Map.Entry<Integer, String>>();
			Map<Integer, Integer> SymTab=new HashMap<Integer, Integer>();
			
			String line = readerSym.readLine();
			while(line!=null) {
				String line1 = line+' ';
				String temp = "";
				int index = 0;
				String symbol = "";
				int lc = 0;
				boolean firstPassed = false;
				for(int i = 0;i<line1.length();i++) {
					if(line1.charAt(i) == ' ') {
						if(isNumeric(temp) && firstPassed == false) {
							firstPassed = true;
							index = Integer.parseInt(temp);
						}
						else if(firstPassed == true && isNumeric(temp) == true) {
							lc = Integer.parseInt(temp);
						}
						else {
							symbol = temp;
						}
						temp = "";
					}
					else {
						temp+=line1.charAt(i);
					}
				}
				
				SymTab.put(index,lc);
				
				line = readerSym.readLine();
			}
			
			line = readerLit.readLine();
			int x = 1;
			while(line!=null) {
				String line1 = line+' ';
				String temp = "";
				String literal = "";
				int lc = 0;
				for(int i = 0;i<line1.length();i++) {
					if(line1.charAt(i) == ' ') {
						if(isNumeric(temp)) {
							lc = Integer.parseInt(temp);
						}
						else {
							literal = temp;
						}
						temp = "";
					}
					else {
						temp+=line1.charAt(i);
					}
				}
				
				Literals.put(x,Pair.of(lc,literal));
				x++;
				line = readerLit.readLine();
			}
			for (int name : Literals.keySet()) 
	            System.out.println("key: " + name);
			
			for (Map.Entry<Integer, String> name : Literals.values()) 
	            System.out.println("key: " + name);
			line = readerICF.readLine();
			while(line!=null) {
				String line1 = line+' ';
				String temp = "";
				int lc = 0;
				String OpcodeIns = "";
				int operand1 = -1;
				int operand2 = -1;
				int counter = 0;
				boolean written = false;
				for(int i = 0;i<line1.length();i++) {
					if(line1.charAt(i) == ' ') {
						counter++;
						if(isNumeric(temp)) {
							lc = Integer.parseInt(temp);
						}
						else {
							if(temp.length()>1&&containsAD(temp)) {
								fr.write("No Machine Code\n");
								written = true;
								break;
							}
							else if(temp.length()>1 && containsDL(temp)) {
								
								if(temp.equals("(DL,1)")) {
									OpcodeIns = "00";
									operand1 = 0;
								}
								else {
									fr.write("No Machine Code\n");
									written = true;
									break;
								}
									
							}
							else if(temp.length()>1 && containsS(temp)) {
								if(operand1==-1) {
									int index = 0;
									String s = "";
									int flag = 0;
									for(int j = 0;j<temp.length();j++) {
										if(temp.charAt(j) == ',') {
											flag = 1;
											continue;
										}
										else if(flag == 1){
											if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
												s+=temp.charAt(j);
											}
										}
									}
									int num = Integer.parseInt(s);
									operand1 = SymTab.get(num);
								}
								else if(temp.length()>1 && operand2 == -1) {
									int index = 0;
									String s = "";
									int flag = 0;
									for(int j = 0;j<temp.length();j++) {
										if(temp.charAt(j) == ',') {
											flag = 1;
											continue;
										}
										else if(flag == 1){
											if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
												s+=temp.charAt(j);
											}
										}
									}
									int num = Integer.parseInt(s);
									operand2 = SymTab.get(num);
								}
							}
							else if(temp.length()>1 && containsL(temp)) {
								
								if(operand1==-1) {
										int index = 0;
										String s = "";
										int flag = 0;
										s+=temp.charAt(2);
										int num = Integer.parseInt(s);
									
										operand1 = Literals.get(num).getKey();
								}
								else if(operand2 == -1) {
									int index = 0;
									String s = "";
									int flag = 0;
									s+=temp.charAt(2);
									int num = Integer.parseInt(s);
									System.out.println(num);
									operand2 = Literals.get(num).getKey();
								}
								
							}
							else if(temp.length()>1  && containsC(temp)) {
								
								if(operand1==-1) {
										int index = 0;
										String s = "";
										int flag = 0;
										for(int j = 0;j<temp.length();j++) {
											if(temp.charAt(j) == ',') {
												flag = 1;
												continue;
											}
											else if(flag == 1){
												if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
													s+=temp.charAt(j);
												}
											}
										}
										int num = Integer.parseInt(s);
										operand1 = num;
								}
								else if(operand2 == -1) {
									int index = 0;
									String s = "";
									int flag = 0;
									for(int j = 0;j<temp.length();j++) {
										if(temp.charAt(j) == ',') {
											flag = 1;
											continue;
										}
										else if(flag == 1){
											if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
												s+=temp.charAt(j);
											}
										}
									}
									int num = Integer.parseInt(s);
									operand2 = num;
								}
								
							}
							
							else if(temp.length()>1 && containsRG(temp)) {
								
								if(operand1==-1) {
										int index = 0;
										String s = "";
										int flag = 0;
										for(int j = 0;j<temp.length();j++) {
											if(temp.charAt(j) == ',') {
												flag = 1;
												continue;
											}
											else if(flag == 1){
												if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
													s+=temp.charAt(j);
												}
											}
										}
										int num = Integer.parseInt(s);
										operand1 = num;
								}
								else if(operand2 == -1) {
									int index = 0;
									String s = "";
									int flag = 0;
									for(int j = 0;j<temp.length();j++) {
										if(temp.charAt(j) == ',') {
											flag = 1;
											continue;
										}
										else if(flag == 1){
											if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
												s+=temp.charAt(j);
											}
										}
									}
									int num = Integer.parseInt(s);
									operand2 = num;
								}
								
							}
							else if(temp.length()>1 && containsCC(temp)) {
								
								if(operand1==-1) {
										int index = 0;
										String s = "";
										int flag = 0;
										for(int j = 0;j<temp.length();j++) {
											if(temp.charAt(j) == ',') {
												flag = 1;
												continue;
											}
											else if(flag == 1){
												if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
													s+=temp.charAt(j);
												}
											}
										}
										int num = Integer.parseInt(s);
										operand1 = num;
								}
								else if(operand2 == -1) {
									int index = 0;
									String s = "";
									int flag = 0;
									for(int j = 0;j<temp.length();j++) {
										if(temp.charAt(j) == ',') {
											flag = 1;
											continue;
										}
										else if(flag == 1){
											if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
												s+=temp.charAt(j);
											}
										}
									}
									int num = Integer.parseInt(s);
									operand2 = num;
								}
								
							}
							else if(temp.length()>1 && containsIS(temp)) {
								int index = 0;
								String s = "";
								int flag = 0;
								for(int j = 0;j<temp.length();j++) {
									if(temp.charAt(j) == ',') {
										flag = 1;
										continue;
									}
									else if(flag == 1){
										if(temp.charAt(j)>=48 && temp.charAt(j)<=57) {
											s+=temp.charAt(j);
										}
									}
								}
								OpcodeIns = s;
								
							}
						}
						temp = "";
					}
					else {
						temp+=line1.charAt(i);
					}
				}
				
				if(written == false ) {
					if(operand1!=-1 && operand2 == -1) {
						fr.write(lc+" "+OpcodeIns+" "+operand1+ " "+"\n");
					}
					else if(operand1==-1 && operand2 != -1) {
						fr.write(lc+" "+OpcodeIns+" "+operand2+"\n");
					}
					else if(operand1 == -1 && operand2 == -1) {
						fr.write(lc+" "+OpcodeIns+" "+"\n");
					}
					else {
						fr.write(lc+" "+OpcodeIns+" "+operand1+" "+operand2+"\n");
					}
					
				}
				
				line = readerICF.readLine();
			}
			
			
			readerICF.close();
			readerSym.close();
			readerLit.close();
			fr.close();
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}