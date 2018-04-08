package com.scrat.zhuhaibus.module.update;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.local.Preferences;
import com.scrat.zhuhaibus.data.modle.Res;
import com.scrat.zhuhaibus.data.modle.UpdateInfo;
import com.scrat.zhuhaibus.framework.util.L;
import com.scrat.zhuhaibus.framework.util.Utils;

import java.lang.ref.WeakReference;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by scrat on 2017/11/19.
 */

public class UpdateHelper {
    private AlertDialog dialog;

    public UpdateHelper init(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        return this;
    }

    public void release() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void checkUpdate(Activity activity, boolean ignoreCacheVer, boolean showNoNeedToUpdateToast) {
        WeakReference<Activity> weakActivity = new WeakReference<>(activity);
        Context applicationContext = activity.getApplicationContext();
        DataRepository.getInstance().getCoreService()
                .getUpdateInfo(System.currentTimeMillis())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(updateInfoRes -> {
                    UpdateInfo updateInfo = updateInfoRes.getData();
                    int forceVer = updateInfo.getForceVer();
                    Preferences.getInstance().setLastServerForceVerCode(forceVer);
                    int serverVer = updateInfo.getVer();
                    Preferences.getInstance().setLastServerVerCode(serverVer);
                    L.v("curr_server_ver:%s\tcurr_server_foce_ver:%s", serverVer, forceVer);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<UpdateInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        // ignore
                    }

                    @Override
                    public void onNext(Res<UpdateInfo> updateInfoRes) {
                        Activity currActivity = weakActivity.get();
                        if (currActivity == null) {
                            return;
                        }

                        UpdateInfo updateInfo = updateInfoRes.getData();
                        int currVerCode = Utils.getVersionCode(applicationContext);
                        int serverForceVer = Preferences.getInstance().getLastServerForceVerCode();
                        if (currVerCode < serverForceVer) {
                            dialog.setMessage(updateInfo.getContent());
                            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "立即更新", (dialogInterface, i) -> {
                                dialog.dismiss();
                                downloadApk(currActivity, updateInfo.getUrl());
                            });
                            dialog.show();
                            return;
                        }

                        int lastServerVer = Preferences.getInstance().getLastServerVerCode();
                        int lastIgnoreVer = Preferences.getInstance().getLastIgnoreVerCode();
                        if ((ignoreCacheVer && currVerCode < lastServerVer) || lastIgnoreVer < lastServerVer) {
                            Preferences.getInstance().setLastIgnoreVerCode(lastServerVer);
                            dialog.setMessage(updateInfo.getContent());
                            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "稍后再说", (dialogInterface, i) -> {
                                dialog.dismiss();
                            });
                            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "更新", (dialogInterface, i) -> {
                                dialog.dismiss();
                                downloadApk(currActivity, updateInfo.getUrl());
                            });
                            dialog.show();
                            return;
                        }

                        if (showNoNeedToUpdateToast) {
                            Toast.makeText(currActivity, "很棒！你已经是最新版本啦！", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void downloadApk(Activity activity, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), "请前往应用市场更新", Toast.LENGTH_LONG).show();
        }
    }

}
