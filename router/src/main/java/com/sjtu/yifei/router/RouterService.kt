package com.sjtu.yifei.router

import com.sjtu.yifei.annotation.Go
import com.sjtu.yifei.router.RouterPath.LAUNCHER_EDITOR
import com.sjtu.yifei.router.RouterPath.LAUNCHER_BUS1
import com.sjtu.yifei.router.RouterPath.LAUNCHER_HYBRID
import com.sjtu.yifei.router.RouterPath.NEED_LOGIN

interface RouterService {

    @Go(LAUNCHER_EDITOR)
    fun openEditorUi():Boolean

    @Go(LAUNCHER_BUS1)
    fun openBus1Ui():Boolean

    @Go(LAUNCHER_HYBRID)
    fun openHybridUi():Boolean

    @Go(NEED_LOGIN)
    fun getILoginProviderImpl(): ILoginProvider
}