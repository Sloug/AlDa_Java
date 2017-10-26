package dicitonary;

import java.util.Arrays;
import java.util.Iterator;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V>{

	private Entry<K, V>[] data;
	private int size;
	private static final int DEF_CAPACITY = 16;
	@Override
	public V insert(K key, V value) {
		int index = searchKey(key);
		if (index != -1) {
			V tmp = data[index].getValue();
			data[index].setValue(value);
			return tmp;
		}
		if (data.length == size) {
			ensureCapacity(size*2);
		}
		int i = size-1;
		while (i>=0 && key.compareTo(data[i].getKey()) < 0) {
			data[i+1] = data[i];
			i--;
		}
		data[i+1] = new Entry<>(key, value);
		size++;
		return null;
	}

	@Override
	public V search(K key) {
		int index = searchKey(key);
		if(index == -1) {
			return null;
		} else {
			return data[index].getValue();
		}
	}

	@Override
	public V remove(K key) {
		int index = searchKey(key);
		if(index == -1) {
			return null;
		} else {
			V tmp = data[index].getValue();
			for (int i = index; i < size-1; i++) {
				data[i] = data[i+1];
			}
			data[--size] = null;
			return tmp;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new Iterator<Entry<K, V>>() {
			private int current_index = -1;
			@Override
			public boolean hasNext() {
				if(current_index == data.length -1) {
					return false;
				}
				return data[current_index + 1] != null;
			}

			@Override
			public Entry<K, V> next() {
				return data[++current_index];
			}
		};
	}

	private int searchKey(K key) {
		int li = 0;
		int re = size - 1;
		while (re >= li) {
			int m = (li + re)/2;
			if (key.compareTo(data[m].getKey())< 0) {
				re = m - 1;
			} else if (key.compareTo(data[m].getKey()) > 0) {
				li= m + 1;
			} else {
				return m; // key gefunden
			}
		}
		return -1; //key nicht gefunden
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity(int size) {
		if(size < this.size) {
			return;
		}
		Entry[] dataTmp = data;
		data = new Entry[size];
		System.arraycopy(dataTmp, 0, data, 0, this.size);
	}

	public SortedArrayDictionary() {
		size = 0;
		data = new Entry[DEF_CAPACITY];
	}





}
