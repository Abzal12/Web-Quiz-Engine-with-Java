package engine;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizAnswer {
    private boolean isSuccess;
    private String feedback;

    public QuizAnswer() {
    }

    public QuizAnswer(boolean isSuccess, String feedback) {
        this.isSuccess = isSuccess;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
