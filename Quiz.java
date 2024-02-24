package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quiz {
    private int id;
    @JsonProperty(required = true)
    @NotBlank
    private String title;
    @JsonProperty(required = true)
    @NotBlank
    private String text;
    @JsonProperty(required = true)
    @NotNull
    @Size(min = 2)
    private List<String> options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;
    public final static Quiz quizQuestion = new Quiz(100, "The Java Logo",
            "What is depicted on the Java logo?",
            Arrays.asList("Robot","Tea leaf","Cup of coffee","Bug"));

    public Quiz() {
    }

    public Quiz(int id, String title, String text, List<String> options) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public Quiz(int id, String title, String text, List<String> options, List<Integer> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
