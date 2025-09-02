package org.univocal.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.univocal.models.CalculatorSettings;

/**
 * Settings dialog for calculator preferences.
 * Allows users to change theme and view app information.
 *
 * @author Calculator Team
 * @version 1.0
 */
public class SettingsDialog {

    private final Stage parentStage;
    private final CalculatorSettings settings;
    private final ThemeChangeListener themeChangeListener;

    private Stage dialogStage;
    private ComboBox<CalculatorSettings.Theme> themeComboBox;

    /**
     * Interface for handling theme changes.
     */
    public interface ThemeChangeListener {
        void onThemeChanged(CalculatorSettings.Theme newTheme);
    }

    /**
     * Creates a new settings dialog.
     */
    public SettingsDialog(Stage parentStage, CalculatorSettings settings, ThemeChangeListener themeChangeListener) {
        this.parentStage = parentStage;
        this.settings = settings;
        this.themeChangeListener = themeChangeListener;
    }

    /**
     * Shows the settings dialog.
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
     * Creates the settings dialog window.
     */
    private void createDialog() {
        dialogStage = new Stage();
        dialogStage.setTitle("Settings");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setResizable(false);

        VBox mainLayout = createMainLayout();
        Scene scene = new Scene(mainLayout, 350, 300);

        // Apply current theme to dialog
        applyThemeToDialog(scene);

        dialogStage.setScene(scene);
        dialogStage.centerOnScreen();
    }

    /**
     * Creates the main layout for the dialog.
     */
    private VBox createMainLayout() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.TOP_CENTER);

        // Dialog title
        Label titleLabel = new Label("Calculator Settings");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Theme selection section
        VBox themeSection = createThemeSection();

        // Separator
        Separator separator = new Separator();

        // About section
        VBox aboutSection = createAboutSection();

        // Buttons
        HBox buttonSection = createButtonSection();

        layout.getChildren().addAll(
                titleLabel,
                themeSection,
                separator,
                aboutSection,
                buttonSection
        );

        return layout;
    }

    /**
     * Creates the theme selection section.
     */
    private VBox createThemeSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER_LEFT);

        Label themeLabel = new Label("Theme:");
        themeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        themeComboBox = new ComboBox<>();
        themeComboBox.getItems().addAll(CalculatorSettings.Theme.values());
        themeComboBox.setValue(settings.getTheme());
        themeComboBox.setPrefWidth(200);

        // Handle theme changes
        themeComboBox.setOnAction(e -> {
            CalculatorSettings.Theme selectedTheme = themeComboBox.getValue();
            if (selectedTheme != null && selectedTheme != settings.getTheme()) {
                settings.setTheme(selectedTheme);
                themeChangeListener.onThemeChanged(selectedTheme);
                applyThemeToDialog(dialogStage.getScene());
            }
        });

        section.getChildren().addAll(themeLabel, themeComboBox);
        return section;
    }

    /**
     * Creates the about section.
     */
    private VBox createAboutSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);

        Label aboutLabel = new Label("About");
        aboutLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        Button aboutButton = new Button("About Calculator");
        aboutButton.setStyle("-fx-pref-width: 200px;");
        aboutButton.setOnAction(e -> showAboutDialog());

        section.getChildren().addAll(aboutLabel, aboutButton);
        return section;
    }

    /**
     * Creates the button section.
     */
    private HBox createButtonSection() {
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button okButton = new Button("OK");
        okButton.setPrefWidth(80);
        okButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        okButton.setOnAction(e -> dialogStage.close());

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(80);
        cancelButton.setOnAction(e -> dialogStage.close());

        buttonBox.getChildren().addAll(okButton, cancelButton);
        return buttonBox;
    }

    /**
     * Shows the about dialog.
     */
    private void showAboutDialog() {
        AboutDialog aboutDialog = new AboutDialog(dialogStage, settings);
        aboutDialog.show();
    }

    /**
     * Applies the current theme to the dialog.
     */
    private void applyThemeToDialog(Scene scene) {
        String backgroundColor = settings.isDarkTheme() ? "#2c3e50" : "#f8f9fa";
        String textColor = settings.isDarkTheme() ? "#ecf0f1" : "#2c3e50";

        scene.getRoot().setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: %s;",
                backgroundColor, textColor
        ));
    }
}