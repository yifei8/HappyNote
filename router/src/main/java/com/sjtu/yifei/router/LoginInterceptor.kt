package com.sjtu.yifei.router

import android.util.Log
import android.widget.Toast
import com.sjtu.yifei.annotation.Interceptor
import com.sjtu.yifei.route.AInterceptor
import com.sjtu.yifei.route.ActivityCallback
import com.sjtu.yifei.route.ActivityLifecycleMonitor
import com.sjtu.yifei.route.Routerfit


@Interceptor(priority = 3)
class LoginInterceptor : AInterceptor {

    override fun intercept(chain: AInterceptor.Chain) {
        Log.e(TAG, "path:" + chain.path())
        //需要登录
        if (RouterPath.LAUNCHER_BUS1.equals(chain.path(), ignoreCase = true)) {
            Routerfit.register(RouterService::class.java).openLoginUi(ActivityCallback { result, data ->
                if (result == Routerfit.RESULT_OK) {//登录成功
                    Toast.makeText(ActivityLifecycleMonitor.getTopActivity(), "登录成功", Toast.LENGTH_SHORT).show()
                    chain.proceed()
                } else {
                    Toast.makeText(ActivityLifecycleMonitor.getTopActivity(), "请先完成登录", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            chain.proceed()
        }
    }

    companion object {
        private const val TAG = "LoginInterceptor"
    }

}
