package com.sjtu.yifei.base.util

import android.util.Log

/**
 * 类描述：这里可以使用任意自己熟悉或者流行的log库，而不用修改业务层的log代码
 * 创建人：yifei
 * 创建时间：2018/10/29
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class LogUtil {

    companion object {
        // 设置为false则可以使得Log不输出
        var enable: Boolean = true

        fun isEnable(enable: Boolean) {
            LogUtil.enable = enable
        }

        fun v(tag: String, msg: String) {
            if (enable) {
                Log.v(tag, msg)
            }
        }

        fun d(tag: String, msg: String) {
            if (enable) {
                Log.d(tag, msg)
            }
        }

        fun i(tag: String, msg: String) {
            if (enable) {
                Log.i(tag, msg)
            }
        }

        fun e(tag: String, msg: String) {
            if (enable) {
                Log.e(tag, msg)
            }
        }

    }
}