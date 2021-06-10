package com.example.marsrealestate

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marsrealestate.network.MarsProperty
import com.example.marsrealestate.overview.MarsApiStatus
import com.example.marsrealestate.overview.PhotoGridAdapter

/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 * 當沒有Mars屬性數據時（數據為空），隱藏RecyclerView，否則顯示。
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data:List<MarsProperty>?){
    // 在bindRecyclerView()函數內部，強制轉換recyclerView.adapter為PhotoGridAdapter，
    // 並adapter.submitList()使用數據調用。這告訴RecyclerView新列表何時可用。
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

/**
 * 使用 Glide 庫通過 URL 將圖像加載到 [ImageView]
 *  bindImage()將 ImageView和 String作為參數的函數。
 *  用 註釋函數@BindingAdapter。該@BindingAdapter註解告訴數據綁定要執行此綁定適配器時的XML項具有imageUrl屬性。
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        // 使用 HTTPS 方案，請附加buildUpon.scheme("https")到toUri構建器。
        // 該toUri()方法是來自 Android KTX 核心庫的 Kotlin 擴展函數，因此它看起來就像是String類的一部分。
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        // 調用Glide.with()將圖像從Uri對象加載到ImageView. com.bumptech.glide.Glide請求時導入。
        Glide.with(imgView.context)
            .load(imgUri)
            .apply {
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            }
            .into(imgView)
    }
}

/**
 * This binding adapter displays the [MarsApiStatus] of the network request in an image view.
 * when  the request is loading, it displays a loading_animation.
 * If the request has an error, it displays a broken image to reflect the connection error.
 *  When the request is finished, it hides the image view.
 */
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarsApiStatus?){
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
