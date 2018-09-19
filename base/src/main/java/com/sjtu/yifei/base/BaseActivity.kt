package com.sjtu.yifei.base

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/6/27
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
open class BaseActivity: AppCompatActivity() {


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (android.R.id.home == id) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}