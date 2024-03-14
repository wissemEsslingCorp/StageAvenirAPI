package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.Catégorie
import com.crosemont.dti.g26.stageavenirapi.Modèle.Compétence
import com.crosemont.dti.g26.stageavenirapi.Modèle.DemandeStage

interface CompétenceDAO: DAO<Compétence> {

     fun chercherTousCompétenceParDemandeStage(code: Int): List<Compétence>
}