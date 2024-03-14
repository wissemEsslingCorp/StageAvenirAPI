package com.crosemont.dti.g26.stageavenirapi.Modèle

import com.crosemont.dti.g26.stageavenirapi.Modèle.Enum.Etat

class AccordStage(
    var idAccord: Int,
    var commentaire: String?,
    var etat: Etat?,
    var etudiant: Utilisateur?,
    var offre: OffreStage?
)
{
}