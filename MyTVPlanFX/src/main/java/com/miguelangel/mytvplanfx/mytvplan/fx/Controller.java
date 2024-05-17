package com.miguelangel.mytvplanfx.mytvplan.fx;


import com.miguelangel.mytvplanfx.model.BaseResponse;
import com.miguelangel.mytvplanfx.model.Video;
import com.miguelangel.mytvplanfx.model.VideoResponse;
import com.miguelangel.mytvplanfx.services.DeleteVideo;
import com.miguelangel.mytvplanfx.services.GetVideos;
import com.miguelangel.mytvplanfx.services.PostVideo;
import com.miguelangel.mytvplanfx.services.PutVideo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.miguelangel.mytvplanfx.utils.Messages.ShowError;
import static com.miguelangel.mytvplanfx.utils.Messages.ShowMessage;

public class Controller implements Initializable {
    @FXML
    private TextField tfTitle;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<String> cbPlatform;
    @FXML
    private ComboBox<String> cbCategory;
    @FXML
    private ComboBox<Integer> cbRating;
    @FXML
    private ComboBox<String> cbFilterType;
    @FXML
    private ComboBox<String> cbFilterPlatform;
    @FXML
    private ComboBox<String> cbFilterCategory;
    @FXML
    private ComboBox<String> cbFilterRating;
    @FXML
    private ListView<Video> videoList;
    private ObservableList<Video> videos;
    private Video selectedVideo = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectVideos("");
        initFields();
    }

    public void onClick_SaveVideo(ActionEvent actionEvent) {
        if (!tfTitle.getText().isEmpty()) {
            saveVideo(obtainVideoFromFields());
            clearFields();
        } else {
            ShowError("A title must be written");
        }
    }

    public void onClick_UpdateVideo(ActionEvent actionEvent) {
        if (tfTitle.getText().isEmpty()) {
            ShowError("A title must be written");
        } else if (selectedVideo != null) {
            Video videoUpdate = obtainVideoFromFields();
            videoUpdate.setId(selectedVideo.getId());
            updateVideo(videoUpdate);
            clearFields();

        } else {
            ShowError("A video must be selected from the list");
        }
    }

    public void onClick_DeleteVideo(ActionEvent actionEvent) {
        if (selectedVideo != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Video");
            alert.setHeaderText("Delete the video: " + selectedVideo.title);
            alert.setContentText("Are you sure with this?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Video videoDelete = obtainVideoFromFields();
                    videoDelete.setId(selectedVideo.getId());
                    deleteVideo(videoDelete);
                    clearFields();
                }
            });
        } else {
            ShowError("A video must be selected from the list");
        }
    }

    //CRUD SERVICES
    private void selectVideos(String filter) {
        GetVideos response = new GetVideos(filter);
        response.start();

        response.setOnSucceeded(e -> {
            VideoResponse videoResponse = response.getValue();

            if (videoResponse.getOk()) {
                videos = FXCollections.observableArrayList(videoResponse.getResult());
                videoList.setItems(videos);
            } else {
                if (!filter.isEmpty()) {
                    ShowError("No videos found");
                } else {
                    ShowError(videoResponse.getError());
                }

            }
        });

        response.setOnFailed(e -> {
            ShowError("Failed to get videos.");
        });
    }

    private void saveVideo(Video video) {
        PostVideo response = new PostVideo(video);
        response.start();

        response.setOnSucceeded(e -> {
            BaseResponse baseResponse = response.getValue();

            if (baseResponse.getOk()) {
                ShowMessage("Video saved successfully");
                selectVideos("");
            } else {
                ShowError(baseResponse.getError());
            }
        });

        response.setOnFailed(e -> {
            ShowError("Failed to save the video.");
        });
    }

    private void updateVideo(Video video) {
        PutVideo response = new PutVideo(video);
        response.start();

        response.setOnSucceeded(e -> {
            BaseResponse baseResponse = response.getValue();

            if (baseResponse.getOk()) {
                ShowMessage("Video updated successfully");
                selectVideos("");
            } else {
                ShowError(baseResponse.getError());
            }
        });

        response.setOnFailed(e -> {
            ShowError("Failed to update the video.");
        });
    }

    private void deleteVideo(Video video) {
        DeleteVideo response = new DeleteVideo(video);
        response.start();

        response.setOnSucceeded(e -> {
            BaseResponse baseResponse = response.getValue();

            if (baseResponse.getOk()) {
                ShowMessage("Video deleted successfully");
                selectVideos("");
            } else {
                ShowError(baseResponse.getError());
            }
        });

        response.setOnFailed(e -> {
            ShowError("Failed to delete the video.");
        });
    }

    public void onClick_FillInForm(MouseEvent mouseEvent) {
        selectedVideo = videoList.getSelectionModel().getSelectedItem();

        if (selectedVideo != null) {
            tfTitle.setText(selectedVideo.getTitle());
            cbCategory.getSelectionModel().select(selectedVideo.getCategory());
            cbType.getSelectionModel().select(selectedVideo.getType());
            cbRating.getSelectionModel().select(selectedVideo.getRating());
            cbPlatform.getSelectionModel().select(selectedVideo.getPlatform());
        }

    }

    public void onClick_FilterRating(ActionEvent actionEvent) {
        if (cbFilterRating.getValue() != null) {
            deselectComboBoxes(cbFilterRating);
            String filter = "";

            String selectedOption = String.valueOf(cbFilterRating.getSelectionModel().getSelectedItem());
            if (!selectedOption.equals("Show all")) {
                filter = "rating/" + selectedOption;
            }
            selectVideos(filter);
        }
    }

    public void onClick_FilterCategory(ActionEvent actionEvent) {
        if (cbFilterCategory.getValue() != null) {
            deselectComboBoxes(cbFilterCategory);

            String filter = "";
            String selectedOption = cbFilterCategory.getSelectionModel().getSelectedItem();
            if (!selectedOption.equals("Show all")) {
                filter = "category/" + selectedOption;
            }
            selectVideos(filter);
        }
    }

    public void onClick_FilterPlatform(ActionEvent actionEvent) {
        if (cbFilterPlatform.getValue() != null) {
            deselectComboBoxes(cbFilterPlatform);

            String filter = "";
            String selectedOption = cbFilterPlatform.getSelectionModel().getSelectedItem();
            if (!selectedOption.equals("Show all")) {
                filter = "platform/" + selectedOption;
            }
            selectVideos(filter);
        }
    }

    public void onClick_FilterType(ActionEvent actionEvent) {
        if (cbFilterType.getValue() != null) {
            deselectComboBoxes(cbFilterType);

            String filter = "";
            String selectedOption = cbFilterType.getSelectionModel().getSelectedItem();

            if (!selectedOption.equals("Show all")) {
                filter = "type/" + selectedOption;
            }
            selectVideos(filter);
        }
    }

    private void initFields() {
        cbFilterType.setItems(FXCollections.observableArrayList("Show all", "movie", "series", "mini-series"));
        cbType.setItems(FXCollections.observableArrayList("movie", "series", "mini-series"));
        cbFilterPlatform.setItems(FXCollections.observableArrayList("Show all", "Netflix", "HBO", "Disney+", "TV"));
        cbPlatform.setItems(FXCollections.observableArrayList("Netflix", "HBO", "Disney+", "TV"));
        cbFilterCategory.setItems(FXCollections.observableArrayList("Show all", "comedy", "thriller", "adventures", "other"));
        cbCategory.setItems(FXCollections.observableArrayList("comedy", "thriller", "adventures", "other"));
        cbFilterRating.setItems(FXCollections.observableArrayList("Show all", "0", "1", "2", "3", "4", "5"));
        cbRating.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5));

        cbType.getSelectionModel().select(0);
        cbPlatform.getSelectionModel().select(0);
        cbCategory.getSelectionModel().select(0);
        cbRating.getSelectionModel().select(0);
    }

    private void deselectComboBoxes(ComboBox<String> comboBoxSelected) {
        if (comboBoxSelected != cbFilterType) {
            cbFilterType.getSelectionModel().clearSelection();
        }
        if (comboBoxSelected != cbFilterPlatform) {
            cbFilterPlatform.getSelectionModel().clearSelection();
        }
        if (comboBoxSelected != cbFilterCategory) {
            cbFilterCategory.getSelectionModel().clearSelection();
        }
        if (comboBoxSelected != cbFilterRating) {
            cbFilterRating.getSelectionModel().clearSelection();
        }
    }

    private Video obtainVideoFromFields() {
        String title = tfTitle.getText();
        String type = cbType.getValue();
        String category = cbCategory.getValue();
        String platform = cbPlatform.getValue();
        int rating = cbRating.getValue();

        return new Video(title, type, platform, category, rating);
    }

    private void clearFields() {
        deselectComboBoxes(new ComboBox<>());
        tfTitle.setText(null);
    }
}