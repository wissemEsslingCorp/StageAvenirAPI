package com.crosemont.dti.g26.stageavenirapi.Controleurs

import com.crosemont.dti.g26.stageavenirapi.Exceptions.RessourceInexistanteException
import com.crosemont.dti.g26.stageavenirapi.Modèle.DemandeStage
import com.crosemont.dti.g26.stageavenirapi.Service.ServiceDemandeDeStage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DemandesStagesControleur(val service: ServiceDemandeDeStage) {

    @Operation(
        summary = "Obtenir toutes les demandes de stage",
        description = "Récupère la liste complète des demandes de stage.",
        responses = [
            ApiResponse(responseCode = "200", description = "Liste des demandes de stage récupérée avec succès."),
            ApiResponse(responseCode = "403", description = "Accès refusé."),
            ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
        ]
    )
    @GetMapping("/demandeStages")
    fun obtenirToutesDemandesStage() = service.obtenirToutesDemandesStage()


    @Operation(
        summary = "Obtenir une demande de stage par code",
        description = "Récupère une demande de stage en fonction du code fourni.",
        responses = [
            ApiResponse(responseCode = "200", description = "Demande de stage récupérée avec succès."),
            ApiResponse(responseCode = "404", description = "La demande de stage spécifiée n'existe pas."),
            ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
        ]
    )
    @GetMapping("/demandeStage/{code}")
    fun obtenirDemandeStageParCode(@PathVariable code: Int) =
        service.obtenirDemandeParId(code) ?: throw RessourceInexistanteException("La demande de stage $code n'est pas inscrite au service.")

    @Operation(
        summary = "Ajouter une demande de stage",
        description = "Ajoute une nouvelle demande de stage.",
        responses = [
            ApiResponse(responseCode = "201", description = "Demande de stage ajoutée avec succès."),
            ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
            ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
        ]
    )
    @PostMapping("/employeur/{employeur_id}/demande_Stage")
    fun ajouterDemandeStage(@RequestBody demande: DemandeStage, @PathVariable employeur_id: String): ResponseEntity<DemandeStage> {
        val nouvelleDemande = service.ajouterDemande(demande)

        if (nouvelleDemande != null) {
            val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(nouvelleDemande.idDemandeStage)
                .toUri()

            return ResponseEntity.created(uri).body(nouvelleDemande)
        }
        return ResponseEntity.internalServerError().build()
    }
    @Operation(
        summary = "Supprimer une demande de stage",
        description = "Supprime une demande de stage en fonction du code fourni.",
        responses = [
            ApiResponse(responseCode = "204", description = "Demande de stage supprimée avec succès."),
            ApiResponse(responseCode = "404", description = "La demande de stage spécifiée n'existe pas."),
            ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
        ]
    )

    @DeleteMapping("/demandeStage/{code}")
    fun supprimerDemandeStage(@PathVariable code: Int): ResponseEntity<Void> {
        service.effacerDemande(code)
        return ResponseEntity.noContent().build()
    }
    @Operation(
        summary = "Modifier une demande de stage",
        description = "Modifie une demande de stage en fonction du code fourni.",
        responses = [
            ApiResponse(responseCode = "201", description = "Demande de stage modifiée avec succès."),
            ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
            ApiResponse(responseCode = "404", description = "La demande de stage spécifiée n'existe pas."),
            ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
        ]
    )

    @PutMapping("/demandeStage/{code}")
    fun modifierDemandeStage(@PathVariable code: Int, @RequestBody demande: DemandeStage): ResponseEntity<DemandeStage> {
        val nouvelleDemande = service.modifierDemande(code, demande)

        return nouvelleDemande?.let {
            val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(it.idDemandeStage)
                .toUri()

            ResponseEntity.created(uri).body(it)
        } ?: ResponseEntity.ok(demande)

    }
    @PutMapping("/demandeStage/status/{code}")
    fun modifierStatus(@PathVariable code: Int,@RequestBody demande: DemandeStage): ResponseEntity<DemandeStage>{
        val nouvelleDemande = service.modifierStatus(code, demande)
        return nouvelleDemande?.let {
            val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(it.idDemandeStage)
                .toUri()

            ResponseEntity.created(uri).body(it)
        } ?: ResponseEntity.ok(demande)

    }
}