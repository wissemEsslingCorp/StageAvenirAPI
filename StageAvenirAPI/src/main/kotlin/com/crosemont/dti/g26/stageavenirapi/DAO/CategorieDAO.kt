package com.crosemont.dti.g26.stageavenirapi.DAO


import com.crosemont.dti.g26.stageavenirapi.Modèle.Catégorie

interface CategorieDAO: DAO<Catégorie>   {
    override fun ajouter(catégorie: Catégorie): Catégorie?
    override fun chercherParCode(code: Int): Catégorie?

    override fun chercherTous(): List<Catégorie>

    override fun modifier(id: Int, catégorie: Catégorie): Catégorie?

    override fun effacer (id: Int )

}