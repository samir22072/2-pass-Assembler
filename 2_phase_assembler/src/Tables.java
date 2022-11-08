import java.util.HashMap;
import java.util.AbstractMap;
import java.util.Map;

class Pair {
	// Return a map entry (key-value pair) from the specified values
	public static <T, U> Map.Entry<T, U> of(T first, U second) {
		return new AbstractMap.SimpleEntry<>(first, second);
	}
}

public class Tables {

	HashMap<String, Map.Entry<String, Integer>> OPTAB;
	HashMap<String, Integer> RG, CC;

	public Tables() {
		OPTAB = new HashMap<>();
		CC = new HashMap<>();
		RG = new HashMap<>();

		OPTAB.put("STOP", Pair.of("IS", 0));
		OPTAB.put("ADD", Pair.of("IS", 1));
		OPTAB.put("SUB", Pair.of("IS", 2));
		OPTAB.put("MULT", Pair.of("IS", 3));
		OPTAB.put("MOVER", Pair.of("IS", 4));
		OPTAB.put("MOVEM", Pair.of("IS", 5));
		OPTAB.put("COMP", Pair.of("IS", 6));
		OPTAB.put("BC", Pair.of("IS", 7));
		OPTAB.put("DIV", Pair.of("IS", 8));
		OPTAB.put("READ", Pair.of("IS", 9));
		OPTAB.put("PRINT", Pair.of("IS", 10));
		OPTAB.put("START", Pair.of("AD", 1));
		OPTAB.put("END", Pair.of("AD", 2));
		OPTAB.put("ORIGIN", Pair.of("AD", 3));
		OPTAB.put("EQU", Pair.of("AD", 4));
		OPTAB.put("LTORG", Pair.of("AD", 5));
		OPTAB.put("DC", Pair.of("DL", 1));
		OPTAB.put("DS", Pair.of("DL", 2));
		CC.put("LT", 1);
		CC.put("LE", 2);
		CC.put("EQ", 3);
		CC.put("GT", 4);
		CC.put("GE", 5);
		CC.put("ANY", 6);
		RG.put("AREG", 1);
		RG.put("BREG", 2);
		RG.put("CREG", 3);
		RG.put("DREG", 4);

	}

	public String getType(String s) {
		s = s.toUpperCase();
		if (OPTAB.containsKey(s)) {
			if (OPTAB.get(s).getKey() == "AD") {
				return "AD";
			} else if (OPTAB.get(s).getKey() == "IS") {
				return "IS";
			} else if (OPTAB.get(s).getKey() == "DL") {
				return "DL";
			} 
		}
		else if (CC.containsKey(s)) {
			return "CC";
		} else if (RG.containsKey(s)) {
			return "RG";
		}

		return "";

	}

	public int getCode(String s) {
		s = s.toUpperCase();
		if (OPTAB.containsKey(s)) {
			if (OPTAB.get(s).getKey() == "AD") {
				return OPTAB.get(s).getValue();
			} else if (OPTAB.get(s).getKey() == "IS") {
				return OPTAB.get(s).getValue();
			} else if (OPTAB.get(s).getKey() == "DL") {
				return OPTAB.get(s).getValue();
			} 
		}
		else if (CC.containsKey(s)) {
			return CC.get(s);
		} else if (RG.containsKey(s)) {
			return RG.get(s);
		}

		return -1;

	}
}