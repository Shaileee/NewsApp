package shaileedemo.project.data.errorhandling

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import shaileedemo.project.data.models.RetrofitResult
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }
        val upperBound = getParameterUpperBound(0, returnType)

        return if (upperBound is ParameterizedType && upperBound.rawType == RetrofitResult::class.java) {
            object : CallAdapter<Any, Call<Result<*>>> {
                override fun responseType(): Type {
                    return getParameterUpperBound(0, upperBound)}

                override fun adapt(call: Call<Any>): Call<Result<*>> {
                    return ResultCall(call) as Call<Result<*>>
                }
            }
        } else {
            null
        }
    }
}
