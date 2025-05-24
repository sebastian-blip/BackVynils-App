package com.example.vinyls

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinyls.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class DetalleAlbumTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navegarADetalleYVerificarTitulo() {

        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Albumes").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("Ir a sección Albumes").performClick()

        // Espera a que cargue la lista de álbumes
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("A Night at the Opera").fetchSemanticsNodes().isNotEmpty()
        }

        // Clic en un álbum
        composeTestRule.onNodeWithText("A Night at the Opera").performClick()

        // Verifica que el título del álbum está presente en la vista de detalle
        composeTestRule.onNodeWithText("A Night at the Opera", ignoreCase = true)
            .assertIsDisplayed()
    }

    @Test
    fun cambiarAPestanaGeneroYVerificarTexto() {

        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Albumes").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("Ir a sección Albumes").performClick()

        // Espera a que cargue la lista de álbumes
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("A Night at the Opera").fetchSemanticsNodes().isNotEmpty()
        }

        // Clic en un álbum
        composeTestRule.onNodeWithText("A Night at the Opera").performClick()

        // Cambiar a la pestaña "Género musical"
        composeTestRule.onNodeWithText("Género musical").performClick()

        // Verificar que se muestre "Rock"
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Rock", ignoreCase = true).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Rock", ignoreCase = true).assertIsDisplayed()

    }

}