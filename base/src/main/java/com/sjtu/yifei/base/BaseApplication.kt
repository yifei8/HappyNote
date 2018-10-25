package com.sjtu.yifei.base

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/6/27
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
open class BaseApplication : Application() {

    companion object {
        var context: Context by Delegates.notNull()
        var token: String by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        token = ""
    }

}