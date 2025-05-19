package eu.kanade.tachiyomi.util.system

import android.annotation.TargetApi
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient

@Suppress("OverridingDeprecatedMember")
abstract class WebViewClientCompat : WebViewClient() {
    open fun shouldOverrideUrlCompat(
        view: WebView,
        url: String,
    ): Boolean = false

    open fun shouldInterceptRequestCompat(
        view: WebView,
        url: String,
    ): WebResourceResponse? = null

    open fun onReceivedErrorCompat(
        view: WebView,
        errorCode: Int,
        description: String?,
        failingUrl: String,
        isMainFrame: Boolean,
    ) {
    }

    @TargetApi(Build.VERSION_CODES.N)
    final override fun shouldOverrideUrlLoading(
      view: WebView,
      request: WebResourceRequest,
    ): Boolean = false

    // WebViewClient*.* (nama file kamu temukan)
    final override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String,
    ): Boolean = false          // ← biarkan WebView memuat sendiri
    
    final override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest,
    ): WebResourceResponse? = null   // ← jangan intercept
    
    final override fun shouldInterceptRequest(
        view: WebView,
        url: String,
    ): WebResourceResponse? = null   // ← jangan intercept
    
    final override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError,
    ) {
        onReceivedErrorCompat(
            view,
            error.errorCode,
            error.description?.toString(),
            request.url.toString(),
            request.isForMainFrame,
        )
    }

    final override fun onReceivedError(
        view: WebView,
        errorCode: Int,
        description: String?,
        failingUrl: String,
    ) {
        onReceivedErrorCompat(view, errorCode, description, failingUrl, failingUrl == view.url)
    }

    final override fun onReceivedHttpError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceResponse,
    ) {
        onReceivedErrorCompat(
            view,
            error.statusCode,
            error.reasonPhrase,
            request.url
                .toString(),
            request.isForMainFrame,
        )
    }
}
