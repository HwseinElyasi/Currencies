package hwsein.developer.example.dunipool.features.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import hwsein.developer.example.dunipool.R
import hwsein.developer.example.dunipool.databinding.ListItemBinding
import hwsein.developer.example.dunipool.remote.model.CoinData
import hwsein.developer.example.dunipool.remote.requirement.BASE_URL_IMAGE

class MarketAdapter(
    private val allData: List<CoinData.Data>,
    private val coin : CallBakAdapter
) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    private lateinit var binding: ListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {

        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(binding.root)

    }

    override fun getItemCount() = allData.size

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.setData(allData[position])
    }

    inner class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

        fun setData(data: CoinData.Data) {

         if (data.rAW != null && data.dISPLAY != null){

             binding.coiName.text = data.coinInfo.fullName
             binding.coinPrice.text = data.dISPLAY.uSD.pRICE

             val marketCapt = data.rAW.uSD.mKTCAP / 1000000000
             val dot2 = marketCapt.toString().indexOf('.')
             binding.marketCap.text = "$" + marketCapt.toString().substring(0 , dot2 + 3) + " B"

             Glide.with(itemView)
                 .load(BASE_URL_IMAGE + data.coinInfo.imageUrl)
                 .into(binding.imgCoin)

             val change = data.rAW.uSD.cHANGE24HOUR

             if (change > 0){

                 binding.coinChange.setTextColor(ContextCompat.getColor(itemView.context , R.color.colorGain))
                 binding.coinChange.text = change.toString().substring(0 , 6) + "%"

             }else if (change < 0){

                 binding.coinChange.setTextColor(ContextCompat.getColor(itemView.context , R.color.colorLoss))
                 binding.coinChange.text = change.toString().substring(0 , 7) + "%"

             }else
                 binding.coinChange.text = "0%"

             itemView.setOnClickListener {

                 coin.itemClickHandler(data)

             }

         }



        }

    }

    interface CallBakAdapter {

        fun itemClickHandler(coin : CoinData.Data)

    }


}