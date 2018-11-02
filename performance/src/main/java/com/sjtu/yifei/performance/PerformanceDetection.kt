package com.sjtu.yifei.performance

import android.app.Application
import android.support.v4.app.Fragment
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.router.IPerformanceProvider
import com.sjtu.yifei.router.RouterPath
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
@Route(path = RouterPath.I_PERFORMANCE_PROVIDER)
class PerformanceDetection constructor(private val application: Application, private val isOpenWatcher: Boolean) : IPerformanceProvider {

    private var refWatcher: RefWatcher? = null
    private var isInAnalyzerProcess: Boolean = false

    override fun isInAnalyzerProcess(): Boolean {
        return isInAnalyzerProcess
    }

    /**
     * 观察fragment内存泄漏
     * @param fragment
     */
    override fun watcherFragment(fragment: Fragment) {
        if (refWatcher != null) {
            refWatcher!!.watch(fragment)
        }
    }

    /**
     * 必须在UI线程中初始化
     */
    override fun initLeakCanary() {
        if (BuildConfig.DEBUG && isOpenWatcher) {
            if (LeakCanary.isInAnalyzerProcess(application)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                isInAnalyzerProcess = true
            } else {
                if (refWatcher == null) {
                    val excludedRefs = AndroidExcludedRefs.createAppDefaults()
                            .instanceField("android.view.inputmethod.InputMethodManager", "sInstance")
                            .instanceField("android.view.inputmethod.InputMethodManager", "mLastSrvView")
                            .instanceField("android.support.v7.widget.AppCompatEditText", "mContext")
                            .instanceField("com.android.internal.policy.DecorView", "mContextRoot")
                            .instanceField("android.widget.LinearLayout", "mContext")
                            .instanceField("com.android.internal.policy.PhoneWindow\$DecorView", "mContext")
                            .instanceField("android.support.v7.widget.SearchView\$SearchAutoComplete", "mContext")
                            .build()

                    refWatcher = LeakCanary.refWatcher(application)
                            .listenerServiceClass(AppLeakCanaryService::class.java)
                            .excludedRefs(excludedRefs)
                            .buildAndInstall()
                }
            }
        }
    }

}
