package com.purbon.db.index;

public class RBTree<Key extends Comparable<Key>, Value> {

	enum Color {
		RED, BLACK
	};

	class RBTreeNode {
		RBTreeNode leftNode=null;
		RBTreeNode rightNode=null;
		RBTreeNode parent=null;
		Key key;
		Color color;
		
		public boolean isLeaf() {
			return (leftNode == null) && (rightNode == null);
		}
	}

	private RBTreeNode root;

	public RBTree() {
		root = null;
	}
	
	public String toString() {
		return toString(root, 0);
	}
	
	private String toString(RBTreeNode node, int level) {
		if (node == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append("("+level+")"+node.key+","+node.color);
		sb.append(";");
		level += 1;
		sb.append(toString(node.leftNode, level));
 		sb.append(toString(node.rightNode, level));
  		return sb.toString();
	}
	
	public void add(Key key) {
 		RBTreeNode node = addRawNode(key);
		while ((node != root) && (node.parent.color == Color.RED) ) {
			if (node.parent == node.parent.parent.leftNode) {
				RBTreeNode y = node.parent.parent.rightNode;
				if (y.color == Color.RED) {
					node.parent.color = Color.BLACK;
					y.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
				}
				else {
					if (node == node.parent.rightNode) {
						node = node.parent;
						left_rotate(node);
					}
					node.parent.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
					right_rotate(node.parent.parent);
				}
			}
			else {
				RBTreeNode y = node.parent.parent.leftNode;
				if (y.color == Color.RED) {
					node.parent.color = Color.BLACK;
					y.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
				}
				else {
					if (node == node.parent.leftNode) {
						node = node.parent;
						right_rotate(node);
					}
					node.parent.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
					left_rotate(node.parent.parent);
				}
			}
		}
		root.color = Color.BLACK;
 	}
	
	public RBTreeNode search(Key key) {
		return search(key, root);
	}
	
	public void delete(Key key) {
		RBTreeNode node = search(key);
		if (node.leftNode != null && node.rightNode != null) {
			// node with two non-leaf children
			RBTreeNode newNode = find_max(node.leftNode);
			node.key = newNode.key;
			RBTreeNode child = null;
			if (newNode.parent.leftNode == newNode) {
				if (newNode.rightNode != null) {
					child = newNode.rightNode;
				} else if (newNode.leftNode != null) {
					child = newNode.leftNode;
				}
				newNode.parent.leftNode = child;
			} else if (newNode.parent.rightNode == newNode) {
 				if (newNode.rightNode != null) {
					child = newNode.rightNode;
				} else if (newNode.leftNode != null) {
					child = newNode.leftNode;
				}
				newNode.parent.rightNode = child;
			}
			child.parent = newNode.parent;
			newNode = null;
			
		} else {
			// one is a non leaf children.
			RBTreeNode child = node.rightNode.isLeaf() ? node.leftNode : node.rightNode;
			replace_with(node, child);
			if (node.color == Color.BLACK) {
				if (child.color == Color.RED) {
					child.color = Color.BLACK;
				} else {
					rebalance(child);
				}
			}
		}
	}
	
	private void rebalance(RBTreeNode child) {
		if (child.parent != null) {
			// delete_case 2
			RBTreeNode s = child.parent.leftNode == child ? child.parent.rightNode
					: child.parent.leftNode;
			if (s.color == Color.RED) {
				child.parent.color = Color.RED;
				s.color = Color.BLACK;
				if (child == child.parent.leftNode)
					left_rotate(child.parent);
				else
					right_rotate(child.parent);
			}
			// delete_case 3
			if (child.parent.color == Color.BLACK && s.color == Color.BLACK
					&& s.leftNode.color == Color.BLACK
					&& s.rightNode.color == Color.BLACK) {
				s.color = Color.RED;
				rebalance(child.parent);
			} else {
				// delete_case 4
				if (child.parent.color == Color.RED && s.color == Color.BLACK
						&& s.leftNode.color == Color.BLACK
						&& s.rightNode.color == Color.BLACK) {
					s.color = Color.RED;
					child.parent.color = Color.BLACK;
				} else {
					// delete_case 5
					if (s.color == Color.BLACK) {
						if ((child.parent.leftNode == child)
								&& s.rightNode.color == Color.BLACK
								&& s.leftNode.color == Color.RED) {
							s.color = Color.RED;
							s.leftNode.color = Color.BLACK;
							right_rotate(s);
						} else if (child == child.parent.rightNode
								&& s.leftNode.color == Color.BLACK
								&& s.rightNode.color == Color.RED) {
							s.color = Color.RED;
							s.rightNode.color = Color.BLACK;
							left_rotate(s);
						}
					}
					// delete_case 6
					s.color = child.parent.color;
					child.parent.color = Color.BLACK;
					if (child == child.parent.leftNode) {
						s.rightNode.color = Color.BLACK;
						left_rotate(child.parent);
					} else {
						s.leftNode.color = Color.BLACK;
						right_rotate(child.parent);
					}
				}
			}

		}
	}
	
	private RBTreeNode find_max(RBTreeNode node) {
		if (node.leftNode != null && node.rightNode != null) {
			if (node.leftNode.key.compareTo(node.rightNode.key) > 0) {
				return find_max(node.leftNode);
			} else
				return find_max(node.rightNode);
		} else if (node.leftNode == null && node.rightNode != null) {
			return find_max(node.rightNode);
		} else if (node.rightNode == null && node.leftNode != null) {
			return find_max(node.leftNode);
		} else {
			return node;
		}
 	}

	private void replace_with(RBTreeNode node,RBTreeNode new_node) {
		new_node.parent = node.parent;
		if (node.parent.leftNode == node) {
 			node.parent.leftNode = new_node;
		} else if (node.parent.rightNode == node) {
 			node.parent.rightNode = new_node;
		}
		node = null;
		
	}
	
	private RBTreeNode search(Key key, RBTreeNode node) {
		if (key.compareTo(node.key) == 0) {
			return node;
		} else if (key.compareTo(node.key) < 0) {
			return search(key, node.leftNode);
		} else if (key.compareTo(node.key) > 0) {
			return search(key, node.rightNode);
		}
		return null;
	}

	private void left_rotate(RBTreeNode x) {
 		RBTreeNode y = x.rightNode;
 		x.rightNode = y.leftNode;
 		if (y.leftNode != null)
 			y.leftNode.parent = x;
 		y.parent = x.parent;    
 		if ( x.parent == null ) 
 		   root = y;
 		else {
 			if (x == x.parent.leftNode) 
 				x.parent.leftNode = y;
 			else
 				x.parent.rightNode = y;
 		}
 		x.leftNode = y;
 		x.parent = y;
	}

	private void right_rotate(RBTreeNode x) {
		RBTreeNode y = x.leftNode;
 		x.leftNode = y.rightNode;
 		if (y.rightNode != null)
 			y.rightNode.parent = x;
 		y.parent = x.parent;    
 		if ( x.parent == null ) 
 		   root = y;
 		else {
 			if (x == x.parent.rightNode) 
 				x.parent.rightNode = y;
 			else
 				x.parent.leftNode = y;
 		}
 		x.rightNode = y;
 		x.parent = y;
	}

	private RBTreeNode addRawNode(Key key) {
		if (root == null) {
 			root = new RBTreeNode();
			root.key = key;
			root.color = Color.RED;
			root.parent = null;
			return root;
		} else
			return addRawNode(key, root);
	}

	private RBTreeNode addRawNode(Key key, RBTreeNode node) {
 		if (key.compareTo(node.key) < 0) {
			if (node.leftNode == null) {
 				node.leftNode = new RBTreeNode();
				node.leftNode.key = key;
				node.leftNode.color = Color.RED;
				node.leftNode.parent = node;
				return node.leftNode;
			} else {
				return addRawNode(key, node.leftNode);
			}
		} else {
			if (node.rightNode == null) {
				node.rightNode = new RBTreeNode();
				node.rightNode.key = key;
				node.rightNode.color = Color.RED;
				node.rightNode.parent = node;
				return node.rightNode;
			} else {
				return addRawNode(key, node.rightNode);
			}
		}
	}
 
}
