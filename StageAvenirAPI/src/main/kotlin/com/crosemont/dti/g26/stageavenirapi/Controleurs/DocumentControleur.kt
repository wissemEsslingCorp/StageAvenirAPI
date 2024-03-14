 package com.crosemont.dti.g26.stageavenirapi.Controleurs

import com.crosemont.dti.g26.stageavenirapi.DAO.DocumentDAOImplement
import com.crosemont.dti.g26.stageavenirapi.Modèle.Document
import com.crosemont.dti.g26.stageavenirapi.Modèle.Enum.Type
import com.crosemont.dti.g26.stageavenirapi.Service.ServiceGestionUtilisateur
import com.crosemont.dti.g26.stageavenirapi.Service.ServiceOffreDeStage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal

@RestController
class DocumentControleur(var service : ServiceGestionUtilisateur) {

    @Operation(
        summary = "Obtenir tous les documents.",
        description = "Retourne tous les documents (Cv et documents supplémentaires) liés à un étudiant , une demande de stage ou une candidature.",
        operationId = "obtenirDocuments",
        responses = [
            ApiResponse(responseCode = "200", description = "Les documents ont bien été récupérés."),
            ApiResponse(responseCode = "204", description = "La requête a été acceptée mais la liste de document est vide."),
            ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
            ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
            ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires.")
        ])
    @GetMapping("/documents")
    fun obtenirTousLesDocuments(): ResponseEntity<List<Document>>? {
        var listeDocuments =  service.récupérerTousLesDocuments()
        if (listeDocuments.isNotEmpty()) {
            val uri = listeDocuments?.let {
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(it.size)
                    .toUri()
            }

            return uri?.let { ResponseEntity.ok().body(listeDocuments) }
        }
        return ResponseEntity.internalServerError().build()
    }



    @Operation(
        summary = "Obtenir les documents reliés à une candidature.",
        description = "Récupère les documents liés à une candidature",
        operationId = "obtenirDocumentsParCandidature",
        responses = [
            ApiResponse(responseCode = "200  ", description = "Les documents ont bien été récupérés."),
            ApiResponse(responseCode = "204", description = "La requête a été acceptée mais la liste de document est vide."),
            ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
            ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
            ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires.")
        ])
    @GetMapping("/employeur/offresStage/offre/candidatures/{candidature_id}/documents")
    fun obtenirLesDocumentsParCandidature(@PathVariable candidature_id:String, principal: Principal?): ResponseEntity<List<Document>>? {
        var listeDocuments = principal?.let { service.récupérerDocumentsParCandidatures(candidature_id.toInt(), it.name) }
        if (listeDocuments != null) {

            val uri = listeDocuments?.let {
                    ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{code}")
                        .buildAndExpand(it.size)
                        .toUri()
            }

                return uri?.let { ResponseEntity.ok().body(listeDocuments) }

        }
        return ResponseEntity.internalServerError().build()
    }


    @Operation(
        summary = "Obtenir les documents liés à une demande de stage.",
        description = "Récupère les documents qui sont liés à une demande de stage ",
        operationId = "obtenirDocumentsParDemande",
        responses = [
            ApiResponse(responseCode = "200", description = "Les documents ont bien été récupérés."),
            ApiResponse(responseCode = "204", description = "La requête a été acceptée mais la liste de document est vide."),
            ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
            ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
            ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires.")
        ])
    @GetMapping("/etudiant/demandesStages/{demande_id}/documents")
    fun obtenirLesDocumentsParDemandeDeStage(@PathVariable demande_id:String, principal: Principal? ): ResponseEntity<List<Document>>? {
        var listeDocuments = principal?.let { service.récupérerDocumentsParDemandeDeStage(demande_id.toInt(), it.name) }
        if (listeDocuments != null) {

            val uri = listeDocuments?.let {
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(it.size)
                    .toUri()
            }

            return uri?.let { ResponseEntity.ok().body(listeDocuments) }

        }
        return ResponseEntity.internalServerError().build()
    }

    @Operation(
        summary = "Ajoute un document de type CV",
        description = "Ajoute un document de type CV dans la base de données et le lie à l'utilisateur qui envoie la requête.",
        operationId = "ajouterCV",
        responses = [
            ApiResponse(responseCode = "201", description = "Le CV a bien été enregistré."),
            ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
            ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
            ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires.")
        ])
    @PostMapping("/etudiant/profil/cv")
    fun ajouterUnCv(principal: Principal?, @RequestBody cv:Document): ResponseEntity<Document>? {
        if (principal != null) {
            println(principal.name)
        }else {
            println("controler null")
        }
        var cv_ajouté = principal?.let { service.ajouterUnCv(cv, it.name) }
        if (cv != null) {

            val uri = cv_ajouté?.let {
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(it)
                    .toUri()
            }

            return uri?.let { ResponseEntity.created(it).body(cv_ajouté) }

        }
        return ResponseEntity.internalServerError().build()
    }

    @Operation(
        summary = "Modifier un CV.",
        description = "Prend un CV en paramètre pur le chercher et le modifier dans la base de données.",
        operationId = "modifierCV",
        responses = [
            ApiResponse(responseCode = "201", description = "Le CV a bien été modifié."),
            ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
            ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
            ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires.")
        ])
    @PutMapping("/etudiant/profil/cv")
    fun modifierUnCv(principal: Principal?, @RequestBody cv:Document): ResponseEntity<Document>? {
        var  cv_modifié = principal?.let { service.modifierUnCv(cv, it.name) }
        if (cv != null) {

            val uri = cv_modifié?.let {
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(it)
                    .toUri()
            }

            return uri?.let { ResponseEntity.created(it).body(cv_modifié) }

        }
        return ResponseEntity.internalServerError().build()
    }

    @Operation(
        summary = "Obntenir le CV d'un étudiant.",
        description = "Prend l'id de l'étudiant pour chercher le CV qui lui appartient, et le retourne ",
        operationId = "obtenirCvEtudiant",
        responses = [
            ApiResponse(responseCode = "201", description = "La candidature  a bien été envoyée."),
            ApiResponse(responseCode = "204", description = "La requête a été acceptée mais aucun cv n'a été trouvé."),
            ApiResponse(responseCode = "400", description = "La requête est mal formulée."),
            ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
            ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires.")
        ])
    @GetMapping("/etudiant/profil/cv")
    fun obtenirLeCV(principal: Principal?): ResponseEntity<Document>? {
        var  cv_modifié = principal?.let { service.obtenirCVParEtudiant(it.name) }
        if (principal != null) {

            val uri = cv_modifié?.let {
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(it)
                    .toUri()
            }

            return uri?.let { ResponseEntity.ok().body(cv_modifié) }
        }
        return ResponseEntity.internalServerError().build()
    }




}