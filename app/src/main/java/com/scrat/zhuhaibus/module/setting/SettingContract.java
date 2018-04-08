package com.scrat.zhuhaibus.module.setting;

public interface SettingContract {
    interface Presenter {
        void clearCache();
    }

    interface View {

        void setPresenter(Presenter presenter);

        void showClearCacheSuccess();
    }
}
