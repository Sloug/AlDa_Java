package dictionary;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TextualUserInterface {
    public static void main(String[] args) {
        Dictionary<String, String> dict = null;
        boolean shouldExit = false;
        while (!shouldExit) {
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            if (cmd.startsWith("create")) {
                String[] arguments = cmd.split("\\s+");
                String kind = arguments[1];
                switch (kind) {
                    case "SortedArrayDictionary": dict = new SortedArrayDictionary();
                    break;
                    case "HashDictionary": dict = new HashDictionary(3);
                    break;
                    case "BinaryTreeDictionary": dict = new BinaryTreeDictionary();
                    break;
                    default: System.out.println("Invalid Implementation!");
                }

            } else if (cmd.startsWith("read")) {
                if (dict == null) {
                    System.out.println("Please creat a dictionary at first!");
                } else {
                    String[] arguments = cmd.split("\\s+");
                    int count = 0;
                    if (arguments.length > 1) {
                        String nb = arguments[1];
                        count = Integer.parseInt(nb);
                    }
                    readDict(count, dict);
                    //TODO
                }
            } else if (cmd.equals("p")) {
                if (dict == null) {
                    System.out.println("Please creat a dictionary at first!");
                } else {
                    for (Dictionary.Entry<String, String> e : dict) {
                        System.out.println(e.getKey() + ": " + e.getValue());
                    }
                }
            } else if (cmd.startsWith("s")) {
                if (dict == null) {
                    System.out.println("Please creat a dictionary at first!");
                } else {
                    String[] arguments = cmd.split("\\s+");
                    if (arguments.length > 1) {
                        String germanKey = arguments[1];
                        System.out.println(dict.search(germanKey));
                    } else {
                        System.out.println("Invalid Format!");
                    }
                }
            } else if (cmd.startsWith("i")) {
                if (dict == null) {
                    System.out.println("Please creat a dictionary at first!");
                } else {
                    String[] arguments = cmd.split("\\s+");
                    if (arguments.length > 2) {
                        String germanKey = arguments[1];
                        String englishValue = arguments[2];
                        dict.insert(germanKey, englishValue);
                    } else {
                        System.out.println("Invalid Format!");
                    }
                }
            } else if (cmd.startsWith("r")) {
                if (dict == null) {
                    System.out.println("Please creat a dictionary at first!");
                } else {
                    String[] arguments = cmd.split("\\s+");
                    if (arguments.length > 1) {
                        String toRemove = arguments[1];
                        dict.remove(toRemove);
                    } else {
                        System.out.println("Invalid Format!");
                    }
                }
            } else if (cmd.equals("exit")) {
                shouldExit = true;
            } else {
                System.out.println("Invalid Command");
            }
        }
        System.exit(0);
    }
    private static void readDict(int count, Dictionary<String, String> dict) {
        JFrame jf = new JFrame();
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int status = fc.showOpenDialog(jf);
        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            System.out.println(selectedFile);
//            System.out.println("How much Entrys should be readed? (default all)");
            try {
                FileReader fr = new FileReader(selectedFile);
                BufferedReader br = new BufferedReader(fr);
                int index = 0;
                while (true) {
                    String re = br.readLine();
                    if (count != 0 && index == count) {
                        break;
                    }
                    if (re != null) {
                        String[] arguments = re.split("\\s+");
                        if (arguments.length > 1) {
                            dict.insert(arguments[0], arguments[1]);
                        }
                        index++;
                    } else break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You haven't choose any file!");
            System.exit(1);
        }
    }
}
