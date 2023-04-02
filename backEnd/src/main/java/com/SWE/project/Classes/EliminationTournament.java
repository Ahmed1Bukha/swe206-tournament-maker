package com.SWE.project.Classes;

import java.util.Date;

import com.SWE.project.Enums.TOURNAMENT_TYPES;

public class EliminationTournament extends Tournament {

    EliminationTournament(String tournament_name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        super(tournament_name, startDate, endDate, timeBetweenStages, tournamentType);
    }

}
