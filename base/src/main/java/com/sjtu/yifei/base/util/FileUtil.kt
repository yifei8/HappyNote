package com.sjtu.yifei.base.util

import android.content.Context
import android.os.Environment

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/11/1
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class FileUtil {

    companion object {

        /**
         * /data/data/com.xxx.xxx/cache
         */
        fun getCacheDir(context: Context): String {
            return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
                    || !Environment.isExternalStorageRemovable())
                context.externalCacheDir!!.path
            else
                context.cacheDir.path
        }

        /**
         * /data/data/com.xxx.xxx/files
         */
        fun getFilesDir(context: Context): String {
            return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
                    || !Environment.isExternalStorageRemovable())
                context.externalCacheDir!!.path
            else
                context.filesDir.path
        }
    }

}