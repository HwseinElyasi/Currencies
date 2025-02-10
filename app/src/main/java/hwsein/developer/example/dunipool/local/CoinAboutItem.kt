package hwsein.developer.example.dunipool.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinAboutItem(

    val website : String? = "no-data" ,
    val gitHb : String? = "no-data" ,
    val reddit : String? = "no-data" ,
    val desc : String? = "no-data" ,
    val twit : String? = "no-data"

) : Parcelable