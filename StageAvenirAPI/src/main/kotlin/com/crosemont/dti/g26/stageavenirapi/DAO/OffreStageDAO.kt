package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.OffreStage
import org.springframework.data.annotation.Id

interface OffreStageDAO : DAO<OffreStage> {

    override fun chercherParCode(code: Int): OffreStage?

    override fun chercherTous(): List<OffreStage>

    //override fun ajouter(offre: OffreStage): OffreStage?

    fun ajouterUneOffre (codeEntreprise: Int, offre: OffreStage): OffreStage?

    override fun modifier(id:Int,offre: OffreStage): OffreStage?

    override fun effacer(code: Int)

    fun modifierVisibilité(id: Int, visibilité :Boolean, état : String): OffreStage?

    fun chercherParCodeCatégorie(code_categorie: Int): List<OffreStage>

    fun obtenirOffresEnCoursApprobation():List<OffreStage>




}