package com.sjtu.yifei.router

import android.support.v4.app.Fragment

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/11/2
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface IPerformanceProvider {

    /**
     * This process is dedicated to LeakCanary for heap analysis.
     * You should not init your app in this process.
     */
    fun isInAnalyzerProcess(): Boolean

    fun watcherFragment(fragment: Fragment)

    fun initLeakCanary()
}