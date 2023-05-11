import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/const.dart';
import 'package:swe206/home_student/tournament_page_student.dart';

class TournamentCardStudent extends StatelessWidget {
  const TournamentCardStudent(this.title, this.game, this.type, this.status,
      this.based, this.id, this.startDate, this.endDate,
      {this.numberOfParticipant = 1, super.key});
  final String title;
  final String game;
  final String type;
  final String status;
  final String based;
  final int numberOfParticipant;
  final int id;
  final String startDate;
  final String endDate;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => TournamentPage(this)),
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
                  "Current status: $status",
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
