package com.sjtu.yifei.hybrid.web;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sjtu.yifei.base.OkHttpClientManager;
import com.sjtu.yifei.base.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 如果要自定义WebViewClient必须要集成此类
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private String TAG = "BridgeWebViewClient";
    private BridgeWebView webView;
    private final OkHttpClient client;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
        client = OkHttpClientManager.Companion.getInstance().getBaseClient("webview");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            return this.onCustomShouldOverrideUrlLoading(view, url) || super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String url = request.getUrl().toString();
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
                webView.handlerReturnData(url);
                return true;
            } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
                webView.flushMessageQueue();
                return true;
            } else {
                return this.onCustomShouldOverrideUrlLoading(view, url) || super.shouldOverrideUrlLoading(view, request);
            }
        } else {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    protected boolean onCustomShouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);

        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }

        onCustomPageFinished(view, url);

    }

    protected void onCustomPageFinished(WebView view, String url) {

    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse response = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            response = loadResponse(request);
        }
        return response != null ? response : super.shouldInterceptRequest(view, request);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private WebResourceResponse loadResponse(WebResourceRequest request) {
        String url = request.getUrl().toString();
        // suppress favicon requests as we don't display them anywhere
        if (!url.endsWith(".js")) {
            return null;
        }
        try {
            Map<String, String> headers = request.getRequestHeaders();
            Request.Builder builder = new Request.Builder().url(url);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            Request okReq = builder.build();
            final long startMillis = System.currentTimeMillis();
            final Response okResp = client.newCall(okReq).execute();
            final long dtMillis = System.currentTimeMillis() - startMillis;
            LogUtil.Companion.d(TAG, url + "\n Got response after " + dtMillis + "ms");
            return okHttpResponseToWebResourceResponse(okResp);
        } catch (Exception e) {
            LogUtil.Companion.e(TAG, "Failed to load request: " + url);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convert OkHttp {@link Response} into a {@link WebResourceResponse}
     *
     * @param resp The OkHttp {@link Response}
     * @return The {@link WebResourceResponse}
     */
    private WebResourceResponse okHttpResponseToWebResourceResponse(Response resp) {
        final String contentTypeValue = resp.header("Content-Type");
        if (contentTypeValue != null) {
            if (contentTypeValue.indexOf("charset=") > 0) {
                final String[] contentTypeAndEncoding = contentTypeValue.split("; ");
                final String contentType = contentTypeAndEncoding[0];
                final String charset = contentTypeAndEncoding[1].split("=")[1];
                return new WebResourceResponse(contentType, charset, resp.body().byteStream());
            } else {
                return new WebResourceResponse(contentTypeValue, null, resp.body().byteStream());
            }
        } else {
            return new WebResourceResponse("application/octet-stream", null, resp.body().byteStream());
        }
    }
}
