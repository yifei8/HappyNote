package com.sjtu.yifei.happynote

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.base.util.setupActionBar
import com.sjtu.yifei.route.Routerfit
import com.sjtu.yifei.router.RouterService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                message.setOnClickListener {
                    Routerfit.register(RouterService::class.java).openBus1Ui()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                message.setOnClickListener {
                    Routerfit.register(RouterService::class.java).openEditorUi()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                message.setOnClickListener {
                    Routerfit.register(RouterService::class.java).openHybridUi("http://www.baidu.com")
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.app_name)
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
