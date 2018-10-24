package com.sjtu.yifei.base

import okhttp3.Cache
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/10/23
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface ApiService {

    @GET("search/user")
    fun search(@Query("q") query: String,
               @Query("page") page: Int,
               @Query("pageSize") pageSize: Int)

    companion object Factory {
        const val BASE_URL = "http://baobab.kaiyanapp.com/api/"

        fun create(): ApiService? {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return null
        }

        private fun getOkHttpClient(): OkHttpClient {
            //添加一个log拦截器,打印所有的log
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            //可以设置请求过滤的水平,body,basic,headers
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            //设置 请求的缓存的大小跟位置
            val cacheFile = File("", "cache")
            val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

            return OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                    .cache(cache)  //添加缓存
                    .connectTimeout(60L, TimeUnit.SECONDS)
                    .readTimeout(60L, TimeUnit.SECONDS)
                    .writeTimeout(60L, TimeUnit.SECONDS)
                    .build()
        }
    }
}