package com.selfapps.a8queengame.dao.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.time.chrono.ChronoLocalDate

@Entity(foreignKeys = [ForeignKey(entity = GameResult::class,
        parentColumns = ["id"],
        childColumns = ["gameResult"]
        )])
data class Q8_WinCode (
        @PrimaryKey(autoGenerate = true) var id:Long?,
        var code: String,
        var date: ChronoLocalDate,
        var gameResult: Long
)