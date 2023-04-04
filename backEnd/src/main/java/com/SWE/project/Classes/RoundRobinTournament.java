package com.SWE.project.Classes;

import com.SWE.project.Enums.TOURNAMENT_TYPES;

public class RoundRobinTournament extends Tournament {

    RoundRobinTournament(String tournament_name, long startDate, long endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        super(tournament_name, startDate, endDate, timeBetweenStages, tournamentType);
    }

}
