package com.sjtu.yifei.business

import android.os.Bundle
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.base.util.setupActionBar
import com.sjtu.yifei.router.RouterPath

@Route(path = RouterPath.LAUNCHER_BUS1)
class Bus1Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus1)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
}
