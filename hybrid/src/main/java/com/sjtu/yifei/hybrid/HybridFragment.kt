package com.sjtu.yifei.hybrid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.github.lzyzsd.jsbridge.DefaultHandler
import kotlinx.android.synthetic.main.hybrid_fragment.*
import java.io.Serializable


class HybridFragment : Fragment() {

    companion object {
        const val TAG = "HybridFragment"
        private const val ARG_URL_KEY = "hybrid_url_key"
        fun newInstance(url: String): HybridFragment {
            val fragment = HybridFragment()
            val args = Bundle()
            args.putString(ARG_URL_KEY, url)
            fragment.arguments = args
            return fragment
        }
    }

    private var mUrl: String = ""
    private val RESULT_CODE = 0
    lateinit var mUploadMessage: ValueCallback<Uri>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.hybrid_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        webView.setDefaultHandler(DefaultHandler())
        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.d(TAG, "newProgress -> $newProgress")
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String, capture: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                mUploadMessage = uploadMsg
                val chooseIntent = Intent(Intent.ACTION_GET_CONTENT)
                chooseIntent.type = "image/*"
                startActivityForResult(chooseIntent, RESULT_CODE)
            }
        }

        mUrl = arguments!!.getString(ARG_URL_KEY)
        if (!TextUtils.isEmpty(mUrl)) {
            webView.loadUrl(mUrl)
        } else {
            webView.loadUrl("file:///android_asset/demo.html")
        }


        webView.registerHandler("submitFromWeb") { data, function ->
            Log.i(TAG, "handler = submitFromWeb, data from web = $data")
            function.onCallBack("submitFromWeb exe, response data 中文 from Java")
        }

        webView.send("hello")

    }


    data class User(val name: String
                    , val location: Location
                    , val testStr: String) : Serializable

    data class Location(val address: String)
}
