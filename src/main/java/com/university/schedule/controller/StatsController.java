package com.university.schedule.controller;

import com.university.schedule.Utils.CycleStats;
import com.university.schedule.model.Cycle;
import com.university.schedule.model.Schedule;
import com.university.schedule.service.ScheduleService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class StatsController extends AbstractController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private TableView<CycleStats> statsTable;

    @FXML
    private TableColumn<CycleStats, String> cycleCol;

    @FXML
    private TableColumn<CycleStats, Integer> percentCol;

    @Autowired
    private ScheduleService scheduleService;

    private List<CycleStats> cycleStats = new ArrayList<>();

    private void initCols() {
        cycleCol.setCellValueFactory(new PropertyValueFactory<>("cycle"));
        percentCol.setCellValueFactory(new PropertyValueFactory<>("percent"));
    }

    private void loadData() {
        cycleStats.clear();

        System.out.println(groups.getGroupNo());
        Map<Cycle, Integer> stats = scheduleService.countSubjects(groups);
        int sum = 0;
        for (Map.Entry<Cycle, Integer> entry : stats.entrySet()) {
            sum += entry.getValue();
        }

        for (Map.Entry<Cycle, Integer> entry : stats.entrySet()) {
            cycleStats.add(new CycleStats(entry.getKey(), entry.getValue(), new Double(entry.getValue())/sum*100));
        }
        statsTable.getItems().setAll(cycleStats);
    }

    private void showPie(){
        for(CycleStats cs: cycleStats){
            PieChart.Data slice = new PieChart.Data(cs.getCycle().getName(), cs.getCount());
            pieChart.getData().add(slice);
        }
        pieChart.setLegendSide(Side.BOTTOM);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCols();
        loadData();
        showPie();
    }
}
