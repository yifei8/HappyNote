package com.sjtu.yifei.base

import com.sjtu.yifei.base.util.AppUtils
import com.sjtu.yifei.base.util.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**https://github.com/git-xuhao/KotlinMvp.git
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/10/23
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class NetManager private constructor() {

    companion object {
        val instance: NetManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetManager()
        }
    }

    private var retrofitBuilder: Retrofit.Builder

    init {//构造函数
        retrofitBuilder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
    }

    fun <T> create(apiClass: Class<T>, baseUrl: String): T {
        return retrofitBuilder.baseUrl(baseUrl)
                .build()
                .create(apiClass)
    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(addCacheInterceptor())
                .addInterceptor(addHeaderInterceptor())
                .addInterceptor(addQueryParameterInterceptor())
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build()
    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("deviceModel", AppUtils.getMobileModel())
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("token", BaseApplication.token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(BaseApplication.context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(BaseApplication.context)) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }

}