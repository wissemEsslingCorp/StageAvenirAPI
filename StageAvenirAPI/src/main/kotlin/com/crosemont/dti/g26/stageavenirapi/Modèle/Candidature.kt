package com.crosemont.dti.g26.stageavenirapi.Modèle

import com.crosemont.dti.g26.stageavenirapi.Modèle.Enum.Etat

data class Candidature(
    var idCandidature: Int,
    val etat: Etat?,
    val commentaire: String?,
    val offre: OffreStage?,
    val etudiant: Utilisateur?,
    val documents: List<Document>? = mutableListOf()
){

}
