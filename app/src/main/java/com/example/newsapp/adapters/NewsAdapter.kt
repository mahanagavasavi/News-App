package com.example.newsapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.models.Article
import com.example.newsapp.R

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    lateinit var articleImage : ImageView
    lateinit var articleSource: TextView
    lateinit var articleTitle : TextView
    lateinit var articleDescription : TextView
    lateinit var articleDateTime : TextView

    val listNews=ArrayList<Article>()
   /* private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }*/

    fun setData(listNew: List<Article>)
    {
        listNews.clear()
        listNews.addAll(listNew)
        notifyDataSetChanged()
    }
    //val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news , parent , false)
        )
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    private var onItemClickListener : ((Article) -> Unit)? = null
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
      val article = listNews[position]
        articleImage = holder.itemView.findViewById(R.id.articleImage)
        articleTitle = holder.itemView.findViewById(R.id.articleTitle)
        articleSource = holder.itemView.findViewById(R.id.articleSource)
        articleDescription = holder.itemView.findViewById(R.id.articleDescription)
        articleDateTime = holder.itemView.findViewById(R.id.articleDateTime)


        holder.itemView.apply{
            article.urlToImage.let{
                Glide.with(this).load(article.urlToImage).into(articleImage)

            }
            articleSource.text = article.source?.name
            articleTitle.text = article.title
            articleDescription.text = article.description
            articleDateTime.text = article.publishedAt

            setOnClickListener{
                onItemClickListener?. let {
                    Log.d("HeadlinesFragment", "Article clicked: ${article.toString()}")
                    it(article)
                }
            }
        }


    }



    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClickListener = listener
    }
}