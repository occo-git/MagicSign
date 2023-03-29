package com.softigress.magicsigns.Activities._base;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._system.Utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class IntentChooser {

    private PackageManager packageManager;
    private String subj;
    private String text;
    private Uri fileUri;
    private String type;
    private String[] allowedPackageNames;

    public IntentChooser(PackageManager packageManager, String subj, String text) {
        this(packageManager, subj, text, null);
    }

    public IntentChooser(PackageManager packageManager, String subj, String text, Uri fileUri) {
        this.packageManager = packageManager;
        this.subj = subj;
        this.text = text;
        if (fileUri != null) {
            this.fileUri = fileUri;
            this.type = "image/*";
        } else
            this.type = "text/plain";
        initPackageNames();
    }

    private void initPackageNames() {
        String packageNamesStr = Utils.remoteConfigManager.getPackageNames();
        if (packageNamesStr != null && packageNamesStr.length() > 0)
            allowedPackageNames = packageNamesStr.split(";");
    }

    private boolean containsPackageName(String packageName) {
        for (String name : allowedPackageNames) {
            if (packageName.contains(name))
                return true;
        }
        return false;
    }

    public Intent createChooser() {
        if (allowedPackageNames != null && allowedPackageNames.length > 0) {
            List<Intent> intentShareList = new ArrayList<Intent>();

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(shareIntent, 0);

            for (ResolveInfo resInfo : resolveInfoList) {
                String packageName = resInfo.activityInfo.packageName;
                String name = resInfo.activityInfo.name;

                if (containsPackageName(packageName)) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, name));
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType(this.type);
                    if (this.fileUri != null)
                        shareIntent.putExtra(Intent.EXTRA_STREAM, this.fileUri);
                    intent.putExtra(Intent.EXTRA_SUBJECT, this.subj);
                    intent.putExtra(Intent.EXTRA_TEXT, this.text);
                    shareIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    intentShareList.add(intent);
                }
            }

            if (intentShareList.isEmpty()) {
                return null;
            } else {
                Intent chooserIntent = Intent.createChooser(intentShareList.remove(0), Utils.getRes(R.string.dlg_Share_Title));
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentShareList.toArray(new Parcelable[]{}));
                return chooserIntent;
            }
        }
        return null;
    }
}
