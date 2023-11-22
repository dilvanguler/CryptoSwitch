import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dilvan.cryptoswitch.R
import com.dilvan.cryptoswitch.endpoint.ApiClient
import com.dilvan.cryptoswitch.endpoint.Crypto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoViewModel : ViewModel() {
    val cryptoData = MutableLiveData<List<Crypto>>()
}

