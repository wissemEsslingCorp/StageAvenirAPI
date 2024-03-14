package com.crosemont.dti.g26.stageavenirapi.Controleurs

import com.crosemont.dti.g26.stageavenirapi.Service.ServiceOffreDeStage
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.bind.annotation.PutMapping

@SpringBootTest
@AutoConfigureMockMvc
class StageControleurTest {

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var service: ServiceOffreDeStage

    @Autowired
    private lateinit var MockMvc : MockMvc

    @Test
    //@GetMapping("/offres_Stages")
    fun `Étant donnée les offres de stages lorsqu'on effectue une requête GET pour obtenir tous les offres de stages on obtient un JSON qui contient tous les offres de stages`(){
        TODO("Méthode non-implémentée")
    }

    @Test
    //@GetMapping("/offres_Stages/{code}")
    fun `Étant donnée les offres de stages lorsqu'on effectue une requête GET de recherche par code alors ont obtient un JSON qui contient une offre de stage dont le code est RF125 et un code de retour 200`(){
        TODO("Méthode non-implémentée")
    }

    @Test
    //@GetMapping("/offres_Stages/{code}")
    fun `Étant donnée l'offre de stage dont le code est HS123 et qui n'est pas inscrit su service lorsqu'on effectue une requête GET de recherche par code alors on obtient un code de retour 404 et le message d'erreur et le message d'erreur « L'offre de stage HS123 n'est pas inscrite au service »`(){
        TODO("Méthode non-implémentée")
    }

    @Test
    //@GetMapping("/candidatures")
    fun `Étant donnée les candidatures lorsqu'on effectue une requête GET pour obtenir tous les canditatures on obtient un JSON qui contient tous les canditatues`(){
        TODO("Méthode non-implémentée")
    }

    @Test
    //@PutMapping("/candidatures/{id}")
    fun `Étant donnée les candidatures lorsqu'on effectue une requête PUT d'annulation par code alors on obtient un un JSON qui contient une candidature dont le code est NV125, un code de retour 201 et l'uri de la ressource annulé`(){
        TODO("Méthode non-implémentée")
    }

    @Test
    //@PostMapping("/candidatures")
    fun `Étant donnée la candidature dont le code est NV125 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter et que le champ nom est manquant dans le JSON envoyé alors on obtient un code de retour 400`(){
        TODO("Méthode non-implémentée")
    }

    @Test
    //@PostMapping("/candidatures")
    fun `Étant donnée la candidature dont le code est RF125 qui existe déjà lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un code de retour 409 et le message d'erreur « Le candidat RF125 est déjà inscrit au service » `(){
        TODO("Méthode non-implémentée")
    }






}