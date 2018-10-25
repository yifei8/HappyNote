package com.sjtu.yifei.eyes.ui

import android.os.Bundle
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.eyes.R
import com.sjtu.yifei.router.RouterPath

@Route(path = RouterPath.LAUNCHER_EYES)
open class EyesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eyes_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, EyesFragment.newInstance())
                    .commitNow()
        }
    }

}
