package com.app.greenveg

import com.app.greenveg.utils.ApiException
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

abstract class SafeApiRequest {
suspend fun <T:Any> apiRequest(call:suspend() ->Response<T>):T{
val response= call.invoke()
    if(response.isSuccessful){
        return response.body()!!
    }else{
val error=response.errorBody()?.string()
val errormsg=StringBuilder()
    error.let {
try {
errormsg.append(JSONObject(it!!).getString("message"))
}catch (e:Exception)
{
e.printStackTrace()
}
        throw ApiException(errormsg.toString())
    }
    }
}
}
