package com.hyeokbeom.domain.usecase

import com.hyeokbeom.shared.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

/**
 * UseCase Business Logic
 * UseCase 사용시
 */

/* UseCase without Parameter */
abstract class NonParamUseCaseExecutor<T>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(): Result<T> {
        return try {
            withContext(coroutineDispatcher) {
                execute().let {
                    Result.Success(it)
                }
            }
        } catch (e: HttpException) {
            /* >> Exception for an unexpected, non-2xx HTTP response << */
            val errorResponse = Json.decodeFromString<Result.FailureResponse>(
                e.response()?.errorBody()?.string()!!.toString()
            )
            takeIf { errorResponse.name.isNotEmpty() }?.let {
                Result.Failure(errorResponse)
            } ?: let {
                Result.Error(e)
            }

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): T
}

abstract class UseCaseExecutor<in P, T>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(param: P): Result<T> {
        return try {
            withContext(coroutineDispatcher) {
                execute(param).let {
                    Result.Success(it)
                }
            }
        } catch (e: HttpException) {
            /* >> Exception for an unexpected, non-2xx HTTP response << */
            val errorResponse = Json.decodeFromString<Result.FailureResponse>(
                e.response()?.errorBody()?.string()!!.toString()
            )
            takeIf { errorResponse.name.isNotEmpty() }?.let {
                Result.Failure(errorResponse)
            } ?: let {
                Result.Error(e)
            }

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): T
}