package hwsein.developer.example.dunipool.mvp.presenter

import ChartData
import hwsein.developer.example.dunipool.local.CoinAboutItem
import hwsein.developer.example.dunipool.mvp.model.ModelCoinActivity
import hwsein.developer.example.dunipool.mvp.view.ViewCoinActivity
import hwsein.developer.example.dunipool.remote.CallBackManager
import hwsein.developer.example.dunipool.remote.model.CoinData

class PresenterCoinActivity(
    private val view: ViewCoinActivity,
    private val model: ModelCoinActivity,
) {

    fun onCreate() {


    }

    fun setDataFromActivityAndChartDataFromServer(coinData: CoinData.Data){

        view.setStatisticsAndChartData(coinData , object : ViewCoinActivity.SendCoinDataToModel{

            override fun sendCoin(data: Pair<String, String>) {

                model.getChartData(data.first , data.second ,
                    object : CallBackManager<Pair<List<ChartData.Data> , ChartData.Data?>>{
                        override fun onSuccess(data: Pair<List<ChartData.Data>, ChartData.Data?>) {

                            view.getChartData(data)

                        }

                        override fun onError(error: String) {

                        }


                    })

            }

        })

    }

    fun receivedAboutDataFromActivity(item : CoinAboutItem){

        view.setAboutData(item)

    }

}