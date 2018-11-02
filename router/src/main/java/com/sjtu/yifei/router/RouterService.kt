package com.sjtu.yifei.router

import android.app.Application
import com.sjtu.yifei.annotation.Extra
import com.sjtu.yifei.annotation.Go
import com.sjtu.yifei.route.ActivityCallback
import com.sjtu.yifei.router.RouterPath.I_PERFORMANCE_PROVIDER
import com.sjtu.yifei.router.RouterPath.LAUNCHER_BUS1
import com.sjtu.yifei.router.RouterPath.LAUNCHER_EDITOR
import com.sjtu.yifei.router.RouterPath.LAUNCHER_HYBRID
import com.sjtu.yifei.router.RouterPath.LAUNCHER_LOGIN

interface RouterService {

    @Go(LAUNCHER_LOGIN)
    fun openLoginUi(@Extra callback: ActivityCallback): Boolean

    @Go(LAUNCHER_EDITOR)
    fun openEditorUi(): Boolean

    @Go(LAUNCHER_BUS1)
    fun openBus1Ui(): Boolean

    @Go(LAUNCHER_HYBRID)
    fun openHybridUi(@Extra("url") url: String): Boolean

    @Go(I_PERFORMANCE_PROVIDER)
    fun openPerformanceDetection(@Extra("application") application: Application, @Extra("isOpenWatcher") isOpenWatcher: Boolean): IPerformanceProvider
}