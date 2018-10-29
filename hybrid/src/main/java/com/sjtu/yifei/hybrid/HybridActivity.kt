package com.sjtu.yifei.hybrid

import android.os.Bundle
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.base.util.setupActionBar
import com.sjtu.yifei.router.RouterPath

@Route(path = RouterPath.LAUNCHER_HYBRID)
class HybridActivity : BaseActivity() {

    private var mUrl:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hybrid_activity)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        mUrl = intent.getStringExtra("hybrid_load_url")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HybridFragment.newInstance(mUrl))
                    .commitNow()
        }
    }

}
