package com.global.training.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 *
 */

public class DevicesUtil {

    /**
     * 获取imei
     *
     * @param context 上下文对象
     * @return imei
     */
    public final static String getDeviceId(Context context) {
/*		String imei = null;
        try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId() == null ? "" : telephonyManager
					.getDeviceId();
		} catch (Exception e) {
			imei = "" ;
		}*/
        return "";
    }

    /**
     * 获取系统语言
     *
     * @return 系统语言
     */
    public final static Locale getAppLocale() {
        Locale locale;
        //适配7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 获取应用配置系统语言，会跟随应用配置被改变
     *
     * @return 系统语言
     */
    public final static String getAppLanguage() {
        return getAppLocale().getLanguage();
    }

    /**
     * 获取应用配置系统国家，会跟随应用配置被改变
     *
     * @return 国家
     */
    public final static String getAppCountry() {
        return getAppLocale().getCountry();
    }

    /**
     * 获取系统语言
     *
     * @return 系统语言
     */
    public final static Locale getSystemLocale() {
        Locale locale;
        //适配7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
            locale = Resources.getSystem().getConfiguration().locale;
        }
        return locale;
    }

    /**
     * 获取系统语言
     *
     * @return 系统语言
     */
    public final static String getSystemLanguage() {
        return getSystemLocale().getLanguage();
    }

    /**
     * 获取系统国家
     *
     * @return 国家
     */
    public final static String getSystemCountry() {
        return getSystemLocale().getCountry();
    }

    /**
     * 获取设备系统版本
     *
     * @return 系统版本
     */
    public final static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备系统类型
     *
     * @return
     */
    public static String getDeviceType() {
        return "android";
    }

    /**
     * 获取设备类型
     *
     * @return
     */
    public static String getDeviceModel() {
        Build bd = new Build();
        return bd.MODEL;
    }

    /**
     * 获取Android id
     *
     * @param context 上下文对象
     * @return
     */
    public static String getAndroidID(Context context) {
        String android_id = null;
        try {
            android_id = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            android_id = "";
        }
        return android_id;
    }


    /**
     * @return String 返回类型
     * @Title: getIMEI(设备的IMEI)
     * @author xiaoming.yuan
     * @data 2013-8-10 下午3:41:05
     * @param: @param context
     * @param: @return
     */
    public static String getMobileEquipmentIdentity(Context context) {
        String imei = "";
//        try {
//            TelephonyManager telephonyManager =
//                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            imei = telephonyManager.getDeviceId() == null ? "" : telephonyManager.getDeviceId();
//        } catch (Exception e) {
//            imei = "";
//        }
        return imei;
    }

    /**
     * 获取系统电量
     *
     * @param context 上下文对象
     * @return 系统电量
     */
    public static String getBatteryStatus(Context context) {
        String battery = null;
        try {
            IntentFilter mFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent intent = context.getApplicationContext().registerReceiver(null, mFilter);
            if (intent != null) {
                int level = intent.getIntExtra("level", -1);
                battery = String.valueOf(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
            battery = "-1";
        }
        return battery;
    }

    /**
     * 获取时区（系统api）
     *
     * @return 时区
     */
    public static String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getDisplayName(false, TimeZone.SHORT);
    }

    /**
     * 获取当前时区（自定义方法）
     *
     * @return
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true, true, tz.getRawOffset());
    }

    /**
     * 生成时间戳
     *
     * @param includeGmt
     * @param includeMinuteSeparator
     * @param offsetMillis
     * @return
     */
    public static String createGmtOffsetString(boolean includeGmt,
                                               boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);

    }

    /**
     * 获取sim卡状态 return true：可用 false:不可用
     *
     * @param context 上下文对象
     */
    public static boolean getSIMState(Context context) {
        boolean state = false;
        TelephonyManager manager = (TelephonyManager) context.getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (manager.getSimState()) {
            case TelephonyManager.SIM_STATE_READY:
                state = true;
                break;
            default:
                state = false;
                break;
        }
        return state;
    }

    /**
     * 获取运营商信息
     *
     * @param context 上下文对象
     * @return
     */


    /**
     * Get Telephone IMSI
     *
     * @param context 上下文对象
     */
    public static String getTelImsi(Context context) {
//        try {
//            TelephonyManager tm = (TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            String imsi = tm.getSubscriberId();
//            if (StringVerifyUtil.isEmpty(imsi))
//                imsi = "";
//            if (imsi.length() > 15) {
//                imsi = imsi.substring(0, 15);
//            }
//            return imsi;
//        } catch (Exception e) {
//            return "";
//        }
        return "";
    }

    //获取设备最佳运行的CPU个数，用于获取设置线程池核心线程个数
    private static int numCPUCores = 0;

    /**
     * copy form facebook sdk，获取设备最佳运行的CPU个数
     * Return our best guess at the available number of cores. Will always return at least 1.
     * @return The minimum number of CPU cores
     */
    public static int refreshBestGuessNumberOfCPUCores() {
        // If we have calculated this before, return that value
        if (numCPUCores > 0) {
            return numCPUCores;
        }
        // Enumerate all available CPU files and try to count the number of CPU cores.
        try {
            File cpuDir = new File("/sys/devices/system/cpu/");
            File[] cpuFiles = cpuDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String fileName) {
                    return Pattern.matches("cpu[0-9]+", fileName);
                }
            });

            if (cpuFiles != null) {
                numCPUCores = cpuFiles.length;
            }
        } catch (Exception e) {
        }

        // If enumerating and counting the CPU cores fails, use the runtime. Fallback to 1 if
        // that returns bogus values.
        if (numCPUCores <= 0) {
            numCPUCores = Math.max(Runtime.getRuntime().availableProcessors(), 1);
        }
        return numCPUCores;
    }
}
