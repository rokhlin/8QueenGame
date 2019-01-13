package com.selfapps.a8queengame.dao.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import com.selfapps.a8queengame.model.Difficulty


@Entity(tableName = "players")
data class Player (
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "resultId") var resultId: Long?,
    var score: Int,
    var level: Int,
    var difficulty: Difficulty

)