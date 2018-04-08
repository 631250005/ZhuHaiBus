package com.scrat.zhuhaibus.module.feedback;

public interface FeedbackContract {
    interface Presenter {
        void feedback(String contact, String content);
    }

    interface View {
        void setPresenter(Presenter presenter);

        void onFeedback();

        void feedbackFail(int resId);

        void feedbackSuccess();
    }
}
