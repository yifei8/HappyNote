package gl.center.testrouter.router

import android.content.Intent
import com.sjtu.yifei.annotation.Flags
import com.sjtu.yifei.annotation.Go
import com.sjtu.yifei.annotation.Uri

interface RouterService {
    //Activity 跳转，支持注解传入参数/Flags/requestCode，参数解析遵循android机制
    @Flags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    @Go(RouterPath.GL_LOGIN_ACTIVITY)
    fun launchLoginActivity(): Boolean

    fun getILoginProviderImpl(): ILoginProvider
}