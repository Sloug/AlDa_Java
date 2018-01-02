package dicitonary;

import java.util.Iterator;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V>  implements Dictionary<K, V>{

	private Node root;
	private int size;
	private V oldValue;// Rückgabeparameter
	@Override
	public V insert(K key, V value) {
		root = insertR(key,value,root);
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
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return null;
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
			s.append(p.entry.getKey()).append("\n");
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
		}else if(node.entry.getKey().compareTo(key) < 0) {
			return searchR(node.left, key);
		}else if(node.entry.getKey().compareTo(key) > 0) {
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
		else if (key.compareTo(p.entry.getKey()) < 0)
			p.left = insertR(key, value, p.left);
		else if (key.compareTo(p.entry.getKey()) > 0)
			p.right = insertR(key, value, p.right);
		else {
			// Schlüssel bereits vorhanden:
			oldValue = p.entry.getValue();
			p.entry.setValue(value);
		}
		return p;
	}

	private Node<K,V> removeR(K key, Node<K,V> p) {
		if(p ==null) {
			oldValue=null;
		}else if(key.compareTo(p.entry.getKey()) < 0)
			p.left=removeR(key,p.left);
		else if (key.compareTo(p.entry.getKey()) > 0) p.right = removeR(key,p.right);
		else if (p.left == null || p.right == null) {
			//  p muss gelöscht werden
			//  und hat  ein oder  kein Kind:
			oldValue = p.entry.getValue();
			p = (p.left!=null) ?p.left:p.right;
			size--;
		}else{
			//  p muss gelöscht werden  und hat  zwei Kinder:
		Entry<K,V> min = new Entry<K,V>(null, null);
			p.right = getRemMinR(p.right, min);
			oldValue = p.entry.getValue();
			p.entry = new Entry<K,V>(min.getKey(), min.getValue());
			size--;
		}
		return p;
	}

	private Node<K,V>  getRemMinR(Node<K,V> p, Entry<K,V> min) {
		assert p  != null;
		if (p.left == null) {
			min = new Entry<>(p.entry.getKey(), p.entry.getValue());
			p = p.right;
		} else
			p.left  = getRemMinR(p.left,  min);
		return p;
	}
//	private static class
//	MinEntry<K, V> {
//		private K key;
//		private V value;
//	}

	static class Node<K,V> {
		Entry<K,V> entry;
		Node<K,V> left;   // linkes Kind
		Node<K,V> right;  // rechtes Kind

//		private Entry<K,V> getEntry() {
//			return entry;
//		}

		private Node(K key, V value) {
			this.entry = new Entry<>(key, value);
			this.left = null;
			this.right = null;
		}
	}
}