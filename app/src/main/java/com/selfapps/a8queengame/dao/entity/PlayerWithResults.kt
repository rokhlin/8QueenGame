package com.selfapps.a8queengame.dao.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class PlayerWithResults {
    @Embedded lateinit var player: Player

    @Relation(parentColumn = "id", entity = Player::class, entityColumn = "playerId")
    lateinit var results: List<GameResult>
}