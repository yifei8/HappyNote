package com.sjtu.yifei.business.api

import com.sjtu.yifei.base.OkHttpClientManager
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/11/1
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class RetrofitManager private constructor() {


    companion object {
        val instance: RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManager()
        }
    }

    private var retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClientManager.instance.getBaseClient("retrofit"))

    fun <T> create(apiClass: Class<T>, baseUrl: String): T {
        return retrofitBuilder.baseUrl(baseUrl)
                .build()
                .create(apiClass)
    }
}