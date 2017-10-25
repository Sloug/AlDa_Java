/*
 * Test der verscheidenen Dictionary-Implementierungen
 *
 * O. Bittel
 * 22.02.2017
 */
package dicitonary;

/**
 * Static test methods for different Dictionary implementations.
 * @author oliverbittel
 */
public class DictionaryTest {

	/**
	 * @param args not used.
	 */
	public static void main(String[] args)  {
		
		testSortedArrayDictionary();
		testHashDictionary();
		//testBinaryTreeDictionary();
	}

	private static void testSortedArrayDictionary() {
		Dictionary<String, String> dict = new SortedArrayDictionary<>();
		testDict(dict);
	}
	
	private static void testHashDictionary() {
		Dictionary<String, String> dict = new HashDictionary<>(3);
		testDict(dict);
	}
	
	private static void testBinaryTreeDictionary() {
		Dictionary<String, String> dict = new BinaryTreeDictionary<>();
		testDict(dict);
        
        // Test für BinaryTreeDictionary mit prettyPrint 
        // (siehe Aufgabe 10; Programmiertechnik 2).
        // Pruefen Sie die Ausgabe von prettyPrint auf Papier nach.
        BinaryTreeDictionary<Integer, Integer> btd = new BinaryTreeDictionary<>();
        btd.insert(10, 0);
        btd.insert(20, 0);
        btd.insert(30, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(40, 0);
        btd.insert(50, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(21, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(35, 0);
        btd.insert(60, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        System.out.println("For Each Loop:");
        for (Dictionary.Entry<Integer, Integer> e : btd) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }

        btd.remove(30);
        System.out.println("remove:");
        btd.prettyPrint();

        btd.remove(35);
        btd.remove(40);
        btd.remove(50);
        System.out.println("remove:");
        btd.prettyPrint();
    }
	
	private static void testDict(Dictionary<String, String> dict) {
		System.out.println("===== New Test Case ========================");
		System.out.println("test " + dict.getClass());
		System.out.println(dict.insert("gehen", "go") == null);
		System.out.println(dict.insert("gehen", "walk").equals("go"));
		System.out.println(dict.search("gehen").equals("walk"));
		System.out.println(dict.remove("gehen").equals("walk"));
		System.out.println(dict.remove("gehen") == null);
		dict.insert("starten", "start");
		dict.insert("gehen", "go");
		dict.insert("schreiben", "write");
		dict.insert("reden", "say");
		dict.insert("arbeiten", "work");
		dict.insert("lesen", "read");
		dict.insert("singen", "sing");
		dict.insert("schwimmen", "swim");
		dict.insert("rennen", "run");
		dict.insert("beten", "pray");
		dict.insert("tanzen", "dance");
		dict.insert("schreien", "cry");
		dict.insert("tauchen", "dive");
		dict.insert("fahren", "drive");
		dict.insert("spielen", "play");
		dict.insert("planen", "plan");
		dict.insert("lane", "la");
		for (Dictionary.Entry<String, String> e : dict) {
			if(e == null) {
				System.out.println("lala");
			}
			System.out.println(e.getKey() + ": " + e.getValue() + " search: " + dict.search(e.getKey()));
		}
	}
	
}
