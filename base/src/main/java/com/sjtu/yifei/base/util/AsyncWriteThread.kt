package com.sjtu.yifei.base.util

import android.os.Environment

import java.io.File
import java.io.IOException
import java.util.concurrent.ConcurrentLinkedQueue

import okio.Okio

/**
 * 类描述：异步写入字符串到文件中
 * 创建人：yifei
 * 创建时间：2018/10/29
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class AsyncWriteThread(private val dirName: String, private val fileName: String) : Thread() {

    companion object {
        fun init(dirName: String, fileName: String) {
            val asyncWriteThread = AsyncWriteThread(dirName, fileName)


        }


    }

    private var isRunning = false
    private val lock = java.lang.Object()
    var file: File? = null
        private set
    private val mQueue = ConcurrentLinkedQueue<String>()

    val isFinish: Boolean
        get() = mQueue.isEmpty() && !isRunning

    init {
        if (file == null) {
            file = initFile()
        }
        if (file != null) {
            isRunning = true
        }
    }

    fun enqueue(str: String) {
        mQueue.add(str)
        if (!isRunning) {
            awake()
        }
    }

    /**
     * 判断文件是否存在
     *
     * @return
     */
    val isFileExist: Boolean
        get() = if (file == null) {
            false
        } else file!!.exists() && file!!.length() > 3


    /**
     * 删除文件
     */
    fun deleteFile(): Boolean {
        return if (file == null) {
            false
        } else file!!.exists() && file!!.delete()
    }

    private fun awake() {
        synchronized(lock) {
            lock.notify()
        }
    }

    override fun run() {
        while (true) {
            synchronized(lock) {
                isRunning = true
                while (!mQueue.isEmpty()) {
                    try {
                        //pop出队列的头字符串写入到文件中
                        write(mQueue.poll())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                isRunning = false
                try {
                    lock.wait()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }

        }
    }

    private fun write(json: String) {
        try {
            if (file == null || !file!!.exists()) {
                file = initFile()
            }
            Okio.buffer(Okio.appendingSink(file!!))
                    .writeUtf8(json)
                    .close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun initFile(): File? {
        if (exist()) {
            val dir = File(dirName)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(this.dirName, fileName)
            try {
                if (!file.exists()) {
                    if (file.createNewFile()) {
                        return file
                    }
                } else {
                    return file
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return null
    }

    private fun exist(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

}
