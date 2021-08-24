package com.unicorn.refuel.ui.fra.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.R
import com.unicorn.refuel.app.addDefaultItemDecoration
import com.unicorn.refuel.app.defaultPageSize
import com.unicorn.refuel.app.errorMsg
import com.unicorn.refuel.app.toast
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import io.reactivex.rxjava3.core.Single


abstract class PageFra<T> : BaseFra() {

    abstract fun initPageAdapter()

    abstract fun loadPage(page: Int): Single<Response<Page<T>>>

    protected lateinit var pageAdapter: BaseQuickAdapter<T, BaseViewHolder>

    private val loadMoreModule get() = pageAdapter.loadMoreModule

    private val startPage = 1

    protected open val pageSize = defaultPageSize

    private val size
        get() = pageAdapter.data.size

    private val nextPage
        get() = size / pageSize + startPage

    override fun initViews() {
        initSwipeRefreshLayout()
        initRecyclerView()
        initLoadMoreModule()
    }

    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout.run {
            setColorSchemeResources(R.color.white)
//            setProgressBackgroundColorSchemeResource(R.color.md_red_700)
        }
    }

    private fun initRecyclerView() {
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            initPageAdapter()
            adapter = pageAdapter
        }
        initItemDecoration(mRecyclerView)
    }

    protected open fun initItemDecoration(recyclerView: RecyclerView) {
        mRecyclerView.addDefaultItemDecoration()
    }

    private fun initLoadMoreModule() {
        loadMoreModule.run {
            // 设置为 false 可以更好地测试分页逻辑
            isAutoLoadMore = true
            isEnableLoadMoreIfNotFullPage = true
        }
    }

    override fun initBindings() {
        mSwipeRefreshLayout.setOnRefreshListener { loadStartPage() }
        loadMoreModule.setOnLoadMoreListener { loadNextPage() }
        loadStartPage()
    }

    protected open fun loadStartPage() {
        mSwipeRefreshLayout.isRefreshing = true
        loadPage(startPage)
            .lifeOnMain(this)
            .subscribe({
                mSwipeRefreshLayout.isRefreshing = false
                if (it.failed) return@subscribe
                pageAdapter.setList(it.data.content)
                checkIsLoadAll(it)
//                setEmptyViewIfNeed(it)
            }, {
                mSwipeRefreshLayout.isRefreshing = false
                it.errorMsg().toast()
            })
    }

//    private fun setEmptyViewIfNeed(pageResponse: Response<Page<T>>) {
//        if (pageResponse.data.content.isEmpty()) pageAdapter.setEmptyView(R.layout.ui_empty_view)
//    }

    private fun loadNextPage() {
        loadPage(nextPage)
            .lifeOnMain(this)
            .subscribe({
                if (it.failed) {
                    loadMoreModule.loadMoreFail()
                    return@subscribe
                }
                pageAdapter.addData(it.data.content)
                loadMoreModule.loadMoreComplete()
                checkIsLoadAll(it)
            }, {
                loadMoreModule.loadMoreFail()
                it.errorMsg().toast()
            })
    }

    private fun checkIsLoadAll(pageResponse: Response<Page<T>>) {
        val isLoadAll = size == pageResponse.data.totalElements.toInt()
        if (isLoadAll) loadMoreModule.loadMoreEnd()
    }

    abstract val mRecyclerView: RecyclerView

    abstract val mSwipeRefreshLayout: SwipeRefreshLayout

}