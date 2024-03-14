package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.Utilisateur



interface UtilisateurDAO : DAO<Utilisateur>    {
    override fun ajouter(utilisateur: Utilisateur): Utilisateur?
    fun chercherUserParCode(code: String): Utilisateur?

   // fun chercherCategorieParCode(code: Int): Categorie?
    override fun chercherTous(): List<Utilisateur>

    override fun modifier(id: Int,utilisateur: Utilisateur): Utilisateur?

    override fun effacer (id: Int )


}