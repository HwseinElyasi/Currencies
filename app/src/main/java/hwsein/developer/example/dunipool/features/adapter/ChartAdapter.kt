package hwsein.developer.example.dunipool.features.adapter

import ChartData
import com.robinhood.spark.SparkAdapter

class ChartAdapter(
    private val historyCallData : List<ChartData.Data>,
    private val baseLine : String
) : SparkAdapter() {

    //تعداد داده هایی که باید روی نمودار نمایش داده بشن
    override fun getCount(): Int {
        return historyCallData.size
    }

    //موقعیت داده رو روی نمودار نشون میده
    override fun getItem(index: Int): ChartData.Data {

        return historyCallData[index]

    }

    //این متد مقدار هر داده توی نمودار y رو نمایش میده که از close که نقطه ی بسته شدن ارز هست رو بهش دادیم
    override fun getY(index: Int): Float {

       return historyCallData[index].close.toFloat()

    }

    //اینجا برای اینه ک کاربر وقتی دستشو روی نمودار تکون داد یه خط برشا به نمایش دربیاد و موقعیت براش نمایش داده بشه
    override fun hasBaseLine(): Boolean {
        return true
    }

    //اینم مقدار خط پایس
    override fun getBaseLine(): Float {

        return baseLine.toFloat() ?: super.getBaseLine()

    }


}