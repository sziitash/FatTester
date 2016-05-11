//ActivityManager

package com.meizu.newtest.Activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {
    private List<Activity> mActivities = new ArrayList<>();
    private static ActivityManager sInstance;

    private ActivityManager() {
    };

    public static ActivityManager instance() {
        if (sInstance == null) {
            sInstance = new ActivityManager();
        }

        return sInstance;
    }

    public void registActivity(Activity activity) {
        mActivities.add(activity);
    }

    public void unRigistActivity(Activity activity) {
        mActivities.remove(activity);
    }
}
