package com.selfapps.a8queengame.dao.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.time.chrono.ChronoLocalDate

@Entity(foreignKeys = [ForeignKey(entity = GameResult::class ,
        parentColumns = ["id"],
        childColumns = ["playerId"],
        onDelete = ForeignKey.CASCADE)])
data class GameResult(
       @PrimaryKey(autoGenerate = true) var id: Long?,
       var date: ChronoLocalDate,
       var playerId:Long,
       var gameType: GameType
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GameResult) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}