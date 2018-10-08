package com.kdk96.glide

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Scheduler

class GlideCacheCleaner(
        private val context: Context,
        private val ioScheduler: Scheduler,
        private val mainThreadScheduler: Scheduler
) {
    fun clearCache() = Completable.fromAction { GlideApp.get(context).clearDiskCache() }
            .subscribeOn(ioScheduler)
            .observeOn(mainThreadScheduler)
            .andThen(Completable.fromAction { GlideApp.get(context).clearMemory() })
}