package englishTest

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
import com.example.vinyls.MainActivity
import org.junit.Rule
import org.junit.Test

class CrearAlbumEnglishTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCrearAlbumCamposYBoton() {

        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Albumes").fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithContentDescription("addAlbumButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithContentDescription("Create Album")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("Create Album").performClick()


        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Create Album").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Name").performTextInput("Volumen 2.0")
        composeTestRule.onNodeWithText("Cover URL").performTextInput("https://comodibujar.club/wp-content/uploads/2019/04/oso-panda-kawaii-1.jpg")
        composeTestRule.onNodeWithText("Release date (YYYY-MM-DD)").performTextInput("2025-02-02")
        composeTestRule.onNodeWithText("Description").performTextInput("Álbum 1")
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