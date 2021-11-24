package com.employees.utils.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employees.model.enums.Errors.SOMETHING_WENT_WRONG
import com.employees.utils.UiEventsManager.showError
import com.employees.utils.UiEventsManager.showLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Runs a Job in the given [CoroutineContext].
 * @param ctx CoroutineContext to execute the work from (Default is [Dispatchers.IO]).
 * @param block Work to execute.
 */
fun ViewModel.launchUseCase(ctx: CoroutineContext = Dispatchers.IO, block: suspend () -> Unit) =
    viewModelScope.launch(ctx) {
        showLoading(true)
        try {
            block()
        } catch (t: Throwable) {
            Timber.e(t)
            showError(SOMETHING_WENT_WRONG)
        }
        showLoading(false)
    }