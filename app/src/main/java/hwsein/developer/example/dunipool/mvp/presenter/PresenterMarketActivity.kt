package hwsein.developer.example.dunipool.mvp.presenter

import hwsein.developer.example.dunipool.mvp.model.ModelMarketActivity
import hwsein.developer.example.dunipool.mvp.view.ViewMarketActivity
import hwsein.developer.example.dunipool.remote.CallBackManager
import hwsein.developer.example.dunipool.remote.model.CoinData

class PresenterMarketActivity(
    private val view : ViewMarketActivity,
    private val model : ModelMarketActivity
) {

    fun onCreate(){

        newsHandler()
        coinListHandler()
        reloadData()
        createAboutData()

    }

    fun onResume(){

        newsHandler()
        coinListHandler()

    }

    private fun newsHandler(){

        model.getNewsInServer(object : CallBackManager<ArrayList<Pair<String, String>>> {
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {

                view.showNewsOrChange(data)

            }

            override fun onError(error: String) {}

        })


    }

    private fun coinListHandler(){

        model.getCoinsInServer(object : CallBackManager<List<CoinData.Data>> {
            override fun onSuccess(data: List<CoinData.Data>) {

                view.showCoinInMarketActivity(data)

            }

            override fun onError(error: String) {}


        })

    }

    private fun reloadData(){

        view.reloadDataByUsingSwipeRefresh()

    }

    private fun createAboutData(){

        view.getAboutDataFromAssets()

    }

}