package escom.tt.ceres.ceresmobile.singleton

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Created by hali on 21/03/18.
 */
class CeresRequestQueue constructor(context: Context) {
  companion object {
    @Volatile
    private var INSTANCE: CeresRequestQueue? = null

    fun getInstance(context: Context) =
        INSTANCE ?: synchronized(this) {
          INSTANCE ?: CeresRequestQueue(context)
        }
  }

  val requestQueue: RequestQueue by lazy {
    // applicationContext is key, it keeps you from leaking the
    // Activity or BroadcastReceiver if someone passes one in.
    Volley.newRequestQueue(context.applicationContext)
  }

  fun <T> addToRequestQueue(req: Request<T>) {
    requestQueue.add(req)
  }
}