package com.crosemont.dti.g26.stageavenirapi.Controleurs

import com.crosemont.dti.g26.stageavenirapi.Service.ServiceOffreDeStage
import com.crosemont.dti.g26.stageavenirapi.SourceDonnéesTests
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class CandidatureControleurTests {

    @MockBean
    lateinit var service: ServiceOffreDeStage

    @Autowired
    private lateinit var mockMvc: MockMvc


    /*@Test
    fun `Étant donné un utilisateur authentifié et des candidatures envoyées par ce dernier, lorsqu'il effectue un requête GET pour obtenir la liste de ces candidautures  alors il obtient la liste des candidature et un code de retour 200` (){
        Mockito.`when`(service.obtenirCandidaturesParEtudiant("auth0|658258a0c15592e55505b4e5")).thenReturn(SourceDonnéesTests.offresDeStage)

        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/candidatures"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0]['code']").value("RF125"))
    }*/
}




