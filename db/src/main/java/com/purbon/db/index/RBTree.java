package com.purbon.db.index;

public class RBTree<Key extends Comparable<Key>, Value> {

	enum Color {
		RED, BLACK
	};

	@SuppressWarnings("hiding")
	class RBTreeNode<Key extends Comparable<Key>, Value>  {
		RBTreeNode<Key, Value> leftNode=null;
		RBTreeNode<Key, Value> rightNode=null;
		RBTreeNode<Key, Value> parent=null;
		Key key;
		Color color;
		
		public boolean isLeaf() {
			return (leftNode == null) && (rightNode == null);
		}
		
		public String toString() {
			return key+" "+color;
		}
	}

	private RBTreeNode<Key, Value> root;

	public RBTree() {
		root = null;
	}
	
	public String toString() {
		return toString(root, 0);
	}

	public RBTreeNode getRoot() {
		return root;
	}
	
	private String toString(RBTreeNode<Key, Value> node, int level) {
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
 		RBTreeNode<Key, Value> node = addRawNode(key);
		while ((node != root) && (node.parent.color == Color.RED) ) {
			if (node.parent == node.parent.parent.leftNode) {
				RBTreeNode<Key, Value> y = node.parent.parent.rightNode;
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
				RBTreeNode<Key, Value> y = node.parent.parent.leftNode;
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
	
	public RBTreeNode<Key, Value> search(Key key) {
		return search(key, root);
	}
	
	public void delete(Key key) {
		RBTreeNode<Key, Value> node = search(key);
		if (node.leftNode != null && node.rightNode != null) {
			// node with two non-leaf children
			RBTreeNode<Key, Value> newNode = find_max(node.leftNode);
  			node.key = newNode.key;
			RBTreeNode<Key, Value> child = null;
			if (newNode.parent.leftNode == newNode) {
				if (newNode.rightNode != null) {
					child = newNode.rightNode;
 				} else if (newNode.leftNode != null) {
					child = newNode.leftNode;
				} else  {
					child = newNode; 
				} 
				newNode.parent.leftNode = null;
			} else if (newNode.parent.rightNode == newNode) {
 				if (newNode.rightNode != null) {
					child = newNode.rightNode;
				} else if (newNode.leftNode != null) {
					child = newNode.leftNode;
				} else  {
					child = newNode; 
				} 
 				newNode.parent.rightNode = null;
 			}
			child.parent = newNode.parent;
			newNode = null;
			
		} else {
			// one is a non leaf children.
			RBTreeNode<Key, Value> child = node.rightNode==null ? node.leftNode : node.rightNode;
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
	
	private void rebalance(RBTreeNode<Key, Value> child) {
		if (child.parent != null) {
			// delete_case 2
			RBTreeNode<Key, Value> s = child.parent.leftNode == child ? child.parent.rightNode
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
	
	private RBTreeNode<Key, Value> find_max(RBTreeNode<Key, Value> node) {
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

	private void replace_with(RBTreeNode<Key, Value> node,RBTreeNode<Key, Value> new_node) {
		new_node.parent = node.parent;
		if (node.parent.leftNode == node) {
 			node.parent.leftNode = new_node;
		} else if (node.parent.rightNode == node) {
 			node.parent.rightNode = new_node;
		}
		node = null;
		
	}
	
	private RBTreeNode<Key, Value> search(Key key, RBTreeNode<Key, Value> node) {
		if (key.compareTo(node.key) == 0) {
			return node;
		} else if (key.compareTo(node.key) < 0) {
			return search(key, node.leftNode);
		} else if (key.compareTo(node.key) > 0) {
			return search(key, node.rightNode);
		}
		return null;
	}

	private void left_rotate(RBTreeNode<Key, Value> x) {
 		RBTreeNode<Key, Value> y = x.rightNode;
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

	private void right_rotate(RBTreeNode<Key, Value> x) {
		RBTreeNode<Key, Value> y = x.leftNode;
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

	private RBTreeNode<Key, Value> addRawNode(Key key) {
		if (root == null) {
 			root = new RBTreeNode<Key, Value>();
			root.key = key;
			root.color = Color.RED;
			root.parent = null;
			return root;
		} else
			return addRawNode(key, root);
	}

	private RBTreeNode<Key, Value> addRawNode(Key key, RBTreeNode<Key, Value> node) {
 		if (key.compareTo(node.key) < 0) {
			if (node.leftNode == null) {
 				node.leftNode = new RBTreeNode<Key, Value>();
				node.leftNode.key = key;
				node.leftNode.color = Color.RED;
				node.leftNode.parent = node;
				return node.leftNode;
			} else {
				return addRawNode(key, node.leftNode);
			}
		} else {
			if (node.rightNode == null) {
				node.rightNode = new RBTreeNode<Key, Value>();
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
