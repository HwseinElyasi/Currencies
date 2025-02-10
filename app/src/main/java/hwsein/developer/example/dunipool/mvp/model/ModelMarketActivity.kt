package hwsein.developer.example.dunipool.mvp.model

import android.util.Log
import hwsein.developer.example.dunipool.remote.ApiManager
import hwsein.developer.example.dunipool.remote.CallBackManager
import hwsein.developer.example.dunipool.remote.model.CoinData
import hwsein.developer.example.dunipool.remote.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelMarketActivity {

    private val retrofitHandler = ApiManager().apiService

    fun getNewsInServer(callBack : CallBackManager<ArrayList<Pair<String , String>>>){

        retrofitHandler.getNews().enqueue(object : Callback<NewsData> {

            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {

                val data = response.body()!!
                val dataToSend = ArrayList<Pair<String , String>>()

                data.data.forEach {
                    dataToSend.add(Pair(it.title , it.url))
                }

                callBack.onSuccess(dataToSend)

            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {

                callBack.onError(t.message.toString())
                Log.i("ERROR" , t.message.toString())

            }
        })


    }

    fun getCoinsInServer(callBack : CallBackManager<List<CoinData.Data>>){

        retrofitHandler.getTopCoins().enqueue(object : Callback<CoinData> {
            override fun onResponse(call: Call<CoinData>, response: Response<CoinData>) {

                val data = response.body()!!
                val sendData = data.data
                callBack.onSuccess(sendData)

            }

            override fun onFailure(call: Call<CoinData>, t: Throwable) {

                Log.i("SERVER_ERROR" , t.message.toString())

            }

        })


    }

}