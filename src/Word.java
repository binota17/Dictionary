public class Word {
    private String word_target;
    private String word_explain;

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public String getWord_target() {
        return word_target;
    }

    /**
     * Constructor.
     */
    public Word() {

    }

    /**
     * Constructor 2.
     * @param word_target arg
     * @param word_explain arg
     */
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }
}
