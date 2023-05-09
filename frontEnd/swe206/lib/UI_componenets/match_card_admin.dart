import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';

import '../home_admin/match_page_admin.dart';

class MatchCardAdmin extends StatelessWidget {
  const MatchCardAdmin(
      {required this.id,
      required this.participantA,
      required this.participantB,
      required this.game,
      required this.date,
      required this.scoreA,
      required this.scoreB,
      required this.title,
      required this.endDate,
      required this.finished,
      super.key});
  final int id;
  final String participantA;
  final String participantB;
  final String game;
  final String date;
  final int scoreA;
  final int scoreB;
  final String title;
  final bool endDate;
  final bool finished;
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => MatchPageAdmin(this),
          ),
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
                  "${participantA} vs ${participantB} ",
                  style: const TextStyle(fontSize: 30),
                ),
              ),
              Text("Game: $game"),
              Text("Date: $date"),
            ],
          ),
        ),
      ),
    );
  }
}