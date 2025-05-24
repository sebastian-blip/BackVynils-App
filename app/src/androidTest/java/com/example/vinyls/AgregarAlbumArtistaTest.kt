package com.example.vinyls

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4


import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AgregarAlbumArtistaTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun testAgregarAlbumArtista() {

        // Paso 1: Ir al home
        composeTestRule.onNodeWithText("Continue").performClick()

        // Paso 2: Esperar que se cargue el home
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Artistas").fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Ir a la lista de artistas
        composeTestRule.onNodeWithContentDescription("Ir a sección Artistas").performClick()

        // Paso 4: Esperar que aparezca el artista
        composeTestRule.waitUntil(timeoutMillis = 15_000) {
            composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna").fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 5: Ir al detalle (primer clickeable)
        composeTestRule
            .onAllNodesWithText("Rubén Blades Bellido de Luna")
            .filter(hasClickAction())
            .onFirst()
            .performClick()

        // Paso 6: Esperar que se cargue el detalle
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithContentDescription("Foto del artista").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("Álbumes")
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithTag("AddAlbumButton").fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithTag("AddAlbumButton").performScrollTo()
        composeTestRule.onNodeWithTag("AddAlbumButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Barra de Busqueda").fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithText("Poeta del pueblo").performClick()


        // Esperar que aparezca el diálogo
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithContentDescription("Botón aceptar para asociar álbum").fetchSemanticsNodes().isNotEmpty()
        }

        // Click en el botón de aceptar
        composeTestRule.onNodeWithContentDescription("Botón aceptar para asociar álbum").performClick()



    }


}