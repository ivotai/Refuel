package com.unicorn.refuel.data.model

data class RecognizeResult(
    val direction: Int,
    val log_id: Long,
    val words_result: List<WordsResult>,
    val words_result_num: Int
)