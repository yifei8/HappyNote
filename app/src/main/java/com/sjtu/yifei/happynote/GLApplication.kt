package com.sjtu.yifei.happynote

import com.sjtu.yifei.base.BaseApplication
import com.sjtu.yifei.base.util.AppUtils
import com.sjtu.yifei.performance.PerformanceDetection
import com.sjtu.yifei.route.Routerfit

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/6/27
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class GLApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        /**
         * This process is dedicated to LeakCanary for heap analysis.
         * You should not init your app in this process.
         */
        if (PerformanceDetection.instance.isInAnalyzerProcess(this)) {
            return
        }

        if (AppUtils.isAppProcess(this)) {
            Routerfit.init(this)
            PerformanceDetection.instance.initLeakCanary(this, true)
        }
    }
}