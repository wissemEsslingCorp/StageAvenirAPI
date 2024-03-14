package com.crosemont.dti.g26.stageavenirapi.Service

import com.crosemont.dti.g26.stageavenirapi.DAO.CandidatureDAO
import com.crosemont.dti.g26.stageavenirapi.DAO.DocumentDAO
import com.crosemont.dti.g26.stageavenirapi.DAO.EntrepriseDAO
import com.crosemont.dti.g26.stageavenirapi.DAO.UtilisateurDAO
import com.crosemont.dti.g26.stageavenirapi.Exceptions.DroitAccèsInsuffisantException
import com.crosemont.dti.g26.stageavenirapi.Exceptions.RessourceInexistanteException
import com.crosemont.dti.g26.stageavenirapi.Modèle.*
import org.springframework.stereotype.Service

@Service
class ServiceGestionUtilisateur (var daoDocument : DocumentDAO, var daoCandidature: CandidatureDAO, var daoUtilisateur : UtilisateurDAO, var daoEntreprise : EntrepriseDAO) {
    //Gestion Utilisateur

    fun verifierRoleUtilisateur(utilisateur: Utilisateur,role :String ):Boolean{
        return utilisateur.role?.nom ?: "null"  == role
    }



    //Gestion des documents
    fun ajouterUnDocumentAuneDemandeDeStage(document: Document, idDemande :Int):Document?{
        return daoDocument.ajouterDocumentADemandeStage(document,idDemande)
    }

    fun ajouterUnDocumentAuneCandidature(document: Document,idCandidature: Int):Document?{
        return daoDocument.ajouterDocumentACandidature(document,idCandidature)
    }
    fun ajouterUnCv(cv :Document, code_etudiant : String ):Document?{
        var etudiant = daoUtilisateur.chercherUserParCode(code_etudiant)
        if (etudiant != null) {
            if (verifierRoleUtilisateur(etudiant , "etudiant")){
                return  daoDocument.ajouterCv(cv, code_etudiant)
            }else throw DroitAccèsInsuffisantException("L'étudiant ${etudiant.nom} n'est pas un étudiant. Seul ce rôle permet d'annuler ne candidature")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${code_etudiant} n'existe pas")
    }
    fun modifierUnCv(cv : Document, code_etudiant: String):Document?{
        var etudiant = daoUtilisateur.chercherUserParCode(code_etudiant)
        if (etudiant != null) {
            if (verifierRoleUtilisateur(etudiant , "etudiant")){
                return   daoDocument.modifierCv(cv)
            }else throw DroitAccèsInsuffisantException("L'étudiant ${etudiant.nom} n'est pas un étudiant. Seul ce rôle permet d'annuler ne candidature")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${code_etudiant} n'existe pas")
    }
    fun obtenirCVParEtudiant (code_etudiant: String):Document?{
        var etudiant = daoUtilisateur.chercherUserParCode(code_etudiant)
        if (etudiant != null) {
            if (verifierRoleUtilisateur(etudiant , "etudiant")){
                return   daoDocument.obtenirCvParEtudiant(code_etudiant)
            }else throw DroitAccèsInsuffisantException("L'étudiant ${etudiant.nom} n'est pas un étudiant. Seul ce rôle permet d'annuler ne candidature")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${code_etudiant} n'existe pas")

    }
    fun récupérerDocumentsParCandidatures(idCandidature: Int, code_employeur : String):List<Document>?{
        var employeur = daoUtilisateur.chercherUserParCode(code_employeur)
        if (employeur != null) {
            if (verifierRoleUtilisateur(employeur , "employeur")){
                return   daoDocument.chercherParCandidature(idCandidature)
            }else throw DroitAccèsInsuffisantException("L'utilisateur ${employeur.nom} n'est pas un employeur. Seuls ce dernier peut accepter ou refuser une candidature")
        }
        throw RessourceInexistanteException("L'employeur avec le code ${code_employeur} n'existe pas")
    }

    fun récupérerDocumentsParDemandeDeStage(idDemandeStage: Int, code_etudiant:String): List<Document>{
        var etudiant = daoUtilisateur.chercherUserParCode(code_etudiant)
        if (etudiant != null) {
            if (verifierRoleUtilisateur(etudiant , "etudiant")){
                return   daoDocument.chercherParDemandeStage(idDemandeStage)
            }else throw DroitAccèsInsuffisantException("L'étudiant ${etudiant.nom} n'est pas un étudiant. Seul ce rôle permet d'annuler ne candidature")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${code_etudiant} n'existe pas")
    }

    fun récupérerTousLesDocuments():List<Document>{
        return daoDocument.chercherTous()
    }



    //Gestion des entreprises

    fun ajouterEntrpriseParEmployeur(code_employeur: String, entreprise: Entreprise):Entreprise?{
        var employeur = daoUtilisateur.chercherUserParCode(code_employeur)
        if (employeur != null) {
            if (verifierRoleUtilisateur(employeur , "employeur")){
                return   daoEntreprise.ajouterEntreprisePourEmployeur(entreprise,code_employeur)
            }else throw DroitAccèsInsuffisantException("L'utilisateur ${employeur.nom} n'est pas un employeur.Seul un employeur peut créer une entreprise")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${code_employeur} n'existe pas")
    }

    fun obtenirToutesLesEntreprisesPourUnEmployeur(code_employeur: String):List<Entreprise>?{
        var employeur = daoUtilisateur.chercherUserParCode(code_employeur)
        if (employeur != null) {
            if (verifierRoleUtilisateur(employeur , "employeur")){
                return   daoEntreprise.chercherTous()
            }else throw DroitAccèsInsuffisantException("L'employeur ${employeur.nom} n'est pas un employeur.")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${code_employeur} n'existe pas")

    }


    fun obtenirEntrepriseParCode( code_entreprise : Int):Entreprise?{
        if (code_entreprise != null) {
                return   daoEntreprise.chercherParCode(code_entreprise)
        }
        throw RessourceInexistanteException("L'employeur avec le code ${code_entreprise} n'existe pas")
    }


}