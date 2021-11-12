package com.waslabrowser.data

interface Mapper<T,V> {
    fun map(obj:T):V
}