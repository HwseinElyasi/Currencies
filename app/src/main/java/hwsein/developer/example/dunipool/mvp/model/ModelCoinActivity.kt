package hwsein.developer.example.dunipool.mvp.model

import ChartData
import android.util.Log
import hwsein.developer.example.dunipool.remote.ApiManager
import hwsein.developer.example.dunipool.remote.CallBackManager
import hwsein.developer.example.dunipool.remote.requirement.ALL
import hwsein.developer.example.dunipool.remote.requirement.HISTO_DAY
import hwsein.developer.example.dunipool.remote.requirement.HISTO_HOUR
import hwsein.developer.example.dunipool.remote.requirement.HOUR
import hwsein.developer.example.dunipool.remote.requirement.HOURS24
import hwsein.developer.example.dunipool.remote.requirement.MONTH
import hwsein.developer.example.dunipool.remote.requirement.MONTH3
import hwsein.developer.example.dunipool.remote.requirement.WEEK
import hwsein.developer.example.dunipool.remote.requirement.YEAR
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelCoinActivity(

) {

    private val retrofitHandler = ApiManager().apiService

    fun getChartData(
        symbol: String,
        period: String,
        callBackManager: CallBackManager<Pair<List<ChartData.Data> , ChartData.Data?>>,
    ) {

         var histoperiod = ""
        var limit = 30
        var aggregate = 1

        when (period) {

            HOUR -> {

                histoperiod = HISTO_HOUR
                limit = 60
                aggregate = 12

            }

            HOURS24 -> {

                histoperiod = HISTO_HOUR
                limit = 24

            }

            WEEK -> {

                histoperiod = HISTO_HOUR
                aggregate = 6

            }

            MONTH -> {

                histoperiod = HISTO_DAY
                limit = 30

            }

            MONTH3 -> {

                histoperiod = HISTO_DAY
                limit = 90

            }

            YEAR -> {

                histoperiod = HISTO_DAY
                aggregate = 13

            }

            ALL -> {

                histoperiod = HISTO_DAY
                aggregate = 30
                limit = 2000


            }

        }

        retrofitHandler.getChartData(
            period = histoperiod,
            fromSymbol = symbol,
            limit = limit,
            aggregate = aggregate
        ).enqueue(object : Callback<ChartData> {

            override fun onResponse(call: Call<ChartData>, response: Response<ChartData>) {

                //این تمام دیتاهاست ولی ما که همرو لازم نداریم
                val fullData = response.body()!!
                //دیتایی که برای نقاط روی نمودار لازم داریم
                val data1 = fullData.data
                //اینم اون تابعی که باهاش مقار ماکسیموم کلوز رو از دله دیتاکلاس میکشیم بیرون که اگه چیزی وجود نداشت باشه برامون نال برمیگردونه =>
                val data2 = fullData.data.maxByOrNull { it.close.toFloat() }
                val pair = Pair(data1 , data2)

                callBackManager.onSuccess(pair)

            }

            override fun onFailure(call: Call<ChartData>, t: Throwable) {
                Log.i("SERVER_ERROR", t.message.toString())
            }


        })


    }


}