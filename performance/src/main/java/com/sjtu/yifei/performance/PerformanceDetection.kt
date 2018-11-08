package com.sjtu.yifei.performance

import android.app.Application
import android.support.v4.app.Fragment
import com.squareup.leakcanary.AndroidExcludedRefs
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * 类描述：内存泄漏配置
 * 创建人：yifei
 * 创建时间：2018/7/23
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class PerformanceDetection private constructor() {

    companion object {
        val instance: PerformanceDetection by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PerformanceDetection()
        }
    }

    private var refWatcher: RefWatcher? = null

    fun isInAnalyzerProcess(application: Application): Boolean {
        return LeakCanary.isInAnalyzerProcess(application)
    }

    /**
     * 观察fragment内存泄漏
     * @param fragment
     */
    fun watcherFragment(fragment: Fragment) {
        refWatcher?.watch(fragment)
    }

    /**
     * 必须在UI线程中初始化
     */
    fun initLeakCanary(application: Application, isOpenWatcher: Boolean) {
        if (BuildConfig.DEBUG && isOpenWatcher) {
            if (refWatcher == null) {
                val excludedRefs = AndroidExcludedRefs.createAppDefaults()
                        .staticField("android.view.inputmethod.InputMethodManager", "sInstance")
                        .instanceField("android.view.inputmethod.InputMethodManager", "mLastSrvView")
                        .instanceField("android.support.v7.widget.AppCompatEditText", "mContext")
                        .instanceField("com.android.internal.policy.DecorView", "mPressGestureDetector")
                        .instanceField("com.android.internal.policy.DecorView", "mContextRoot")
                        .instanceField("android.widget.LinearLayout", "mContext")
                        .instanceField("com.android.internal.policy.PhoneWindow\$DecorView", "mContext")
                        .instanceField("android.support.v7.widget.SearchView\$SearchAutoComplete", "mContext")
                        .build()

                refWatcher = LeakCanary.refWatcher(application)
                        .excludedRefs(excludedRefs)
                        .listenerServiceClass(AppLeakCanaryService::class.java)
                        .buildAndInstall()
            }
        }
    }

}
