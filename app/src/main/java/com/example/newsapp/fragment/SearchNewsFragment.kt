package com.example.newsapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.NewsActivity
import com.example.newsapp.NewsViewModel
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter:NewsAdapter
    val TAG="SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)

            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articalFragment,
                bundle
            )
        }

        var job:Job? =null
        view.findViewById<EditText>(R.id.etSearch).addTextChangedListener {editable ->
            job?.cancel()
            job= MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
             }
        }


        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages=newsResponse.totalResults/ Constants.QUERY_PAGE_SIZE + 2
                        isLastPage=viewModel.searchNewsPage ==totalPages

                        if(isLastPage){
                                view?.findViewById<RecyclerView>(R.id.rvSearchNews)?.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity,"An Error Occured :$message", Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }
    private fun hideProgressBar() {
        view?.findViewById<View>(R.id.paginationProgressBar)?.visibility = View.INVISIBLE
        isLoading=false
    }

    private fun showProgressBar() {
        view?.findViewById<View>(R.id.paginationProgressBar)?.visibility = View.VISIBLE
        isLoading=true
    }

    var isLoading=false
    var isLastPage=false
    var isScrolling=false

    val scrollListener =object :RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager=recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition=layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount=layoutManager.childCount
            val totalItemCount=layoutManager.itemCount

            val isNotLoadingAndNotLastPage= !isLoading && !isLastPage
            val isAtLastItem=firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning=firstVisibleItemPosition >= 0
            val isTotalMoreThanVIsible=totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate=isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVIsible && isScrolling

            if(shouldPaginate){
                viewModel.searchNews(view?.findViewById<EditText>(R.id.etSearch)?.text.toString())
                isScrolling=false
            }
        }
    }
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        view?.findViewById<RecyclerView>(R.id.rvSearchNews)?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }
}