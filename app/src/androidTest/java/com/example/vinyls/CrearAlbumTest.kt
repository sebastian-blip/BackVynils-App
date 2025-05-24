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


        composeTestRule.onNodeWithContentDescription("Ir a sección Albumes").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithContentDescription("Crear Álbum")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("Crear Álbum").performClick()


        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Álbum").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Nombre").performTextInput("Volumen 2.0")
        composeTestRule.onNodeWithText("URL de la portada").performTextInput("https://comodibujar.club/wp-content/uploads/2019/04/oso-panda-kawaii-1.jpg")
        composeTestRule.onNodeWithText("Fecha de lanzamiento (YYYY-MM-DD)").performTextInput("2025-02-02")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Álbum 1")
        composeTestRule
            .onNode(hasScrollAction())
            .performTouchInput {
                swipeUp()
            }


        composeTestRule.onNodeWithTag("openDropdowGen").performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("menuItem_Rock").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("menuItem_Rock").performClick()
        composeTestRule.onNodeWithText("Rock").assertExists()


        composeTestRule.onNodeWithTag("openDropdowRecor").performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("menuItem_Sony Music").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("menuItem_Sony Music").performClick()
        composeTestRule.onNodeWithText("Sony Music").assertExists()


        composeTestRule.onNodeWithTag("botonCrearAlbum").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("✅ Álbum creado exitosamente").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("✅ Álbum creado exitosamente")
            .assertExists()
    }
}