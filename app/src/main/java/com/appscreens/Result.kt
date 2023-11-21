package com.appscreens

sealed class Result<out R> {

    data class Error(val exception: Exception) : Result<Nothing>()
}
