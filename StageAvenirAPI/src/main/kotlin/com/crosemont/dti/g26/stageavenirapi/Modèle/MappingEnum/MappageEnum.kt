package com.crosemont.dti.g26.stageavenirapi.Modèle.MappingEnum

import com.crosemont.dti.g26.stageavenirapi.Modèle.Enum.*

class MappageEnum {


    fun mapToEtat(etatString: String?): Etat {

        return when (etatString) {
            "ACCEPTEE" -> Etat.ACCEPTEE
            "EN_COURS" -> Etat.EN_COURS
            "REFUSEE" -> Etat.REFUSEE
            "ANNULEE" -> Etat.ANNULEE
            else -> throw Exception("Une erreur est survenue au mappage de l'état")
        }

    }
    fun mapToNom(roleString: String?): Nom_role {

        return when (roleString) {
            "etudiant" -> Nom_role.etudiant
            "coordonnateur" -> Nom_role.coordonnateur
            "employeur" -> Nom_role.employeur
            else -> throw Exception("Une erreur est survenue au mappage de l'état")
        }

    }

    fun mapToType (etatString:String?): Type {
        return when (etatString) {
            "CV" -> Type.CV
            "SUPPLEMENT" -> Type.SUPPLEMENT

            else -> throw Exception("Une erreur est survenue au mappage du type")
        }

    }
}