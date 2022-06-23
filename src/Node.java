import java.util.*;
public class Node {
	
	
	private String data;
	private Node parent;
	private List <Node> childs= new ArrayList<Node>();
	
	public Node(String data) {
		this.data=data;
	}
	
	public void setParent(Node p) {
		parent=p;
	}
	public Node getParent() {
		return parent;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public Node getChild(int index) {
		return childs.get(index);
	}
	public void addChild(String newdata) {
		childs.add(new Node(newdata));
	}
	
	public int howeverManyChilds() {
		return childs.size();
	}
	
	public boolean freqCheck(String goal) {
		char[] fchars;
		char[] cchars;
		Dictionary<Character, Integer> fcount = new Hashtable<Character, Integer>();
		Dictionary<Character, Integer> ccount = new Hashtable<Character, Integer>();
		
		fchars = goal.toCharArray();
		
		
		for (int i = 0; i < fchars.length; i++) {
			if(fcount.get(fchars[i])!=null) {
				fcount.put(fchars[i],fcount.get(fchars[i])+1);
			}
			else
				fcount.put(fchars[i],1);
			
		}
		
		cchars= data.toCharArray();
		
		for (int i = 0; i < cchars.length; i++) {
			if(ccount.get(cchars[i])!=null) {
				ccount.put(cchars[i],ccount.get(cchars[i])+1);
			}
			else
				ccount.put(cchars[i],1);
			
		}
		
		
		
		return freqcheck(ccount,fcount,goal.length());
		
	}
	
	private boolean freqcheck(	Dictionary<Character, Integer> cc, 	Dictionary<Character, Integer> fc, int length) {
		
		int currentlength=0;
		Enumeration enu1 = cc.keys();
		while(enu1.hasMoreElements())
		{
			currentlength+=cc.get((char)enu1.nextElement());
		}
		if(currentlength>length) {
			return false;
		}
		
		Enumeration enu = fc.keys();
		if(cc.equals(fc)) {
			return false;
		}
		while(enu.hasMoreElements())
		{
			char a= (char)enu.nextElement();
			
			if(cc.get(a)!=null&&fc.get(a)<cc.get(a))
			{
				return false;
			}
		}
		return true;
	}
	

}
