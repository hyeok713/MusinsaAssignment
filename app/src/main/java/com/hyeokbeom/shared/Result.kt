package com.hyeokbeom.shared

/**
 * Result
 * 특정 이벤트 혹은 액션에 대한 결과 상태
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val data: FailureResponse) : Result<Nothing>()
    data class Error(val exception: Throwable) : Result<Nothing>()

    data class FailureResponse(
        val name: String = "",
        val message: String = ""
    )
}

/** Expected Response 인 경우 사용 **/
val Result<*>.Success
    get() = this is Result.Success && data != null

/** UnExpected Response 인 경우 사용 **/
val <T> Result<T>.Failure
    get() = (this as? Result.Failure)?.data != null

/** Exception 발생시 사용 **/
val <T> Result<T>.Error: Throwable?
    get() = (this as? Result.Error)?.exception

val <T> Result<T>.response: T?
    get() = (this as? Result.Success)?.data

val <T> Result<T>.errorResponse: Result.FailureResponse?
    get() = (this as? Result.Failure)?.data

/**
 * executeResult
 * [결과 상태에 따른 처리 블록 생성]
 * @param onSuccess
 * @param onFailure
 * @param onError Optional, 에러 발생(or etc) 처리
 * @return Result 타입 리턴
 */
fun <T> Result<T>.executeResult(
    onSuccess: (T) -> Unit,
    onFailure: (Result.FailureResponse) -> Unit,
    onError: ((Throwable?) -> Unit)? = null
): Result<T> {
    when {
        Success -> response?.let { onSuccess(it) }
        Failure -> errorResponse?.let { onFailure(it) }
        else -> onError?.let { onError(Error) }
    }

    return this
}