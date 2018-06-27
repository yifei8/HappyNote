package com.sjtu.yifei.base.util

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.transact { replace(id, fragment) }
}

fun AppCompatActivity.addFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.transact { add(id, fragment) }
}

fun AppCompatActivity.findFragmentByTag(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

fun AppCompatActivity.findFragmentById(tag: Int): Fragment? =
        supportFragmentManager.findFragmentById(tag)

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}