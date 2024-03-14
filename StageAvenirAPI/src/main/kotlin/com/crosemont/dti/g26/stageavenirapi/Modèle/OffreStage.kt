package com.crosemont.dti.g26.stageavenirapi.Modèle


import com.crosemont.dti.g26.stageavenirapi.Modèle.Enum.Etat
import java.time.LocalDate

data class OffreStage(


        val idOffreStage: Int,
        val titreOffre: String,
        val posteOffert: String,
        val description: String,
        val estRémunéré: Boolean,
        val datePost: LocalDate,
        val estVisible: Boolean,
        var etat: Etat,
        val entreprise: Entreprise?,
        val catégorie: Catégorie?

)
