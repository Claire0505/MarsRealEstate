package com.example.marsrealestate.network

import com.squareup.moshi.Json

/**
 * Moshi 解析此 JSON 數據並將其轉換為 Kotlin 對象。
 * 為此，它需要有一個 Kotlin MarsProperty 數據類來存儲解析結果
 * 類中的每個變量都MarsProperty對應於 JSON 對像中的一個鍵名。
 *
 * 要在數據類中使用與 JSON 響應中的鍵名不同的變量名，請使用@Json註釋。
 * 數據類中變量的名稱是imgSrcUrl。變量被映射到JSON屬性 img_src 使用@Json(name = "img_src")。
 */
data class MarsProperty(
    val id: String,
    // used to map img_src from the JSON to imgSrcUrl in our class
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double) {
    // 添加一些邏輯以指示屬性是否用於出租
    val isRental
    get() = type == "rent"
}
