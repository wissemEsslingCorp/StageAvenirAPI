package com.crosemont.dti.g26.stageavenirapi.Controleurs

import com.crosemont.dti.g26.stageavenirapi.Exceptions.RessourceInexistanteException
import com.crosemont.dti.g26.stageavenirapi.Modèle.OffreStage
import com.crosemont.dti.g26.stageavenirapi.Service.ServiceGestionUtilisateur
import com.crosemont.dti.g26.stageavenirapi.Service.ServiceOffreDeStage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal

@RestController
class OffresStageControleur(val service: ServiceOffreDeStage) {

    @Operation(
            summary = "Obtenir la liste des offres de stage",
            description = "Récupère la liste complète des offres de stage.",
            operationId = "obtenirOffresStages",
            responses = [
                ApiResponse(responseCode = "200", description = "Liste des offres de stage récupérée avec succès."),
                ApiResponse(responseCode = "403", description = "Accès refusé."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @GetMapping("/offres_Stages")
    fun obtenirOffresStages() = service.obtenirOffresStage()

    @Operation(
            summary = "Obtenir une offre de stage par code",
            description = "Récupère une offre de stage en fonction du code fourni.",
            operationId = "obtenirOffresStagesParCode",
            responses = [
                ApiResponse(responseCode = "200", description = "Offre de stage récupérée avec succès."),
                ApiResponse(responseCode = "404", description = "L'offre de stage spécifiée n'existe pas."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @GetMapping("/offres_Stages/{code}")
    fun obtenirOffresStagesParCode(@PathVariable code: Int) = service.obtenirOffreParCode(code) ?: throw RessourceInexistanteException("L'offre $code n'est pas inscrite au service.")


    @Operation(
            summary = "Obtenir les offres de stage par catégorie",
            description = "Récupère les offres de stage en fonction de la catégorie fournie.",
            operationId = "obtenirOffresStagesParCatégorie",
            responses = [
                ApiResponse(responseCode = "200", description = "Liste des offres de stage récupérée avec succès."),
                ApiResponse(responseCode = "403", description = "Accès refusé."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @GetMapping("etudiant/offres_Stages")
    fun obtenirOffresStagesParCatégorie( principal: Principal? ) : List<OffreStage>? {
        return principal?.let { service.obtenirOffresParCatégorie(it.name) }
    }


    @Operation(
            summary = "Ajouter une offre de stage",
            description = "Ajoute une nouvelle offre de stage pour une entreprise spécifiée.",
            operationId = "ajouterOffreStage",
            responses = [
                ApiResponse(responseCode = "201", description = "Offre de stage ajoutée avec succès."),
                ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
                ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas correctement authentifié."),
                ApiResponse(responseCode = "403", description = "L'utilisateur n'a pas les droits nécessaires."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @PostMapping("/employeur/entreprise/{idEntreprise}/offresStages")

    fun ajouterOffreStage(@RequestBody offre: OffreStage, @PathVariable idEntreprise : Int,principal: Principal? ): ResponseEntity<OffreStage> {

        val nouvelleOffre = principal?.let { service.ajouter(it.name,idEntreprise,offre) }

        if (nouvelleOffre != null) {
            val uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(nouvelleOffre.idOffreStage)
                    .toUri()
            return ResponseEntity.created(uri).body(nouvelleOffre)
        }
        return ResponseEntity.internalServerError().build()
    }

    @Operation(
            summary = "Supprimer une offre de stage",
            description = "Supprime une offre de stage en fonction du code fourni.",
            operationId = "supprimerOffreStage",
            responses = [
                ApiResponse(responseCode = "204", description = "Offre de stage supprimée avec succès."),
                ApiResponse(responseCode = "404", description = "L'offre de stage spécifiée n'existe pas."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @DeleteMapping("/offres_Stage/{code}")
    fun supprimerOffreStage(@PathVariable code: Int, principal: Principal?): ResponseEntity<OffreStage>{
        val offre = principal?.let { service.effacer(code, it.name) }

        return ResponseEntity.noContent().build()
    }

    @Operation(
            summary = "Modifier une offre de stage",
            description = "Modifie une offre de stage en fonction du code fourni.",
            operationId = "modifierOffreStage",
            responses = [
                ApiResponse(responseCode = "201", description = "Offre de stage modifiée avec succès."),
                ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
                ApiResponse(responseCode = "404", description = "L'offre de stage spécifiée n'existe pas."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @PutMapping("offres_Stage/{code}")
    fun modifierOffreStage(@PathVariable code: Int, @RequestBody offre: OffreStage):ResponseEntity<OffreStage>{
        val nouvelOffre = service.modifier(code, offre)

        if (nouvelOffre != null) {
            val uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(nouvelOffre.idOffreStage)
                    .toUri()

            return ResponseEntity.created(uri).body(nouvelOffre)
        }
        return ResponseEntity.ok(offre)
    }

    @Operation(
            summary = "Obtenir les demandes de publication en cours",
            description = "Récupère les offres de stage en cours de validation pour une publication.",
            operationId = "obtenirsOffresStagesEncoursDeValidation",
            responses = [
                ApiResponse(responseCode = "200", description = "Liste des offres de stage en cours de validation récupérée avec succès."),
                ApiResponse(responseCode = "403", description = "Accès refusé."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @GetMapping("coordonateur/demandesPublication")
    fun obtenirsOffresStagesEncoursDeValidation (principal: Principal?): ResponseEntity<List<OffreStage>>? {
        var listeOffres = principal?.let { service.obtenirStagesEnCoursDeValidationPourUnePublication(it.name) }
        if (principal != null) {
            val uri = listeOffres?.let {
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(it.size)
                    .toUri()
            }

            return uri?.let { ResponseEntity.created(it).body(listeOffres) }
        }
        return ResponseEntity.internalServerError().build()

    }

    @Operation(
            summary = "Accepter la publication d'une offre de stage",
            description = "Accepte la publication d'une offre de stage en fonction de l'ID fourni.",
            operationId = "accepterPublicationOffreStage",
            responses = [
                ApiResponse(responseCode = "201", description = "Publication de l'offre de stage acceptée avec succès."),
                ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
                ApiResponse(responseCode = "403", description = "Accès refusé."),
                ApiResponse(responseCode = "404", description = "L'offre de stage spécifiée n'existe pas."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @PutMapping("coordonnateur/demandesPublication/{idOffre}/accepter")
    fun accepterPublicationOffreStage(@PathVariable idOffre : String, principal: Principal?): ResponseEntity<OffreStage> {
        val offreAcceptée = principal?.let { service.approuverPublicationDuneOffre(it.name, idOffre.toInt()) }

        if (offreAcceptée != null) {
            val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(offreAcceptée.idOffreStage)
                .toUri()

            return ResponseEntity.created(uri).body(offreAcceptée)
        }
        return ResponseEntity.ok(offreAcceptée)
    }

    @Operation(
            summary = "Refuser la publication d'une offre de stage",
            description = "Refuse la publication d'une offre de stage en fonction de l'ID fourni.",
            operationId = "refuserPublicationOffreStage",
            responses = [
                ApiResponse(responseCode = "201", description = "Publication de l'offre de stage refusée avec succès."),
                ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
                ApiResponse(responseCode = "403", description = "Accès refusé."),
                ApiResponse(responseCode = "404", description = "L'offre de stage spécifiée n'existe pas."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @PutMapping("coordonnateur/demandesPublication/{idOffre}/refuser")
    fun refuserPublicationOffreStage(@PathVariable idOffre : String, principal: Principal?): ResponseEntity<OffreStage> {
        val offreRefusée = principal?.let { service.refuserPublicationDuneOffre(it.name, idOffre.toInt()) }

        if (offreRefusée != null) {
            val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(offreRefusée.idOffreStage)
                .toUri()

            return ResponseEntity.created(uri).body(offreRefusée)
        }
        return ResponseEntity.ok(offreRefusée)
    }



}