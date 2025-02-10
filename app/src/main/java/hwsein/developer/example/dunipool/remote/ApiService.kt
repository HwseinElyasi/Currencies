package hwsein.developer.example.dunipool.remote

import ChartData
import hwsein.developer.example.dunipool.remote.model.CoinData
import hwsein.developer.example.dunipool.remote.model.NewsData
import hwsein.developer.example.dunipool.remote.requirement.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("v2/news/")
    fun getNews(
        @Header(API_KEY) apiKey : String = API_KEY ,
        @Query("sortOrder") sortOrder : String = "popular"
    ) : Call<NewsData>



    @GET("top/totalvolfull")
    fun getTopCoins(
        @Header(API_KEY) apikey : String = API_KEY,
        @Query("tsym") to_symbol : String = "USD",
        @Query("limit") limit : Int = 10
    ) : Call<CoinData>


    @GET("{period}")
    fun getChartData(
        @Header(API_KEY) apiKey : String = API_KEY ,
        @Path("period") period : String ,
        @Query("fsym") fromSymbol : String ,
        @Query("limit") limit : Int ,
        @Query("aggregate") aggregate : Int ,
        @Query("tsym") toSymbol : String = "USD"
    ) : Call<ChartData>




}