package escom.tt.ceres.ceresmobile.single

import android.content.Context
import com.android.volley.*
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.POST
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AUTH_ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR_CONEXION
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.PARSE_ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SERVER_ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.UNSUCCESSFUL
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

/**
 * Created by hali on 21/03/18.
 */
class CeresRequestQueue constructor(context: Context) {
  companion object {
    @Volatile
    private var INSTANCE: CeresRequestQueue? = null

    fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
      INSTANCE ?: CeresRequestQueue(context)
    }
  }

  private val requestQueue: RequestQueue by lazy {
    // applicationContext is key, it keeps you from leaking the
    // Activity or BroadcastReceiver if someone passes one in.
    Volley.newRequestQueue(context.applicationContext)
  }

  fun <T> addToRequestQueue(req: Request<T>) {
    requestQueue.add(req)
  }

  fun apiObjectRequest(method: Int, url: String, send: JSONObject?) = async(CommonPool) {
    when (method) {
      GET -> {
        getObject(requestQueue, url)
      }
      Request.Method.POST -> {
        postObject(requestQueue, url, send)
      }
    /*
    method == PUT -> {

    }
    method == DELETE -> {

    }
    */
      else -> {
        val response = HashMap<String, String>()
        response["Error"] = "Select a method"
        JSONObject(response)
      }
    }
  }

  private suspend fun getObject(queue: RequestQueue, url: String):
      JSONObject = suspendCancellableCoroutine { continuation ->
    val request = JsonObjectRequest(Request.Method.GET, url, null,
        Response.Listener {
          continuation.resume(it)
        },
        Response.ErrorListener {
          continuation.resume(it.readableVolleyErrorObject())
        })

    queue.add(request)
    continuation.invokeOnCompletion {
      request.cancel()
    }
  }

  private suspend fun postObject(queue: RequestQueue, url: String, send: JSONObject?):
      JSONObject = suspendCancellableCoroutine { continuation ->
    val request = JsonObjectRequest(POST, url, send,
        Response.Listener {
          continuation.resume(it)
        },
        Response.ErrorListener {
          continuation.resume(it.readableVolleyErrorObject())
        })

    queue.add(request)
    continuation.invokeOnCompletion {
      request.cancel()
    }
  }

  fun apiArrayRequest(method: Int, url: String, send: JSONArray?) = async(CommonPool) {
    when (method) {
      GET -> {
        getArray(requestQueue, url)
      }
      Request.Method.POST -> {
        postArray(requestQueue, url, send)
      }
    /*
    method == PUT -> {

    }
    method == DELETE -> {

    }
    */
      else -> {
        val response = HashMap<String, String>()
        response["Error"] = "Select a method"
        JSONArray(response)
      }
    }
  }

  private suspend fun getArray(queue: RequestQueue, url: String):
      JSONArray = suspendCancellableCoroutine { continuation ->
    val request = JsonArrayRequest(GET, url, null,
        Response.Listener {
          continuation.resume(it)
        },
        Response.ErrorListener {
          continuation.resume(it.readableVolleyErrorArray())
        })

    queue.add(request)
    continuation.invokeOnCompletion {
      request.cancel()
    }
  }

  private suspend fun postArray(queue: RequestQueue, url: String, send: JSONArray?):
      JSONArray = suspendCancellableCoroutine { continuation ->
    val request = JsonArrayRequest(POST, url, send,
        Response.Listener {
          continuation.resume(it)
        },
        Response.ErrorListener {
          continuation.resume(it.readableVolleyErrorArray())
        })

    queue.add(request)
    continuation.invokeOnCompletion {
      request.cancel()
    }
  }

  private fun VolleyError.readableVolleyErrorObject(): JSONObject {
    return this.readableVolleyError(false) as JSONObject
  }

  private fun VolleyError.readableVolleyErrorArray(): JSONArray {
    return this.readableVolleyError(true) as JSONArray
  }

  private fun VolleyError.readableVolleyError(forceArray: Boolean): Any {
    val response = JSONObject()
    response.put(UNSUCCESSFUL, TRUE)
    if (this is TimeoutError || this is NoConnectionError) {
      response.put(ERROR, ERROR_CONEXION)
    } else if (this is AuthFailureError) {
      response.put(ERROR, AUTH_ERROR)
    } else if (this is ServerError) {
      response.put(ERROR, SERVER_ERROR)
    } else if (this is NetworkError) {
      response.put(ERROR, ERROR_CONEXION)
    } else if (this is ParseError) {
      response.put(ERROR, PARSE_ERROR)
    }

    if (forceArray)
      return JSONArray().put(response)

    return response
  }
}