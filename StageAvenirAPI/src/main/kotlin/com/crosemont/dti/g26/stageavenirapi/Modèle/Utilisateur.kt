package com.crosemont.dti.g26.stageavenirapi.Modèle





class Utilisateur (
    val idutilisateur: String?,
    val nom: String?,
    val prenom: String?,
    val courriel: String?,
    val telephone: String?,
    val catégorie: Catégorie?,
    val role: Role?

){
    constructor(idUtilisateur: String) : this(
        idutilisateur = idUtilisateur,
        nom = "",
        prenom = "",
        courriel = "",
        telephone = "",
        catégorie = null,
        role = null
    )
}

