package com.erkindilekci.valorantsphere.data.repository

import com.erkindilekci.valorantsphere.data.remote.ValorantApi
import com.erkindilekci.valorantsphere.data.remote.dto.agentdetails.AgentDetails
import com.erkindilekci.valorantsphere.data.remote.dto.agentlist.Agent
import com.erkindilekci.valorantsphere.data.remote.dto.gamemodes.GameMode
import com.erkindilekci.valorantsphere.data.remote.dto.maps.Map
import com.erkindilekci.valorantsphere.data.remote.dto.weapon.Weapon
import com.erkindilekci.valorantsphere.util.BaseResponse
import com.erkindilekci.valorantsphere.util.Resource
import com.erkindilekci.valorantsphere.util.wrapWithFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValorantRepository @Inject constructor(
    private val valorantApi: ValorantApi
) {
    fun getAllAgents(): Flow<Resource<BaseResponse<List<Agent>>>> {
        return wrapWithFlow(valorantApi::getAllAgents)
    }

    fun getAgentDetails(id: String): Flow<Resource<BaseResponse<AgentDetails>>> {
        return wrapWithFlow {
            valorantApi.getAgentDetails(id)
        }
    }

    fun getAllWeapons(): Flow<Resource<BaseResponse<List<Weapon>>>> {
        return wrapWithFlow(valorantApi::getAllWeapons)
    }

    fun getWeaponDetails(id: String): Flow<Resource<BaseResponse<Weapon>>> {
        return wrapWithFlow {
            valorantApi.getWeaponDetails(id)
        }
    }

    fun getAllMaps(): Flow<Resource<BaseResponse<List<Map>>>> {
        return wrapWithFlow(valorantApi::getAllMaps)
    }

    fun getAllGameModes(): Flow<Resource<BaseResponse<List<GameMode>>>> {
        return wrapWithFlow(valorantApi::getAllGameModes)
    }
}
