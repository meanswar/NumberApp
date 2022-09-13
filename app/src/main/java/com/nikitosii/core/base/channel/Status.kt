package com.nikitosii.core.base.channel

data class Status<E>(val obj: E?, val channelState: ChannelState) {

    enum class ChannelState {
        REFRESHING,
        UP_TO_DATE,
        ONLY_LOCAL
    }

    companion object {
        fun <E> refreshing(data: E) = Status(data, ChannelState.REFRESHING)
        fun <E> upToDate(data: E) = Status(data, ChannelState.UP_TO_DATE)
        fun <E> onlyLocal(data: E) = Status(data, ChannelState.ONLY_LOCAL)
    }
}