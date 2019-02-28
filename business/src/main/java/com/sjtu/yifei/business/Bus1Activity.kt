package com.sjtu.yifei.business

import android.os.Bundle
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.base.util.LogUtil
import com.sjtu.yifei.base.util.setupActionBar
import com.sjtu.yifei.business.api.ApiService
import com.sjtu.yifei.eyes.bean.HomeBean
import com.sjtu.yifei.router.RouterPath
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bus1.*

@Route(path = RouterPath.LAUNCHER_BUS1)
class Bus1Activity : BaseActivity() {

    companion object {
        const val EXTRA_POSITION = "position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus1)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val position = intent.getIntExtra(EXTRA_POSITION, -1)

        tv1.text = "position: $position"

        subscribe(ApiService.apiService.getFirstHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<HomeBean>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: HomeBean) {
                        LogUtil.d(TAG, t.toString())
                    }

                    override fun onError(e: Throwable) {
                    }

                }))

    }
}
