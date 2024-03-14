package com.crosemont.dti.g26.stageavenirapi.controlleurs

import com.crosemont.dti.g26.stageavenirapi.Modèle.Entreprise
import com.crosemont.dti.g26.stageavenirapi.Service.ServiceGestionUtilisateur
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal

@RestController
class EntrepriseControleur(val service : ServiceGestionUtilisateur)  {
    @Operation(
            summary = "Ajouter une entreprise ",
            description = "Ajoute une entreprise et la lie avec l'employeur qui envoie la requête.",
            operationId = "ajouterEntreprise",
            responses = [
                ApiResponse(responseCode = "200", description = "Entrprise ajoutée avec succès."),
                ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
                ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @PostMapping("/employeur/entreprises")
    fun ajouterEntreprise( principal: Principal? , @RequestBody entreprise: Entreprise) : ResponseEntity<Entreprise>?{
        if (principal != null) {
            println(principal.name)
        }
        var entrprise = principal?.let { service.ajouterEntrpriseParEmployeur(it.name, entreprise) }
        if (entreprise != null) {
            val uri = entrprise?.let {
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{code}")
                        .buildAndExpand(it)
                        .toUri()
            }

            return uri?.let { ResponseEntity.created(it).body(entrprise) }
        }

        return ResponseEntity.internalServerError().build()
    }

    @Operation(
            summary = "Obtenir les entreprises d'un employeur ",
            description = "Permet de récupérer la liste des entreprises d'un employeur ",
            operationId = "obtenirEntreprises",
            responses = [
                ApiResponse(responseCode = "200", description = "Liste des entreprises récupérée avec succès."),
                ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
                ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @GetMapping("/employeur/entreprises")
    fun obtenirEntreprises(principal: Principal?): ResponseEntity<List<Entreprise>>?{
        var listeEntreprise = principal?.let { service.obtenirToutesLesEntreprisesPourUnEmployeur(it.name) }
        if (principal != null) {
            val uri = listeEntreprise?.let {
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{code}")
                        .buildAndExpand(it.size)
                        .toUri()
            }

            return uri?.let { ResponseEntity.created(it).body(listeEntreprise) }
        }

        return ResponseEntity.internalServerError().build()

    }
    @Operation(
            summary = "Obtenir une entreprise d'un employeur ",
            description = "Permet de récupérer une entreprises sécifique d'un employeur ",
            operationId = "obtenirEntreprise",
            responses = [
                ApiResponse(responseCode = "200", description = "Entreprise récupérée avec succès."),
                ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
                ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @GetMapping("/employeur/entreprises/{code_entreprise}")
    fun obtenirEntrepriseParCode(principal: Principal?, @PathVariable code_entreprise : String ): ResponseEntity<Entreprise>?{

        var entrprise =  service.obtenirEntrepriseParCode(code_entreprise.toInt())
        if (code_entreprise != null) {
            val uri = entrprise?.let {
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{code}")
                        .buildAndExpand(it)
                        .toUri()
            }

            return uri?.let { ResponseEntity.created(it).body(entrprise) }
        }

        return ResponseEntity.internalServerError().build()
    }



}