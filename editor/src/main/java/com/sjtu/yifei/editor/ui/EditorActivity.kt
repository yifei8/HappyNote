package com.sjtu.yifei.editor.ui

import android.os.Bundle
import android.view.MotionEvent
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.base.util.setupActionBar
import com.sjtu.yifei.editor.R
import com.sjtu.yifei.router.RouterPath.LAUNCHER_EDITOR

@Route(path = LAUNCHER_EDITOR)
open class EditorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        init()
    }

    fun init() {
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // 返回true，那么代表事件就此消费，不会继续往别的地方传了，事件终止
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
    }

}