package com.example.marsrealestate.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * Moshi 解析此 JSON 數據並將其轉換為 Kotlin 對象。
 * 為此，它需要有一個 Kotlin MarsProperty 數據類來存儲解析結果
 * 類中的每個變量都MarsProperty對應於 JSON 對像中的一個鍵名。
 *
 * 要在數據類中使用與 JSON 響應中的鍵名不同的變量名，請使用@Json註釋。
 * 數據類中變量的名稱是imgSrcUrl。變量被映射到JSON屬性 img_src 使用@Json(name = "img_src")。
 *
 * 該Parcelable接口使對象能夠被序列化，以便對象的數據可以在片段或活動之間傳遞。
 * 在這種情況下，要MarsProperty通過 Safe Args將對象內部的數據傳遞給詳細信息片段，MarsProperty必須實現該Parcelable接口。
 */
// 將@Parcelize註釋添加到類定義中
@Parcelize
data class MarsProperty(
    val id: String,
    // used to map img_src from the JSON to imgSrcUrl in our class
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double) : Parcelable {
    // 添加一些邏輯以指示屬性是否用於出租
    val isRental
    get() = type == "rent"
}
