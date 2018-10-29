package com.sjtu.yifei.router

import android.util.Log
import com.sjtu.yifei.annotation.Interceptor
import com.sjtu.yifei.route.AInterceptor
import com.sjtu.yifei.route.Routerfit


@Interceptor(priority = 3)
class LoginInterceptor : AInterceptor {

    override fun intercept(chain: AInterceptor.Chain) {
        Log.e(TAG, "path:" + chain.path())
        //需要登录
        if (RouterPath.LAUNCHER_BUS1.equals(chain.path(), ignoreCase = true)) {
            val iProvider = Routerfit.register(RouterService::class.java).getILoginProviderImpl()
            iProvider.login()
        } else {
            chain.proceed()
        }
    }

    companion object {
        private const val TAG = "LoginInterceptor"
    }

}
