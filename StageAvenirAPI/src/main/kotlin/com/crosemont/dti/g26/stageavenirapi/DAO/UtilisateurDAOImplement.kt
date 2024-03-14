package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.Utilisateur
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UtilisateurDAOImplement (val bd : JdbcTemplate, val daoCategorie: CategorieDAOImplement, val daoRole: RoleDAOImplement): UtilisateurDAO  {
    override fun ajouter(utilisateur: Utilisateur): Utilisateur? {
        val sql = "INSERT INTO utilisateur (idutilisateur, nom, prenom, courriel, telephone, catégorie_idcategorie, role_idRole) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"

        bd.update(
            sql,
            utilisateur.idutilisateur,
            utilisateur.nom,
            utilisateur.prenom,
            utilisateur.courriel,
            utilisateur.telephone,
            utilisateur.catégorie?.idCatégorie,
            utilisateur.role?.idRole
        )

        return utilisateur
    }
    override fun chercherUserParCode(code: String): Utilisateur? {

        var utilisateur: Utilisateur? = null


        try {
            bd.query("SELECT * FROM utilisateur WHERE idutilisateur = ?", arrayOf(code)) { response, _ ->


                   utilisateur =  Utilisateur(
                        idutilisateur = response.getString("idutilisateur"),
                        nom = response.getString("nom"),
                        prenom = response.getString("prenom"),
                        courriel = response.getString("courriel"),
                        telephone = response.getString("telephone"),
                        catégorie = daoCategorie?.chercherParCode(response.getInt("categorie_idcategorie")),
                        role = daoRole?.chercherParCode(response.getInt("role_idRole")),
                    )

            }

        }catch (e: Exception){
            println("ERREUR DAO :" + e)
        }
        return utilisateur
    }

    override fun chercherParCode(code: Int): Utilisateur? {
        TODO("Not yet implemented")
    }

    /* override fun chercherCategorieParCode(code: Int): Categorie? {
        TODO("Not yet implemented")
    }*/

    override fun chercherTous(): List<Utilisateur> {
        TODO("Not yet implemented")
    }

    override fun modifier(id: Int, utilisateur: Utilisateur): Utilisateur? {
        TODO("Not yet implemented")
    }

    override fun effacer(id: Int) {
        TODO("Not yet implemented")
    }

}