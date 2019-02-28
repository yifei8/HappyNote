package com.sjtu.yifei.happynote

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.Toast
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.base.util.setupActionBar
import com.sjtu.yifei.route.ActivityCallback
import com.sjtu.yifei.route.Routerfit
import com.sjtu.yifei.router.RouterService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                type_navigation = R.id.navigation_home
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                type_navigation = R.id.navigation_dashboard
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                type_navigation = R.id.navigation_notifications
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private var type_navigation: Int = R.id.navigation_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.app_name)
        }
        message.setOnClickListener {
            when (type_navigation) {
                R.id.navigation_home -> Routerfit.register(RouterService::class.java).openDetailList()
                R.id.navigation_dashboard -> Routerfit.register(RouterService::class.java).openEditorUi(ActivityCallback { result, data ->
                    Toast.makeText(MainActivity@ this, "${result == Routerfit.RESULT_OK}     $data", Toast.LENGTH_SHORT).show()
                })
                R.id.navigation_notifications -> Routerfit.register(RouterService::class.java).openHybridUi("www.baidu.com")
            }
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
