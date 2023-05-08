import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/match_card_admin.dart';

import '../home_admin/tournament_page_admin.dart';

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
  final Map<String, List<MatchCardAdmin>> matches;
  final bool isOpen;
  final bool isFinished;
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => TournamentPageAdmin(this)),
        );
      },
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Container(
          width: MediaQuery.of(context).size.width - 20,
          decoration: BoxDecoration(
            border: Border.all(color: Colors.black),
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Container(
                width: MediaQuery.of(context).size.width - 20,
                decoration: const BoxDecoration(color: Color(0xff9AB6FF)),
                child: Text(
                  textAlign: TextAlign.center,
                  title,
                  style: const TextStyle(fontSize: 30),
                ),
              ),
              Text("Game: $game"),
              Text("Tournament type: $type"),
              Text("Current status: $status")
            ],
          ),
        ),
      ),
    );
  }
}
