package com.crosemont.dti.g26.stageavenirapi.Modèle

import com.crosemont.dti.g26.stageavenirapi.Modèle.Enum.Type

data class Document(
    val idDocument: Int,
    val nom: String,
    val type: Type,
    val contenu: ByteArray,
    val etudiant: Utilisateur?,
    val demande:DemandeStage?,
    val candidature: Candidature?

)
