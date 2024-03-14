package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.DemandeStage

interface DemandeStageDAO :DAO<DemandeStage> {

    override fun ajouter(demande: DemandeStage): DemandeStage?
    override fun chercherParCode(code: Int): DemandeStage?

    //fun chercherCategorieParCode(code: Int): Categorie?
    override fun chercherTous(): List<DemandeStage>


   override fun modifier(id: Int,element: DemandeStage): DemandeStage?
   fun modifierStatus(id: Int, demande: DemandeStage): DemandeStage?
    override fun effacer (id: Int )





}