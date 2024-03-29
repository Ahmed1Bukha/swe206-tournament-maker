import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/const.dart';
import 'package:swe206/UI_componenets/match_card_admin.dart';
import 'package:swe206/home_admin/modify_pages/modify_page_match.dart';

class MatchPageAdmin extends StatelessWidget {
  MatchPageAdmin(this.matchCardAdmin, {super.key});
  MatchCardAdmin matchCardAdmin;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        backgroundColor: Colors.white,
        iconTheme: IconThemeData(color: Colors.black),
        title: Text(
          "Match page",
          style: h2,
        ),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Center(
            child: Text(
              "Participant:\n${matchCardAdmin.participantA} vs ${matchCardAdmin.participantB}",
              style: h4,
              textAlign: TextAlign.center,
            ),
          ),
          TextButton(
            onPressed: () {
              Navigator.pushReplacement(
                  context,
                  MaterialPageRoute(
                    builder: (context) => ModifyMatchPage(matchCardAdmin),
                  ));
            },
            child: Text("Edit match"),
          )
        ],
      ),
    );
  }
}
