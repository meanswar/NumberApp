package com.nikitosii.util.ext

import com.nikitosii.core.base.channel.Status
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.SendChannel

fun <T> T.asRefreshing() = Status.refreshing(this)
fun <T> T.asUpToDate() = Status.upToDate(this)
fun <T> T.asOnlyLocal() = Status.upToDate(this)

suspend fun <T> T.sendToChannel(channel: SendChannel<T>) = channel.send(this)

suspend fun <T> T.offerToChannel(channel: SendChannel<T>) = channel.trySend(this).isSuccess

suspend fun <T> ConflatedBroadcastChannel<Status<T>>.setChannelState(state: Status.ChannelState) =
    this.valueOrNull?.copy(channelState = state)?.sendToChannel(this)

suspend fun <T> ConflatedBroadcastChannel<Status<T>>.setAsRefreshing() = setChannelState(Status.ChannelState.REFRESHING)
suspend fun <T> ConflatedBroadcastChannel<Status<T>>.setAsUpToDate() = setChannelState(Status.ChannelState.UP_TO_DATE)
suspend fun <T> ConflatedBroadcastChannel<Status<T>>.setAsOnlyLocal() = setChannelState(Status.ChannelState.ONLY_LOCAL)