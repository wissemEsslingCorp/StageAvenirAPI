package com.crosemont.dti.g26.stageavenirapi

import com.crosemont.dti.g26.stageavenirapi.Modèle.*
import com.crosemont.dti.g26.stageavenirapi.Modèle.Enum.Etat
import java.time.LocalDate
import java.util.Date

class SourceDonnéesTests {
    companion object{
        val candidatures = mutableListOf(
            Candidature(
                1,
                Etat.EN_COURS,
                "Candidature en attente de traitement",
                OffreStage(
                    1,
                    "Poste cherché en Java",
                    "Programmeur analyste",
                    "Nous recherchons des programmeurs",
                    true,
                    LocalDate.now(),
                    true,
                    Etat.ACCEPTEE,
                    Entreprise(
                        1,
                        "Tech-Logiciel",
                        "629 rue d évolène",
                        "DescriptionEntreprise",
                        "logiciel",
                        Utilisateur(
                            "auth0|658265b0f82b42b3d4ebd08b",
                            "Doe",
                            "John",
                            "johndoe@dti.ca",
                            "9876543210",
                            Catégorie(2, "Réseau"),
                            Role(2, "employeur")
                        )
                    ),
                    Catégorie(1, "Programmation")
                ),
                Utilisateur(
                    "auth0|658258a0c15592e55505b4e5",
                    "Wissem",
                    "Benaraiba",
                    "wissem@example.com",
                    "0123456789",
                    Catégorie(1, "Programmation"),
                    Role(1, "etudiant")
                ),
                emptyList()
            ),
            // ... Ajoutez d'autres candidatures selon le même modèle
        )

        val offresDeStage = candidatures.map { it.offre }
    }
}