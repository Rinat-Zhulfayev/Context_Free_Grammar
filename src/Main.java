import java.io.IOException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String txtFile = "cfg.txt";

		HashMap<String, List<String>> rules_map = new HashMap<String, List<String>>();
		List<List<String>> rules_list = new ArrayList<>();

		List<Character> alphabet = new ArrayList<Character>();
		List<String> letter_used = new ArrayList<>();
		String start;
		if (io.contains_empty(txtFile)) {
			rules_list = io.readRulesWithEmpty(txtFile, alphabet, letter_used);
			rules_map = CNF.convetToCNF(rules_list, letter_used, alphabet, rules_map);
			start = CNF.getStart();
		} else {
			rules_map = io.readRules(txtFile, alphabet);
			start = io.getStart();
			System.out.println(rules_map);
		}
		System.out.println("Alphabet: " + alphabet);

		String input = io.getInput(alphabet);
		
		boolean findAllPaths = false;

		ParseTree lang = new ParseTree(start, rules_map);

		lang.inLanguage(lang.root, input, findAllPaths);

	}
}