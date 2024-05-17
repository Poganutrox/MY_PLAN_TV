module com.miguelangel.mytvplanfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;


    opens com.miguelangel.mytvplanfx to javafx.fxml;
    exports com.miguelangel.mytvplanfx;
    exports com.miguelangel.mytvplanfx.model;
    opens com.miguelangel.mytvplanfx.model;
    exports com.miguelangel.mytvplanfx.mytvplan.fx;
    opens com.miguelangel.mytvplanfx.mytvplan.fx to javafx.fxml;
}