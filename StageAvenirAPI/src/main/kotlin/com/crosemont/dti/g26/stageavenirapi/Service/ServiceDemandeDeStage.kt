package com.crosemont.dti.g26.stageavenirapi.Service

import com.crosemont.dti.g26.stageavenirapi.DAO.DemandeStageDAOImplement
import com.crosemont.dti.g26.stageavenirapi.Modèle.Candidature
import com.crosemont.dti.g26.stageavenirapi.Modèle.Catégorie
import com.crosemont.dti.g26.stageavenirapi.Modèle.DemandeStage
import com.crosemont.dti.g26.stageavenirapi.Modèle.Document
import org.springframework.stereotype.Service

@Service
class ServiceDemandeDeStage(val dao : DemandeStageDAOImplement) {


    fun obtenirToutesDemandesStage(): List<DemandeStage> = dao.chercherTous()

    fun obtenirDemandeParId(code: Int): DemandeStage? = dao.chercherParCode(code)

    fun postuler(codeEtudiant: Int, candidature: Candidature, documents: List<Document>) {
        // Implémentation de la logique de postulation pour une demande de stage
        // ...
    }

    fun obtenirCandidatures(codeEtudiant: Int) {
        // Implémentation pour obtenir les candidatures d'un étudiant pour une demande de stage spécifique
        // ...
    }
    fun modifierStatus(id: Int, demande: DemandeStage): DemandeStage? = dao.modifierStatus(id, demande)

    fun ajouterDemande(demande: DemandeStage): DemandeStage? = dao.ajouter(demande)

    fun effacerDemande(id: Int) = dao.effacer(id)

    fun modifierDemande(id: Int, demande: DemandeStage): DemandeStage? = dao.modifier(id, demande)

}