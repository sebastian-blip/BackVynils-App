package com.example.vinyls

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.test.*

@RunWith(AndroidJUnit4::class)
class CrearAlbumScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCrearAlbumCamposYBoton() {
        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Albumes").fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithContentDescription("addAlbumButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Álbum").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Nombre").performTextInput("Volumen 2.0")
        composeTestRule.onNodeWithText("URL de la portada").performTextInput("https://comodibujar.club/wp-content/uploads/2019/04/oso-panda-kawaii-1.jpg")
        composeTestRule.onNodeWithText("Fecha de lanzamiento (YYYY-MM-DDTHH:MM:SS-05:00)").performTextInput("Fecha de lanzamiento (2025-02-'02':30:15-05:00)")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Álbum 1")
        composeTestRule.onNodeWithText("Genero").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Rock").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Rock").performClick()
        composeTestRule.onNodeWithText("Sello discográfico").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Sony Music").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Sony Music").performClick()
        composeTestRule.onNodeWithText("Crear Álbum").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("✅ Álbum creado exitosamente").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("✅ Álbum creado exitosamente")
            .assertExists()
    }
}