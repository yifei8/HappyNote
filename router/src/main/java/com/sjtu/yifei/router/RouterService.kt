package gl.center.testrouter.router

import com.sjtu.yifei.annotation.Go
import gl.center.testrouter.router.RouterPath.LAUNCHER_EDITOR
import gl.center.testrouter.router.RouterPath.NEED_LOGIN

interface RouterService {

    @Go(LAUNCHER_EDITOR)
    fun openEditorUi():Boolean

    @Go(NEED_LOGIN)
    fun getILoginProviderImpl(): ILoginProvider
}