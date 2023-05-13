import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:flutter_svg/svg.dart';

import '../home_admin/match_page_admin.dart';
import 'const.dart';

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
  final String endDate;
  final bool finished;

  @override
  Widget build(BuildContext context) {
    Color backGroundColor =
        Colors.white; // TODO: Change color when due it today.
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
        child: PhysicalModel(
          borderRadius: BorderRadius.circular(20),
          color: backGroundColor,
          elevation: 15,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Center(
                    child: ClipRRect(
                        child: SvgPicture.asset(
                      "lib/assets/svg/score.svg",
                    )),
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
                  "EndDate: $endDate",
                  style: h4,
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
