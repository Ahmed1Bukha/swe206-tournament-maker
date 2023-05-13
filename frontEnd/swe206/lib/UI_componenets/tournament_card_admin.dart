import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/match_card_admin.dart';

import '../home_admin/tournament_page_admin.dart';
import 'const.dart';

class TournamentCardAdmin extends StatelessWidget {
  const TournamentCardAdmin(
      {required this.id,
      required this.title,
      required this.game,
      required this.type,
      required this.status,
      required this.matches,
      required this.startDate,
      required this.endDate,
      required this.timeBetween,
      required this.tournamentBased,
      required this.isOpen,
      required this.isFinished,
      required this.studentPerTeam,
      super.key});
  final int id;
  final String title;
  final String game;
  final String type;
  final String status;
  final String startDate;
  final String endDate;
  final int timeBetween;
  final String tournamentBased;
  final List<dynamic> matches;
  final bool isOpen;
  final bool isFinished;
  final int studentPerTeam;
  @override
  Widget build(BuildContext context) {
    String statusTournament = status == "true" ? "Open for register" : "closed";

    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => TournamentPageAdmin(this)),
        );
      },
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: PhysicalModel(
          borderRadius: BorderRadius.circular(20),
          color: Colors.white,
          elevation: 15,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(20),
                    child: Image(
                      image: const AssetImage("lib/assets/img/football.jpg"),
                      width: MediaQuery.of(context).size.width - 50,
                    ),
                  ),
                ),
                Container(
                  width: MediaQuery.of(context).size.width - 20,
                  decoration: const BoxDecoration(
                    borderRadius:
                        BorderRadius.vertical(top: Radius.circular(20)),
                  ),
                  child: Text(
                    textAlign: TextAlign.left,
                    title,
                    style: h3,
                  ),
                ),
                const SizedBox(
                  height: 10,
                ),
                Text(
                  "Game: $game",
                  style: h4,
                ),
                Text(
                  "Tournament type: $type",
                  style: h4,
                ),
                Text(
                  "Current status: $statusTournament",
                  style: h4,
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
