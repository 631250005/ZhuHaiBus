package com.scrat.zhuhaibus.module.feedback;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.modle.Feedback;
import com.scrat.zhuhaibus.data.modle.Res;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedbackPresenter implements FeedbackContract.Presenter {
    private FeedbackContract.View view;

    public FeedbackPresenter(FeedbackContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void feedback(String contact, String content) {

        view.onFeedback();
        Feedback feedback = new Feedback()
                .setContact(contact)
                .setContent(content);
        DataRepository.getInstance().getCoreService()
                .feedback(feedback)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.feedbackFail(R.string.error_msg);
                    }

                    @Override
                    public void onNext(Res res) {
                        view.feedbackSuccess();
                    }
                });
    }
}
