package com.example.marsrealestate.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

/**
 * Retrofit 至少需要兩個可用的東西來構建 Web 服務 API：Web 服務的基本 URI 和轉換器工廠。
 * 轉換器告訴 Retrofit 如何處理它從 Web 服務返回的數據。
 * 在這種情況下，您希望 Retrofit 從 Web 服務獲取 JSON 響應，並將其作為String.
 * Retrofit 具有ScalarsConverter支持字符串和其他原始類型的 ，
 * 因此您可以addConverterFactory()使用ScalarsConverterFactory.
 * 最後，您調用build()以創建 Retrofit 對象。
 *
 *  在調用 Retrofit 構建器的正下方，定義一個接口，該接口定義 Retrofit 如何使用 HTTP 請求與 Web 服務器通信。
 *  在MarsApiService接口下方，定義一個公共對象，調用它MarsApi來初始化 Retrofit 服務。這是在創建服務對象時使用的標準 Kotlin 代碼模式。
 */

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 *  定義一個接口，該接口定義 Retrofit 如何使用 HTTP 請求與 Web 服務器通信。
 *  使用@GET註釋並指定該 Web 服務方法的路徑或端點。在這種情況下，端點稱為realestate。
 *  getProperties()調用該方法時，Retrofit 將端點附加realestate到基本 URL [BASE_URL]
 * ，並創建一個Call對象。該對Call像用於啟動請求。
 */
interface MarsApiService {
    @GET("realestate")
    fun getProperties():
        Call<String>
}

/**
 * Retrofit.create()方法使用MarsApiService interface 創建 Retrofit 服務本身。
 * 由於此調用的計算量很大，因此您可以延遲初始化 Retrofit 服務，需要使用時才會用到
 * 由於應用程序只需要一個 Retrofit 服務實例，您可以使用一個名為 的公共對象將該服務公開給應用程序的其餘部分[MarsApi]
 * 一旦所有設置完成，每次您的應用程序調用 時 [MarsApi.retrofitService]，它都會獲得一個實現MarsApiService.
 *
 *  “延遲實例化 by lazy”是指故意延遲對象創建，直到您真正需要該對像以避免不必要的計算或使用其他計算資源。
 */
object MarsAPi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}