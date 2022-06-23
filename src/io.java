import java.io.*;
import java.util.*;

public class io {
	private static String start;
	static boolean flag = false;

	public static String getStart() {
		return start;
	}

	public static String getInput(List<Character> alphabet) {
		Scanner sc = new Scanner(System.in);
		String input;
		boolean flag = true;
		while (true) {
			try {
				System.out.println("Please enter string for verification: ");
				input = sc.nextLine();
			} catch (Exception e) {
				System.out.println("Invalid input: String contains non-alpabetical characters!");
				continue;
			}
			char[] ch = input.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				if (!alphabet.contains(ch[i])) {
					System.out.println("Invalid input: String contains non-alpabetical characters!");
					flag = false;
					break;
				} else
					flag = true;
			}
			if (!flag)
				continue;
			else
				break;
		}
		sc.close();
		return input;
	}

	public static boolean contains_empty(String txtFile) throws IOException {
		BufferedReader bf;
		boolean empty = false;
		try {
			bf = new BufferedReader(new FileReader(txtFile));

			for (String line; (line = bf.readLine()) != null;) {
				if (line.contains("#")) {
					empty = true;
				}

			}
			bf.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such a file");
			e.printStackTrace();
		}
		return empty;
	}

	public static List<List<String>> readRulesWithEmpty(String txtFile, List<Character> alphabet,
			List<String> letter_used) throws IOException {
		BufferedReader bf;
		List<List<String>> rules = new ArrayList<List<String>>();

		try {
			bf = new BufferedReader(new FileReader(txtFile));
			for (String line; (line = bf.readLine()) != null;) {
				List<String> rule = new ArrayList<String>();
				line = line.replace(">", ";");
				line = line.replace("|", ";");
				for (int i = 0; i < line.length(); i++) {
					if (!alphabet.contains(line.charAt(i)) && line.charAt(i) != ';'
							&& !Character.isUpperCase(line.charAt(i))) {
						alphabet.add(line.charAt(i));

					}
					if (!letter_used.contains(line.charAt(i)) && Character.isAlphabetic(line.charAt(i))
							&& Character.isUpperCase(line.charAt(i))) {
						letter_used.add(Character.toString(line.charAt(i)));
					}
				}
				String[] st = line.split(";");

				for (int i = 0; i < st.length; i++) {
					rule.add(st[i]);
				}
				rules.add(rule);
			}
			bf.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such a file");
			e.printStackTrace();
		}
		return rules;
	}

	public static HashMap<String, List<String>> readRules(String txtFile, List<Character> alphabet) throws IOException {
		BufferedReader bf;
		HashMap<String, List<String>> rules = new HashMap<String, List<String>>();

		try {
			bf = new BufferedReader(new FileReader(txtFile));
			for (String line; (line = bf.readLine()) != null;) {

				line = line.replace(">", ";");
				line = line.replace("|", ";");
				for (int i = 0; i < line.length(); i++) {
					if (!flag) {
						start = Character.toString(line.charAt(i));
						flag = true;
					}
					if (!alphabet.contains(line.charAt(i)) && line.charAt(i) != ';' && !Character.isUpperCase(line.charAt(i))) {
						alphabet.add(line.charAt(i));
						
					}
				}

				String[] st = line.split(";");

				if (rules.isEmpty())
					rules.put(st[0], new ArrayList<>());
				for (int i = 0; i < rules.size(); i++) {
					if (!rules.containsKey(st[0]))
						rules.put(st[0], new ArrayList<>());
				}
				for (int i = 1; i < st.length; i++) {
					rules.get(st[0]).add(st[i]);
				}
			}
			bf.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such a file");
			e.printStackTrace();
		}

		return rules;
	}
}
