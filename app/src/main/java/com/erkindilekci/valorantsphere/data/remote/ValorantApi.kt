package com.erkindilekci.valorantsphere.data.remote

import com.erkindilekci.valorantsphere.data.remote.dto.agentdetails.AgentDetails
import com.erkindilekci.valorantsphere.data.remote.dto.agentlist.Agent
import com.erkindilekci.valorantsphere.data.remote.dto.gamemodes.GameMode
import com.erkindilekci.valorantsphere.data.remote.dto.maps.Map
import com.erkindilekci.valorantsphere.data.remote.dto.weapon.Weapon
import com.erkindilekci.valorantsphere.util.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ValorantApi {

    @GET("agents?isPlayableCharacter=true")
    suspend fun getAllAgents(): Response<BaseResponse<List<Agent>>>

    @GET("agents/{id}")
    suspend fun getAgentDetails(
        @Path("id") id: String
    ): Response<BaseResponse<AgentDetails>>

    @GET("weapons")
    suspend fun getAllWeapons(): Response<BaseResponse<List<Weapon>>>

    @GET("weapons/{id}")
    suspend fun getWeaponDetails(
        @Path("id") id: String
    ): Response<BaseResponse<Weapon>>

    @GET("maps")
    suspend fun getAllMaps(): Response<BaseResponse<List<Map>>>

    @GET("gamemodes")
    suspend fun getAllGameModes(): Response<BaseResponse<List<GameMode>>>
}
