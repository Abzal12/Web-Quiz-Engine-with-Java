package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quiz {
    private String title;
    private String text;
    private List<String> options;
    public final static Quiz quizQuestion = new Quiz("The Java Logo",
            "What is depicted on the Java logo?",
            Arrays.asList("Robot","Tea leaf","Cup of coffee","Bug"));

    public Quiz() {
    }

    public Quiz(String title, String text, List<String> options) {
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
