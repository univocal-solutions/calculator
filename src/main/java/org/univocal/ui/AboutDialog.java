package org.univocal.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.univocal.models.CalculatorSettings;

import java.awt.Desktop;
import java.net.URI;

/**
 * About dialog showing application information.
 * Displays version, author, and license information.
 *
 * @author Calculator Team
 * @version 1.0
 */
public class AboutDialog {

    private final Stage parentStage;
    private final CalculatorSettings settings;
    private Stage dialogStage;

    // Application information
    private static final String APP_NAME = "Univocal Calculator";
    private static final String APP_VERSION = "1.0.0";
    private static final String APP_AUTHOR = "Univocal Development Team";
    private static final String APP_YEAR = "2025";
    private static final String APP_LICENSE = "MIT License";
    private static final String APP_WEBSITE = "https://www.univocal.org";
    private static final String APP_GITHUB = "https://github.com/univocal/javafx-calculator";

    /**
     * Creates a new about dialog.
     */
    public AboutDialog(Stage parentStage, CalculatorSettings settings) {
        this.parentStage = parentStage;
        this.settings = settings;
    }

    /**
     * Shows the about dialog.
     */
    public void show() {
        if (dialogStage != null && dialogStage.isShowing()) {
            dialogStage.toFront();
            return;
        }

        createDialog();
        dialogStage.show();
    }

    /**
     * Creates the about dialog window.
     */
    private void createDialog() {
        dialogStage = new Stage();
        dialogStage.setTitle("About " + APP_NAME);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setResizable(false);

        VBox mainLayout = createMainLayout();
        Scene scene = new Scene(mainLayout, 400, 350);

        // Apply current theme
        applyThemeToDialog(scene);

        dialogStage.setScene(scene);
        dialogStage.centerOnScreen();
    }

    /**
     * Creates the main layout for the about dialog.
     */
    private VBox createMainLayout() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        // App icon (placeholder - you can replace with actual icon)
        Label iconLabel = new Label("🧮");
        iconLabel.setStyle("-fx-font-size: 48px;");

        // App name
        Label nameLabel = new Label(APP_NAME);
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Version
        Label versionLabel = new Label("Version " + APP_VERSION);
        versionLabel.setStyle("-fx-font-size: 14px; -fx-opacity: 0.8;");

        // Description
        Label descriptionLabel = new Label("A modern, cross-platform calculator\nbuilt with JavaFX");
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-alignment: center;");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(300);

        // Author and year
        Label authorLabel = new Label("© " + APP_YEAR + " " + APP_AUTHOR);
        authorLabel.setStyle("-fx-font-size: 12px; -fx-opacity: 0.7;");

        // License
        Label licenseLabel = new Label("Licensed under " + APP_LICENSE);
        licenseLabel.setStyle("-fx-font-size: 12px; -fx-opacity: 0.7;");

        // Links
        VBox linksBox = createLinksSection();

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(100);
        closeButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        closeButton.setOnAction(e -> dialogStage.close());

        layout.getChildren().addAll(
                iconLabel,
                nameLabel,
                versionLabel,
                descriptionLabel,
                authorLabel,
                licenseLabel,
                linksBox,
                closeButton
        );

        return layout;
    }

    /**
     * Creates the links section with website and GitHub links.
     */
    private VBox createLinksSection() {
        VBox linksBox = new VBox(5);
        linksBox.setAlignment(Pos.CENTER);

        // Website link
        Hyperlink websiteLink = new Hyperlink("Visit Website");
        websiteLink.setOnAction(e -> openURL(APP_WEBSITE));

        // GitHub link
        Hyperlink githubLink = new Hyperlink("View on GitHub");
        githubLink.setOnAction(e -> openURL(APP_GITHUB));

        linksBox.getChildren().addAll(websiteLink, githubLink);
        return linksBox;
    }

    /**
     * Opens a URL in the default browser.
     */
    private void openURL(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            // Fallback: could show a dialog with the URL to copy
            System.err.println("Could not open URL: " + url);
        }
    }

    /**
     * Applies the current theme to the dialog.
     */
    private void applyThemeToDialog(Scene scene) {
        String backgroundColor = settings.isDarkTheme() ? "#2c3e50" : "#ffffff";
        String textColor = settings.isDarkTheme() ? "#ecf0f1" : "#2c3e50";

        scene.getRoot().setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: %s;",
                backgroundColor, textColor
        ));
    }
}