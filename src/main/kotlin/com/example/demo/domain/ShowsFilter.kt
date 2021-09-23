package com.example.demo.domain

sealed class ShowsFilter {
    data class FilterByTitle(val title: String) : ShowsFilter()
    object NoFilter : ShowsFilter()

    companion object {
        fun fromFilterString(filter: String?): ShowsFilter =
            if (filter == null) {
                NoFilter
            } else {
                FilterByTitle(filter)
            }
    }
}
