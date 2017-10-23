package dicitonary;

import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V>{

	private LinkedList<Entry<K, V>>[] data;
	private int size;
	private int lastIndex;
	@Override
	public V insert(K key, V value) {
		int index = searchIndex(key);
		if(data[index].contains(key)){
			V oldValue = data[index].get(data[index].indexOf(key)).getValue();
			data[index].get(data[index].indexOf(key)).setValue(value);
			return oldValue;
		}
		if(data.length == size) {
			ensureCapacity(data.length*2);
		}
		if(searchIndex(key) > lastIndex) {
			lastIndex = searchIndex(key);
		}
		data[searchIndex(key)].add(new Entry<>(key, value));
		return null;

	}

	@Override
	public V search(K key) {
		int index = searchIndex(key);

		if(data[index].contains(key)) {

				return data[index].get(data[index].indexOf(key)).getValue();
		}
		return null;
	}

	@Override
	public V remove(K key) {
		int index = searchIndex(key);
		if (data[index].contains(key)) {
			V oldValue = data[index].get(data[index].indexOf(key)).getValue();
			data[index].remove(key);
			return oldValue;
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new Iterator<Entry<K, V>>() {
			int currentIndex = 0;
			@Override
			public boolean hasNext() {
				if(currentIndex < size) {
					return true;
				}
				return false;
			}

			@Override
			public Entry<K, V> next() {
				boolean hasNext = currentIndex < size;
				K key = 
				return null;
			}
		};
	}

//	@SuppressWarnings("unchecked")
	private void ensureCapacity(int size) {
		if(size < this.size) {
				return;
		}
		LinkedList[] dataTmp = data;
		data = new LinkedList[getNextPrimeNumber(size)];
		for(LinkedList<Entry<K, V>> e : dataTmp) {
			for (Entry<K, V> m : e) {
				data[searchIndex(m.getKey())].add(m);
			}
		}
	}

	private int getNextPrimeNumber(int number) {
		int i = number;
		boolean isNotPrime = true;
		while(isNotPrime){
			int j = (int) Math.sqrt(i);
			for (int a = 2; a <= j; a++) {
				if(i%a == 0) {
					isNotPrime = true;
					break;
				} else {
					isNotPrime = false;
				}
			}
			i++;
		}
		return --i;
	}

	private int searchIndex(K key) {
		return key.hashCode() % data.length;
	}
	
	public HashDictionary(int size) {
		size = 0;
		lastIndex = -1;
		this.data = new LinkedList[size];
	}




}
