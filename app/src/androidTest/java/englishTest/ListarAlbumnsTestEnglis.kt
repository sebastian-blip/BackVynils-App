package englishTest

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinyls.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class ListarAlbumnsTestEnglis {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun albumListScreen_displaysNewCreatedAlbum() {
        val randomAlbumName = "Álbum " + UUID.randomUUID().toString().take(6)

        // Continuar a vista principal
        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("Albumes").fetchSemanticsNodes().isNotEmpty()
        }

        // Ir a pantalla de creación
        composeTestRule.onNodeWithContentDescription("addAlbumButton").performClick()
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithContentDescription("Create Album").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithContentDescription("Create Album").performClick()

        // Esperar a que cargue la pantalla
        composeTestRule.waitUntil(10_000) {
            composeTestRule.onAllNodesWithText("Create Album").fetchSemanticsNodes().isNotEmpty()
        }

        // Llenar campos
        composeTestRule.onNodeWithText("Name").performTextInput(randomAlbumName)
        composeTestRule.onNodeWithText("Cover URL").performTextInput("https://comodibujar.club/wp-content/uploads/2019/04/oso-panda-kawaii-1.jpg")
        composeTestRule.onNodeWithText("Release date (YYYY-MM-DD)").performTextInput("2025-02-02")
        composeTestRule.onNodeWithText("Description").performTextInput("Álbum 1")

        // Scroll para desplegables
        composeTestRule.onNode(hasScrollAction()).performTouchInput { swipeUp() }

        // Seleccionar género
        composeTestRule.onNodeWithTag("openDropdowGen").performClick()
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithTag("menuItem_Rock").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("menuItem_Rock").performClick()
        composeTestRule.onNodeWithText("Rock").assertExists()

        // Seleccionar record label
        composeTestRule.onNodeWithTag("openDropdowRecor").performClick()
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithTag("menuItem_Sony Music").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("menuItem_Sony Music").performClick()
        composeTestRule.onNodeWithText("Sony Music").assertExists()

        // Crear álbum
        composeTestRule.onNodeWithTag("botonCrearAlbum").performClick()


        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithContentDescription("Create Album")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        var found = false
        repeat(100) { attempt ->
            composeTestRule.waitForIdle()

            val exists = composeTestRule.onAllNodesWithText(randomAlbumName).fetchSemanticsNodes().isNotEmpty()
            if (exists) {
                found = true
                return@repeat
            }

            try {

                val nextButton = composeTestRule.onNodeWithTag("boton_siguiente")
                nextButton.assertIsEnabled()
                nextButton.performClick()
            } catch (_: AssertionError) {

                return@repeat
            }

            composeTestRule.waitForIdle()
        }

        if (found) {
            composeTestRule.onNodeWithText(randomAlbumName).assertExists()
        } else {

            throw AssertionError("El álbum no se encontró después de 10 intentos.")
        }
    }
}