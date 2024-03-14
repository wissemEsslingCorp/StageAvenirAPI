package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.Candidature

interface CandidatureDAO :DAO<Candidature> {

    override fun ajouter(element: Candidature): Candidature?
    override fun chercherParCode(code: Int): Candidature?
    override fun chercherTous(): List<Candidature>
    override fun modifier(id: Int ,element: Candidature): Candidature?
    override fun effacer(elementId: Int)
    fun chercherParEtudiant(code_etudiant : String):List<Candidature>
    fun chercherParOffreStage(code_offre : Int):List<Candidature>
    fun postulerPourUneOffre(candidature: Candidature, code_etudiant: String,idOffre:Int):Candidature?
    fun annulerCandidature(candidatureId: Int):Candidature?
    fun accepterCandidature(candidatureId: Int):Candidature?
    fun refuserCandidature(candidatureId: Int):Candidature?


}