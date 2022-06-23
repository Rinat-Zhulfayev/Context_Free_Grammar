import java.util.*;

public class CNF {
	private static String start;

	public static String getStart() {
		return start;
	}

	private static HashMap<String, List<String>> convertToMap(List<List<String>> rules,
			HashMap<String, List<String>> rules1) {
		for (List<String> rule : rules) {
			rules1.put(rule.get(0), new ArrayList<>());
			for (int i = 1; i < rule.size(); i++) {
				rules1.get(rule.get(0)).add(rule.get(i));
			}
		}
		return rules1;

	}

	public static HashMap<String, List<String>> convetToCNF(List<List<String>> rules, List<String> usedLetters,
			List<Character> alphabet, HashMap<String, List<String>> rules_map) {
		System.out.println("Initial grammar:");
		printRules(rules);
		List<String> new_start = new ArrayList<String>();
		for (int i = 90; i > 65; i--) {
			if (!usedLetters.contains(Character.toString(i))) {
				new_start.add(Character.toString(i));
				start = Character.toString(i);
				break;
			}
		}

		new_start.add(rules.get(0).get(0));

		rules.add(new_start);
		remove_empty(rules);

		remove_unit(rules);

		simplify(rules, usedLetters, alphabet);

		System.out.println("Grammar in Chebyshev normal form: ");
		printRules(rules);
		rules_map = convertToMap(rules, rules_map);
		return rules_map;
	}

	public static void remove_empty(List<List<String>> rules) {

		List<String> nullable = new ArrayList<String>();

		for (int i = 0; i < rules.size(); i++) {
			for (int j = 1; j < rules.get(i).size(); j++) {
				if (rules.get(i).get(j).equals("#")) {
					nullable.add(rules.get(i).get(0));
					rules.get(i).remove(j);
				}
			}
		}

		String update = "";
		for (int k = 0; k < nullable.size(); k++) {
			for (int i = 0; i < rules.size(); i++) {
				for (int j = 1; j < rules.get(i).size(); j++) {
					// if two or more nullable
					if (rules.get(i).get(j).contains(nullable.get(k)) && rules.get(i).get(j).length() >= 3) {
						char[] chars = rules.get(i).get(j).toCharArray();
						char temp;
						for (int l = 0; l < chars.length; l++) {
							if (chars[l] == nullable.get(k).toCharArray()[0]) {
								temp = chars[l];
								chars[l] = ' ';
								rules.get(i).add(String.valueOf(chars).strip());
								chars[l] = temp;
								j++;
							}
						}
					}
					if (rules.get(i).get(j).contains(nullable.get(k)) && rules.get(i).get(j).length() == 2) {
						update = rules.get(i).get(j).replace(nullable.get(k), "");
						rules.get(i).add(update);
						j++;
					}
					if (rules.get(i).get(j).contains(nullable.get(k)) && rules.get(i).get(j).length() == 1) {
						update = "#";
						rules.get(i).add(update);
						j++;
					}
				}
			}
		}
		for (int i = 0; i < rules.size(); i++) {
			for (int j = 1; j < rules.get(i).size(); j++) {
				if (rules.get(i).get(j).equals("#")) {
					remove_empty(rules);
				}
			}
		}
	}

	public static void remove_unit(List<List<String>> rules) {
		String unit;
		for (int i = 0; i < rules.size(); i++) {
			for (int j = 1; j < rules.get(i).size(); j++) {
				if (rules.get(i).get(j).length() == 1 && Character.isUpperCase(rules.get(i).get(j).charAt(0))) {
					unit = rules.get(i).get(j);
					rules.get(i).remove(j);
					for (List<String> rule : rules) {
						if (rule.get(0).equals(unit)) {
							for (int k = 1; k < rule.size(); k++) {
								if (!rules.get(i).contains(rule.get(k)))
									rules.get(i).add(rule.get(k));
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < rules.size(); i++) {
			for (int j = 1; j < rules.get(i).size(); j++) {
				if (rules.get(i).get(j).length() == 1 && Character.isUpperCase(rules.get(i).get(j).charAt(0))) {
					remove_unit(rules);
				}
			}
		}
	}

	public static void simplify(List<List<String>> rules, List<String> usedLetters, List<Character> alphabet) {

		String newString = "";
		for (int i = 0; i < rules.size(); i++) {
			for (int j = 1; j < rules.get(i).size(); j++) {
				if (rules.get(i).get(j).length() >= 3) {
					List<String> newRule = new ArrayList<String>();
					newString = getAppropriateLetter(usedLetters);
					usedLetters.add(newString);
					newRule.add(newString);
					newRule.add(rules.get(i).get(j).charAt(1) + "" + rules.get(i).get(j).charAt(2));
					if (!rules.contains(newRule) && !newRule.get(0).equals(newRule.get(1)))
						rules.add(newRule);
					for (int k = 0; k < rules.size(); k++) {
						for (int k2 = 1; k2 < rules.get(k).size(); k2++) {
							if (rules.get(k).get(k2).contains(newRule.get(1)) && rules.get(k).get(k2).length() >= 3) {
								String removed = rules.get(k).remove(k2);
								rules.get(k).add(removed.charAt(0) + newRule.get(0) + removed.substring(3));
							}
						}
					}
				}
				if (rules.get(i).get(j).length() == 2 && alphabet.contains(rules.get(i).get(j).charAt(0))) {
					newString = getAppropriateLetter(usedLetters);
					usedLetters.add(newString);
					List<String> newRule = new ArrayList<String>();
					newRule.add(newString);
					newRule.add(rules.get(i).get(j).charAt(0) + "");
					if (!rules.contains(newRule))
						rules.add(newRule);

					for (int k = 0; k < rules.size(); k++) {
						for (int k2 = 1; k2 < rules.get(k).size(); k2++) {
							if (rules.get(k).get(k2).contains(newRule.get(1)) && rules.get(k).get(k2).length() == 2) {
								String removed = rules.get(k).remove(k2);
								rules.get(k).add(newString + removed.charAt(1));
							}
						}
					}
				} else if (rules.get(i).get(j).length() == 2 && alphabet.contains(rules.get(i).get(j).charAt(1))) {
					newString = getAppropriateLetter(usedLetters);
					usedLetters.add(newString);
					List<String> newRule = new ArrayList<String>();
					newRule.add(newString);
					newRule.add(rules.get(i).get(j).charAt(1) + "");
					if (!rules.contains(newRule))
						rules.add(newRule);

					for (int k = 0; k < rules.size(); k++) {
						for (int k2 = 1; k2 < rules.get(k).size(); k2++) {
							if (rules.get(k).get(k2).contains(newRule.get(1)) && rules.get(k).get(k2).length() == 2) {
								String removed = rules.get(k).remove(k2);
								rules.get(k).add(removed.charAt(0) + newString);
							}
						}
					}
				}
			}
		}
	}

	public static void printRules(List<List<String>> rules) {
		for (int i = 0; i < rules.size(); i++) {
			System.out.print(rules.get(i).get(0) + " --> ");
			for (int j = 1; j < rules.get(i).size(); j++) {
				System.out.print(rules.get(i).get(j));
				if (j < rules.get(i).size() - 1)
					System.out.print(" | ");
			}
			System.out.println();
		}
	}

	public static String getAppropriateLetter(List<String> used_letters) {
		for (int i = 65; i <= 91; i++) {
			if (!used_letters.contains(Character.toString(i)))
				return Character.toString(i);
		}
		return null;
	}

}
