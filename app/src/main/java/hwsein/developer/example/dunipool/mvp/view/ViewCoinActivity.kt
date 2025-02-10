package hwsein.developer.example.dunipool.mvp.view

import ChartData
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import hwsein.developer.example.dunipool.R
import hwsein.developer.example.dunipool.databinding.ActivityCoinBinding
import hwsein.developer.example.dunipool.features.adapter.ChartAdapter
import hwsein.developer.example.dunipool.local.CoinAboutItem
import hwsein.developer.example.dunipool.remote.model.CoinData
import hwsein.developer.example.dunipool.remote.requirement.ALL
import hwsein.developer.example.dunipool.remote.requirement.HOUR
import hwsein.developer.example.dunipool.remote.requirement.HOURS24
import hwsein.developer.example.dunipool.remote.requirement.MONTH
import hwsein.developer.example.dunipool.remote.requirement.MONTH3
import hwsein.developer.example.dunipool.remote.requirement.TWITTER_BASE_URL
import hwsein.developer.example.dunipool.remote.requirement.WEEK
import hwsein.developer.example.dunipool.remote.requirement.YEAR

class ViewCoinActivity(
    private val context : Context
) {
    val binding = ActivityCoinBinding.inflate(LayoutInflater.from(context))

    @SuppressLint("SetTextI18n")
    fun setStatisticsAndChartData(coin : CoinData.Data, sendCoinDataToModel: SendCoinDataToModel ){

        binding.layoutStatistics.txtOpen.text = coin.dISPLAY.uSD.oPEN24HOUR
        binding.layoutStatistics.txtTodayHigh.text = coin.dISPLAY.uSD.hIGH24HOUR
        binding.layoutStatistics.txtTodayLow.text = coin.dISPLAY.uSD.lOW24HOUR
        binding.layoutStatistics.txtChangeToday.text = coin.dISPLAY.uSD.cHANGE24HOUR
        binding.layoutStatistics.txtAlgorithm.text = coin.coinInfo.algorithm
        binding.layoutStatistics.txtTotalVolume.text = coin.dISPLAY.uSD.tOTALVOLUME24H
        binding.layoutStatistics.txTMarketcap.text = coin.dISPLAY.uSD.mKTCAP

        binding.toolbarCoin.toolbar.title = coin.coinInfo.fullName

        var period = HOUR

        sendCoinDataToModel.sendCoin(Pair(coin.coinInfo.name, period))
        binding.layoutChart.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            when(checkedId) {

                R.id.radio_12h -> period = HOUR

                R.id.radio_1day -> period = HOURS24

                R.id.radio_1week -> period = WEEK

                R.id.radio_1month -> period = MONTH

                R.id.radio_3month -> period = MONTH3

                R.id.radio_1year -> period = YEAR

                R.id.radio_all -> period = ALL

            }

            sendCoinDataToModel.sendCoin(Pair( coin.coinInfo.name, period))

        }

        binding.layoutChart.txtPrice.text = coin.dISPLAY.uSD.pRICE
        val change = coin.rAW.uSD.cHANGE24HOUR

        if (change > 0) {

            binding.layoutChart.txtChange2.setTextColor(ContextCompat.getColor(context , R.color.colorGain))
            binding.layoutChart.txtChange2.text = change.toString().substring(0 , 6) + "%"
            binding.layoutChart.txtChange1.text = " " + coin.dISPLAY.uSD.cHANGE24HOUR
            binding.layoutChart.txtUpDown.text = "▲"
            binding.layoutChart.txtUpDown.setTextColor(ContextCompat.getColor(context , R.color.colorGain))
            binding.layoutChart.sparkView.lineColor = ContextCompat.getColor(context , R.color.colorGain)

        } else if (change < 0){

            binding.layoutChart.txtChange2.setTextColor(ContextCompat.getColor(context , R.color.colorLoss))
            binding.layoutChart.txtChange2.text = change.toString().substring(0 , 6) + "%"
            binding.layoutChart.txtChange1.text = " " + coin.dISPLAY.uSD.cHANGE24HOUR
            binding.layoutChart.txtUpDown.text = "▼"
            binding.layoutChart.txtUpDown.setTextColor(ContextCompat.getColor(context , R.color.colorLoss))
            binding.layoutChart.sparkView.lineColor = ContextCompat.getColor(context , R.color.colorLoss)

        }else{

            binding.layoutChart.txtChange1.text = "0"
            binding.layoutChart.txtChange2.text = "$0"
            binding.layoutChart.txtUpDown.visibility = View.INVISIBLE

        }

        binding.layoutChart.sparkView.setScrubListener {

            if (it == null)

                binding.layoutChart.txtPrice.text = coin.dISPLAY.uSD.pRICE

            else
                binding.layoutChart.txtPrice.text = "$ " + (it as ChartData.Data).close.toString()

        }

    }

    @SuppressLint("SetTextI18n")
    fun setAboutData(coin : CoinAboutItem){

            binding.layoutAbout.textWebsite.text = coin.website
            binding.layoutAbout.textTwitter.text = "@" + coin.twit
            binding.layoutAbout.textGithub.text = coin.gitHb
            binding.layoutAbout.textReddit.text = coin.reddit
            binding.layoutAbout.someData.text = coin.desc

            binding.layoutAbout.textWebsite.setOnClickListener {

                openWebsiteCoin(coin.website!!)

            }
            binding.layoutAbout.textTwitter.setOnClickListener {

                openWebsiteCoin(TWITTER_BASE_URL + coin.twit!!)

            }
            binding.layoutAbout.textGithub.setOnClickListener {

                openWebsiteCoin(coin.gitHb!!)

            }
            binding.layoutAbout.textReddit.setOnClickListener {

                openWebsiteCoin(coin.reddit!!)

            }

        }


    private fun openWebsiteCoin(uri : String){

        val intent = Intent(Intent.ACTION_VIEW , Uri.parse(uri))
        context.startActivity(intent)

    }

    fun getChartData(data : Pair<List<ChartData.Data> , ChartData.Data?>){

        //مقدار second همون baseline که برای چارت خودمون ست کردیم که باید اونجایی ک نمودار open شده رو بهش بدیم دیگه...
        val chartAdapter = ChartAdapter(data.first , data.second?.open.toString())
        binding.layoutChart.sparkView.adapter = chartAdapter

    }

    interface SendCoinDataToModel{

        fun sendCoin(data : Pair<String , String>){}

    }




}