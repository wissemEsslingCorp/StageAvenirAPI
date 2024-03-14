package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.AccordStage

interface AccordStageDAO : DAO<AccordStage> {

    override fun ajouter(element: AccordStage): AccordStage?
    override fun modifier(id :Int , element: AccordStage): AccordStage?
    fun approuverUnAccord(element: Int):AccordStage?
    fun désaprouverUnAccord(element:Int):AccordStage?

    fun selectionnerAccordParCategorie(categorieId : Int):List<AccordStage>?


}