package android.example.com.dictionaryproviderexample;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by moacir.ramos on 18/04/17.
 */

public class MyAccessibilityService extends AccessibilityService {

    public static final String TAG = "MOACIR";

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        Log.i(TAG, "Service is connected");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Treat event by package

        Log.i(TAG, "Event : " + event);

        AccessibilityNodeInfo source = event.getSource();

        Log.i(TAG, "Event text: " + event.getText());
        if (source != null){
            source.refresh();

            String viewId = source.getViewIdResourceName();
            Log.i(TAG, "View id : " + viewId);

            List<AccessibilityNodeInfo> nodeInfos = source.findAccessibilityNodeInfosByViewId("com.android.chrome:id/url_bar");
            for (AccessibilityNodeInfo n : nodeInfos){
                CharSequence text = n.getText();
                if (text != null){
                    Log.i(TAG, "Node text is : " + text);
                }
            }


        }

//        AccessibilityNodeInfo source1 = findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
//        if (source1 != null){
//            String viewId = source.getViewIdResourceName();
//
//            Log.i(TAG, "Focus view id : " + viewId);
//        }

    }

    @Override
    public void onInterrupt() {
        Log.i(TAG, "Service is interrupted");
    }
}
