package dicitonary;

import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V>{

	private LinkedList<Entry<K, V>>[] data;
	private int size;
	@Override
	public V insert(K key, V value) {
		// TODO Auto-generated method stub
		if(){

		} else {
			return null;
		}
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

	private void ensureCapacity(int size) {
		if(size < this.size) {
				return;
		}
		LinkedList[] dataTmp = data;
		data = new LinkedList[getNextPrimeNumber(size * 2)];
		for(LinkedList<Entry<K, V>> e : dataTmp) {
			for (Entry<K, V> m : e) {
			}
		}
	}

	private int getNextPrimeNumber(int number) {
		return 0;
	}

	private int searchIndex(K key) {
		return 0;
	}
	
	public HashDictionary(int size) {
		size = 0;
		this.data = new LinkedList[size];
	}




}
