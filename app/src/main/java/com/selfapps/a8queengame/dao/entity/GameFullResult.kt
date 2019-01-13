package com.selfapps.a8queengame.dao.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class GameFullResult {
    @Embedded lateinit var gameResult: GameResult

    @Relation(parentColumn = "id",entity = Q8_WinCode::class, entityColumn = "gameResult")
    lateinit var winCodes: List<Q8_WinCode>
}