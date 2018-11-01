package com.sjtu.yifei.base

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/6/27
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
open class BaseActivity : AppCompatActivity() {

    var TAG = this.javaClass.simpleName

    private var mCompositeDisposable: CompositeDisposable? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (android.R.id.home == id) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        unSubscribe()
        super.onDestroy()
    }

    /**
     * 优化rx 内存泄漏问题
     *
     * @param disposable
     */
    fun subscribe(disposable: Disposable?) {
        if (disposable != null) {
            if (mCompositeDisposable == null) {
                mCompositeDisposable = CompositeDisposable()
            }
            mCompositeDisposable?.add(disposable)
        }
    }

    /**
     * 优化rx 内存泄漏问题
     */
    private fun unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.clear()
        }
    }


}