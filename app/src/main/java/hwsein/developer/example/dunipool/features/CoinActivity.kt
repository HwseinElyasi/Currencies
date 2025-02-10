package hwsein.developer.example.dunipool.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hwsein.developer.example.dunipool.local.CoinAboutItem
import hwsein.developer.example.dunipool.mvp.model.ModelCoinActivity
import hwsein.developer.example.dunipool.mvp.presenter.PresenterCoinActivity
import hwsein.developer.example.dunipool.mvp.view.ViewCoinActivity
import hwsein.developer.example.dunipool.remote.model.CoinData

class CoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewCoinActivity(this)
        setContentView(view.binding.root)

        val presenter = PresenterCoinActivity(view, ModelCoinActivity())
        presenter.onCreate()

        val coin = intent.getBundleExtra("coin")!!
        val statistics = coin.getParcelable<CoinData.Data>("bundle1")!!
        val about = coin.getParcelable<CoinAboutItem>("bundle2") ?: CoinAboutItem()


        presenter.setDataFromActivityAndChartDataFromServer(statistics)
        view.setAboutData(about)


    }

}
