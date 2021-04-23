import com.caicai.jiujiu.bean.ErrorResult
import com.caicai.jiujiu.bean.ResultBean
import com.caicai.jiujiu.bean.ResultState

object ResultUtil {
    const val VERSION = "1.0"

    fun success(data: Any? = null, message: String? = null): ResultBean = ResultBean(ResultState.SUCCESS.code, message
            ?: ResultState.SUCCESS.message, VERSION, data)

    fun fail(message: String? = null, data: Any? = null): ResultBean = ResultBean(ResultState.ERROR.code, message
            ?: ResultState.ERROR.message, VERSION, data)

    fun fail(error: ErrorResult): ResultBean = ResultBean(error.code, error.message, VERSION)
}