package dictionary;

import java.util.Iterator;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V>  implements Dictionary<K, V>{

	private Node root;
	private int size;
	private V oldValue;// Rückgabeparameter
	@Override
	public V insert(K key, V value) {
		root = insertR(key,value,root);
		if (root != null) {
			root.parent = null;
		}
		return oldValue;
	}

	@Override
	public V search(K key) {
		Node<K,V> search = searchR(root, key);
		if(search==null) {
			return null;
		}
		return search.entry.getValue();
//		return (V) searchR(root, key).getEntry().getValue();
	}

	@Override
	public V remove(K key) {
		root = removeR(key, root);
		if (root != null) {
			root.parent = null;
		}
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new Iterator<Entry<K, V>>() {
			Node<K,V> current = root;
			boolean first = true;
			int index = 0;
			@Override
			public boolean hasNext() {
				boolean tmp = index < size;
				return tmp;
			}

			@Override
			public Entry<K, V> next() {
				if(first) {
					if (root != null) {
						first = false;
						current = leftMostDecendant(root);
						index++;
						return current.entry;
					} else {
						return null;
						//Error hasNext==false
					}
				} else {
					if(current.right != null) {
						current = leftMostDecendant(current.right);
						index++;
						return current.entry;
					} else {
						current = parentOfLeftMostAncestor(current);
						index++;
						return current.entry;
					}
				}
			}
		};
	}

	private Node<K, V> leftMostDecendant(Node<K, V> p) {
		assert p != null;
		while (p.left != null) {
			p = p.left;
		}
		return p;
	}

	private Node<K, V> parentOfLeftMostAncestor(Node<K, V> p) {
		assert p != null;
		while (p.parent != null && p.parent.right == p) {
			p = p.parent;
		}
		return p.parent; //kann auch null sein
	}

	public void prettyPrint() {
		StringBuilder s = new StringBuilder();
		prettyPrintR(s, root, 0);
		System.out.println(s);
	}

	private static void prettyPrintR(StringBuilder s, Node p, int depth) {
		if (p != null) {
			for(int i = 0; i < depth -1; i++) {
				s.append("\t");
			}
			if(depth > 0) {
				s.append("|__");
			}
			s.append(p.entry.getKey());
			if (p.parent != null) {
				s.append(" parent : " + p.parent.entry.getKey());
			}
			s.append("\n");
			if(p.left == null && p.right != null) {
				for(int i = 0; i < depth; i++) {
					s.append("\t");
				}
				if(depth > 0) {
					s.append("|__");
				}
				s.append("#").append("\n");
			}
			prettyPrintR(s,p.left,depth + 1);
			if(p.right == null && p.left != null) {
				for(int i = 0; i < depth; i++) {
					s.append("\t");
				}
				if(depth > 0) {
					s.append("|__");
				}
				s.append("#").append("\n");
			}
			prettyPrintR(s,p.right,depth + 1);
		}
	}



	private Node<K,V> searchR(Node<K,V> node, K key) {
		if(node==null) {
			return null;
		}else if(node.entry.getKey().compareTo(key) > 0) {
			return searchR(node.left, key);
		}else if(node.entry.getKey().compareTo(key) < 0) {
			return searchR(node.right, key);
		} else {
			return node;
		}
	}

	private Node<K,V> insertR(K key, V value, Node<K,V> p) {
		if (p == null) {
			p = new Node(key, value);
			oldValue = null;
			size++;
		}
		else if (key.compareTo(p.entry.getKey()) < 0) {
			p.left = insertR(key, value, p.left);
			if (p.left != null) {
				p.left.parent = p;
			}
		}
		else if (key.compareTo(p.entry.getKey()) > 0) {
			p.right = insertR(key, value, p.right);
			if (p.right != null) {
				p.right.parent = p;
			}
		}
		else {
			// Schlüssel bereits vorhanden:
			oldValue = p.entry.getValue();
			p.entry.setValue(value);
		}
		p = balance(p);
		return p;
	}

	private Node<K,V> removeR(K key, Node<K,V> p) {
		if(p ==null) {
			oldValue=null;
		}else if(key.compareTo(p.entry.getKey()) < 0) {
			p.left = removeR(key, p.left);
			if (p.left != null) {
				p.left.parent = p;
			}
		}
		else if (key.compareTo(p.entry.getKey()) > 0) {
			p.right = removeR(key,p.right);
			if (p.right != null) {
				p.right.parent = p;
			}
		}
		else if (p.left == null || p.right == null) {
			//  p muss gelöscht werden
			//  und hat  ein oder  kein Kind:
			oldValue = p.entry.getValue();
			p = (p.left!=null) ?p.left:p.right;
			size--;
		}else{
			//  p muss gelöscht werden  und hat  zwei Kinder:
			MinEntry<K,V> min = new MinEntry<>();
			p.right = getRemMinR(p.right, min);
			oldValue = p.entry.getValue();
			p.entry = new Entry<K,V>(min.key, min.value);
			size--;
		}
		p = balance(p);
		return p;
	}

	private Node<K, V> balance(Node<K, V> p) {
		if (p == null) {
			return null;
		}
		p.height = Math.max(getHeight(p.left), getHeight(p.right) + 1);
		if (getBalance(p) == -2) {
			if(getBalance(p.left) <= 0) {
				p = rotateRight(p);
			} else {
				p = rotateLeftRight(p);
			}
		} else if (getBalance(p) == 2) {
			if (getBalance(p.right) >= 0) {
				p = rotateLeft(p);
			} else {
				p = rotateRightLeft(p);
			}
		}
		return p;
	}

	private Node<K, V> rotateRight(Node<K, V> p) {
		assert p.left != null;
		Node<K, V> q = p.left;
		p.left = q.right;
		if (p.left != null) {
			p.left.parent = p;
		}
		q.right = p;
		if (q.right != null) {
			q.right.parent = q;
		}
		p.height = Math.max(getHeight(p.left), getHeight(p.right) + 1);
		q.height = Math.max(getHeight(q.left), getHeight(q.right) + 1);
		return q;
	}

	private Node<K, V> rotateLeft(Node<K, V> p) {
		assert p.right != null;
		Node<K, V> q = p.right;
		p.right = q.left;
		if (p.right != null) {
			p.right.parent = p;
		}
		q.left = p;
		if (q.left != null) {
			q.left.parent = q;
		}
		p.height = Math.max(getHeight(p.left), getHeight(p.right) + 1);
		q.height = Math.max(getHeight(q.left), getHeight(q.right) + 1);
		return q;
	}

	private Node<K, V> rotateLeftRight(Node<K, V> p) {
		assert p.left != null;
		p.left = rotateLeft(p.left);
		if (p.left != null) {
			p.left.parent = p;
		}
		return rotateRight(p);
	}

	private Node<K, V> rotateRightLeft(Node<K, V> p) {
		assert p.right != null;
		p.right = rotateRight(p.right);
		if (p.right != null) {
			p.right.parent = p;
		}
		return rotateLeft(p);
	}

	private int getHeight(Node<K, V> p) {
		if (p == null) {
			return -1;
		} else {
			return p.height;
		}
	}

	private int getBalance(Node<K, V> p) {
		if(p == null) {
			return 0;
		} else {
			return getHeight(p.right) - getHeight(p.left);
		}
	}

	private Node<K,V>  getRemMinR(Node<K,V> p, MinEntry<K,V> min) {
		assert p  != null;
		if (p.left == null) {
			min.key = p.entry.getKey();
			min.value = p.entry.getValue();
			p = p.right;
		} else
			p.left  = getRemMinR(p.left,  min);
		p = balance(p);
		return p;
	}

	private static class MinEntry<K, V> {
		private K key;
		private V value;
	}

	static class Node<K,V> {
		int height;
		Entry<K,V> entry;
		Node<K, V> parent; //Elternzeiger
		Node<K,V> left;   // linkes Kind
		Node<K,V> right;  // rechtes Kind

//		private Entry<K,V> getEntry() {
//			return entry;
//		}

		private Node(K key, V value) {
			height = 0;
			this.entry = new Entry<>(key, value);
			this.left = null;
			this.right = null;
			this.parent = null;
		}
	}
}