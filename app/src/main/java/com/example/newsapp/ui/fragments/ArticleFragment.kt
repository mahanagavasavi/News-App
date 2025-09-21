package com.example.newsapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {
      lateinit var newsViewModel: NewsViewModel
      lateinit var binding:FragmentArticleBinding
      val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding  = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel

        val article = args.article
        binding.progressBar.visibility = View.VISIBLE
        binding.webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = View.GONE
                }

                override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    binding.progressBar.visibility = View.GONE
                    // Optionally, you can show an error message to the user here.
                }
            }
            article.url?.let {
                loadUrl(it)
            }
        }
        binding.fab.setOnClickListener{
            newsViewModel.addToFavourite(article)
            Snackbar.make(view,"Added to favourites",Snackbar.LENGTH_SHORT).show()
        }
    }
}
