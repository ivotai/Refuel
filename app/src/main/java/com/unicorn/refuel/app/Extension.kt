package com.unicorn.refuel.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

fun View.safeClicks(): Observable<Unit> = this.clicks()
    .throttleFirst(2, TimeUnit.SECONDS)

fun Activity.startAct(cls: Class<*>, finishSelf: Boolean = false) {
    startActivity(Intent(this, cls))
    if (finishSelf) finish()
}

fun Context.startAct(cls: Class<*>) {
    startActivity(Intent(this, cls))
}

fun Fragment.startAct(cls: Class<*>, finishAct: Boolean = false) {
    requireActivity().startAct(cls, finishSelf = finishAct)
}

fun TextView.trimText() = text.toString().trim()

fun TextView.isEmpty(): Boolean = trimText().isEmpty()

fun String?.toast() = this.let { ToastUtils.showShort(it) }

fun ViewPager2.removeEdgeEffect() {
    (this.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
}


fun Throwable.errorMsg(): String {
    val errorMsg = when (this) {
        is UnknownHostException -> "接口地址设置有误或无网络"
        is SocketTimeoutException -> "超时了"
        is HttpException -> when (code()) {
            500 -> "服务器内部错误"
            else -> "错误码${code()}"
        }
        else -> toString()
    }
    return errorMsg
}

fun RecyclerView.addDefaultItemDecoration() {
    this.apply {
        MaterialDividerItemDecoration(
            context,
            LinearLayoutManager.VERTICAL
        ).apply {
            dividerThickness = 1
        }.let { addItemDecoration(it) }
    }
}

fun Fragment.finishAct() = this.requireActivity().finish()

//fun Long.toDisplayDateFormat(): String = DateTime(this).toString(displayDateFormat)