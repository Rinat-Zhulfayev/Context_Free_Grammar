import java.util.*;

public class ParseTree {
	private boolean flag = true;
	public Node root;
	HashMap<String, List<String>> rules;

	public ParseTree(String start, HashMap<String, List<String>> ruleinput) {
		root = new Node(start);
		rules = ruleinput;
	}

	public void inLanguage(Node currentnode, String input, boolean allpaths) {
		if (flag || allpaths) {
			Node cache = currentnode;

			for (int i = 0; i < cache.getData().length(); i++) {
				if (rules.containsKey(Character.toString(cache.getData().charAt(i)))) {
					for (int j = 0; j < rules.get(Character.toString(cache.getData().charAt(i))).size(); j++) {
						cache.addChild(derive(cache.getData(), j, i, rules));
					}
				}
			}

			for (int c = 0; c < cache.howeverManyChilds(); c++) {
				cache.getChild(c).setParent(cache);
			}


			for (int c = 0; c < cache.howeverManyChilds(); c++) {
				if (cache.getChild(c).getData().equals(input)) {
					System.out.println("--------------------------------------");
					recprint(cache.getChild(c));
					flag = false;

				}
				if (cache.getChild(c).freqCheck(input)) {
					inLanguage(cache.getChild(c), input, allpaths);
				}
			}
		}
	}

	private String recprint(Node n) {

		if (n.getParent() != null) {
			recprint(n.getParent());
			System.out.print(n.getParent().getData() + " => ");
		}
		System.out.println(n.getData());
		return "";
	}

	private String derive(String start, int rulenum, int index, HashMap<String, List<String>> rules) {
		String fstr = start;

		fstr = start.substring(0, index) + rules.get(Character.toString(start.charAt(index))).get(rulenum)
				+ start.substring(index + 1, start.length());
		return fstr;
	}

	
}
