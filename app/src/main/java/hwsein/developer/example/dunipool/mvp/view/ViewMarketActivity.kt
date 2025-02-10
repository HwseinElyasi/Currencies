package hwsein.developer.example.dunipool.mvp.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import hwsein.developer.example.dunipool.databinding.ActivityMarketBinding
import hwsein.developer.example.dunipool.features.CoinActivity
import hwsein.developer.example.dunipool.features.adapter.MarketAdapter
import hwsein.developer.example.dunipool.local.CoinAboutData
import hwsein.developer.example.dunipool.local.CoinAboutItem
import hwsein.developer.example.dunipool.remote.model.CoinData


class ViewMarketActivity(
    private val context: Context,
) {
    private lateinit var data1: ArrayList<Pair<String, String>>
    private lateinit var data2: List<CoinData.Data>
    private lateinit var aboutDataMap: MutableMap<String, CoinAboutItem>

    val binding = ActivityMarketBinding.inflate(LayoutInflater.from(context))

    fun showNewsOrChange(data: ArrayList<Pair<String, String>>) {

        data1 = data

        val random = (0..45).random()
        binding.moduleNews.textNews.startAnimation(textAnimation())
        binding.moduleNews.textNews.text = data[random].first

        if (binding.moduleNews.textNews.text.isNotEmpty())
            binding.moduleNews.progressBar.visibility = View.GONE

        binding.moduleNews.imageNews.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data[random].second))
            context.startActivity(intent)

        }

        binding.moduleNews.textNews.setOnClickListener {
            showNewsOrChange(data)
        }

    }

    private fun textAnimation(): Animation {

        val anim = AlphaAnimation(0f, 1f)
        anim.duration = 2000
        anim.fillAfter = true

        return anim
    }

    fun getAboutDataFromAssets() {

        aboutDataMap = mutableMapOf<String , CoinAboutItem>()


        val fileInString = context.applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()
        val dataAboutAll = gson.fromJson(fileInString, CoinAboutData::class.java)
        dataAboutAll.forEach {

            aboutDataMap[it.currencyName] = CoinAboutItem(
                it.info.web,
                it.info.github,
                it.info.reddit,
                it.info.desc,
                it.info.twt
            )

        }


    }

    private fun cleanData(data: List<CoinData.Data>) : List<CoinData.Data>{

        val newData = mutableListOf<CoinData.Data>()

        data.forEach {

            if (it.rAW != null || it.dISPLAY != null) {
                newData.add(it)
            }

        }

        return newData
    }

    fun showCoinInMarketActivity(data: List<CoinData.Data>) {

        data2 = data

        binding.coinList.recyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )

        val adapter = MarketAdapter(cleanData(data), object : MarketAdapter.CallBakAdapter {

            override fun itemClickHandler(coin: CoinData.Data) {

                val bundle = Bundle()
                bundle.putParcelable("bundle1", coin)
                bundle.putParcelable("bundle2", aboutDataMap[coin.coinInfo.name])

                val intent = Intent(context, CoinActivity::class.java)
                intent.putExtra("coin", bundle)
                context.startActivity(intent)

            }

        })

        binding.coinList.recyclerView.adapter = adapter

        if (binding.coinList.recyclerView.adapter == adapter)
            binding.coinList.progressBarCoinList.visibility = View.GONE

        binding.coinList.moreButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://coinmarketcap.com/"))
            context.startActivity(intent)

        }

    }

    fun reloadDataByUsingSwipeRefresh() {

        binding.swipeRefresh.setOnRefreshListener {

            Toast.makeText(context, "اطلاعات به روز شدند!", Toast.LENGTH_SHORT).show()

            showCoinInMarketActivity(data2)
            showNewsOrChange(data1)

            Handler(Looper.getMainLooper()).postDelayed({

                binding.swipeRefresh.isRefreshing = false

            }, 1000)

        }


    }


}
