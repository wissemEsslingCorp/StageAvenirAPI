package com.crosemont.dti.g26.stageavenirapi.Service

import com.crosemont.dti.g26.stageavenirapi.DAO.*
import com.crosemont.dti.g26.stageavenirapi.Exceptions.DroitAccèsInsuffisantException
import com.crosemont.dti.g26.stageavenirapi.Exceptions.RessourceInexistanteException
import com.crosemont.dti.g26.stageavenirapi.Modèle.Candidature
import com.crosemont.dti.g26.stageavenirapi.Modèle.OffreStage
import com.crosemont.dti.g26.stageavenirapi.Modèle.*
import com.crosemont.dti.g26.stageavenirapi.Modèle.Enum.Etat
import org.springframework.stereotype.Service

@Service
class ServiceOffreDeStage(val daoEntreprise:EntrepriseDAO, val daoUtilisateur: UtilisateurDAO, val daoOffreStage: OffreStageDAO, val daoCandidature: CandidatureDAO,val daoAccord:AccordStageDAO, val daoDocument: DocumentDAO, val serviceGestionUtilisateur: ServiceGestionUtilisateur){


    //======================================Offres de stage
    fun obtenirOffresStage(): List<OffreStage> = daoOffreStage.chercherTous()
    fun obtenirOffreParCode (code: Int): OffreStage? = daoOffreStage.chercherParCode(code)
    fun obtenirOffresParCatégorie (code_utilisateur: String): List<OffreStage>? {
        var etudiant = daoUtilisateur.chercherUserParCode(code_utilisateur)
        if (etudiant != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(etudiant , "etudiant")){
                return  etudiant.catégorie?.let { daoOffreStage.chercherParCodeCatégorie(it.idCatégorie) }

            }else throw DroitAccèsInsuffisantException("L'utilisateur ${etudiant.nom} n'est pas un etudiant")
        }
    throw RessourceInexistanteException("L'utilisateur avec le code ${code_utilisateur} n'existe pas")
    }

    fun effacer(code: Int,code_employeur: String ) {
        var employeur = daoUtilisateur.chercherUserParCode(code_employeur)
        if (employeur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(employeur , "employeur")){
               return daoOffreStage.effacer(code)
            }else throw DroitAccèsInsuffisantException("L'utilisateur ${employeur.nom} n'est pas un employeur")
        }
        throw RessourceInexistanteException("L'utilisateur avec le code ${code_employeur} n'existe pas")


    }


    fun modifier(code: Int, offre: OffreStage): OffreStage? = daoOffreStage.modifier(code, offre)

    fun ajouter (code_employeur:String, codeEntreprise: Int, offre: OffreStage): OffreStage? {

        var employeur = daoUtilisateur.chercherUserParCode(code_employeur)
        if (employeur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(employeur , "employeur")){
                return daoOffreStage.ajouterUneOffre(codeEntreprise,offre)

            }else throw DroitAccèsInsuffisantException("L'utilisateur ${employeur.nom} n'est pas un employeur")
        }
        throw RessourceInexistanteException("L'utilisateur avec le code ${code_employeur} n'existe pas")
    }


    fun obtenirStagesEnCoursDeValidationPourUnePublication(code_coordo : String ): List<OffreStage> {
        return daoOffreStage.obtenirOffresEnCoursApprobation()
    }

    fun approuverPublicationDuneOffre(code_coordo : String, idOffre : Int): OffreStage?{
        return daoOffreStage.modifierVisibilité(idOffre, true, "ACCEPTEE")
    }

    fun refuserPublicationDuneOffre(code_coordo : String, idOffre : Int): OffreStage?{
        return daoOffreStage.modifierVisibilité(idOffre, false, "REFUSEE")
    }



    //========================================Candidatures
    fun postulerPourUneOffre (codeEtudiant : String ,codeOffre:Int, candidature: Candidature):Candidature?{
        var etudiant = daoUtilisateur.chercherUserParCode(codeEtudiant)
        if (etudiant != null) {
            println("service postuler :"+ (etudiant.role?.nom ?: "Pas de role"))
        }
        if (etudiant != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(etudiant , "etudiant")){
                var nouvelleCandidature = daoCandidature.postulerPourUneOffre(candidature,codeEtudiant,codeOffre)
                if (nouvelleCandidature != null ){
                    for (document in candidature.documents!!){
                        daoDocument.ajouterDocumentACandidature(document, nouvelleCandidature.idCandidature)
                    }

                }
                return nouvelleCandidature
            }else throw DroitAccèsInsuffisantException("L'étudiant ${etudiant.nom} n'est pas un étudiant")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${codeEtudiant} n'existe pas")

    }

    fun obtenirCandidaturesParEtudiant (codeEtudiant:String):List<Candidature>{
        var etudiant = daoUtilisateur.chercherUserParCode(codeEtudiant)
        if (etudiant != null) {
            println(etudiant.catégorie)
        }
        if (etudiant != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(etudiant , "etudiant")){
                return daoCandidature.chercherParEtudiant(codeEtudiant)
            }else throw DroitAccèsInsuffisantException("L'étudiant ${etudiant.nom} n'est pas un étudiant")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${codeEtudiant} n'existe pas")
    }



    fun annulerCandidature(idCandidature: Int , codeEtudiant : String):Candidature?{
        var etudiant = daoUtilisateur.chercherUserParCode(codeEtudiant)
        if (etudiant != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(etudiant , "etudiant")){
                return  daoCandidature.annulerCandidature(idCandidature)
            }else throw DroitAccèsInsuffisantException("L'étudiant ${etudiant.nom} n'est pas un étudiant. Seul ce rôle permet d'annuler ne candidature")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${codeEtudiant} n'existe pas")

    }
    fun accepterCandidature(candidature: Candidature, code_employeur : String):Candidature?{
        var employeur = daoUtilisateur.chercherUserParCode(code_employeur)
        if (employeur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(employeur , "employeur")){
                var accordStage =  AccordStage(0, null, Etat.EN_COURS, candidature.etudiant, candidature.offre)
                daoAccord.ajouter(accordStage)
                return daoCandidature.accepterCandidature(candidature.idCandidature)
            }else throw DroitAccèsInsuffisantException("L'utilisateur ${employeur.nom} n'est pas un employeur. Seuls ce dernier peut accepter ou refuser une candidature")
        }
        throw RessourceInexistanteException("L'employeur avec le code ${code_employeur} n'existe pas")
    }


    fun refuserCandidature(idCandidature: Int, code_employeur: String):Candidature?{
        var employeur = daoUtilisateur.chercherUserParCode(code_employeur)
        if (employeur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(employeur , "employeur")){
                return  daoCandidature.refuserCandidature(idCandidature)
            }else throw DroitAccèsInsuffisantException("L'utilisateur ${employeur.nom} n'est pas un employeur. Seuls ce dernier peut accepter ou refuser une candidature")
        }
        throw RessourceInexistanteException("L'employeur avec le code ${code_employeur} n'existe pas")
    }

    fun obtenirCandidaturesParOffreStage (codeDemande:Int, code_utilisateur : String):List<Candidature>{
        var utilisateur = daoUtilisateur.chercherUserParCode(code_utilisateur)
        if (utilisateur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(utilisateur , "employeur")){
                return daoCandidature.chercherParOffreStage(codeDemande)
            }else throw DroitAccèsInsuffisantException("L'utilisateur ${utilisateur.nom} n'est pas un employeur")
        }
        throw RessourceInexistanteException("L'étudiant avec le code ${code_utilisateur} n'existe pas")
    }



    //==============================================Proposition de stage


    //==============================================Accords de stages

    fun obtenirAccords(id_coordo: String): List<AccordStage>?{
        var utilisateur = daoUtilisateur.chercherUserParCode(id_coordo)
        if (utilisateur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(utilisateur , "coordonnateur")){
                return utilisateur.catégorie?.let { daoAccord.chercherTous() }
            }else throw DroitAccèsInsuffisantException("L'Utilisateur ${utilisateur.nom} n'est pas un coordonnateur")

        }
        throw RessourceInexistanteException("L'étudiant avec le code ${id_coordo} n'existe pas")

    }

    fun obtenirAccordParCode(code_coordo:String,idAccordStage: Int): AccordStage?{
        var utilisateur = daoUtilisateur.chercherUserParCode(code_coordo)
        if (utilisateur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(utilisateur , "coordonnateur")){
                return utilisateur.catégorie?.let { daoAccord.chercherParCode(idAccordStage) }
            }else throw DroitAccèsInsuffisantException("L'étudiant ${utilisateur.nom} n'est pas un coordonnateur")
        }
        throw RessourceInexistanteException("L'Utilisateur avec le code ${code_coordo} n'existe pas")

    }


    fun approuverAccordStage(code_coordo: String, idAccordStage : Int):AccordStage?{
        var utilisateur = daoUtilisateur.chercherUserParCode(code_coordo)
        if (utilisateur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(utilisateur , "coordonnateur")){
                return  daoAccord.approuverUnAccord(idAccordStage)
            }else throw DroitAccèsInsuffisantException("L'utilisateur ${utilisateur.nom} n'est pas un coordonnateur")
        }
        throw RessourceInexistanteException("L'utilisateur avec le code ${code_coordo} n'existe pas")
    }
    fun refuserAccordStage(code_coordo: String, idAccordStage: Int):AccordStage?{

        var utilisateur = daoUtilisateur.chercherUserParCode(code_coordo)
        if (utilisateur != null) {
            if (serviceGestionUtilisateur.verifierRoleUtilisateur(utilisateur , "coordonnateur")){
                return daoAccord.désaprouverUnAccord(idAccordStage)
            }else throw DroitAccèsInsuffisantException("L'utilisateur ${utilisateur.nom} n'est pas un coordonnateur")
        }
        throw RessourceInexistanteException("L'utilisateur avec le code ${code_coordo} n'existe pas")
    }






}