package com.sjtu.yifei.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.business.R
import com.sjtu.yifei.route.Routerfit
import com.sjtu.yifei.router.RouterPath
import com.sjtu.yifei.router.RouterService
import kotlinx.android.synthetic.main.activity_detail_list.*

@Route(path = RouterPath.LAUNCHER_DETAIL_LIST)
class DetailListActivity : BaseActivity() {

    companion object {
        val items = listOf(
                "给初学者的RxJava2.0教程（七）: Flowable",
                "Android之View的诞生之谜",
                "Android之自定义View的死亡三部曲之Measure",
                "Using ThreadPoolExecutor in Android ",
                "Kotlin 泛型定义与 Java 类似，但有着更多特性支持。",
                "Android异步的姿势，你真的用对了吗？",
                "Android 高质量录音库。",
                "Android 边缘侧滑效果，支持多种场景下的侧滑退出。"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_list)

        listview.layoutManager = LinearLayoutManager(this)
        listview.adapter = MainAdapter(items)
    }

    class MainAdapter(val items : List<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = items[position]
            holder.textView.setOnClickListener {
                Routerfit.register(RouterService::class.java).openBus1Ui(position)
            }
        }

        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    }
}
