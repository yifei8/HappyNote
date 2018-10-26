package com.sjtu.yifei.login

import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.route.Routerfit
import com.sjtu.yifei.router.ILoginProvider
import com.sjtu.yifei.router.RouterPath
import com.sjtu.yifei.router.RouterService

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/10/26
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@Route(path = RouterPath.NEED_LOGIN)
class ILoginProviderImpl : ILoginProvider {
    override fun login(): Boolean {
        Routerfit.register(RouterService::class.java).openLoginUi()
        return false
    }

}