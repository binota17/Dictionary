import VoiceRegonizer.RegconizerExtention;
import edu.cmu.sphinx.api.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;


public class DictionaryAppController {
    private DictionaryCommandline DCL = new DictionaryCommandline();
    private Dictionary dictionary = DCL.getManagement().insertFromFile();
    private List<String> history = new ArrayList<>();

    @FXML
    private Text voiceMessage;

    @FXML
    private Text voiceMessage1;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button delButton;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField wordSearch;
    private String wSpeak = "";

    @FXML
    private TextField wordDel;

    @FXML
    private TextArea addWordExplain;

    @FXML
    private TextField addWordTarget;

    @FXML
    private Button addWordButton;

    @FXML
    private Button editWordButton;

    @FXML
    private TextField editWordEng;

    @FXML
    private TextArea editWordMeaning;

    @FXML
    private TextField wordEdit;

    private RegconizerExtention rec;

    @FXML
    private TextField message;

    @FXML
    private TextField voiceText;

    @FXML
    private Button voice;


    /**
     * hiện danh sách gợi ý từ bằng click chuột vào search.
     */
    public void suggestList(ActionEvent event) {
        voiceMessage.setText("");
        //update dictionary
        dictionary.getWords().clear();
        dictionary = DCL.getManagement().insertFromFile();
        // history
        history.add(0,wordSearch.getText());
        wSpeak = wordSearch.getText();
        // blank text field
        if (wordSearch.getText().trim() == "") {
            listView.getItems().clear();
            textArea.setText("");
            // reset speaker
            wSpeak = "";
        } else {
            // suggest words on list view
            listView.getItems().clear();
            List<String> suggestW = DCL.dictionarySearcher(wordSearch.getText(), dictionary);
            Collections.sort(suggestW);
            ObservableList<String> list = FXCollections.observableArrayList(suggestW);
            listView.setItems(list);
            // print explaination
            String explainString = DCL.getManagement().dictionaryLookup(wordSearch.getText(), dictionary).getWord_explain();
            String explain = "";
            while (explainString.contains("\t")) {
                explain += explainString.substring(0, explainString.indexOf("\t")) + "\n";
                explainString = explainString.substring(explainString.indexOf("\t") + 1);
            }
            explain += explainString;
            textArea.setText(explain);

            // list view selection
            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    textArea.clear();
                    if (newValue != null) {
                        wordSearch.setText(newValue);
                        // add to speaker
                        wSpeak = newValue;
                        // print explainaion
                        String explainString = DCL.getManagement().dictionaryLookup(newValue, dictionary).getWord_explain();
                        String explain = "";
                        while (explainString.contains("\t")) {
                            explain += explainString.substring(0, explainString.indexOf("\t")) + "\n";
                            explainString = explainString.substring(explainString.indexOf("\t") + 1);
                        }
                        explain += explainString;
                        textArea.setText(explain);
                    }
                }
            });
        }
    }

    /**
     * hiện danh sách gợi ý từ = bấm phím enter.
     * code gần tương tự như click chuột.
     */
    public void KeyCodeSuggest(ActionEvent event) {
        wordSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    voiceMessage.setText("");
                    // history
                    history.add(0,wordSearch.getText());
                    //update dictionary
                    dictionary.getWords().clear();
                    dictionary = DCL.getManagement().insertFromFile();
                    // blank textfield
                    if (wordSearch.getText().trim() == "") {
                        listView.getItems().clear();
                        textArea.setText("");
                        // reset word for speaker
                        wSpeak = "";
                    } else {
                        // suggest words
                        wSpeak = wordSearch.getText();
                        listView.getItems().clear();
                        List<String> suggestW = DCL.dictionarySearcher(wordSearch.getText(), dictionary);
                        Collections.sort(suggestW);
                        ObservableList<String> list = FXCollections.observableArrayList(suggestW);
                        listView.setItems(list);

                        // print explaination
                        String explainString = DCL.getManagement().dictionaryLookup(wordSearch.getText(), dictionary).getWord_explain();
                        String explain = "";
                        while (explainString.contains("\t")) {
                            explain += explainString.substring(0, explainString.indexOf("\t")) + "\n";
                            explainString = explainString.substring(explainString.indexOf("\t") + 1);
                        }
                        explain += explainString;
                        textArea.setText(explain);

                        // list view selection
                        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                textArea.clear();
                                if (newValue != null) {
                                    wordSearch.setText(newValue);
                                    //add to speaker
                                    wSpeak = newValue;
                                    String explainString = DCL.getManagement().dictionaryLookup(newValue, dictionary).getWord_explain();
                                    String explain = "";
                                    while (explainString.contains("\t")) {
                                        explain += explainString.substring(0, explainString.indexOf("\t")) + "\n";
                                        explainString = explainString.substring(explainString.indexOf("\t") + 1);
                                    }
                                    explain += explainString;
                                    textArea.setText(explain);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * phát âm English chuẩn 9.0.
     */
    public void clickSpeak(ActionEvent event) {
        voiceMessage.setText("");
        if(wSpeak.length() == 0) {
            DCL.getManagement().Speak("WHAT ARE U LOOKING FOR?");
        } else {
            DCL.getManagement().Speak(wSpeak);
        }
    }

    /**
     * mở cửa số xóa từ.
     */
    public void clickDel(ActionEvent event) {
        try {
            Parent rootDel = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/delWord.fxml")));
            Stage delWordStage = new Stage();
            delWordStage.setTitle("Delete Word");
            delWordStage.setScene(new Scene(rootDel));
            Image delIcon = new Image("image/delete.png");
            delWordStage.getIcons().add(delIcon);
            delWordStage.setResizable(false);
            delWordStage.initModality(Modality.APPLICATION_MODAL);
            delWordStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        voiceMessage.setText("");
    }

    /**
     * xóa từ.
     */
    public void delWord(ActionEvent event) {
        String d = wordDel.getText();
        Word wordD = DCL.getManagement().dictionaryLookup(d, dictionary);

        if (wordD.getWord_explain().equals("Word not found.")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Word not found: " + d);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are u sure bout that?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                DCL.getManagement().dictionaryDelete(d, dictionary);
                if (delButton != null) {
                    Stage stage = (Stage) delButton.getScene().getWindow();
                    stage.close();
                }
                DCL.getManagement().dictionaryExportToFile(dictionary);
            } else if (option.get() == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    /**
     * mở cửa số thêm từ mới.
     */
    public void clickAdd(ActionEvent event) {
        try {
            Parent rootAdd = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/addWord.fxml")));
            Stage addWordStage = new Stage();
            addWordStage.setScene(new Scene(rootAdd));
            addWordStage.setTitle("Add Word");
            Image addIcon = new Image("image/add.png");
            addWordStage.getIcons().add(addIcon);
            addWordStage.setResizable(false);
            addWordStage.initModality(Modality.APPLICATION_MODAL);
            addWordStage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        voiceMessage.setText("");
    }

    /**
     * thêm từ mới.
     */
    public void addWord(ActionEvent event) {
        String target = addWordTarget.getText().trim();
        String explain = addWordExplain.getText().trim();

        if (target.isEmpty() || explain.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: YOU MUST TYPE BOTH WORD AND MEANING.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are u sure bout that?");

            Optional<ButtonType> option = alert.showAndWait();
            if(option.get() == ButtonType.OK){
                DCL.getManagement().addWord(target, explain, dictionary);
                if (addWordButton != null) {
                    Stage stage = (Stage) addWordButton.getScene().getWindow();
                    stage.close();
                }
                DCL.getManagement().dictionaryExportToFile(dictionary);
            }
            else if(option.get() == ButtonType.CANCEL){
                alert.close();
            }
        }

    }

    /**
     * mở cửa số sửa từ.
     */
    public void clickEdit(ActionEvent event){
        try {
            Parent rootEdit = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/editWord.fxml")));
            Stage editWordStage = new Stage();
            editWordStage.setTitle("Edit Word");
            editWordStage.setResizable(false);
            editWordStage.setScene(new Scene(rootEdit));
            Image editIcon = new Image("image/edit.png");
            editWordStage.getIcons().add(editIcon);
            editWordStage.initModality(Modality.APPLICATION_MODAL);
            editWordStage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        voiceMessage.setText("");
    }

    /**
     * sửa từ.
     * có thể sửa từ hoặc nghĩa.
     */
    public void editWord(ActionEvent event) {
        Word wordE = DCL.getManagement().dictionaryLookup(wordEdit.getText(),dictionary);
        if (wordE.getWord_explain().equals("Word not found.")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: WORD NOT FOUND: " + wordEdit.getText());
            alert.show();
        } else if (editWordEng.getText().trim().isEmpty() && editWordMeaning.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: YOU MUST CHANGE AT LEAST ONE ATTRIBUTE!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are u sure bout that?");

            Optional<ButtonType> option = alert.showAndWait();
            if(option.get() == ButtonType.OK){
                if (!editWordEng.getText().trim().isEmpty()) {
                    wordE.setWord_target(editWordEng.getText().toLowerCase().trim());
                }
                if (!editWordMeaning.getText().trim().isEmpty()) {
                    wordE.setWord_explain((editWordMeaning.getText().trim()));
                }

                if (addWordButton != null) {
                    Stage stage = (Stage) editWordButton.getScene().getWindow();
                    stage.close();
                }
                DCL.getManagement().dictionaryExportToFile(dictionary);

            } else if(option.get() == ButtonType.CANCEL){
                alert.close();
            }

        }
    }

    /**
     * nhan dien giong noi.
     */
    public void clickVoice(ActionEvent event) throws Exception {
        Configuration config = new Configuration();
        config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        config.setDictionaryPath("src\\txt\\words.dic");
        config.setLanguageModelPath("src\\txt\\words.lm");
        rec = new RegconizerExtention(config);
        rec.startRecognition(true);
        String result = rec.getResult().getHypothesis();
        rec.stopRecognition();
        rec.closeRecognitionLine();
        listView.getItems().clear();
        textArea.clear();
        wSpeak = result;
        wordSearch.setText(result.toLowerCase());
        voiceMessage.setText("Click to try again.");
    }

    /**
     * lịch sử tra cứu.
     */
    public void history(ActionEvent event) {
        ObservableList<String> list = FXCollections.observableArrayList(history);
        listView.setItems(list);
    }
}
