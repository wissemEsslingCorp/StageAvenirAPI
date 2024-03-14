package com.crosemont.dti.g26.stageavenirapi.Modèle

data class Entreprise (
        var idEntreprise : Int,
        val nom : String,
        val adresse : String,
        val description: String,
        val secteur : String,
        val employeur : Utilisateur ?
)

