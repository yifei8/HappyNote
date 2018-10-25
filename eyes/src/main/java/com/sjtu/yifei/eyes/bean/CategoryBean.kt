package com.sjtu.yifei.eyes.bean

import java.io.Serializable

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/10/25
 * 修改人：
 * 修改时间：
 * 修改备注：
 * desc:分类 Bean
 */
data class CategoryBean(val id: Long, val name: String, val description: String, val bgPicture: String, val bgColor: String, val headerImage: String) : Serializable