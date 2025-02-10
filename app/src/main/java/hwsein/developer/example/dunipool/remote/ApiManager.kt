package hwsein.developer.example.dunipool.remote

import hwsein.developer.example.dunipool.remote.requirement.BASE_API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

     val apiService : ApiService

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }


}