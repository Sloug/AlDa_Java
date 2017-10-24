package dicitonary;

import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V>{

	private LinkedList<Entry<K, V>>[] data;
	private int size;
	@Override
	public V insert(K key, V value) {
		Entry entry = searchEntry(key);
		int index = searchIndex(key);
		if(data[index] == null) {
			data[index] = new LinkedList<>();
		}
		if(entry.getKey().equals(key)) {
			V oldValue = (V) entry.getValue();
			entry.setValue(value);
			return oldValue;
		}
		if(data.length == size) {
			ensureCapacity(data.length*2);
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
				Entry<K,V> e = null;
				Entry<K,V> nextEntry = null;
				while(currentIndex < size-1) {
					if(currentIndex==0) {
						for(LinkedList<Entry<K,V>> list : data) {
							if(list == null) {
								continue;
							} else {

								for(Entry<K,V> entry : list) {
									if(e == null) {
										e = entry;
									}
									if(entry.getKey().compareTo(e.getKey()) < 0) {
										e = entry;
									}
								}
							}
						}
						nextEntry = e;
					} else {
						for(LinkedList<Entry<K,V>> list: data) {
							if(list== null) {
								continue;
							} else {
								for(Entry<K,V> entry : list) {
									if(entry.getKey().compareTo(nextEntry.getKey()) < 0 && entry != e) {
										nextEntry = entry;
									}
								}
							}
						}

					}
					currentIndex++;
				}
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
				}
				isNotPrime = false;

			}
			i++;
		}
		return --i;
	}

	private int searchIndex(K key) {
		return key.hashCode() % data.length;
	}

	private Entry searchEntry(K key) {
		int index = searchIndex(key);
		if(data[index] == null) {
			return null;
		}
		for(Entry<K,V> entry : data[searchIndex(key)]) {
			if(entry.getKey().equals(key)) {
				return entry;
			}
		}
		return null;
	}
	public HashDictionary(int size) {
		this.size = 0;
		this.data = new LinkedList[size];
	}




}
