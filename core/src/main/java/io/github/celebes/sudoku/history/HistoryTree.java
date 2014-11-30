package io.github.celebes.sudoku.history;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

public class HistoryTree {
	DefaultMutableTreeNode root;
	DefaultMutableTreeNode current;
	DefaultMutableTreeNode prev;
	
	private int movesCounter = 0;
	
	public HistoryTree(Move m) {
		movesCounter++;
		m.setNumber(movesCounter);
		
		root = new DefaultMutableTreeNode("root");
		current = new DefaultMutableTreeNode(m);
		root.add(current);
		prev = root;
	}
	
	public void add(Move m) {
		movesCounter++;
		m.setNumber(movesCounter);
		
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(m);
		current.add(newNode);
		prev = current;
		current = newNode;
	}
	
	public void back() {
		if(current.isRoot() == true) {
			return;
		}
		
		DefaultMutableTreeNode temp = prev;
		current = prev;
		prev = (DefaultMutableTreeNode) temp.getParent();
	}
	
	public boolean movePossible(Move m) {
		return checkIfMovePossibleRecursive(m, current);
	}
	
	public boolean checkIfMovePossibleRecursive(Move m, DefaultMutableTreeNode node) {
		boolean result = true;
		
		if(node.isRoot()) {
			return true;
		}
		
		if(((Move)node.getUserObject()).equals(m)) {
			return false;
		} else {
			Enumeration<DefaultMutableTreeNode> e = node.children();
			
			while(e.hasMoreElements()) {
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();
				result = result &&  checkIfMovePossibleRecursive(m, n);
			}
		}
		
		return result;
	}
	
	public DefaultMutableTreeNode getCurrentNode() {
		return current;
	}
	
	public Move getCurrentMove() {
		if(current.isRoot()) {
			return null;
		} else {
			return (Move)current.getUserObject();
		}
	}
	
	@Override
	public String toString() {
		return "MOVES HISTORY TREE:\n\n" + recursiveMasterpiece(root);
	}
	
	private String recursiveMasterpiece(DefaultMutableTreeNode node) {
		Enumeration<DefaultMutableTreeNode> e = node.children();
		
		String result = "";
		String spaces = "";
		
		for(int i=0; i<node.getLevel()+1; i++) {
			spaces += "    ";
		}
		
		if(node.isRoot()) {
			result += "root\n";
		}
		
		if(e.hasMoreElements()) {
			while(e.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
				result += spaces + ((Move)child.getUserObject()).toString() + "\n" + recursiveMasterpiece(child);
			}
			return result;
		} else {
			return "";
		}
	}

	public String toTGF() {
		String result = recursiveNodesTGF(root) + "#\n" + recursiveEdgesTGF(root);
		return result;
	}
	
	private String recursiveEdgesTGF(DefaultMutableTreeNode node) {
		Enumeration<DefaultMutableTreeNode> e = node.children();
		
		String edges = "";
		
		String parent = "";
		
		if(node.isRoot()) {
			parent += "1 ";
		} else {
			parent += (((Move)node.getUserObject()).getNumber() + 1) + " ";
		}
		
		if(e.hasMoreElements()) {
			while(e.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
				edges += parent + (((Move)child.getUserObject()).getNumber() + 1) + "\n";
				edges += recursiveEdgesTGF(child);
			}
			
			return edges;
		} else {
			return "";
		}
	}

	private String recursiveNodesTGF(DefaultMutableTreeNode node) {
		Enumeration<DefaultMutableTreeNode> e = node.children();
		
		String nodes = "";
		
		if(node.isRoot()) {
			nodes += "1 root\n";
		}
		
		if(e.hasMoreElements()) {
			while(e.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
				nodes += (((Move)child.getUserObject()).getNumber() + 1) + " " + ((Move)child.getUserObject()).toString() + "\n" + recursiveNodesTGF(child);
			}
			return nodes;
		} else {
			return "";
		}
	}
	
}
