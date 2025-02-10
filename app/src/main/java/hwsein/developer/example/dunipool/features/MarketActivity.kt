package hwsein.developer.example.dunipool.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hwsein.developer.example.dunipool.mvp.model.ModelMarketActivity
import hwsein.developer.example.dunipool.mvp.presenter.PresenterMarketActivity
import hwsein.developer.example.dunipool.mvp.view.ViewMarketActivity


class MarketActivity : AppCompatActivity() {
private lateinit var presenter : PresenterMarketActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewMarketActivity(this)
        setContentView(view.binding.root)
        presenter = PresenterMarketActivity(view , ModelMarketActivity())

        presenter.onCreate()

    }

    override fun onResume() {
        super.onResume()

        presenter.onResume()

    }
}

