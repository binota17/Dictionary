import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline {
    private DictionaryManagement management = new DictionaryManagement();
    private Dictionary dictionary = new Dictionary();
    Scanner in = new Scanner(System.in);

    public DictionaryManagement getManagement() {
        return management;
    }

    /**
     * in tất cả các từ.
     */
    public void showAllWords(Dictionary dictionary) {
        System.out.printf("|%-2s |%-15s |%-15s %n","NO", "ENGLISH", "VIETNAMESE");
        int no = 0;
        for (Word i : dictionary.getWords()) {
            System.out.printf("|%-2d |%-15s |%-15s %n", ++no, i.getWord_target(), i.getWord_explain() );
        }
    }

    /**
     * basic.
     */
    public void dictionaryBasic() {
        management.insertFromCommandline(dictionary);
        showAllWords(dictionary);
    }

    /**
     * advanced.
     */
    public void dictionaryAdvanced() {
        Dictionary dictionary = management.insertFromFile();
      //  showAllWords(dictionary);
        String s = management.dictionaryLookup(in.nextLine(),dictionary).getWord_explain();
        System.out.println(s);
    }

    /**
     * searcher.
     */
    public List<String> dictionarySearcher(String key, Dictionary dictionary) {
        List<String> result = new ArrayList<>();
        for (Word word : dictionary.getWords()) {
            if (word.getWord_target().startsWith(key.trim().toLowerCase())) {
                result.add(word.getWord_target());
            }
        }
        return result;
    }
}
