package com.iven.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Iven
 * @date 2017/2/17 10:51
 * @Description 广播监听充电状态的变化
 */

public class PowerConnectionReceiver extends BroadcastReceiver {
    private static final String TAG = "zpy_PowerConnectionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        /*int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;*/
        String message = "";
        boolean isCharging = intent.getBooleanExtra("isCharging", false);
        boolean usbCharge = intent.getBooleanExtra("usbCharge", false);
        boolean acCharge = intent.getBooleanExtra("acCharge", false);
        if (isCharging) {
            Toast.makeText(context, "充电中", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onReceive: 31" + "行 = usbCharge = " + usbCharge + "    acCharge = " + acCharge);
        }
    }
}
