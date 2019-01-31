package com.sjtu.yifei.performance

import com.squareup.leakcanary.AnalysisResult
import com.squareup.leakcanary.DisplayLeakService
import com.squareup.leakcanary.HeapDump

class AppLeakCanaryService : DisplayLeakService() {

    override fun afterDefaultHandling(heapDump: HeapDump, result: AnalysisResult, leakInfo: String) {
        super.afterDefaultHandling(heapDump, result, leakInfo)
        // TODO: 2018/7/9  这里可以将内存泄漏上传到自己到服务器上
    }
}
