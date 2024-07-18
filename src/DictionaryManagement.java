import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    Scanner in = new Scanner(System.in);

    /**
     * nhập dữ liệu chay vào từ điển.
     * nhập số từ trước. sau đó mỗi dòng lần lượt là từ tiếng anh + nghĩa.
     */
    public void insertFromCommandline(Dictionary dictionary) {
        System.out.println("Số từ nhập vào:");
        int N = Integer.parseInt(in.nextLine());
        for (int i = 0; i < N; i++) {
            Word word = new Word(in.nextLine().trim(), in.nextLine().trim());
            dictionary.getWords().add(word);
        }
    }

    /**
     * đọc dữ liệu từ dictionary.txt.
     * @return
     */
    public Dictionary insertFromFile() {
        Dictionary dictionary = new Dictionary();
        String FILE_URL = "src\\txt\\dictionary.txt";
        BufferedReader reader = null;
        try {
            File file = new File(FILE_URL);

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                int index = line.indexOf("\t");
                String target = line.substring(0, index);
                target = target.trim();
                String explain = line.substring(index);
                Word word = new Word(target.trim(), explain.trim());
                dictionary.getWords().add(word);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dictionary;
    }

    /**
     * tra cứu.
     */
    public Word dictionaryLookup(String key, Dictionary dictionary) {
        for (Word w : dictionary.getWords()) {
            if (w.getWord_target().equals(key.trim().toLowerCase())) {
                return w;
            }
        }
            Word error = new Word("", "Word not found.");
            return error;
    }

    /**
     * xóa từ.
     */
    public void dictionaryDelete(String key,Dictionary dictionary) {
        Word del = dictionaryLookup(key, dictionary);
        boolean isRemoved = dictionary.getWords().remove(del);
        if (isRemoved) {
            System.out.println("Deleted.");
        } else System.out.println("Cannot delete.");
    }

    /**
     * thêm từ.
     */
    public void addWord(String target, String explain, Dictionary dictionary) {
        // thay enter = tab de con doc file.
        if (explain.contains("\n")) {
            explain = explain.replace("\n", "\t");
        }
        Word check = dictionaryLookup(target, dictionary);
        if (check.getWord_explain().equals("Word not found.")) {
            Word wordAdd = new Word(target.toLowerCase(), explain.toLowerCase());
            dictionary.getWords().add(wordAdd);
            System.out.println("Word added!");
        } else {
            check.setWord_explain(check.getWord_explain() + "\t" + explain);
        }
    }

    /**
     * sửa từ.
     */
    public void editWord(Dictionary dictionary) {
        System.out.print("Word u want to edit: ");
        Word editWord = dictionaryLookup(in.nextLine(),dictionary);
        if (!editWord.getWord_explain().equals("Word not found.")) {
            System.out.print("Type <E> to edit English word or <M> to edit meaning: ");
            char edit = in.nextLine().charAt(0);
            if (edit == 'E') {
                System.out.print("Edit to: ");
                editWord.setWord_target(in.nextLine().trim());
            } else if (edit == 'M') {
                System.out.print("Edit to: ");
                editWord.setWord_explain(in.nextLine().trim());
            } else {
                System.out.println("Invalid command.");
            }
        } else System.out.println("Invalid word.");
    }

    /**
     * xuat tu dien ra dictionary.txt .
     */
    public void dictionaryExportToFile(Dictionary dictionary) {
        String FILE_URL = "src\\txt\\dictionary.txt";
        BufferedWriter writer = null;
        try {
            File file = new File(FILE_URL);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            for (Word w : dictionary.getWords()) {
                writer.write(w.getWord_target() + "\t" + w.getWord_explain() + "\n");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * speaker.
     */
    public void Speak(String key) {
        Voice voice;
        VoiceManager vm = VoiceManager.getInstance();
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = vm.getVoice("kevin16");
        voice.allocate();
        voice.speak(key);
        voice.deallocate();
    }
}
