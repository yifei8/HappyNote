package gl.center.testrouter.router

import android.util.Log
import com.sjtu.yifei.annotation.Interceptor
import com.sjtu.yifei.route.AInterceptor
import com.sjtu.yifei.route.Routerfit


@Interceptor(priority = 3)
class LoginInterceptor : AInterceptor {

    override fun intercept(chain: AInterceptor.Chain) {
        Log.e(TAG, "path:" + chain.path())
        //XXXXXX 需要登录
        if (RouterPath.NEED_LOGIN.equals(chain.path(), ignoreCase = true)) {
            val iProvider = Routerfit.register(RouterService::class.java).getILoginProviderImpl()
            iProvider.login()
        } else {
            chain.proceed()
        }
    }

    companion object {
        private val TAG = "LoginInterceptor"
    }

}
