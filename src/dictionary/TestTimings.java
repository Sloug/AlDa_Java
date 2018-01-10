package dictionary;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestTimings {

    public static void main(String[] args) {
        File file = new File("/home/carsten/Development/AlDa_Java/dtengl.txt");
        try

        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
//            boolean first = true;
            Dictionary<String, String> dict = null;
            Dictionary<String, String> dict2 = null;
            List<String> germanWords = new ArrayList<>();
            List<String> englishWords = new ArrayList<>();
            while (true) {
                String re = br.readLine();
                if (re != null) {
                    String[] arguments = re.split("\\s+");
                    if (arguments.length > 1) {
                        germanWords.add(arguments[0]);
                        englishWords.add(arguments[1]);
                    }
                } else break;
            }
            for (int j = 0; j < 3; j++) {
                switch (j) {
                    case 2:
                        dict = new SortedArrayDictionary<>();
                        dict2 = new SortedArrayDictionary<>();
                        System.out.println("SortedArrayDictionary:\n");
                        break;
                    case 1:
                        dict = new HashDictionary<>(3);
                        dict2 = new HashDictionary<>(3);
                        System.out.println("HashDictionary:\n");
                        break;
                    case 0:
                        dict = new BinaryTreeDictionary<>();
                        dict2 = new BinaryTreeDictionary<>();
                        System.out.println("BinaryTreeDictionary:\n");
                        break;
                }
                FileReader r = new FileReader(file);
                BufferedReader b = new BufferedReader(r);
                //time1
                long first = System.currentTimeMillis();
                for (int i = 0; i < 8000; i++) {
                    String re = b.readLine();
                    String[] arguments = re.split("\\s+");
                    if (arguments.length > 1) {
                        dict.insert(arguments[0], arguments[1]);
                    }
                }
//                System.out.println(dict2 instanceof HashDictionary);
                //time1
                long second = System.currentTimeMillis();
                System.out.println("Insert 8000: " + (second - first));
//                dict = null;

                FileReader f = new FileReader(file);
                BufferedReader c = new BufferedReader(f);
                //time2
                long first1 = System.currentTimeMillis();
                while (true) {
                    String re = c.readLine();
                    if (re != null) {
                        String[] arguments = re.split("\\s+");
                        if (arguments.length > 1) {
                            dict2.insert(arguments[0], arguments[1]);
                        }
                    } else break;
                }
                //time2
                long second1 = System.currentTimeMillis();
                System.out.println("Insert all: " + (second1 - first1) + "\n");
                //TODO search tests





                int ind = 0;
                long firstSearch = System.currentTimeMillis();
                for (String e : germanWords) {
                    if(ind >= 8000) {
                        break;
                    }
                    dict.search(e);
//                    System.out.println(dict.search(e));
                    ind++;

                }
                long secondSearch = System.currentTimeMillis();
                ind = 0;
                long firstSearch1 = System.currentTimeMillis();
                for (String e : englishWords) {
                    if(ind >= 8000) {
                        break;
                    }
                    dict.search(e);
//                    System.out.println(dict.search(e));
                    ind++;
                }
                long secondSearch1 = System.currentTimeMillis();
                for (String e : germanWords) {
                    dict2.search(e);
//                    System.out.println(dict2.search(e));

                }
                long secondSearch2 = System.currentTimeMillis();
                for (String e : englishWords) {
                    dict2.search(e);
//                    System.out.println(dict2.search(e));
                }
                long secondSearch3 = System.currentTimeMillis();
                System.out.println("Successful search 8000: " + (secondSearch - firstSearch));
                System.out.println("Unsuccessful search 8000: " + (secondSearch1 - firstSearch1) + "\n");
                System.out.println("Successful search all: " + (secondSearch2 - secondSearch1));
                System.out.println("Unsuccessful search all: " + (secondSearch3 - secondSearch2) + "\n");

            }
        } catch (Exception e) {

        }
    }
}