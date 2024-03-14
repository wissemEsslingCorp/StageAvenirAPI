package com.crosemont.dti.g26.stageavenirapi.Controleurs

import com.crosemont.dti.g26.stageavenirapi.Modèle.AccordStage
import com.crosemont.dti.g26.stageavenirapi.Modèle.Candidature
import com.crosemont.dti.g26.stageavenirapi.Service.ServiceOffreDeStage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal

@RestController
class AccordStageControleur(val service :ServiceOffreDeStage)  {
    @Operation(
            summary = "Obtenir la liste des accords de stages ",
            description = "Récupère la liste complète des accords de stage qui on été créés lors de l'acceptation d'une candidature par un employeur.",
            operationId = "obtenirAccords",
            responses = [
                ApiResponse(responseCode = "200", description = "Liste des accords de stages récupérée avec succès."),
                ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
                ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @GetMapping("coordonnateur/accordStages")
    fun obtenirAccordsDeStage(principal: Principal?): ResponseEntity<List<AccordStage>>?{
        var listeAccord = principal?.name?.let { service.obtenirAccords(it) }
        if (principal != null) {
            val uri = listeAccord?.let {
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{code}")
                        .buildAndExpand(it.size)
                        .toUri()
            }
            return uri?.let { ResponseEntity.ok().body(listeAccord) }
        }

        return ResponseEntity.internalServerError().build()
    }

    @Operation(
            summary = "Obtenir la liste des accords de stages par l'id ",
            description = "Récupère  un accord de stage par l'id lorsque l'utilisateur sélectionne un accord.",
            operationId = "obtenirAccordId",
            responses = [
                ApiResponse(responseCode = "200", description = "accord de stages récupéré avec succès."),
                ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
                ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @GetMapping("coordonnateur/accordStages/{idAccord}")
    fun obtenirAccordsDeStageParId(principal: Principal?, @PathVariable idAccord: String): ResponseEntity<AccordStage>?{
        var accord = principal?.name?.let { service.obtenirAccordParCode(principal.name, idAccord.toInt()) }
        if (principal != null) {
            val uri = accord?.let {
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{code}")
                        .buildAndExpand(it)
                        .toUri()
            }

            return uri?.let { ResponseEntity.ok().body(accord) }
        }

        return ResponseEntity.internalServerError().build()
    }
    @Operation(
            summary = "Approuver un accord de stage ",
            description = "Récupère l'id de l'accord de stage à modifier pour changer son état de EN_COURS  à ACCEPTÉE.",
            operationId = "approuverAccord",
            responses = [
                ApiResponse(responseCode = "200", description = "Accord acceptée avec succès.."),
                ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
                ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @PutMapping("coordonnateur/accordStages/{idAccordStage}/approuver")
    fun approuverAccordStage(@PathVariable idAccordStage:String, principal: Principal? ): ResponseEntity<AccordStage>? {
        var resultat = principal?.let { service.approuverAccordStage(it.name,idAccordStage.toInt()) }
        if (idAccordStage != null) {
            val uri = resultat?.let {
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(it.idAccord)
                    .toUri()
            }

            return uri?.let { ResponseEntity.created(it).body(resultat) }
        }

        return ResponseEntity.internalServerError().build()

    }
    @Operation(
            summary = "Désapprouver un accord de stage ",
            description = "Récupère l'id de l'accord de stage à modifier pour changer son état de EN_COURS  à REFUSÉE.",
            operationId = "désapprouverAccord",
            responses = [
                ApiResponse(responseCode = "200", description = "Accord refusée avec succès."),
                ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
                ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires."),
                ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
            ]
    )
    @PutMapping("coordonnateur/accordStages/{idAccordStage}/desaprouver")
    fun désapprouverAccordStage(@PathVariable idAccordStage:String, principal: Principal?): ResponseEntity<AccordStage>? {

        var resultat = principal?.let { service.refuserAccordStage(it.name,idAccordStage.toInt()) }
        if (idAccordStage != null) {
            val uri = resultat?.let {
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(it.idAccord)
                    .toUri()
            }

            return uri?.let { ResponseEntity.created(it).body(resultat) }
        }

        return ResponseEntity.internalServerError().build()
    }

}