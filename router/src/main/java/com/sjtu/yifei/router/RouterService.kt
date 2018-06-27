package gl.center.testrouter.router

import android.content.Intent
import com.sjtu.yifei.annotation.Flags
import com.sjtu.yifei.annotation.Go
import com.sjtu.yifei.annotation.Uri


/**
 * Created by center
 */ 
interface RouterService {
    //Activity 跳转，支持注解传入参数/Flags/requestCode，参数解析遵循android机制
    @Flags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    @Go(RouterPath.GL_LOGIN_ACTIVITY)
    fun launchLoginActivity(): Boolean

    //支持直接解析标准URL进行跳转 传入 注解@Uri 参数
    fun launchSchemeActivity(@Uri uriString: String): Boolean
    
//    @Go("/test-module1/Test1Activity")
//    fun launchTestActivityForResult(@Extra("para1") para1: String, @Extra("para2") para2: Int, @RequestCode requestCode: Int): Boolean
//
//    //Fragment初始化，支持注解传入参数，参数解析遵循android机制
//    @Go("/login-module/TestFragment")
//    fun getTestFragment(@Extra("param1") para1: String, @Extra("param2") para2: IntArray): Fragment

    fun getILoginProviderImpl(): ILoginProvider
}