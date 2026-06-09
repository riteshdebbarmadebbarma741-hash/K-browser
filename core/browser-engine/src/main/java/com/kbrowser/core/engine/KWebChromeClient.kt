package com.kbrowser.core.engine

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView

class KWebChromeClient(
    private val onProgressChanged: (Int) -> Unit,
    private val onReceivedIcon: (Bitmap) -> Unit,
    private val onReceivedTitle: (String) -> Unit,
    private val onShowCustomView: (View, WebChromeClient.CustomViewCallback) -> Unit,
    private val onHideCustomView: () -> Unit
) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        onProgressChanged(newProgress)
    }

    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
        super.onReceivedIcon(view, icon)
        icon?.let { onReceivedIcon(it) }
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        title?.let { onReceivedTitle(it) }
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        if (view != null && callback != null) {
            onShowCustomView(view, callback)
        }
    }

    override fun onHideCustomView() {
        onHideCustomView()
    }
}
