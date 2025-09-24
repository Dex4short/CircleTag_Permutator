package oop;

public class Node{
	private static final String tokens[] = {"", "I+", "I-", "V+", "V-", "X+", "X-", "L+", "L-", "C+", "C-", "D+", "D-", "M+", "M-"};
	private int i=-1, cap;
	private Node next_node;
	
	public Node(String initial_token) {
		i = getIndex(initial_token);
		cap = tokens.length;
	}
	public void next() {
		i++;
		if(i >= cap) {
			if(next_node != null) next_node.next();
			i=0;
		}
	}
	public void setNextNode(Node node) {
		this.next_node = node;
	}
	public Node getNextNode() {
		return next_node;
	}
	public void setCap(int cap) {
		this.cap = cap;
	}
	public int getCap() {
		return cap;
	}
	public String getToken() {
		return tokens[i];
	}
	public int getTokenIndex() {
		return i;
	}
	
	//helpers
	public static final int getIndex(String token) {
		for(int t=0; t<tokens.length; t++) {
			if(tokens[t].equals(token)) return t;
		}
		return -1;
	}
}