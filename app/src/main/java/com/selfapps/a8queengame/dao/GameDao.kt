package com.selfapps.a8queengame.dao

import android.arch.persistence.room.Dao
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Query
import com.selfapps.a8queengame.dao.entity.PlayerWithResults
import com.selfapps.a8queengame.model.Player


@Dao
interface GameDao {

    @Query("SELECT * FROM gameresult WHERE id = :playerId")
    fun getPalyerWithResults(playerId: String): PlayerWithResults

    

}