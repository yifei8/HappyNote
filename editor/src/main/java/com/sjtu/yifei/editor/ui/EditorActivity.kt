package com.sjtu.yifei.editor.ui

import android.os.Bundle
import android.widget.Toast
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.base.util.setupActionBar
import com.sjtu.yifei.editor.R
import com.sjtu.yifei.route.ActivityCallback
import com.sjtu.yifei.route.Routerfit
import com.sjtu.yifei.router.RouterPath.LAUNCHER_EDITOR
import com.sjtu.yifei.router.RouterService
import kotlinx.android.synthetic.main.content_editor.*

@Route(path = LAUNCHER_EDITOR)
open class EditorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        initData()
    }

    private fun initData() {
        tv.setOnClickListener {
            Routerfit.register(RouterService::class.java).openHybridUi("https://www.taobao.com", ActivityCallback { result, data ->
                Toast.makeText(MainActivity@ this.baseContext, "${result == Routerfit.RESULT_OK}     $data", Toast.LENGTH_SHORT).show()
            })
        }
        tv_finish_success.setOnClickListener {
            Routerfit.setResult(Routerfit.RESULT_OK, "调用 editor 成功")
            finish()
        }
    }




}