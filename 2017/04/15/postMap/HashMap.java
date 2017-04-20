package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class HashMap<K, V> {

	public class Entry {
		private K key;
		private V value;
		private int hashcode;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
			this.hashcode = hashFunction(key);
		}

		public final K getKey() {
			return key;
		}

		public final V getValue() {
			return value;
		}
		
		final int getHashcode() {
			return hashcode;
		}

		@Override
		public final String toString() {
			return key + " = " + value;
		}
	}

	private LinkedList<Entry>[] table;
	private int capacity = defaultCap;
	private double tableSize = 0;
	private int size;
	private static final double resizeRatio = 0.5;
	private static final int defaultCap = 32;

	@SuppressWarnings("unchecked")
	public HashMap() {
		table = new LinkedList[capacity];
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean containsKey(K key) {
		int hashcode = hashFunction(key);
		if (hashcode >= table.length || size <= 0)
			return false;
		LinkedList<Entry> list = table[hashcode];
		if (list == null)
			return false;
		return list.stream().filter(e -> key.equals(e.getKey())).count() > 0;
	}

	public V get(K key) {
		int hashcode = hashFunction(key);
		if (hashcode >= table.length || size <= 0)
			return null;
		LinkedList<Entry> list = table[hashcode];
		if (list == null)
			return null;
		return list.stream().filter(e -> key.equals(e.getKey())).map(e -> e.getValue()).findFirst().orElse(null);
	}

	public void put(K key, V value) {
		if (tableSize / capacity > resizeRatio)
			resize();
		remove(key);
		Entry e = new Entry(key, value);
		if (table[e.hashcode] == null){
			table[e.hashcode] = new LinkedList<Entry>();
			tableSize++;
		}
		table[e.getHashcode()].add(e);

		size++;
	}

	public boolean remove(K key) {
		int hashcode = hashFunction(key);
		if (hashcode >= table.length || size <= 0)
			return false;
		LinkedList<Entry> list = table[hashcode];
		if (list == null)
			return false;
		list.removeIf(entry -> entry.getKey().equals(key));
		if(list.isEmpty()){
			tableSize--;
			table[hashcode] = null;
		}
		size--;

		return true;
	}
	
	public List<Entry> getEntrys() {
		ArrayList<Entry> entrys = new ArrayList<>();
		Arrays.asList(table).stream().filter(l -> l !=null).forEach(l -> l.forEach(e -> entrys.add(e)));
		
		return entrys;
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		capacity = defaultCap;
		table = new LinkedList[capacity];
		size = 0;
	}

	@SuppressWarnings("unchecked")
	private void resize() {
		capacity = capacity * 2;

		LinkedList<Entry>[] array = new LinkedList[capacity];
		for (LinkedList<Entry> l : table){
			if (l != null){
				for (Entry e : l){
					e.hashcode = hashFunction(e.key);
					if (array[e.hashcode] == null) {
						array[e.hashcode] = new LinkedList<Entry>();
						tableSize++;
					}
					array[e.hashcode].add(e);
				}
			}
		}

		this.table = array;
	}

	private int hashFunction(K key) {
		return key.hashCode() & (capacity - 1);
	} 

	public static void main(String[] args) {
		
		HashMap<Integer, Double> map = new HashMap<>();
		IntStream.range(0, 100).forEach(i -> map.put((int)(Math.random()*1000),Math.random()*100));
		map.getEntrys().forEach(e -> System.out.println(e));
		System.out.println("==================  Sort:  =======================");
		map.getEntrys().stream().sorted((e1, e2) -> Integer.valueOf(e1.key).compareTo(Integer.valueOf(e2.key))).forEach(e -> System.out.println(e));
		System.out.println( map.isEmpty());
		System.out.println( map.size());
		System.out.println(map.remove(1));
		System.out.println( map.size());
	}
}
