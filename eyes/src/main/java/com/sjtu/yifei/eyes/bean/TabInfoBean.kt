package com.sjtu.yifei.eyes.bean

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/10/25
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
data class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)

    data class Tab(val id: Long, val name: String, val apiUrl: String)
}