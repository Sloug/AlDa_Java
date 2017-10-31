package dicitonary;

import java.util.Iterator;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V>  implements Dictionary<K, V>{

	Node root;
	@Override
	public V insert(K key, V value) {
		if (root == null) {
			root = new Node(new Entry<>(key,value));
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V search(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void prettyPrint() {
		
	}

	static class Node<Entry<>> {
		Entry entry;
		Node<Entry> left;   // linkes Kind
		Node<Entry> right;  // rechtes Kind

		private Node(Entry entry) {
			this.entry = entry;
		}
	}
}