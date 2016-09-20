//ActivityManager

package com.meizu.newtest.Activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class TestActivityManager {
    private List<Activity> mActivities = new ArrayList<>();
    private static TestActivityManager sInstance;

    private TestActivityManager() {
    };

    public static TestActivityManager instance() {
        if (sInstance == null) {
            sInstance = new TestActivityManager();
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
