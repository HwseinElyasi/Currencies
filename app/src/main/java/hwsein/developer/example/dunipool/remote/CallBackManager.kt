package hwsein.developer.example.dunipool.remote

interface CallBackManager<T> {

    fun onSuccess(data : T)

    fun onError(error : String)

}